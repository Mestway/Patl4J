package org.openfuxml.xml.addon.chart;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlDimension extends AbstractXmlChartTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlDimension.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Dimension.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Dimension actual = create(true);
    	Dimension expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Dimension.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Dimension create(boolean withChilds)
    {
    	Dimension xml = new Dimension();
    	xml.setHeight(20);
    	xml.setWidth(30);
    	xml.setRatio(1.3);
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlDimension.initFiles();	
		TestXmlDimension test = new TestXmlDimension();
		test.save();
    }
}