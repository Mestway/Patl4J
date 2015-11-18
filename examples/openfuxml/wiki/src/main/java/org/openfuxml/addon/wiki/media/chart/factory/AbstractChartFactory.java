package org.openfuxml.addon.wiki.media.chart.factory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.DocumentHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class AbstractChartFactory
{
	final static Logger logger = LoggerFactory.getLogger(AbstractChartFactory.class);
	
	protected double getChartValue(String xp, String type, Document doc) 
	{
		double value=0;
		try
		{
			XPath xPath = DocumentHelper.createXPath(xp+"[@type='"+type+"']"); 
			Element element = (Element)xPath.selectSingleNode(doc); 
			value = new Double(element.getTextTrim()); 
		}
		catch (Exception e) {logger.error("",e);}
		return value;
	}
}
