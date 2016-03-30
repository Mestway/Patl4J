package org.openfuxml.addon.chart.processor.timeseries;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.xml.addon.chart.Data;
import org.openfuxml.xml.addon.chart.DataSet;
import org.openfuxml.xml.addon.chart.RendererTimeseries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSeriesDateSummer
{
	final static Logger logger = LoggerFactory.getLogger(TimeSeriesDateSummer.class);
	
	boolean activated;
	
	private TimeSeriesDateSummer(boolean activated)
	{
		this.activated=activated;
	}
	
	public static TimeSeriesDateSummer factory(RendererTimeseries renderer)
	{
		if(renderer.isSetSumDate())
		{
			if(renderer.isSumDate())
			{
				return new TimeSeriesDateSummer(true);
			}
		}
		else
		{
			logger.warn("RendererTimeseries@sumDate not set. Defaulting to false");
		}
		return new TimeSeriesDateSummer(false);
	}
	
	public DataSet process(DataSet dataSet)
	{
		if(!activated){return dataSet;}
		
		List<Data> result = new ArrayList<Data>();
		
		for(Data data : dataSet.getData())
		{
			if(result.size()==0)
			{
				result.add(data);
			}
			else
			{
				if(result.get(result.size()-1).getRecord().equals(data.getRecord()))
				{
					result.get(result.size()-1).setY(result.get(result.size()-1).getY()+data.getY());
				}
				else
				{
					result.add(data);
				}
			}
		}
		dataSet.getData().clear();
		dataSet.getData().addAll(result);
		return dataSet;
	}
}