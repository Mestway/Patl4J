package org.openfuxml.renderer.processor.epub.factory;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.DocumentHelper;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.processor.pre.ExternalContentEagerLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EpubSectionFactory
{
	final static Logger logger = LoggerFactory.getLogger(ExternalContentEagerLoader.class);
	
	private EpubTitleFactory titleFactory;
	private EpubParagraphFactory paragraphFactory;
	
	public EpubSectionFactory(EpubTitleFactory titleFactory,Namespace ns) 
	{
		this.titleFactory=titleFactory;
		paragraphFactory = new EpubParagraphFactory(ns);
	}
	
	public List<Element> createSection(Section section, int depth) 
	{
		List<Element> result = new ArrayList<Element>(); 
		
		for(Object s : section.getContent())
		{
			if(s instanceof Title){result.add(titleFactory.createTitle((Title)s));}
			else if(s instanceof Section){result.addAll(createSection((Section)s, depth++));}
			else if(s instanceof Paragraph){result.add(paragraphFactory.createParagraph((Paragraph)s));}
			else if(s instanceof String){}
			else {logger.warn("Unknwon Content: "+s.getClass().getSimpleName());}
		}		
		return result;
	}
}
