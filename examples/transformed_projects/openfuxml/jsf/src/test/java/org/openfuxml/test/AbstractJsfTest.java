package org.openfuxml.test;

import java.io.File;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractJsfTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJsfTest.class);	
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.addAltPath("config.ofx-jsf.test");
			loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
    }
	
	//****Copy from AbstractAhtUtilsXmlTest ******
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("Actual XML differes from expected XML",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected static XMLGregorianCalendar getDefaultXmlDate()
	{
		return DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11));
	}
	
	protected void save(Object xml, File f)
	{
		logger.debug("Saving Reference XML");
		JaxbUtil.debug(xml);
    	JaxbUtil.save(f, xml, true);
	}
	//***********************************
}