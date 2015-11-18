package org.openfuxml.test.addon.jsf;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.jsf.data.jaxb.Example;
import org.openfuxml.addon.jsf.data.jaxb.JsfNsPrefixMapper;
import org.openfuxml.addon.jsf.data.jaxb.Metatag;
import org.openfuxml.addon.jsf.data.jaxb.ObjectFactory;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;


public class TestMetatag
{
	final static Logger logger = LoggerFactory.getLogger(TestMetatag.class);
	
	public TestMetatag()
	{
		
	}
	
	public void xmlConstruct()
	{	
		Metatag metatag = new Metatag();
		
		Tag tag = new Tag();
		tag.setName("Testame");
	
		Metatag.Examples examples = new Metatag.Examples();
		
		Example example = new Example();
		
		Reference r = new Reference();
		r.setTarget("xx3");
		
		Paragraph p = new Paragraph();
		p.getContent().add("Test");
		p.getContent().add(r);
		p.getContent().add("dahinter");
		example.getContent().add(p);
		
		examples.getExample().add(example);
		metatag.setExamples(examples);
		metatag.setTag(tag);
		
		try
		{
			JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
			m.setProperty("com.sun.xml.bind.namespacePrefixMapper",new JsfNsPrefixMapper());

			m.marshal(metatag, System.out);
		}
		catch (JAXBException e) {logger.debug(e);}
	}
		
	public void load(String file) throws FileNotFoundException
	{
		Metatag metatag = (Metatag)JaxbUtil.loadJAXB(file, Metatag.class);
		JaxbUtil.debug(metatag,new JsfNsPrefixMapper());
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing Metatag");
			
		TestMetatag test = new TestMetatag();
		test.xmlConstruct();
		test.load(args[0]);
	}
}