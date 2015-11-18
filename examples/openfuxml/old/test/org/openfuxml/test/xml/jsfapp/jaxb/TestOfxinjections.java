package org.openfuxml.test.xml.jsfapp.jaxb;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.jsfapp.data.jaxb.Iframe;
import org.openfuxml.addon.jsfapp.data.jaxb.Ofxinjection;
import org.openfuxml.addon.jsfapp.data.jaxb.Ofxinjections;

public class TestOfxinjections
{
	final static Logger logger = LoggerFactory.getLogger(TestOfxinjections.class);
	
	public TestOfxinjections()
	{
		
	}
	
	public void xmlConstruct()
	{	
		Ofxinjections ofxIs = new Ofxinjections();

		Ofxinjection ofxI = new Ofxinjection();
		ofxI.setId("app-xx");
		Iframe iframe = new Iframe();
		iframe.setSrc("xx");
		iframe.setWidth(800);
		iframe.setHeight(850);
		iframe.setStyle("border: 3px solid #808080");
		
		ofxI.setIframe(iframe);
		ofxIs.getOfxinjection().add(ofxI);
		
		JaxbUtil.debug(ofxIs);
	}
	
			
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing Metatag");
			
		TestOfxinjections test = new TestOfxinjections();
		test.xmlConstruct();
	}
}