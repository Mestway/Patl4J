package org.openfuxml.test.xml.wiki;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.media.image.WikiImageProcessor;
import org.openfuxml.addon.wiki.util.WikiConfigChecker;

public class TestImageFetcher
{
	final static Logger logger = LoggerFactory.getLogger(TestImageFetcher.class);
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/config/wiki/wiki.xml");
		Configuration config = ConfigLoader.init();
		WikiConfigChecker.check(config);
			
		WikiImageProcessor wip = new WikiImageProcessor(config);
		wip.fetch("Bellagio waterfront.jpg");
		wip.save("bellagio");
    }
}