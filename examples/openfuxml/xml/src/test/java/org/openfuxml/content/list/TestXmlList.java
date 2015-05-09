package org.openfuxml.content.list;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.TestXmlComment;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlList extends AbstractXmlListTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlList.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, List.class);
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	List actual = create(true);
    	List expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), List.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static List create(boolean withChilds)
    {
    	List xml = new List();
    	
    	if(withChilds)
    	{
    		xml.setComment(TestXmlComment.create(false));
    		xml.setType(TestXmlType.create(false));
    		
    		xml.getItem().add(TestXmlItem.create(false));xml.getItem().add(TestXmlItem.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlList.initFiles();	
		TestXmlList test = new TestXmlList();
		test.save();
    }
}