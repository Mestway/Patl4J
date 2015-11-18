package org.openfuxml.content.layout;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFloat extends AbstractXmlLayoutTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix, Float.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Float actual = create();
    	Float expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Float.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Float create()
    {
    	Float xml = new Float();
    	xml.setValue(true);
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlFloat.initFiles();	
		TestXmlFloat test = new TestXmlFloat();
		test.save();
    }
}