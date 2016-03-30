package org.openfuxml.addon.chart.util;

import java.util.Date;

import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Year;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimePeriodFactory
{	
	final static Logger logger = LoggerFactory.getLogger(TimePeriodFactory.class);
	
	public static enum OfxChartTimePeriod {Hour,Day,Month,Year};
	
	public synchronized static RegularTimePeriod getRtp(OfxChartTimePeriod ofxTimePeriod, Date d)
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
	
	public synchronized static Class<?> getPeriodClass(String value)
	{	
		Class<?> c = null;
		switch(getOfxTimePeriod(value))
		{
			case Hour: c = Hour.class;break;
			case Day: c = Day.class;break;
			case Month: c = Month.class;break;
			case Year: c = Year.class;break;
			default: c = Day.class;break;
		}
		return c;
	}
	
	private synchronized static OfxChartTimePeriod getOfxTimePeriod(String value)
	{
		return OfxChartTimePeriod.valueOf(value);
	}
}
