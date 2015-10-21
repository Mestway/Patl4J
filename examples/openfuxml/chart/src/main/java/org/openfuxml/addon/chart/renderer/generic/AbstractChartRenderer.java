package org.openfuxml.addon.chart.renderer.generic;

import java.awt.Dimension;

import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.openfuxml.addon.chart.util.OfxChartTypeResolver.AxisOrientation;
import org.openfuxml.xml.addon.chart.Axis;
import org.openfuxml.xml.addon.chart.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(AbstractChartRenderer.class);
	
	protected JFreeChart chart;
	protected Chart ofxChart;
	
	public AbstractChartRenderer()
	{
		
	}
	
	protected void setColors()
	{
		chart.setBackgroundPaint(ChartColorFactory.createColor(ofxChart, ChartColorFactory.Area.backgroundChart));
		chart.getPlot().setBackgroundPaint(ChartColorFactory.createColor(ofxChart, ChartColorFactory.Area.backgroundPlot));
		setSpecialColors();
	}
	protected void setSpecialColors(){logger.error("This should be @Overridden");}
	
	protected void setGrid()
	{
		 if(ofxChart.isSetGrid())
		 {
			 setSpecialGrid();
		 }
	}
	protected void setSpecialGrid(){logger.error("This should be @Overridden");}
	
	protected void setAxis()
	{
		for(Axis axis : ofxChart.getAxis())
		{
			AxisOrientation code = AxisOrientation.valueOf(axis.getCode());
			setAxis(axis,code);
		}
	}
	protected void setAxis(Axis axis,AxisOrientation type)
	{
		logger.error("This should be @Overridden");
	}
	
	public Dimension getSuggestedSize()
	{
		logger.error("This should be @Overridden");
		throw new UnsupportedOperationException();
	}
}