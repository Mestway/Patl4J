package org.openfuxml.addon.wiki.processor.markup;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.dom4j.Element;

import org.openfuxml.addon.wiki.data.jaxb.MarkupProcessor;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.addon.wiki.data.jaxb.XhtmlProcessor;
import org.openfuxml.addon.wiki.processor.ofx.xml.WikiPageProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlFinalProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlReplaceProcessor;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiInlineProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiInlineProcessor.class);
	
	public static boolean debugOutput = false;
	
	private WikiMarkupProcessor wpMarkup;
	private WikiModelProcessor wpModel;
	private XhtmlReplaceProcessor wpXhtmlR;
	private XhtmlFinalProcessor wpXhtmlF;
	private WikiPageProcessor ofxP;
	
	public WikiInlineProcessor(Cmp cmp) throws OfxConfigurationException
	{
		MarkupProcessor mpXml = cmp.getPreprocessor().getWiki().getMarkupProcessor();
		XhtmlProcessor  xpXml = cmp.getPreprocessor().getWiki().getXhtmlProcessor();
		Templates   templates = cmp.getPreprocessor().getWiki().getTemplates();
		
		wpMarkup = new WikiMarkupProcessor(mpXml.getReplacements(), mpXml.getInjections(),templates);
		wpModel = new WikiModelProcessor();
		wpXhtmlR = new XhtmlReplaceProcessor(xpXml.getReplacements());
		wpXhtmlF = new XhtmlFinalProcessor();
		ofxP = new WikiPageProcessor();
	}
	
	public Section toOfx(String wikiPlain) throws OfxInternalProcessingException
	{
		if(debugOutput){logger.debug("wikiPlain: "+wikiPlain);}
		String wikiMarkup = wpMarkup.process(wikiPlain, "ARTICLE ... ");
		if(debugOutput){logger.debug("wikiMarkup: "+wikiMarkup);}
		String xhtmlModel = wpModel.process(wikiMarkup);
		if(debugOutput){logger.debug("xhtmlModel: "+xhtmlModel);}
		String xhtmlReplace = wpXhtmlR.process(xhtmlModel);
		if(debugOutput){logger.debug("xhtmlReplace: "+xhtmlReplace);}
		String xhtmlFinal = wpXhtmlF.process(xhtmlReplace);
		if(debugOutput){logger.debug("xhtmlFinal: "+xhtmlFinal);}
		Element xml = ofxP.process(xhtmlFinal); 
		if(debugOutput){logger.debug(JaxbUtil.toString(xml));}
//		;
		Section section = (Section)JDomUtil.toJaxb(xml, Section.class);
		return section;		
	}
}
