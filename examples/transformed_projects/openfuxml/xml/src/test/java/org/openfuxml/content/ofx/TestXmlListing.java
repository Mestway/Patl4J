package org.openfuxml.content.ofx;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlListing extends AbstractXmlOfxTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Listing.class);
	}
    
    @Test
    public void xmlStructure() throws FileNotFoundException
    {
        Listing actual = create(true);
        Listing expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Listing.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Listing create(boolean withChilds)
    {
        Listing xml = new Listing();
    	xml.setId("myId");
        xml.setCodeLang("myCodeLang");

    	if(withChilds)
    	{
    		xml.setTitle(TestXmlTitle.create(false));
            xml.setRaw(TestXmlRaw.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlListing.initFiles();
		TestXmlListing test = new TestXmlListing();
		test.save();
    }
}