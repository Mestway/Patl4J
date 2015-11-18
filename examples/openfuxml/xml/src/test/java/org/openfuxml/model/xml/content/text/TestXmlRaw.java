package org.openfuxml.model.xml.content.text;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.text.Raw;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRaw extends AbstractXmlTextTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Raw.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Raw actual = create(true);
    	Raw expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Raw.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Raw create(boolean withChilds)
    {
    	Raw xml = new Raw();
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	xml.setValue("myValue");
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlRaw.initFiles();	
		TestXmlRaw test = new TestXmlRaw();
		test.save();
    }
}