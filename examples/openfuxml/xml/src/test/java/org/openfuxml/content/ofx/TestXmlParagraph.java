package org.openfuxml.content.ofx;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlParagraph extends AbstractXmlOfxTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, "paragraph");
	}
    
    @Test
    public void testAuth() throws FileNotFoundException
    {
    	Paragraph actual = create();
    	Paragraph expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Paragraph.class);
    	assertJaxbEquals(expected, actual);
    }
   
    private static Paragraph create(){return create(false);}
    public static Paragraph create(boolean withChilds)
    {
    	Paragraph xml = new Paragraph();
    	xml.setLang("en");
    	
    	logger.warn("Not fully implemented");
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlParagraph.initFiles();	
		TestXmlParagraph test = new TestXmlParagraph();
		test.save();
    }
}