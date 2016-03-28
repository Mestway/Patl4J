package org.openfuxml.test.xml.wiki.jaxb;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchartcontainer;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchartdata;

public class TestOfxChart
{
	final static Logger logger = LoggerFactory.getLogger(TestOfxChart.class);
	
	public TestOfxChart()
	{
		
	}
	
	public void xmlConstruct()
	{
		Ofxchart chart = new Ofxchart();
		chart.setType("bar");
		
		Ofxchartdata cs = new Ofxchartdata();
		cs.setType("x");
		cs.setValue(1924);
		
		Ofxchartcontainer ds = new Ofxchartcontainer();
		ds.getOfxchartdata().add(cs);
		ds.setType("dataset");
		
		Ofxchartcontainer s = new Ofxchartcontainer();
		s.getOfxchartcontainer().add(ds);
		s.setType("dataseries");
		
		chart.getOfxchartcontainer().add(s);
		
		JaxbUtil.debug(chart);
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestOfxChart test = new TestOfxChart();
			test.xmlConstruct();
	}
}