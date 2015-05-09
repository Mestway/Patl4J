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
import org.openfuxml.processor.pre.ExternalContentEagerLoader;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestExternalMergerOfxDoc extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestExternalMergerOfxDoc.class);
	
	private ExternalContentEagerLoader exMerger;
	
	public static String srcDirName = "src/test/resources/data/pre/external/ofxdoc/in";
	public static final String dstDirName = "src/test/resources/data/pre/external/ofxdoc/out";
	
	public TestExternalMergerOfxDoc(File fTest)
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
		exMerger = new ExternalContentEagerLoader();
	}
	
	@After
	public void close()
	{
		exMerger = null;
	}
    
    @Test
    public void render() throws OfxInternalProcessingException, FileNotFoundException
    {
    	render(false);
    }
	
	private void render(boolean saveReference) throws FileNotFoundException, OfxInternalProcessingException
	{
		logger.debug(fTest.getAbsolutePath());
		Document ofxDocDst = exMerger.mergeToOfxDoc(fTest);
		
		if(saveReference)
		{
			JaxbUtil.save(fRef, ofxDocDst, true);
		}
		else
		{
			Document ofxDocRef = JaxbUtil.loadJAXB(fRef.getAbsolutePath(), Document.class);
			Assert.assertEquals(JaxbUtil.toString(ofxDocRef),JaxbUtil.toString(ofxDocDst));
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
		
		for(Object[] o : TestExternalMergerOfxDoc.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestExternalMergerOfxDoc test = new TestExternalMergerOfxDoc(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.render(saveReference);}
			test.close();
			index++;
		}
    }
}