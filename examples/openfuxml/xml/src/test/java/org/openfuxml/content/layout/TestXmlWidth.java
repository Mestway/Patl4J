package org.openfuxml.content.layout;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlWidth extends AbstractXmlLayoutTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix, Width.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Width actual = create(true);
    	Width expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Width.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Width create(boolean withChilds)
    {
    	Width xml = new Width();
    	xml.setValue(12.3);
    	xml.setFlex(false);
    	xml.setNarrow(false);
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
			
		TestXmlWidth.initFiles();	
		TestXmlWidth test = new TestXmlWidth();
		test.save();
    }
}