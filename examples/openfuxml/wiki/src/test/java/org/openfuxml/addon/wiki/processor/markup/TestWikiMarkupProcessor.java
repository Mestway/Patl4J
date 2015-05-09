package org.openfuxml.addon.wiki.processor.markup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.addon.wiki.data.jaxb.MarkupProcessor;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractOfxWikiTest;
import org.openfuxml.test.OfxWikiTstBootstrap;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestWikiMarkupProcessor extends AbstractOfxWikiTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiMarkupProcessor.class);
	
	private WikiMarkupProcessor wmp;
	
	private static final String srcDirName = "src/test/resources/data/wiki/plain";
	private static final String dstDirName = "src/test/resources/data/wiki/markup";
	
	private File fTest;
	private File fRef;

	public TestWikiMarkupProcessor(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-4);
		fRef = new File(dstDirName,name+".txt");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames()
	{
		Collection<Object[]> c = new ArrayList<Object[]>();
		File srcDir = new File(srcDirName);
		for(File f : srcDir.listFiles())
		{
			if(f.getName().endsWith(".txt"))
			{
				Object[] o = new Object[] {f};
				c.add(o);
			}
		}
		return c;
	}
	
	@Before
	public void initWmp() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		Cmp cmp = (Cmp)JaxbUtil.loadJAXB("src/test/resources/config/cmp/wiki.xml", Cmp.class);
		MarkupProcessor mpXml = cmp.getPreprocessor().getWiki().getMarkupProcessor();
		Templates   templates = cmp.getPreprocessor().getWiki().getTemplates();
		
		wmp = new WikiMarkupProcessor(mpXml.getReplacements(), mpXml.getInjections(), templates);
	}
	
	@After
	public void closeWmp()
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
		String plainTxt = StringIO.loadTxt(fTest);
		String markupTxt = wmp.process(plainTxt, "article ... req?");
		if(saveReference)
		{
			StringIO.writeTxt(fRef, markupTxt);
		}
		else
		{
			String markupRefTxt = StringIO.loadTxt(fRef);
			Assert.assertEquals(markupRefTxt,markupTxt);
		}	
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		OfxWikiTstBootstrap.init();	
		
		for(Object[] o : TestWikiMarkupProcessor.initFileNames())
		{
			File fTest = (File)o[0];
			logger.debug(fTest.getAbsolutePath());
			
			TestWikiMarkupProcessor test = new TestWikiMarkupProcessor(fTest);
			test.initWmp();
			test.wikiPlainToMarkup(true);
			test.closeWmp();
		}
    }
}