package org.openfuxml.test;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxCoreTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxCoreTest.class);
	
	protected static NsPrefixMapperInterface nsPrefixMapper;

	private boolean saveReference = false;
	protected File f;

	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("ofx-addon.test");
		loggerInit.init();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		nsPrefixMapper = new OfxNsPrefixMapper();
	}
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("XML-ref differes from XML-test",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected void save(String actual, File f) throws IOException
	{
		if(saveReference)
		{
			RelativePathFactory rpf = new RelativePathFactory(new File("src/test/resources"),RelativePathFactory.PathSeparator.CURRENT);
			logger.debug("Saving Reference to "+rpf.relativate(f));
			StringIO.writeTxt(f, actual);
		}
	}
	
	protected void assertText(String actual, File f) throws IOException
	{		
		String expected = StringIO.loadTxt(f);
		Assert.assertEquals(expected, actual.toString());
	}
	
	public void setSaveReference(boolean saveReference) {this.saveReference = saveReference;}
}