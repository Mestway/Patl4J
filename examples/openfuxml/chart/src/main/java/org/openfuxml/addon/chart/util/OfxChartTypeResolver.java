package org.openfuxml.addon.chart.util;

import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.xml.addon.chart.AxisType;
import org.openfuxml.xml.addon.chart.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxChartTypeResolver
{
	final static Logger logger = LoggerFactory.getLogger(OfxChartTypeResolver.class);
	
	public static enum Type{TimeSeries, TimeBar, Bar, Gantt, Spline};
	
	public static enum AxisOrientation{domain,range0,range1};
	public static enum ChartAxisType{Nil,Number,Date};
	
	public synchronized static Type getType(Renderer type)
	{
		if(type.isSetRendererTimeseries()){return Type.TimeSeries;}
		if(type.isSetBar()){return Type.Bar;}
		if(type.isSetTimebar()){return Type.TimeBar;}
		if(type.isSetGantt()){return Type.Gantt;}
		if(type.isSetSpline()){return Type.Spline;}
		logger.warn("Unknown Charttype");
		JaxbUtil.debug(type);
		return null;
	}
	
	public synchronized static ChartAxisType getAxisType(AxisType type)
	{
		if(type!=null)
		{
			if(type.isSetNumber()){return ChartAxisType.Number;}
			if(type.isSetDate()){return ChartAxisType.Date;}
		}
		return ChartAxisType.Nil;
	}
}
