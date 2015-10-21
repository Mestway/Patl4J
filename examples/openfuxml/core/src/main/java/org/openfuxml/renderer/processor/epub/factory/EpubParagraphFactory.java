package org.openfuxml.renderer.processor.epub.factory;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.DocumentHelper;

import org.openfuxml.content.ofx.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EpubParagraphFactory
{
	final static Logger logger = LoggerFactory.getLogger(EpubParagraphFactory.class);
	
	private Namespace ns;
	
	public EpubParagraphFactory(Namespace ns)
	{
		this.ns=ns;
	}
	
	public Element createParagraph(Paragraph p)
	{
		Element eParagraph = DocumentHelper.createElement("p",ns);
		
		for(Object s : p.getContent())
		{
			if(s instanceof String){eParagraph.addText((String)s);}
			else {logger.warn("Unknwon Content: "+s.getClass().getSimpleName());}
		}
		
		return eParagraph;
	}
}
