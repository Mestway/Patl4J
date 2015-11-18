package org.openfuxml.model.xml.addon.jsf;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxJsfTestBootstrap;
import org.openfuxml.xml.addon.jsf.Attribute;

public class TestXmlAttribute extends AbstractXmlJsfTest
{
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Attribute.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Attribute actual = create(true);
    	Attribute expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Attribute.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Attribute create(boolean withChilds)
    {
    	Attribute xml = new Attribute();
    	xml.setName("myName");
    	xml.setRequired(true);
    	xml.setDefault("myDefault");
    	xml.setShortDescription("myShortDescription");
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		OfxJsfTestBootstrap.init();		
			
		TestXmlAttribute.initFiles();	
		TestXmlAttribute test = new TestXmlAttribute();
		test.save();
    }
}