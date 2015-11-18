package org.openfuxml.test.xml.epub.jaxb;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.epub.data.jaxb.container.Container;

public class TestContainer
{
	final static Logger logger = LoggerFactory.getLogger(TestContainer.class);
	
	public TestContainer()
	{
		
	}
	
	public void xmlConstruct()
	{	
		Container container = new Container();
		container.setVersion("1.0");
		
		Container.Rootfiles rootfiles = new Container.Rootfiles();
		
		Container.Rootfiles.Rootfile rootfile = new Container.Rootfiles.Rootfile();
		rootfile.setFullPath("OEBS/content.opf");
		rootfile.setMediaType("application/oebps-package+xml");
		rootfiles.getRootfile().add(rootfile);
		
		container.setRootfiles(rootfiles);
		
		JaxbUtil.debug(container);
	}
			
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing Metatag");
			
		TestContainer test = new TestContainer();
		test.xmlConstruct();
	}
}