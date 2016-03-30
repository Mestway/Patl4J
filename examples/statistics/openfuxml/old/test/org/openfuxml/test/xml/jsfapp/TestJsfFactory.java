package org.openfuxml.test.xml.jsfapp;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.openfuxml.addon.jsfapp.factory.JsfJspxFactory;

public class TestJsfFactory
{
	final static Logger logger = LoggerFactory.getLogger(TestJsfFactory.class);
	
	public TestJsfFactory()
	{
		
	}
	
	public void test()
	{
		Document doc = JsfJspxFactory.createDOMjspx(); 
		JDomUtil.debug(doc);
	}
	
			
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing Metatag");
			
		TestJsfFactory test = new TestJsfFactory();
		test.test();
	}
}
