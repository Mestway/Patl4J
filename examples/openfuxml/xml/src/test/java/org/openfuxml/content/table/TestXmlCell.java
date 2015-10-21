package org.openfuxml.content.table;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCell extends AbstractXmlTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Cell.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Cell actual = create(true);
    	Cell expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Cell.class);
    	JaxbUtil.info(actual);
    	JaxbUtil.info(expected);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Cell create(boolean withChilds)
    {
    	Cell xml = new Cell();
    	
    	if(withChilds)
    	{
//    		xml.getContent().add(TestXmlImage.create(false));
//    		xml.getContent().add(TestXmlParagraph.create(false));
 //   		xml.getContent().add(TestXmlList.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlCell.initFiles();	
		TestXmlCell test = new TestXmlCell();
		test.save();
    }
}