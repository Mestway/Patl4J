package org.openfuxml.test.renderer.pre;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.renderer.processor.pre.OfxContainerMerger;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestContainerMerger extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestContainerMerger.class);
	
	private OfxContainerMerger containerMerger;
	
	public static String srcDirName = "src/test/resources/data/pre/container/in";
	public static final String dstDirName = "src/test/resources/data/pre/container/out";
	
	public TestContainerMerger(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-4);
		fRef = new File(dstDirName,name+".xml");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xml");}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		containerMerger = new OfxContainerMerger();
	}
	
	@After
	public void close()
	{
		containerMerger = null;
	}
    
    @Test
    public void render() throws OfxInternalProcessingException, FileNotFoundException
    {
    	render(false);
    }
	
	private void render(boolean saveReference) throws FileNotFoundException, OfxInternalProcessingException
	{
		logger.debug(fTest.getAbsolutePath());
		Document ofxSrc = JaxbUtil.loadJAXB(fTest.getAbsolutePath(), Document.class);

		Document ofxDst = containerMerger.merge(ofxSrc);
		
		if(saveReference)
		{
			JaxbUtil.save(fRef, ofxDst, true);
		}
		else
		{
			Document ofxRef = JaxbUtil.loadJAXB(fRef.getAbsolutePath(), Document.class);
			Assert.assertEquals(JaxbUtil.toString(ofxRef),JaxbUtil.toString(ofxDst));
		}	
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
			
		boolean saveReference = false;
		int id = -1;
		int index = 0;
		
		for(Object[] o : TestContainerMerger.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestContainerMerger test = new TestContainerMerger(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.render(saveReference);}
			test.close();
			index++;
		}
    }
}