package org.openfuxml.addon.epub.generator.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.DocumentHelper;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class PartXhtmlFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxExternalMerger.class);
	
	private BodyXhtmlFactory bodyFactory;
	private Namespace nsXhtml; 
	
	public PartXhtmlFactory(Namespace nsXhtml) 
	{
		this.nsXhtml=nsXhtml;
		bodyFactory = new BodyXhtmlFactory(nsXhtml);
	}
	
	public Element createPart(Section section) 
	{
		Element part = DocumentHelper.createElement(new QName("html",nsXhtml)); 
		part.add(getHead()); 
		part.add(bodyFactory.createBody(section)); 
		return part;
	}
	
	private Element getHead() 
	{
		logger.debug("head");
		Element head = DocumentHelper.createElement(new QName("head",nsXhtml)); 
		
		Element metaContent = DocumentHelper.createElement(new QName("meta",nsXhtml)); 
		metaContent.addAttribute("http-equiv", "Content-Type"); 
		metaContent.addAttribute("content", "text/html; charset=UTF-8"); 
		head.add(metaContent); 
		
		Element metaName = DocumentHelper.createElement(new QName("meta",nsXhtml)); 
		metaName.addAttribute("name", "DC.identifier"); 
		metaName.addAttribute("content", "id_Hello_World"); 
		head.add(metaName); 
		
		Element link = DocumentHelper.createElement(new QName("link",nsXhtml)); 
		link.addAttribute("rel", "schema.DC"); 
		link.addAttribute("href", "http://purl.org/dc/elements/1.1/"); 
		head.add(link); 
		
		Element title = DocumentHelper.createElement(new QName("title",nsXhtml)); 
		title.setText("TestTitle"); 
		head.add(title); 
		
		return head;
	}

}
