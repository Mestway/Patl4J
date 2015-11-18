package org.openfuxml.test.addon.chart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.OfxChartRenderer;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.Renderer;
import org.openfuxml.xml.addon.chart.DataSet;
import org.openfuxml.xml.addon.chart.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstOfxBarChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TstOfxBarChartRenderer.class);
	
	public TstOfxBarChartRenderer()
	{
		
	}
	
	public Chart getTimeSeries()
	{
		Chart chart = new Chart();
		chart.setLegend(true);
		
		chart.setRenderer(getType());
		
		chart.getDataSet().add(getX("a"));
//		chart.getContainer().add(getX("b"));
		return chart;
	}
	
	private Renderer getType()
	{
		Renderer type = new Renderer();
		Renderer.Bar tBar = new Renderer.Bar();
		tBar.setVertical(true);
		type.setBar(tBar);
		return type;
	}
	
	private DataSet getX(String label)
	{
		Random rnd = new Random();
		DataSet x = new DataSet();
		x.setLabel(label);
		for(int i=1;i<20;i++)
		{
			Data data = new Data();
			data.setY(rnd.nextInt(i));
			data.setCategory("cat"+rnd.nextInt(3));
			x.getData().add(data);
		}
		return x;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
			
		TstOfxBarChartRenderer test = new TstOfxBarChartRenderer();
		Chart chart = null;
		
		chart = test.getTimeSeries();
		JaxbUtil.debug(chart);
			
		OfxChartRenderer ofxRenderer = new OfxChartRenderer();
		JFreeChart jfreeChart = ofxRenderer.render(chart);
		
		OutputStream os = new FileOutputStream(new File("dist/chart.png"));
		ChartUtilities.writeChartAsPNG(os,jfreeChart,800,300);
	}
}
