package org.openfuxml.addon.chart.renderer;

import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.openfuxml.addon.chart.interfaces.ChartRenderer;
import org.openfuxml.addon.chart.renderer.generic.XYPlotRenderer;
import org.openfuxml.addon.chart.util.ChartLabelResolver;
import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.DataSet;
import org.openfuxml.xml.addon.chart.Data;
import org.openfuxml.xml.addon.chart.Renderer.Timebar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeBarRenderer extends XYPlotRenderer implements ChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TimeBarRenderer.class);
	
	public TimeBarRenderer()
	{
		logger.debug("Active");
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		this.ofxChart=ofxChart;
		Timebar timebar = ofxChart.getRenderer().getTimebar();
        chart = ChartFactory.createXYBarChart(
        	ChartLabelResolver.getTitle(ofxChart),
        	ChartLabelResolver.getAxisLabelX(ofxChart),
            true,
            ChartLabelResolver.getAxisLabelY(ofxChart),                        // range axis label
            createDataset(ofxChart.getDataSet()),                    // data
            PlotOrientation.VERTICAL,
            ofxChart.isLegend(),                       // include legend
            true,
            false
        );

        
        setColors();
        setGrid();
        
        XYPlot plot = (XYPlot) chart.getPlot();
        ClusteredXYBarRenderer renderer = new ClusteredXYBarRenderer(0.0, false);
        
        if(timebar.isSetShadow()){renderer.setShadowVisible(timebar.isShadow());}
        if(timebar.isSetGradient() && timebar.isGradient())
        {
        	renderer.setBarPainter(new StandardXYBarPainter());
        }
        renderer.setDrawBarOutline(false);
        
        plot.setRenderer(renderer);
        return chart;
	}

	private IntervalXYDataset createDataset(List<DataSet> lContainer)
	{
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(DataSet container : lContainer)
		{
			TimeSeries ts = new TimeSeries(container.getLabel());
			for(Data data : container.getData())
			{
					Date d = DateUtil.getDateFromInt(data.getRecord().getYear(), data.getRecord().getMonth(), data.getRecord().getDay());
					ts.addOrUpdate(new Hour(d), data.getY());
			}
			 dataset.addSeries(ts);
		}
		return dataset;
	}
}
