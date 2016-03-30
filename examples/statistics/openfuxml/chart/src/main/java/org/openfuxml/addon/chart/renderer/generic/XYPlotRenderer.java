package org.openfuxml.addon.chart.renderer.generic;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.openfuxml.addon.chart.util.AxisFactory;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.openfuxml.addon.chart.util.OfxChartTypeResolver;
import org.openfuxml.addon.chart.util.OfxChartTypeResolver.AxisOrientation;
import org.openfuxml.addon.chart.util.TimePeriodFactory.OfxChartTimePeriod;
import org.openfuxml.xml.addon.chart.Axis;
import org.openfuxml.xml.addon.chart.Grid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XYPlotRenderer extends AbstractChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(XYPlotRenderer.class);
	
	protected OfxChartTimePeriod ofxTimePeriod;
	
	@Override
	protected void setSpecialColors()
	{
		XYPlot plot = (XYPlot) chart.getPlot();
	    plot.setRangeGridlinePaint(ChartColorFactory.createColor(ofxChart, ChartColorFactory.Area.gridRange));
	    plot.setDomainGridlinePaint(ChartColorFactory.createColor(ofxChart, ChartColorFactory.Area.gridDomain));
	}
	
	@Override
	protected void setSpecialGrid()
	{
		XYPlot plot = (XYPlot) chart.getPlot();
		Grid grid = ofxChart.getGrid();
	    if(grid.isSetDomain()){plot.setDomainGridlinesVisible(grid.isDomain());}
	    if(grid.isSetRange()){plot.setRangeGridlinesVisible(grid.isRange());}
	}
	
	protected void setTimePeriod()
	{
		if(ofxChart.getRenderer().isSetRendererTimeseries() && ofxChart.getRenderer().getRendererTimeseries().isSetTimePeriod())
		{
			logger.debug("checking="+ofxChart.getRenderer().getRendererTimeseries().getTimePeriod());
			
			try
			{
				ofxTimePeriod = OfxChartTimePeriod.valueOf(ofxChart.getRenderer().getRendererTimeseries().getTimePeriod());
			}
			catch(IllegalArgumentException e)
			{
				logger.error("timePeriod "+ofxChart.getRenderer().getRendererTimeseries().getTimePeriod()+ " is not valid, using Hour as default");
				ofxTimePeriod = OfxChartTimePeriod.Hour;
			}
		}
		else if(ofxChart.getRenderer().isSetGantt() && ofxChart.getRenderer().getGantt().isSetTimePeriod())
		{
			ofxTimePeriod = OfxChartTimePeriod.valueOf(ofxChart.getRenderer().getGantt().getTimePeriod());
		}
		else
		{
			logger.warn("chart/charttype/timeseries/@timePeriod is not set!!");
			logger.warn("Using default: Hour");
			ofxTimePeriod=OfxChartTimePeriod.Hour;
		}
	}
	
	@Override
	protected void setAxis(Axis ofxAxis,AxisOrientation axisOrientation)
	{
		logger.info("Setting axis");
		JaxbUtil.trace(ofxAxis);
		ValueAxis axis=null;
		switch(OfxChartTypeResolver.getAxisType(ofxAxis.getAxisType()))
		{
			case Number: axis = AxisFactory.createNumberAxis(ofxAxis);break;
			case Date: axis = AxisFactory.createPeriodAxis(ofxAxis);break;
			default: 	logger.warn("You should specify a type, defaulting to number");
						axis = new NumberAxis();AxisFactory.labelAxisAxis(axis, ofxAxis);
		}
		
		if(axis!=null)
		{
			XYPlot plot = (XYPlot) chart.getPlot();
	        switch(AxisOrientation.valueOf(ofxAxis.getCode()))
	        {
	        	case domain: plot.setDomainAxis(axis);break;
	        	case range0:  plot.setRangeAxis(axis);break;
	        	default: logger.warn("NYI");
	        }
		}

	}
}