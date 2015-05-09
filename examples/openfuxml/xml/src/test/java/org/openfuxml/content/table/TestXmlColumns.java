package org.openfuxml.content.table;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlColumns extends AbstractXmlTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Columns.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Columns actual = create(true);
    	Columns expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Columns.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Columns create(boolean withChilds)
    {
    	Columns xml = new Columns();
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlColumns.initFiles();	
		TestXmlColumns test = new TestXmlColumns();
		test.save();
    }
}