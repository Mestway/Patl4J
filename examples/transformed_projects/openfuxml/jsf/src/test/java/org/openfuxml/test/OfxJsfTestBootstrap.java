package org.openfuxml.test;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxJsfTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OfxJsfTestBootstrap.class);
		
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("config.ofx-jsf.test");
		loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
	}
}