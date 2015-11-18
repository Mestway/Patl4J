package org.openfuxml.addon.chart.renderer.xy;

import java.awt.Paint;

import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.openfuxml.addon.chart.util.OfxCustomPaintColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxSplineRenderer extends XYSplineRenderer
{
	private static final long serialVersionUID = 1;
	
	final static Logger logger = LoggerFactory.getLogger(SplineChartRenderer.class);
	
	private OfxCustomPaintColors ofxPaintColors;
	
	public OfxSplineRenderer()
	{
	}
	
	public Paint getSeriesPaint(int series)
	{
		if(ofxPaintColors!=null)
		{
			return ofxPaintColors.getSeriesPaint(series);
		}
		else
		{
			return super.getSeriesPaint(series);
		}
	}
	
	public OfxCustomPaintColors getOfxPaintColors() {return ofxPaintColors;}
	public void setOfxPaintColors(OfxCustomPaintColors ofxPaintColors) {this.ofxPaintColors = ofxPaintColors;}

}
