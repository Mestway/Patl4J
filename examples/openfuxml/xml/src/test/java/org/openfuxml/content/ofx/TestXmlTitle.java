package org.openfuxml.content.ofx;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTitle extends AbstractXmlOfxTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Title.class);
	}
    
    @Test
    public void jaxbStructure() throws FileNotFoundException
    {
    	Title actual = create(true);
    	Title expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Title.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Title create(boolean withChilds)
    {
    	Title xml = new Title();
    	xml.setNumbering(true);
    	xml.setValue("myTitle");
    	xml.setLang("myLang");
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlTitle.initFiles();	
		TestXmlTitle test = new TestXmlTitle();
		test.save();
    }
}