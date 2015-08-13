package org.openfuxml.addon.wiki.processor.markup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.io.StringIO;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxWikiTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestWikiModelProcessor extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	private WikiModelProcessor wmp;
	
	private static final String srcDirName = "src/test/resources/data/wiki/markup";
	private static final String dstDirName = "src/test/resources/data/xhtml/model";
	
	private File fTest;
	private File fRef;

	public TestWikiModelProcessor(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-4);
		fRef = new File(dstDirName,name+".xhtml");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".txt");}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		wmp = new WikiModelProcessor();
	}
	
	@After
	public void close()
	{
		wmp = null;
	}
    
    @Test
    public void testPlainToMarkup() throws OfxInternalProcessingException
    {
    	logger.debug(fTest.getAbsolutePath());
    	wikiPlainToMarkup(false);
    }
	
	private void wikiPlainToMarkup(boolean saveReference) throws OfxInternalProcessingException
	{
		String markupTxt = StringIO.loadTxt(fTest);
		String modelXhtml = wmp.process(markupTxt);
		if(saveReference)
		{
			StringIO.writeTxt(fRef, modelXhtml.trim());
		}
		else
		{
			String xhtmlRef = StringIO.loadTxt(fRef);
			Assert.assertEquals(xhtmlRef.trim(),modelXhtml.trim());
		}	
	}
	
	public static void chain(int id, boolean saveReference) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		int index = 0;
		for(Object[] o : TestWikiModelProcessor.initFileNames())
		{
			if(id<0 | id==index)
			{
				File fTest = (File)o[0];
				TestWikiModelProcessor test = new TestWikiModelProcessor(fTest);
				test.init();
				logger.trace(id+" "+index);
				test.wikiPlainToMarkup(saveReference);
				test.close();
			}			
			index++;
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		OfxWikiTstBootstrap.init();	
		
		boolean saveReference = true;
		int id = -1;
		
		TestWikiModelProcessor.chain(id,saveReference);
    }
}