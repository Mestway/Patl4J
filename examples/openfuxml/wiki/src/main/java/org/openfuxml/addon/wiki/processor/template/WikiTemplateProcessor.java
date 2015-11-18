package org.openfuxml.addon.wiki.processor.template;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.DocumentHelper;

import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.addon.wiki.processor.markup.WikiInlineProcessor;
import org.openfuxml.addon.wiki.processor.template.transformator.WikiTemplateKeyValueTransformator;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiConfigXmlXpathHelper;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiTemplateProcessor extends AbstractWikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiTemplateProcessor.class);
	
	private Templates templates;
	
	private WikiTemplateKeyValueTransformator kvTransformator;
	
	public WikiTemplateProcessor(WikiInlineProcessor wikiInlineProcessor, Templates templates) 
	{
		this.templates=templates;
		kvTransformator = new WikiTemplateKeyValueTransformator(wikiInlineProcessor);
	}
	
	public void process() throws OfxInternalProcessingException, OfxConfigurationException
	{
		File fTemplateDir = getDir(WikiProcessor.WikiDir.wikiTemplate);
		for(File fTemplate : fTemplateDir.listFiles())
		{
			try
			{
				Template template = (Template)JaxbUtil.loadJAXB(fTemplate.getAbsolutePath(), Template.class);
				File fOfxTemplate = new File(getDir(WikiProcessor.WikiDir.ofxTemplate),template.getId()+".xml");
				Document doc = processTemplate(template); 
				JDomUtil.save(doc, fOfxTemplate, new OutputFormat()); 
			}
			catch (FileNotFoundException e)
			{
				throw new OfxInternalProcessingException(e.getMessage());
			}
		}
	}
	
	private Document processTemplate(Template template) throws OfxConfigurationException 
	{
		Document doc = DocumentHelper.createDocument(); 
		
		Template templateDef = WikiConfigXmlXpathHelper.getTemplate(templates, template.getName());
		Element e = kvTransformator.transform(templateDef,template); 
		doc.setRootElement(e); 
		return doc;
	}
	
}
