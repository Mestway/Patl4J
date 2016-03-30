package org.openfuxml.addon.chart.interfaces;

import java.awt.Dimension;

import org.jfree.chart.JFreeChart;
import org.openfuxml.xml.addon.chart.Chart;

public interface ChartRenderer
{
	JFreeChart render(Chart ofxChart);
	Dimension getSuggestedSize();
}
