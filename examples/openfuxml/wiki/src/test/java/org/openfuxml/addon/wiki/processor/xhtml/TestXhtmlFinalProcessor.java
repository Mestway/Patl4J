package org.openfuxml.addon.wiki.processor.xhtml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.StringIO;

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
public class TestXhtmlFinalProcessor extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	private XhtmlFinalProcessor xhtmlFinalP;
	
	private static final String srcDirName = "src/test/resources/data/xhtml/replace";
	private static final String dstDirName = "src/test/resources/data/xhtml/final";
	
	private File fTest;
	private File fRef;

	public TestXhtmlFinalProcessor(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-6);
		fRef = new File(dstDirName,name+".xhtml");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xhtml");}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		xhtmlFinalP = new XhtmlFinalProcessor();
	}
	
	@After
	public void close()
	{
		xhtmlFinalP = null;
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
		String outTxt = xhtmlFinalP.process(inTxt);
		if(saveReference)
		{
			StringIO.writeTxt(fRef, outTxt);
		}
		else
		{
			String refTxt = StringIO.loadTxt(fRef,false);
			Assert.assertEquals(refTxt.trim(),outTxt.trim());
		}	
	}
	
	public static void chain(int id, boolean saveReference) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		int index = 0;
		for(Object[] o : TestXhtmlFinalProcessor.initFileNames())
		{
			if(id<0 | id==index)
			{
				File fTest = (File)o[0];
				TestXhtmlFinalProcessor test = new TestXhtmlFinalProcessor(fTest);
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
		
		TestXhtmlFinalProcessor.chain(id,saveReference);
    }
}