package org.openfuxml.addon.wiki.media.chart;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.xmlgraphics.java2d.ps.EPSDocumentGraphics2D;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;
import org.openfuxml.addon.wiki.media.chart.factory.BarChartFactory;
import org.openfuxml.addon.wiki.util.WikiConfigChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(ChartRenderer.class);
	
	private MultiResourceLoader mrl;
	private Configuration config;
	
	private String chartName;
	
	public void setChartName(String chartName) {this.chartName = chartName;}

	private Ofxchart ofxChart;
	private int width,height;
	
	public ChartRenderer(Configuration config)
	{
		this.config=config;
		chartName="you-forgot-to-set-a-chart-name";
		mrl = new MultiResourceLoader();
		width = 400;
		height = 300;
	}
	
	public void loadChart(String fileName)
	{
		ofxChart =null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Ofxchart.class);
			Unmarshaller u = jc.createUnmarshaller();
			ofxChart = (Ofxchart)u.unmarshal(mrl.searchIs(fileName));
		}
		catch (JAXBException e) {logger.error("",e);}
		catch (FileNotFoundException e) {logger.error("",e);}
	}
	
	public void applyChart(Ofxchart ofxChart)
	{
		this.ofxChart=ofxChart;
	}
	
	public void render()
	{
		logger.debug(ofxChart.getType());
		BarChartFactory bcf = new BarChartFactory();
        JFreeChart chart = bcf.createChart(ofxChart);
        save(chart);
	}
	
	private void save(JFreeChart chart)
	{
		savePNG(chart);
		saveEPS(chart);
	}
	
	private void savePNG(JFreeChart chart)
	{
		File f = new File(config.getString("/ofx/dir[@type='image-web']")+"/"+chartName+".png");
		try
		{
			OutputStream os = new FileOutputStream(f);
			ChartUtilities.writeChartAsPNG(os,chart,width,height);
			os.close();
		}
		catch (FileNotFoundException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
	}
	
	private void saveEPS(JFreeChart chart)
	{	
		File f = new File(config.getString("/ofx/dir[@type='image-eps']")+"/"+chartName+".eps");
		try
		{
			EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);
	        g2d.setGraphicContext(new org.apache.xmlgraphics.java2d.GraphicContext());
			FileOutputStream out = new FileOutputStream(f);
			g2d.setupDocument(out, width, height);
	        chart.draw(g2d, new Rectangle2D.Double(0,0,width, height));
	        g2d.finish();
	        out.close();
		}
		catch (FileNotFoundException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/config/wiki/wiki.xml");
		Configuration config = ConfigLoader.init();
		WikiConfigChecker.check(config);	
			
		ChartRenderer cr = new ChartRenderer(config);	
		cr.loadChart("resources/data/timeline-ofxchart.xml");
		cr.render();
	}
}