package org.openfuxml.doc;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.test.renderer.OfxClientTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliOfxDoc
{
	final static Logger logger = LoggerFactory.getLogger(CliOfxDoc.class);
	
	public static void main (String[] args) throws Exception
	{
		Configuration config = OfxClientTestBootstrap.init();
		logger.info("TEST");
	}
}