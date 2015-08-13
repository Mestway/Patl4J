package org.openfuxml.test;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxUtilTest.class);

	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("config.ofx-util.test");
		loggerInit.init();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
	}
}