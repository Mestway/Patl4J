package org.openfuxml.test.xml.wiki.jaxb;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Ofx;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;

public class TestWikiReplace
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiReplace.class);
	
	public TestWikiReplace()
	{
		
	}
	
	public void xmlConstruct()
	{
		Wikireplace wikireplace = new Wikireplace();
		wikireplace.setFrom("a");
		wikireplace.setTo("b");
		wikireplace.setDescription("Description");
		
		Ofx container = new Ofx();
		container.getWikireplace().add(wikireplace);
		
		JaxbUtil.debug(container);
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestWikiReplace test = new TestWikiReplace();
			test.xmlConstruct();
	}
}