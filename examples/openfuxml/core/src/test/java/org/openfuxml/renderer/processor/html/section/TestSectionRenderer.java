package org.openfuxml.renderer.processor.html.section;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

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
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.renderer.html.section.SectionRenderer;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestSectionRenderer extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestSectionRenderer.class);
	
	private SectionRenderer renderer;
	
	public static String srcDirName = "src/test/resources/data/html/section/ofx";
	public static final String dstDirName = "src/test/resources/data/html/section/web";
	
	public TestSectionRenderer(File fTest)
	{
		this.fTest = fTest;
		String name = fTest.getName().substring(0, fTest.getName().length()-4);
		fRef = new File(dstDirName,name+".html");
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> initFileNames() {return initFileNames(srcDirName, ".xml");}
	
	@Before
	public void init() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{	
		renderer = new SectionRenderer();
	}
	
	@After
	public void close()
	{
		renderer = null;
	}
    
    @Test
    public void render() throws OfxInternalProcessingException, FileNotFoundException
    {
    	render(false);
    }
	
	private void render(boolean saveReference) throws FileNotFoundException
	{
		logger.debug(fTest.getAbsolutePath());
		Section section = (Section)JaxbUtil.loadJAXB(fTest.getAbsolutePath(), Section.class);

		Element html = DocumentHelper.createElement("html"); 
		html.addContent(renderer.render(section)); 
		
		Document doc = DocumentHelper.createDocument(); 
		doc.setRootElement(html); 
		
		if(saveReference)
		{
			JDomUtil.save(doc, fRef, Format.getPrettyFormat()); 
		}
		else
		{
			Document docRef = JDomUtil.load(fRef);
			Assert.assertEquals(JDomUtil.toString(docRef),JDomUtil.toString(doc));
		}	
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		OfxCoreTestBootstrap.init();
		
		boolean saveReference = true;
		int id = 3;
		int index = 0;
		
		for(Object[] o : TestSectionRenderer.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestSectionRenderer test = new TestSectionRenderer(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.render(saveReference);}
			test.close();
			index++;
		}
    }
}
