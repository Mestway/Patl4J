package org.openfuxml.xml.addon.chart;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGrid extends AbstractXmlChartTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlGrid.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Grid.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Grid actual = create(true);
    	Grid expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Grid.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Grid create(boolean withChilds)
    {
    	Grid xml = new Grid();
    	xml.setDomain(true);
    	xml.setRange(true);
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlGrid.initFiles();	
		TestXmlGrid test = new TestXmlGrid();
		test.save();
    }
}