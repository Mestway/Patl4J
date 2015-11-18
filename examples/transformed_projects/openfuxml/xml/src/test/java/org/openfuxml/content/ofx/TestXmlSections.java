package org.openfuxml.content.ofx;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.media.TestXmlImage;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSections extends AbstractXmlOfxTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Sections.class);
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Sections actual = create(true);
    	Sections expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Sections.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Sections create(boolean withChilds)
    {
    	Sections xml = new Sections();
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlSections.initFiles();	
		TestXmlSections test = new TestXmlSections();
		test.save();
    }
}