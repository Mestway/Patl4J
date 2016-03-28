package org.openfuxml.test;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxXmlTest extends AbstractAhtUtilsXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("ofx-xml.test");
		loggerInit.init();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		logger.warn("Init "+OfxNsPrefixMapper.class.getSimpleName());
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
	}
}