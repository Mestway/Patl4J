package org.openfuxml.addon.chart.util;

import org.openfuxml.addon.chart.util.OfxChartTypeResolver.AxisOrientation;
import org.openfuxml.xml.addon.chart.Axis;
import org.openfuxml.xml.addon.chart.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartLabelResolver
{	
	final static Logger logger = LoggerFactory.getLogger(ChartLabelResolver.class);
	
	public synchronized static String getTitle(Chart ofxChart)
	{
		String result = null;
		if(ofxChart.isSetTitle()){result = ofxChart.getTitle().getLabel();}
		return result;
	}
	
	public synchronized static String getAxisLabelX(Chart ofxChart){return getAxisLabel(ofxChart, AxisOrientation.domain);}
	public synchronized static String getAxisLabelY(Chart ofxChart){return getAxisLabel(ofxChart, AxisOrientation.range0);}
	
	public synchronized static String getAxisLabel(Chart ofxChart, AxisOrientation type)
	{
		String result = null;
		if(ofxChart!=null)
		{
			for(Axis axis : ofxChart.getAxis())
			{
				if(axis.isSetCode() && axis.getCode().equals(type.toString()) && axis.isSetLabel() && axis.getLabel().isSetText())
				{
					result = axis.getLabel().getText();
					break;
				}
			}
		}
		return result;
	}
}
