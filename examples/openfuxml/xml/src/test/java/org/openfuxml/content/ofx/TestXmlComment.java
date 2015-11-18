package org.openfuxml.content.ofx;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlComment extends AbstractXmlOfxTest
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
	public static void initFiles()
	{
		setXmlFile(dirSuffix, Comment.class);
	}
    
    @Test
    public void jaxbStructure() throws FileNotFoundException
    {
    	Comment actual = create(true);
    	Comment expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Comment.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Comment create(boolean withChilds)
    {
    	Comment xml = new Comment();
    	
    	if(withChilds)
    	{
    		xml.getRaw().add(TestXmlRaw.create());
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlComment.initFiles();	
		TestXmlComment test = new TestXmlComment();
		test.save();
    }
}