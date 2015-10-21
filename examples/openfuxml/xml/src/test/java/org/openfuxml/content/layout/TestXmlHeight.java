package org.openfuxml.content.layout;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlHeight extends AbstractXmlLayoutTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix, Height.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Height actual = create(true);
    	Height expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Height.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Height create(boolean withChilds)
    {
    	Height xml = new Height();
    	xml.setValue(45.6);
    	xml.setUnit("pt");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlHeight.initFiles();	
		TestXmlHeight test = new TestXmlHeight();
		test.save();
    }
}