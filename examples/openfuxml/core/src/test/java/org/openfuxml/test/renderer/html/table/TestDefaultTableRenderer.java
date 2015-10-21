package org.openfuxml.test.renderer.html.table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import net.sf.exlp.util.io.LoggerInit;
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
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.renderer.processor.html.interfaces.OfxTableRenderer;
import org.openfuxml.renderer.processor.html.table.DefaultTableRenderer;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TestDefaultTableRenderer extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestDefaultTableRenderer.class);
	
	private OfxTableRenderer renderer;
	
	public static String srcDirName = "src/test/resources/data/html/table/ofx";
	public static final String dstDirName = "src/test/resources/data/html/table/web";
	
	public TestDefaultTableRenderer(File fTest)
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
		renderer = new DefaultTableRenderer();
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
		Table table = (Table)JaxbUtil.loadJAXB(fTest.getAbsolutePath(), Table.class);

		Element html = DocumentHelper.createElement("html"); 
		Element body = DocumentHelper.createElement("body"); 
		body.add(renderer.render(table)); 
		html.add(body); 
		
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
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		boolean saveReference = true;
		int id = -1;
		int index = 0;
		
		for(Object[] o : TestDefaultTableRenderer.initFileNames())
		{
			File fTest = (File)o[0];
		
			TestDefaultTableRenderer test = new TestDefaultTableRenderer(fTest);
			test.init();
			logger.trace(id+" "+index);
			if(id<0 | id==index){test.render(saveReference);}
			test.close();
			index++;
		}
    }
}
