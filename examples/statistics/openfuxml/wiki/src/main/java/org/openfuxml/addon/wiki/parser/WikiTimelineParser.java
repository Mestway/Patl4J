package org.openfuxml.addon.wiki.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.sf.exlp.core.handler.EhResultContainer;
import net.sf.exlp.core.listener.LogListenerXml;
import net.sf.exlp.core.parser.AbstractLogParser;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.util.io.LoggerInit;

import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchartcontainer;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchartdata;
import org.openfuxml.addon.wiki.event.WikiChartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiTimelineParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(WikiTimelineParser.class);
	private static enum Section {none,bardata,plotdata};
	
	private Section section;
	private ArrayList<Pattern> alP,alPBarData,alPPlotData;
	
	private Ofxchart ofxChart;
	private StringBuffer sbParseLine;
	
	public WikiTimelineParser(LogEventHandler leh)
	{
		super(leh);
		alP=new ArrayList<Pattern>();
		alPBarData=new ArrayList<Pattern>();
		alPPlotData=new ArrayList<Pattern>();
		
		alP.add(Pattern.compile("BarData=(.*)"));
		alP.add(Pattern.compile("PlotData=(.*)"));
		
		alPBarData.add(Pattern.compile("[ ]*bar:(\\d*) text:([\\d\\w]*)(.*)"));
		
		alPPlotData.add(Pattern.compile("[ ]*bar:(\\d*) from:(\\d*) till: (\\d*)(.*)"));
		alPPlotData.add(Pattern.compile("[ ]*bar:(\\d*) at:(\\d*)(.*)"));
		alPPlotData.add(Pattern.compile("[ ]*color:(\\w*)(.*)"));
	}

	public void parseLine(String line)
	{
		sbParseLine.append(" parseLine:"+line);
		allLines++;
		boolean unknownPattern = true;
		for(int i=0;i<alP.size();i++)
		{
			Matcher m=alP.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: section = Section.bardata;sbParseLine.append(" new-BarData");break;
					case 1: section = Section.plotdata;
							Ofxchartcontainer ds = new Ofxchartcontainer();
							ds.setType("dataseries");
							ofxChart.getOfxchartcontainer().add(ds);
							sbParseLine.append(" new-PlotData");
							break;		
				}
				i=alP.size();
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			logger.warn("Unknown pattern: " +line);
			unknownLines++;
		}
	}
	
	private void parseBarData(String line)
	{
		sbParseLine.append(" parseBarData:"+line);
		boolean unknownPattern = true;
		for(int i=0;i<alPBarData.size();i++)
		{
			Matcher m=alPBarData.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: addChartData(m.group(1), m.group(2),"label");break;	
				}
				i=alPBarData.size();
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			sbParseLine.append(" UNKNOW ... parsing line!");
			section = Section.none;
			parseLine(line);
		}
	}
	
	private void addChartData(String x, String dataType, String containerType)
	{
		Ofxchartdata cs = new Ofxchartdata();
		cs.setType(dataType);
		cs.setValue(new Double(x));
		
		boolean labelContanerMissing=true;
		for(Ofxchartcontainer ofxChartContainer : ofxChart.getOfxchartcontainer())
		{
			if(ofxChartContainer.getType().equals(containerType))
			{
				ofxChartContainer.getOfxchartdata().add(cs);
				labelContanerMissing = false;
				break;
			}
		}
		if(labelContanerMissing)
		{
			Ofxchartcontainer cc = new Ofxchartcontainer();
			cc.getOfxchartdata().add(cs);
			cc.setType(containerType);
			ofxChart.getOfxchartcontainer().add(cc);
		}
	}
	
	private void parsePlotData(String line)
	{
		sbParseLine.append(" parsePlotData:"+line);
		boolean unknownPattern = true;
		for(int i=0;i<alPPlotData.size();i++)
		{
			Matcher m=alPPlotData.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: addPlotData(m);break;	
				}
				i=alPPlotData.size();
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			section = Section.none;
			parseLine(line);
		}
	}
	
	private void addPlotData(Matcher m)
	{
		Ofxchartcontainer ofxDataSet = new Ofxchartcontainer();
		ofxDataSet.setType("dataset");
		
		Ofxchartdata dX = new Ofxchartdata();
		dX.setType("X");
		dX.setValue(new Double(m.group(1)));
		ofxDataSet.getOfxchartdata().add(dX);
		
		Ofxchartdata dMinY = new Ofxchartdata();
		dMinY.setType("minY");
		dMinY.setValue(new Double(m.group(2)));
		ofxDataSet.getOfxchartdata().add(dMinY);
	
		Ofxchartdata dMaxY = new Ofxchartdata();
		dMaxY.setType("maxY");
		dMaxY.setValue(new Double(m.group(3)));
		ofxDataSet.getOfxchartdata().add(dMaxY);

		int index = ofxChart.getOfxchartcontainer().size()-1;
		ofxChart.getOfxchartcontainer().get(index).getOfxchartcontainer().add(ofxDataSet);
	}
	
	private void parse(String line)
	{
		sbParseLine = new StringBuffer();
		sbParseLine.append("Section:"+section);
		switch(section)
		{
			case none: parseLine(line);break;
			case bardata: parseBarData(line);break;
			case plotdata: parsePlotData(line);break;
		}
		logger.trace(sbParseLine.toString());
	}
	
	@Override
	public void parseItem(List<String> item)
	{
		ofxChart = new Ofxchart();
		ofxChart.setType("bar");
		
		section = Section.none;
		logger.debug("Item received with "+item.size()+" entries");
		for(String line : item){parse(line);}
//		debug();
		WikiChartEvent event = new WikiChartEvent(ofxChart);
		leh.handleEvent(event);
	}
	
	@SuppressWarnings("unused")
	private void debug()
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(Ofxchart.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
			m.marshal(ofxChart, System.out);
		}
		catch (JAXBException e) {logger.error("",e);}
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		LogEventHandler leh = new EhResultContainer();
		LogParser lp = new WikiTimelineParser(leh);
		LogListener ll = new LogListenerXml("resources/data/timeline.xml",lp);
		ll.processMulti("/wikiinjection/wikicontent");
		EhResultContainer results = (EhResultContainer)leh;
		logger.debug("Results: "+results.getAlResults().size());
		WikiChartEvent event = (WikiChartEvent)results.getAlResults().get(0);
		Ofxchart ofxChart = event.getOfxChart();
		
		try
		{
			JAXBContext context = JAXBContext.newInstance(Ofxchart.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
			m.marshal(ofxChart, new File("resources/data/timeline-ofxchart.xml"));
		}
		catch (JAXBException e) {logger.error("",e);}
	}
}