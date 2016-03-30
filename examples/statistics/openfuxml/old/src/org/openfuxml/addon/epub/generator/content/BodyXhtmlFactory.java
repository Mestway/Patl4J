package org.openfuxml.addon.epub.generator.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.processor.epub.factory.EpubSectionFactory;
import org.openfuxml.renderer.processor.epub.factory.EpubTitleFactory;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class BodyXhtmlFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxExternalMerger.class);
	
	private Namespace ns; 
	private EpubSectionFactory sectionFactory;
	private EpubTitleFactory titleFactory;
	
	public BodyXhtmlFactory(Namespace ns) 
	{
		this.ns=ns;
		titleFactory = new EpubTitleFactory(ns);
		sectionFactory = new EpubSectionFactory(titleFactory,ns);
	}
	
	public Element createBody(Section section) 
	{
		logger.debug("add body");
		//Element body = new Element("body",ns);
		Element body = DocumentHelper.createElement("body",ns); 
		body.add(sectionFactory.createSection(section,1)); 
		
		return body;
	}
}
