package org.openfuxml.model.xml.addon.jsf;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxJsfTestBootstrap;
import org.openfuxml.xml.addon.jsf.Component;

public class TestXmlComponent extends AbstractXmlJsfTest
{
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,Component.class.getSimpleName()+".xml");
	}
    
    @Test
    public void xml() throws FileNotFoundException
    {
    	Component actual = create(true);
    	Component expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Component.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Component create(boolean withChilds)
    {
    	Component xml = new Component();
    	
    	if(withChilds)
    	{
    		xml.getAttribute().add(TestXmlAttribute.create(false));
    		xml.getAttribute().add(TestXmlAttribute.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml);}
	
	public static void main(String[] args)
    {
		OfxJsfTestBootstrap.init();		
			
		TestXmlComponent.initFiles();	
		TestXmlComponent test = new TestXmlComponent();
		test.save();
    }
}