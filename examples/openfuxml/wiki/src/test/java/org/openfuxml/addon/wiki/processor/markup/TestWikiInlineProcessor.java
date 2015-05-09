package org.openfuxml.addon.wiki.processor.markup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestWikiInlineProcessor extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	private WikiInlineProcessor wikiInline;
	
	public static String srcDirName = "src/test/resources/data/wiki/inline/in";
	public static final String dstDirName = "src/test/resources/data/wiki/inline/out";
	private static Cmp cmp;
	
	public TestWikiInlineProcessor(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-4);
		fRef = new File(dstDirName,name+".xml");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".txt");}
	
	@BeforeClass
	public static void initCmp() throws FileNotFoundException
	{
		String fNameCmp = "src/test/resources/config/cmp/wiki.xml";
		cmp = (Cmp)JaxbUtil.loadJAXB(fNameCmp, Cmp.class);
	}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		wikiInline = new WikiInlineProcessor(cmp);
	}
	
	@After
	public void close()
	{
		wikiInline = null;
	}
    
    @Test
    public void test() throws OfxInternalProcessingException, FileNotFoundException
    {
    	test(false);
    }
	
	private void test(boolean saveReference) throws FileNotFoundException, OfxInternalProcessingException
	{
		logger.debug(fTest.getAbsolutePath());
		String wikiTxt = StringIO.loadTxt(fTest);
		
		Section section = wikiInline.toOfx(wikiTxt);
		if(saveReference)
		{
			JaxbUtil.save(fRef, section, true);
		}
		else
		{
			Section sectionRef = (Section)JaxbUtil.loadJAXB(fRef.getAbsolutePath(), Section.class);
			Assert.assertEquals(JaxbUtil.toString(sectionRef),JaxbUtil.toString(section));
		}	
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
			
		boolean saveReference = true;
		int id = -1;
		int index = 0;
		
		TestWikiInlineProcessor.initCmp();
		for(Object[] o : TestWikiInlineProcessor.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestWikiInlineProcessor test = new TestWikiInlineProcessor(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.test(saveReference);}
			test.close();
			index++;
		}
    }
}