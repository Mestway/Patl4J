package org.openfuxml.processor.pre;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.io.FilenameUtils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestExternalContentEagerLoader extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestExternalContentEagerLoader.class);
	
	private ExternalContentEagerLoader ecel;
	int expectedExternals;
	
	public static String srcDir = "src/test/resources";
	public static String srcDirName = srcDir+"/data/pre/external/elements/in";
	public static final String dstDirName = srcDir+"/data/pre/external/elements/out";
	
	public TestExternalContentEagerLoader(File fTest)
	{
		this.fTest = fTest;
		expectedExternals = new Integer(FilenameUtils.removeExtension(fTest.getName()));
		String name = FilenameUtils.removeExtension(fTest.getName());
		fRef = new File(dstDirName,name+".xml");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xml");}
	
	@Before
	public void init()
	{	
		ecel = new ExternalContentEagerLoader();
	}
	
	@After
	public void close()
	{
		ecel = null;
	}
	
	@Test
    public void xpathWithDocument() throws OfxInternalProcessingException, FileNotFoundException
    {
    	Document doc = JDomUtil.load(fTest); 
    	XPathExpression<Element> xpe = ecel.build(); 

    	List<Element> list = xpe.evaluate(doc); 
    	Assert.assertEquals(expectedExternals,list.size());
    }
	
    @Test
    public void xpathWithRootElement() throws OfxInternalProcessingException, FileNotFoundException
    {
    	Document doc = JDomUtil.load(fTest); 
    	XPathExpression<Element> xpe = ecel.build(); 

    	List<Element> list = xpe.evaluate(doc.getRootElement()); 
    	Assert.assertEquals(expectedExternals,list.size());
    }
	
    
    @Test
    public void loadFromFile() throws FileNotFoundException, OfxAuthoringException
    {
    	render(fTest.getAbsolutePath(),false);
    }
    
    @Test
    public void loadFromResource() throws FileNotFoundException, OfxAuthoringException
    {
    	RelativePathFactory rpf = new RelativePathFactory(new File(srcDir));
    	String relativeResource = rpf.relativate(fTest.getAbsoluteFile());
    	logger.info(relativeResource);
    	render(relativeResource,false);
    }
	
    private void render(boolean saveReference) throws FileNotFoundException, OfxAuthoringException
    {
    	render(fTest.getAbsolutePath(),saveReference);
    }
    
	private void render(String fileName, boolean saveReference) throws FileNotFoundException, OfxAuthoringException
	{
		Document docActual = ecel.load(fileName); 
		
		if(saveReference)
		{
			JDomUtil.save(docActual, fRef, new OutputFormat()); 
		}
		else
		{
			Document docExcepcted = JDomUtil.load(fRef); 
			Assert.assertEquals(JDomUtil.toString(docExcepcted),JDomUtil.toString(docActual));
		}	
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxAuthoringException
    {
		OfxCoreTestBootstrap.init();
		
		boolean saveReference = true;
		int id = -1;
		int index = 0;
		
		for(Object[] o : TestExternalContentEagerLoader.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestExternalContentEagerLoader test = new TestExternalContentEagerLoader(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.render(saveReference);}
			test.close();
			index++;
		}
    }
}
