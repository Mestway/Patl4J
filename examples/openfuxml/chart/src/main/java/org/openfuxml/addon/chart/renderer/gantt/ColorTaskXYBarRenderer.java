package org.openfuxml.addon.chart.renderer.gantt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.util.Map;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.xy.XYDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorTaskXYBarRenderer extends XYBarRenderer
{
	final static Logger logger = LoggerFactory.getLogger(ColorTaskXYBarRenderer.class);
	   
	private static final long serialVersionUID=1;
	protected XYDataset dataset;
	private Map<String,Color> colorMap;
	 
	public ColorTaskXYBarRenderer(Map<String,Color> colorMap)
	{
		this.colorMap=colorMap;
	}
	
	@Override
	public void drawItem(Graphics2D g2, XYItemRendererState state,
					Rectangle2D dataArea, PlotRenderingInfo info,  XYPlot plot,
					ValueAxis domainAxis,  ValueAxis rangeAxis,
					XYDataset dataset, int series,  int item,
					CrosshairState crosshairState, int pass)
	{
		this.dataset=dataset;
	    super.drawItem(g2, state, dataArea, info, plot,domainAxis, rangeAxis, dataset, series, item, crosshairState, pass);
	}
	    
	@Override
	public Paint getItemPaint(int row, int column)
	{
		Paint result;
		XYTaskDataset tds = (XYTaskDataset)dataset;
	    TaskSeriesCollection tsc = tds.getTasks();
	    TaskSeries ts = tsc.getSeries(row);
	    Task t = ts.get(column);
	    result = getCategoryPaint(t.getDescription());
	    return result;
	}
	   
	private Paint getCategoryPaint(String description)
	{
		Paint result = Color.black;
		if(colorMap.containsKey(description)){result = colorMap.get(description);}
		else{logger.warn("No color for "+description+". Using black!");}
	    return result;
	}
}