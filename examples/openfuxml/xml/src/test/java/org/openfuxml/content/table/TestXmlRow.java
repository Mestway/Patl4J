package org.openfuxml.content.table;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.layout.TestXmlLayout;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRow extends AbstractXmlTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Row.class);}
    
    @Test
    public void jaxbStructure() throws FileNotFoundException
    {
    	Row actual = create(true);
    	Row expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Row.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Row create(boolean withChilds)
    {
    	Row xml = new Row();
    	
    	if(withChilds)
    	{
    		xml.setLayout(TestXmlLayout.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlRow.initFiles();	
		TestXmlRow test = new TestXmlRow();
		test.save();
    }
}