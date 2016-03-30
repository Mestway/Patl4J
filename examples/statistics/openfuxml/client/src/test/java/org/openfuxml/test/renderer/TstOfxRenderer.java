package org.openfuxml.test.renderer;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.renderer.OfxRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstOfxRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TstOfxRenderer.class);
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();

		String propFile = "src/test/resources/properties/user.properties";
			
		if(args.length==1){propFile=args[0];}
			
		ConfigLoader.add(propFile);
		Configuration config = ConfigLoader.init();
		
		OfxRenderer ofx = new OfxRenderer();
		ofx.initCmpUtil(config.getString("ofx.xml.cmp"));
		
		ofx.preProcessor();
		ofx.renderTarget();
	}
}