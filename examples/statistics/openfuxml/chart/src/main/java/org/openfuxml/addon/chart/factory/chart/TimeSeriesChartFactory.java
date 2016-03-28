package org.openfuxml.addon.chart.factory.chart;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.addon.chart.factory.xml.XmlAxisFactory;
import org.openfuxml.addon.chart.util.TimePeriodFactory.OfxChartTimePeriod;
import org.openfuxml.xml.addon.chart.Axis;
import org.openfuxml.xml.addon.chart.AxisType;
import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.Renderer;
import org.openfuxml.xml.addon.chart.RendererTimeseries;

public class TimeSeriesChartFactory
{
	private boolean withLegend, cumulateValues, withGaps, sumDate, orderRecords;
	
	public void setOrderRecords(boolean orderRecords) {
		this.orderRecords = orderRecords;
	}
	private OfxChartTimePeriod timePeriod;
	
	public TimeSeriesChartFactory()
	{
		withLegend = false;
		cumulateValues = false;
		withGaps = false;
		sumDate = true;
		orderRecords = false;
	}
	
	public Chart build()
	{
		Chart chart = new Chart();
		chart.setLegend(withLegend);
		chart.setRenderer(buildRenderer());
		chart.getAxis().addAll(buildAxis());
		return chart;
	}
	
	private Renderer buildRenderer()
	{
		Renderer renderer = new Renderer();
		RendererTimeseries timeSeriesRenderer = new RendererTimeseries();
		timeSeriesRenderer.setGap(withGaps);
		timeSeriesRenderer.setCumulate(cumulateValues);
		timeSeriesRenderer.setSumDate(sumDate);
		timeSeriesRenderer.setOrderRecords(orderRecords);
		if(timePeriod!=null){timeSeriesRenderer.setTimePeriod(timePeriod.toString());}
		renderer.setRendererTimeseries(timeSeriesRenderer);
		return renderer;
	}
	
	private List<Axis> buildAxis()
	{
		List<Axis> list = new ArrayList<Axis>();
		
		AxisType.Date.Ticker t1 = new AxisType.Date.Ticker();
		t1.setFormat("MMM");
		t1.setTimePeriod("Month");
		
		AxisType.Date.Ticker t2 = new AxisType.Date.Ticker();
		t2.setFormat("yyyy");
		t2.setTimePeriod("Year");
		
		AxisType.Date date = new AxisType.Date();
		date.setAutoRangeTimePeriod("Month");
		date.setMajorTickTimePeriod("Year");
		date.getTicker().add(t1);
		date.getTicker().add(t2);
		
		AxisType type = new AxisType();
		type.setDate(date);
		
		Axis axis = XmlAxisFactory.build(XmlAxisFactory.Orientation.domain);
		axis.setAxisType(type);
		
	    list.add(axis);
		return list;
	}
	
	public void setWithLegend(boolean withLegend) {this.withLegend = withLegend;}
	public void setCumulateValues(boolean cumulateValues) {this.cumulateValues = cumulateValues;}
	public void setWithGaps(boolean withGaps) {this.withGaps = withGaps;}

	public void setSumDate(boolean sumDate) {this.sumDate = sumDate;}
	public void setTimePeriod(OfxChartTimePeriod timePeriod) {this.timePeriod = timePeriod;}
}
