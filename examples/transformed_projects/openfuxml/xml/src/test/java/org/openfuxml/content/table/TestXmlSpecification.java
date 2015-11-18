package org.openfuxml.content.table;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.layout.TestXmlAlignment;
import org.openfuxml.content.layout.TestXmlFloat;
import org.openfuxml.content.layout.TestXmlWidth;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSpecification extends AbstractXmlTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass public static void initFiles() {setXmlFile(dirSuffix, Specification.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Specification actual = create(true);
    	Specification expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Specification.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Specification create(boolean withChilds)
    {
    	Specification xml = new Specification();
    	xml.setLong(false);
    	
    	if(withChilds)
    	{
    		xml.setAlignment(TestXmlAlignment.create(false));
    		xml.setWidth(TestXmlWidth.create(false));
    		xml.setColumns(TestXmlColumns.create(false));
    		xml.setFloat(TestXmlFloat.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlSpecification.initFiles();	
		TestXmlSpecification test = new TestXmlSpecification();
		test.save();
    }
}