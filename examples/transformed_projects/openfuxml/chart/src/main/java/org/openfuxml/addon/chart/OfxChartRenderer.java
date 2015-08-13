package org.openfuxml.addon.chart;

import net.sf.exlp.util.xml.JDomUtil;

import org.dom4j.Document;

import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.interfaces.ChartRenderer;
import org.openfuxml.addon.chart.renderer.BarChartRenderer;
import org.openfuxml.addon.chart.renderer.TimeBarRenderer;
import org.openfuxml.addon.chart.renderer.gantt.GanttChartRenderer;
import org.openfuxml.addon.chart.renderer.timeseries.TimeSeriesChartRenderer;
import org.openfuxml.addon.chart.renderer.xy.SplineChartRenderer;
import org.openfuxml.addon.chart.util.OfxChartTypeResolver;
import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxChartRenderer.class);
	
	private ChartRenderer ofxRenderer;
	
	public OfxChartRenderer()
	{
	
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		OfxChartTypeResolver.Type chartType = OfxChartTypeResolver.getType(ofxChart.getRenderer());
		switch(chartType)
		{
			case TimeSeries: ofxRenderer = new TimeSeriesChartRenderer();break;
			case TimeBar:    ofxRenderer = new TimeBarRenderer();break;
			case Bar: 		 ofxRenderer = new BarChartRenderer();break;
			case Gantt:		 ofxRenderer = new GanttChartRenderer();break;
			case Spline:	 ofxRenderer = new SplineChartRenderer();break;
			default:	logger.warn("No Renderer available for "+chartType);
		}
		
		JFreeChart jfreeChart=ofxRenderer.render(ofxChart);
		return jfreeChart;
	}
	
	public JFreeChart render(Document doc) 
	{
		Chart ofxChart = (Chart)JDomUtil.toJaxb(doc, Chart.class);
		return render(ofxChart); 
	}
	
	public ChartRenderer getOfxRenderer() {return ofxRenderer;}
}
