package org.openfuxml.addon.chart.renderer;

import java.awt.Dimension;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.openfuxml.addon.chart.interfaces.ChartRenderer;
import org.openfuxml.addon.chart.renderer.generic.AbstractChartRenderer;
import org.openfuxml.addon.chart.util.ChartLabelResolver;
import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.DataSet;
import org.openfuxml.xml.addon.chart.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarChartRenderer extends AbstractChartRenderer implements ChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(BarChartRenderer.class);
	
	public BarChartRenderer()
	{
		logger.debug("Active");
	}
	
	public JFreeChart render(Chart ofxChart)
	{
        JFreeChart chart = ChartFactory.createBarChart(
        		ChartLabelResolver.getTitle(ofxChart),
        		ChartLabelResolver.getAxisLabelX(ofxChart),
        		ChartLabelResolver.getAxisLabelY(ofxChart),
        		createDataset(ofxChart.getDataSet()),
        		PlotOrientation.VERTICAL,
        		ofxChart.isLegend(),
	            true,               // generate tooltips?
	            false               // generate URLs?
	        );
        return chart;
	}
	
	private CategoryDataset createDataset(List<DataSet> lContainer)
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(DataSet container : lContainer)
		{
			for(Data data : container.getData())
			{
				dataset.addValue(data.getY(), container.getLabel(), data.getCategory());
			}	
		}
        return dataset;
    }
	
	public Dimension getSuggestedSize()
	{
		logger.error("This should be @Overridden");
		throw new UnsupportedOperationException();
	}
}
