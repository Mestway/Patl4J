package org.openfuxml.content.media;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlMedia extends AbstractXmlMediaTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix, Media.class);}
    
    @Test
    public void jaxbStructure() throws FileNotFoundException
    {
    	Media actual = create(true);
    	Media expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Media.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Media create(boolean withChilds)
    {
    	Media xml = new Media();
    	xml.setId("myId");
    	xml.setSrc("mySrc");
    	xml.setDst("myDst");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlMedia.initFiles();	
		TestXmlMedia test = new TestXmlMedia();
		test.save();
    }
}