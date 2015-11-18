package org.openfuxml.addon.chart.renderer.timeseries;

import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.openfuxml.addon.chart.interfaces.ChartRenderer;
import org.openfuxml.addon.chart.processor.DataSetCumulator;
import org.openfuxml.addon.chart.processor.TimeSeriesGapNullifier;
import org.openfuxml.addon.chart.processor.timeseries.TimeSeriesDateSummer;
import org.openfuxml.addon.chart.processor.timeseries.TimeSeriesRecordOrderer;
import org.openfuxml.addon.chart.renderer.generic.XYPlotRenderer;
import org.openfuxml.addon.chart.util.ChartLabelResolver;
import org.openfuxml.addon.chart.util.TimePeriodFactory.OfxChartTimePeriod;
import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.Data;
import org.openfuxml.xml.addon.chart.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSeriesChartRenderer extends XYPlotRenderer implements ChartRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TimeSeriesChartRenderer.class);
	
	public TimeSeriesChartRenderer()
	{
		super();
		logger.debug("Using: "+this.getClass().getSimpleName());
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		this.ofxChart=ofxChart;
		
		setTimePeriod();
		
        chart = ChartFactory.createTimeSeriesChart(
        		ChartLabelResolver.getTitle(ofxChart),
        		null,
        		null,
        		createDataset(ofxChart.getDataSet()),
        		ofxChart.isLegend(),
	            true,
	            false
	        );
        setColors();
        setGrid();
        setAxis();
        
        return chart;
	}
	
	protected RegularTimePeriod getRtp(Date d)
	{
		RegularTimePeriod rtp;
		switch(ofxTimePeriod)
		{
			case Hour: rtp = new Hour(d);break;
			case Day: rtp = new Day(d);break;
			case Month: rtp = new Month(d);break;
			default: rtp = new Hour(d);break;
		}
		return rtp;
	}
	
	private TimeSeriesCollection createDataset(List<DataSet> dataSets)
	{
		TimeSeriesDateSummer dateSummer = TimeSeriesDateSummer.factory(ofxChart.getRenderer().getRendererTimeseries());
		TimeSeriesRecordOrderer recordOrderer = TimeSeriesRecordOrderer.factory(ofxChart.getRenderer().getRendererTimeseries());
		DataSetCumulator cumulator = DataSetCumulator.factory(ofxChart.getRenderer().getRendererTimeseries());
		
		TimeSeriesGapNullifier gapNuller=null;
		boolean nullifyGaps = TimeSeriesGapNullifier.gapNullerActivated(ofxChart.getRenderer().getRendererTimeseries());
		if(nullifyGaps)
		{
			OfxChartTimePeriod timePeriod = OfxChartTimePeriod.valueOf(ofxChart.getRenderer().getRendererTimeseries().getTimePeriod());
			gapNuller = new TimeSeriesGapNullifier(timePeriod);
		}
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(DataSet dataSet : dataSets)
		{
			dataSet = recordOrderer.process(dataSet);
			dataSet = dateSummer.process(dataSet);
			dataSet = cumulator.process(dataSet);
			
			if(nullifyGaps){dataSet = gapNuller.nullifyGapsInContainer(dataSet);}
			TimeSeries ts = new TimeSeries(dataSet.getLabel());
			
			for(Data data : dataSet.getData())
			{
				Date d = DateUtil.getDateFromInt(data.getRecord().getYear(), data.getRecord().getMonth(), data.getRecord().getDay());
				RegularTimePeriod rtp = getRtp(d);
				if(data.isSetY())
				{
					ts.addOrUpdate(rtp, data.getY());
				}
				else
				{
					ts.addOrUpdate(rtp, null);
				}
			}
			dataset.addSeries(ts);
			
			logger.info("Add here TS with same color from container");
		}
		return dataset;
	}
}