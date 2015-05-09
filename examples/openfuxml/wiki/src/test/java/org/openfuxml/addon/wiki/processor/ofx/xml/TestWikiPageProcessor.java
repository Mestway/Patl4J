package org.openfuxml.addon.wiki.processor.ofx.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JDomUtil;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.DocumentHelper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.addon.wiki.processor.markup.TestWikiInlineProcessor;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestWikiPageProcessor extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	private WikiPageProcessor xmlP;
	
	private static final String srcDirName = "src/test/resources/data/xhtml/final";
	private static final String dstDirName = "src/test/resources/data/xml/ofx";
	
	private File fTest;
	private File fRef;

	public TestWikiPageProcessor(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-6);
		fRef = new File(dstDirName,name+".xml");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xhtml");}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		xmlP = new WikiPageProcessor();
	}
	
	@After
	public void close()
	{
		xmlP = null;
	}
    
    @Test
    public void test() throws OfxInternalProcessingException
    {
    	logger.debug(fTest.getAbsolutePath());
    	test(false);
    }
	
	private void test(boolean saveReference) throws OfxInternalProcessingException
	{
		String inTxt = StringIO.loadTxt(fTest,false);
		Element xml = xmlP.process(inTxt); 
		Document doc = DocumentHelper.createDocument(); 
		doc.setRootElement(xml); 
		if(saveReference)
		{
			JDomUtil.save(doc, fRef, new OutputFormat()); 
		}
		else
		{
			Document refDoc = JDomUtil.load(fRef); 
			Assert.assertEquals(JDomUtil.docToTxt(refDoc),JDomUtil.docToTxt(doc));
		}	
	}
	
	public static void chain(int id, boolean saveReference) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		int index = 0;
		for(Object[] o : TestWikiPageProcessor.initFileNames())
		{
			if(id<0 | id==index)
			{
				File fTest = (File)o[0];
				TestWikiPageProcessor test = new TestWikiPageProcessor(fTest);
				test.init();
				logger.trace(id+" "+index);
				test.test(saveReference);
				test.close();
			}			
			index++;
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		boolean saveReference = true;
		int id = -1;
		
		TestWikiPageProcessor.chain(id,saveReference);
    }
}
