package org.openfuxml.content.layout;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAlignment extends AbstractXmlLayoutTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Alignment.class);
	}
    
    @Test
    public void jaxbStructure() throws FileNotFoundException
    {
    	Alignment actual = create(true);
    	Alignment expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Alignment.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Alignment create(boolean withChilds)
    {
    	Alignment xml = new Alignment();
    	xml.setHorizontal("center");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlAlignment.initFiles();	
		TestXmlAlignment test = new TestXmlAlignment();
		test.save();
    }
}