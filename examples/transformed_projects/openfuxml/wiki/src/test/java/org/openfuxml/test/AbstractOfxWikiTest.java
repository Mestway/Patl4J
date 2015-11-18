package org.openfuxml.test;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.junit.BeforeClass;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxWikiTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxWikiTest.class);
	
	protected static NsPrefixMapperInterface nsPrefixMapper;
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		nsPrefixMapper = new OfxNsPrefixMapper();
	}
}