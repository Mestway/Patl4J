package org.openfuxml.xml.addon.chart;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRendererTimeseries extends AbstractXmlChartTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlRendererTimeseries.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,RendererTimeseries.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	RendererTimeseries actual = create(true);
    	RendererTimeseries expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), RendererTimeseries.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static RendererTimeseries create(boolean withChilds)
    {
    	RendererTimeseries xml = new RendererTimeseries();
    	xml.setCumulate(true);
    	xml.setGap(true);
    	xml.setSumDate(true);
    	xml.setOrderRecords(true);
    	xml.setTimePeriod("myTimePeriod");
    	xml.setFrom(TestXmlRendererTimeseries.getDefaultXmlDate());
    	xml.setTo(TestXmlRendererTimeseries.getDefaultXmlDate());
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlRendererTimeseries.initFiles();	
		TestXmlRendererTimeseries test = new TestXmlRendererTimeseries();
		test.save();
    }
}