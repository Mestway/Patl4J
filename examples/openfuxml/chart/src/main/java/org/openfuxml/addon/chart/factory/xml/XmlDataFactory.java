package org.openfuxml.addon.chart.factory.xml;

import net.sf.exlp.util.DateUtil;

import org.openfuxml.xml.addon.chart.Data;

public class XmlDataFactory
{
	public static Data build(double y)
	{
		Data data = new Data();
		data.setY(y);
		
		return data;
	}
	
	public static Data buildForYearMonth(double y, int year, int month)
	{
		Data xml = build(y);
		xml.setRecord(DateUtil.getXmlGc4D(DateUtil.getDateFromInt(year, month, 1)));
		return xml;
	}
}
