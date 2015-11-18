package org.openfuxml.content.ofx;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDocument extends AbstractXmlOfxTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Document.class);
	}
    
    @Test
    public void jaxbStructure() throws FileNotFoundException
    {
    	Document actual = create(true);
    	Document expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Document.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Document create(boolean withChilds)
    {
    	Document xml = new Document();
    	
    	logger.warn("Not fully implemented");
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlDocument.initFiles();	
		TestXmlDocument test = new TestXmlDocument();
		test.save();
    }
}