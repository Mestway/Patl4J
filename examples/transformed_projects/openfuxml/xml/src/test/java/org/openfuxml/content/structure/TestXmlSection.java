package org.openfuxml.content.structure;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSection extends AbstractXmlStructureTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass public static void initFiles(){setXmlFile(dirSuffix, Section.class);}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Section actual = create(true);
    	Section expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Section.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Section create(boolean withChilds)
    {
    	Section xml = new Section();
    	xml.setContainer(true);
    	xml.setInclude("myInclude");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlSection.initFiles();	
		TestXmlSection test = new TestXmlSection();
		test.save();
    }
}