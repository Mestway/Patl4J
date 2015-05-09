package org.openfuxml.test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.svenjacobs.loremipsum.LoremIpsum;

public class AbstractOfxCoreTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxCoreTest.class);
	
	protected static LoremIpsum li;
	private boolean saveReference = false;
	protected File f;
	
	protected DefaultSettingsManager dsm;
	protected  CrossMediaManager cmm;
	
	public AbstractOfxCoreTest()
	{
		dsm = new OfxDefaultSettingsManager();
		cmm = new NoOpCrossMediaManager();
	}

	@BeforeClass
    public static void initLogger()
	{
		if(!LoggerInit.isLog4jInited())
		{
			LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.addAltPath("config.ofx-core.test");
			loggerInit.init();
		}
    }
	
	@BeforeClass
    public static void initLoremIpsum()
	{
		li = new LoremIpsum();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper( new OfxNsPrefixMapper());
	}
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("XML-ref differes from XML-test",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected void save(OfxLatexRenderer renderer, File f) throws IOException
	{
		if(saveReference)
		{
			RelativePathFactory rpf = new RelativePathFactory(new File("src/test/resources"),RelativePathFactory.PathSeparator.CURRENT);
			logger.debug("Saving Reference to "+rpf.relativate(f));
			StringWriter actual = new StringWriter();
			renderer.write(actual);
			StringIO.writeTxt(f, actual.toString());
		}
	}
	
	protected void assertText(OfxLatexRenderer renderer, File f) throws IOException
	{
		StringWriter actual = new StringWriter();
		renderer.write(actual);
		
		String expected = StringIO.loadTxt(f);
		Assert.assertEquals(expected, actual.toString());
	}
	
	protected void debug(OfxLatexRenderer renderer)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("Debugging "+renderer.getClass().getSimpleName());
			System.out.println("************************************");
			for(String s : renderer.getContent())
			{
				System.out.println(s);
			}
			System.out.println("************************************");
		}
	}
	
	protected void renderTest(OfxLatexRenderer renderer, File f) throws IOException
	{
		debug(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
	}
	
	public void setSaveReference(boolean saveReference) {this.saveReference = saveReference;}
}