package org.openfuxml.content.layout;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLine extends AbstractXmlLayoutTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Line.class);
	}
    
    @Test
    public void jaxbStructure() throws FileNotFoundException
    {
    	Line actual = create(true);
    	Line expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Line.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Line create(boolean withChilds)
    {
    	Line xml = new Line();
    	xml.setOrientation("bottom");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlLine.initFiles();	
		TestXmlLine test = new TestXmlLine();
		test.save();
    }
}