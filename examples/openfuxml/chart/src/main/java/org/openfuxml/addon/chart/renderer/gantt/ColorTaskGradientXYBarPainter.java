package org.openfuxml.addon.chart.renderer.gantt;

import org.jfree.chart.renderer.xy.GradientXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorTaskGradientXYBarPainter extends GradientXYBarPainter
{
	final static Logger logger = LoggerFactory.getLogger(ColorTaskGradientXYBarPainter.class);   
	
	private static final long serialVersionUID=1;
	 
	public ColorTaskGradientXYBarPainter()
	{
		super(0, 0.1, 0.9);
	}
	
	@Override
	public void paintBar(java.awt.Graphics2D g2,
            XYBarRenderer renderer,
            int row,
            int column,
            java.awt.geom.RectangularShape bar,
            org.jfree.ui.RectangleEdge base)
	{
//		logger.debug(row+"-"+column);
		super.paintBar(g2,renderer,row,column,bar,base);
	}
}