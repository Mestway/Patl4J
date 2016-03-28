package org.openfuxml.addon.chart.processor;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import net.sf.exlp.util.DateUtil;

import org.openfuxml.addon.chart.util.TimePeriodFactory.OfxChartTimePeriod;
import org.openfuxml.xml.addon.chart.Data;
import org.openfuxml.xml.addon.chart.DataSet;
import org.openfuxml.xml.addon.chart.RendererTimeseries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSeriesGapNullifier
{
	final static Logger logger = LoggerFactory.getLogger(TimeSeriesGapNullifier.class);
	
	int gapUnit;
	
	public TimeSeriesGapNullifier(OfxChartTimePeriod timePeriod)
	{
		switch(timePeriod)
		{
			case Day: gapUnit=GregorianCalendar.DAY_OF_MONTH;break;
			case Month: gapUnit=GregorianCalendar.MONTH;break;
			default: logger.warn("No gapUnit ... app will crash");
		}
	}
	
	public DataSet nullifyGapsInContainer(DataSet container)
	{
		List<Data> lData = new ArrayList<Data>();
		
		int index=0;
		int datasetSize=container.getData().size();
		GregorianCalendar gcBegin;
		GregorianCalendar gcEnd;
		
		logger.warn("Check size of data.array");
		gcBegin=container.getData().get(0).getRecord().toGregorianCalendar();
		gcEnd=container.getData().get(datasetSize-1).getRecord().toGregorianCalendar();
		
		while(gcBegin.before(gcEnd))
		{
			if(index<datasetSize)
			{
				Data currentData = container.getData().get(index);
				GregorianCalendar gcCurrent= currentData.getRecord().toGregorianCalendar();
				int compare = gcCurrent.compareTo(gcBegin); 
				if(compare==0)
				{
					lData.add(currentData);
					index++;
				}
				else{lData.add(createNullData(gcBegin));}
//				logger.debug(DateUtil.tmj(gcBegin)+"-"+DateUtil.sm(gcBegin)+" "+DateUtil.tmj(gcEnd)+" "+DateUtil.tmj(gcCurrent)+ "\t"+compare+ "\t"+gcBegin.getTimeInMillis()+"<"+gcCurrent.getTimeInMillis());
			}
			gcBegin.add(gapUnit, 1);
		}
		
		container.getData().clear();
		container.getData().addAll(lData);
		return container;
	}
	
	private Data createNullData(GregorianCalendar gc)
	{
		Data d = new Data();
		d.setRecord(DateUtil.getXmlGc4D(gc.getTime()));
//		JaxbUtil.debug(d);
		return d;
	}
	
	public static synchronized boolean gapNullerActivated(RendererTimeseries chartTimeSeries)
	{
		if(chartTimeSeries.isSetGap())
		{
			if(chartTimeSeries.isSetTimePeriod())
			{
				return chartTimeSeries.isGap();
			}
			else
			{
				logger.warn("chart/charttype/timeseries/@timePeriod is not set!! No GapNulling");
				return false;
			}
		}
		else {return false;}
	}
}
