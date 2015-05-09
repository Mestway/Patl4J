package org.openfuxml.xml.xpath.wiki;

import net.sf.exlp.exception.ExlpXpathNotFoundException;

import org.openfuxml.addon.wiki.data.jaxb.Auth;
import org.openfuxml.xml.xpath.cmp.HtmlXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiAuthXpath
{
	final static Logger logger = LoggerFactory.getLogger(HtmlXpath.class);
	
	public static synchronized Auth getAuth(String serverId, String authType) throws ExlpXpathNotFoundException
	{
		throw new ExlpXpathNotFoundException("No "+Auth.class.getSimpleName()+" for server=serverId and authType="+authType);
	}
}