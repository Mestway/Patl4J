package org.openfuxml.content.ofx;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRaw extends AbstractXmlOfxTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Raw.class);
	}
    
    @Ignore @Test
    public void jaxbStructure() throws FileNotFoundException
    {
        Raw actual = create();
        Raw expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Raw.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Raw create()
    {
        Raw xml = new Raw();
    	xml.setValue("myValue");
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlRaw.initFiles();
		TestXmlRaw test = new TestXmlRaw();
		test.save();
    }
}