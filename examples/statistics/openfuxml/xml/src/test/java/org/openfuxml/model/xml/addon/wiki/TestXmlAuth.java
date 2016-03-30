package org.openfuxml.model.xml.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.Auth;
import org.openfuxml.test.OfxXmlTstBootstrap;

public class TestXmlAuth extends AbstractXmlWikiTest
{	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"auth.xml");
	}
    
    @Test
    public void testAuth() throws FileNotFoundException
    {
    	Auth actual = create();
    	Auth expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Auth.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Auth create()
    {
    	Auth xml = new Auth();
    	xml.setType("wiki");
    	xml.setName("name");
    	xml.setPassword("xyz");
    	return xml;
    }
    
    public void save() {save(create(),fXml,true);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlAuth.initFiles();	
		TestXmlAuth test = new TestXmlAuth();
		test.save();
    }
}