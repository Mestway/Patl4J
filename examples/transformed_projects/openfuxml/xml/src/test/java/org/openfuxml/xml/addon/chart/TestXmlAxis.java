package org.openfuxml.xml.addon.chart;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAxis extends AbstractXmlChartTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlAxis.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Axis.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Axis actual = create(true);
    	Axis expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Axis.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Axis create(boolean withChilds)
    {
    	Axis xml = new Axis();
    	xml.setCode("myCode");
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlAxis.initFiles();	
		TestXmlAxis test = new TestXmlAxis();
		test.save();
    }
}