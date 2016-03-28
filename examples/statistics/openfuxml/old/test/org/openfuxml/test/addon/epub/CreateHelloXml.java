package org.openfuxml.test.addon.epub;

import java.io.File;
import java.util.Random;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Metadata;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.util.xml.OfxNsPrefixMapper;

import de.svenjacobs.loremipsum.LoremIpsum;

public class CreateHelloXml
{
	final static Logger logger = LoggerFactory.getLogger(CreateHelloXml.class);
	
	private LoremIpsum li;
	private Random rnd;
	
	public CreateHelloXml()
	{
		li = new LoremIpsum();
		rnd = new Random();
		logger.debug(li.getWords(3+rnd.nextInt(100)));
	}
	
	public void generate(String fileName)
	{
		Ofxdoc ofxdoc = new Ofxdoc();
		ofxdoc.setMetadata(createMetadata());
		ofxdoc.setContent(createContent());
		JaxbUtil.save(new File(fileName), ofxdoc, new OfxNsPrefixMapper(), true);
	}
	
	private Metadata createMetadata()
	{
		Metadata meta = new Metadata();
		
		Title title = new Title();
		title.setValue(li.getWords(5));
		meta.setTitle(title);
		
		return meta;
	}
	
	private Content createContent()
	{
		Content content = new Content();
		for(int i=0;i<(2+rnd.nextInt(2));i++)
		{
			Section section = createSection();
			content.getContent().add(section);
		}
		
		return content;
	}
	
	private Section createSection()
	{
		Section section = new Section();
		Title title = new Title();
		title.setValue(li.getWords(3+rnd.nextInt(10)));
		section.getContent().add(title);
		for(int i=0;i<(1+rnd.nextInt(7));i++)
		{
			Paragraph p = new Paragraph();
			p.getContent().add(li.getWords(150+rnd.nextInt(500)));
			section.getContent().add(p);
		}
		return section;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		CreateHelloXml hw = new CreateHelloXml();
		hw.generate("resources/data/xml/epub/helloworld.xml");
	}
}
