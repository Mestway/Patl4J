package org.openfuxml.addon.chart.processor;

import org.openfuxml.xml.addon.chart.Data;
import org.openfuxml.xml.addon.chart.DataSet;
import org.openfuxml.xml.addon.chart.RendererTimeseries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSetCumulator
{
	final static Logger logger = LoggerFactory.getLogger(DataSetCumulator.class);
	
	boolean activated;
	
	private DataSetCumulator(boolean activated)
	{
		this.activated=activated;
	}
	
	public static DataSetCumulator factory(RendererTimeseries renderer)
	{
		if(renderer.isSetCumulate())
		{
			if(renderer.isCumulate()){return new DataSetCumulator(true);}
		}
		else
		{
			logger.warn("RendererTimeseries@Cumulate not set. Defaulting to false");
		}
		return new DataSetCumulator(false);
	}
	
	public DataSet process(DataSet dataSet)
	{
		if(!activated){return dataSet;}
		
		double value=0;
		for(Data data : dataSet.getData())
		{
			data.setY(data.getY()+value);
			value=data.getY();
		}
		
		return dataSet;
	}
}