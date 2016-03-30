package org.openfuxml.test.addon.jsfapp;

import java.io.FileNotFoundException;
import java.util.Map;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.jsfapp.data.jaxb.Menu;
import org.openfuxml.addon.jsfapp.factory.MenuIdLinkFactory;

public class TestMenuIdLink
{
	final static Logger logger = LoggerFactory.getLogger(TestMenuIdLink.class);
	
	private Menu menu;
	
	public TestMenuIdLink(String fileName) throws FileNotFoundException
	{
		menu = (Menu)JaxbUtil.loadJAXB(fileName, Menu.class);
	}
	
	public void linkId()
	{
		MenuIdLinkFactory f = new MenuIdLinkFactory();
		Map<String,String> map = f.getLinkId(menu);
		for(String s : map.keySet())
		{
			logger.debug(s+": "+map.get(s));
		}
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestMenuIdLink test = new TestMenuIdLink("../AHT/resources/config/erp/menu.xml");
		test.linkId();
	}
}
