package org.openfuxml.xml.addon.chart;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTitle extends AbstractXmlChartTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlTitle.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Title.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Title actual = create(true);
    	Title expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Title.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Title create(boolean withChilds)
    {
    	Title xml = new Title();
    	xml.setKey("myKey");
    	xml.setLabel("myLabel");
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