package org.openfuxml.test.addon.jsf;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.jsf.data.jaxb.Attribute;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;

public class TestTaglib
{
	final static Logger logger = LoggerFactory.getLogger(TestTaglib.class);
	
	public TestTaglib()
	{
		
	}
	
	public void xmlConstruct()
	{	
		Taglib taglib = new Taglib();
		taglib.setTlibVersion("1.0");
//		taglib.setJspversion("2.1");
		taglib.setShortName("jwan");
		taglib.setUri("http://www.openfuxml.org");
//		taglib.setInfo("OpenFuXML");
		
		Tag tag = new Tag();
		tag.setName("myTag");
		tag.setInfo("info");
		
		Attribute att = new Attribute();
		att.setName("myAtt");
		att.setRequired(false);
//		att.setDescription("xx");
		tag.getAttribute().add(att);
		
		
		taglib.getTag().add(tag);
		
		JaxbUtil.debug(taglib);
	}
		
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing XSD Taglib");
			
		TestTaglib test = new TestTaglib();
			test.xmlConstruct();
	}
}