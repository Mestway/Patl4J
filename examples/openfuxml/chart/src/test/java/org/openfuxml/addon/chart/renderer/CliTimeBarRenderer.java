package org.openfuxml.addon.chart.renderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.OfxChartRenderer;
import org.openfuxml.addon.chart.test.OfxChartTestBootstrap;
import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.Data;
import org.openfuxml.xml.addon.chart.DataSet;
import org.openfuxml.xml.addon.chart.Grid;
import org.openfuxml.xml.addon.chart.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliTimeBarRenderer
{
	final static Logger logger = LoggerFactory.getLogger(CliTimeBarRenderer.class);
	
	public CliTimeBarRenderer()
	{
		
	}
	
	public Chart getTimeSeries()
	{
		Chart chart = new Chart();
		chart.setLegend(true);
		
		chart.setRenderer(getType());
		chart.setGrid(getGrid());
		
		chart.getDataSet().add(getX("a"));
		return chart;
	}
	
	private Grid getGrid()
	{
		Grid grid = new Grid();
		grid.setDomain(false);
		grid.setRange(false);
		return grid;
	}
	
	private Renderer getType()
	{
		Renderer type = new Renderer();
		Renderer.Timebar tBar = new Renderer.Timebar();
		tBar.setShadow(false);
		tBar.setGradient(false);
		type.setTimebar(tBar);
		return type;
	}
	
	private DataSet getX(String label)
	{
		Random rnd = new Random();
		DataSet x = new DataSet();
		x.setLabel(label);
		for(int i=1;i<5;i++)
		{
			Data data = new Data();
			data.setRecord(DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2010, 1, i)));
			data.setY(1+rnd.nextInt(i));
			if(rnd.nextInt(100)<70){x.getData().add(data);}
		}
		return x;
	}
	
	public static void main (String[] args) throws Exception
	{		
		OfxChartTestBootstrap.init();
		
		CliTimeBarRenderer test = new CliTimeBarRenderer();
		Chart chart = test.getTimeSeries();
		JaxbUtil.info(chart);
			
		OfxChartRenderer ofxRenderer = new OfxChartRenderer();
		JFreeChart jfreeChart = ofxRenderer.render(chart);
		
		OutputStream os = new FileOutputStream(new File("target/chart.png"));
		ChartUtilities.writeChartAsPNG(os,jfreeChart,800,300);
	}
}
