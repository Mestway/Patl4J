package org.openfuxml.xml.xpath;

import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.dom4j.Namespace;

import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxXpath
{
	final static Logger logger = LoggerFactory.getLogger(OfxXpath.class);
	
	public static synchronized Namespace getNsHtml() 
	{
		Namespace nsHtml = Namespace.getNamespace("html", "http://www.openfuxml.org/renderer/html"); 
		return nsHtml;
	}
	
	public static synchronized NsPrefixMapperInterface getNsPrefixMapper()
	{
		return new OfxNsPrefixMapper();
	}
}
