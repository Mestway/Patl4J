package org.openfuxml.content.layout;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLayout extends AbstractXmlLayoutTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Layout.class);
	}
    
    @Test
    public void jaxbStructure() throws FileNotFoundException
    {
    	Layout actual = create(true);
    	Layout expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Layout.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Layout create(boolean withChilds)
    {
    	Layout xml = new Layout();
    	
    	
    	if(withChilds)
    	{
    		xml.getLine().add(TestXmlLine.create(false));xml.getLine().add(TestXmlLine.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlLayout.initFiles();	
		TestXmlLayout test = new TestXmlLayout();
		test.save();
    }
}