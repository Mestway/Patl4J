package org.openfuxml.addon.wiki.processor.markup;

import java.io.File;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Injections;
import org.openfuxml.addon.wiki.data.jaxb.Markup;
import org.openfuxml.addon.wiki.data.jaxb.ObjectFactory;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.data.jaxb.Replacements;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiConfigXmlXpathHelper;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.addon.wiki.util.WikiContentIO;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiMarkupProcessor extends AbstractWikiProcessor implements WikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiMarkupProcessor.class);
	private final static String ls = SystemUtils.LINE_SEPARATOR;
	
	public static enum InjectionType {xml,wiki};
	
	private Replacements replacements;
	private Injections injections;
	private Templates templates;
	
	private String wikiText;
	private ObjectFactory of;
	
	private int templateCounter;
	private static String templateStartDelemiter = "{{";
	private static String templateEndDelemiter = "}}";
	
	@Deprecated
	private File dirInjection;
	private int injectionId;
	
	public WikiMarkupProcessor(Cmp cmp) throws OfxConfigurationException
	{
		this(cmp.getPreprocessor().getWiki().getMarkupProcessor().getReplacements(),
				cmp.getPreprocessor().getWiki().getMarkupProcessor().getInjections(),
				cmp.getPreprocessor().getWiki().getTemplates());
	}
	
	public WikiMarkupProcessor(Replacements replacements, Injections injections, Templates templates) throws OfxConfigurationException
	{
		this.templates=templates;
		this.replacements = WikiConfigXmlXpathHelper.initReplacements(replacements);
		this.injections = WikiConfigXmlXpathHelper.initInjections(injections);
		templateCounter=0;
	}
	
	@Override
	protected void processCategory(Content content) throws OfxInternalProcessingException
	{
		Category category = content.getCategory();
		for(Page page : category.getPage())
		{
			processPage(page);
		}
	}
	
	@Override
	protected void processPage(Content content) throws OfxInternalProcessingException
	{
		Page page = content.getPage();
		processPage(page);
	}
	
	protected void processPage(Page page) throws OfxInternalProcessingException
	{
		logger.debug("Processing Page: "+page.getName());
		String fName = page.getFile()+"."+WikiProcessor.WikiFileExtension.txt;
		String txt = StringIO.loadTxt(srcDir, fName);
		String result = process(txt, page.getName());
		StringIO.writeTxt(dstDir, fName, result);
	}
	
	
	public String process(String wikiPlain, String article) throws OfxInternalProcessingException
	{
		//TODO Check the meaning of article!!
		this.wikiText=wikiPlain;
		for(Wikireplace replace : replacements.getWikireplace()){processReplacements(replace);}
		for(Wikiinjection inject : injections.getWikiinjection()){wikiInject(inject,article);}
		for(Template template : templates.getTemplate()){processTemplate(template);}
		return this.wikiText;
	}

	private void processReplacements(Wikireplace replace)
	{
		wikiText = wikiText.replaceAll(replace.getFrom(), replace.getTo());
	}
	
	private void processTemplate(Template template) throws OfxInternalProcessingException
	{
		logger.debug("Processing Template: "+template.getName());
		int beginIndex=-1;
		while((beginIndex=wikiText.indexOf(templateStartDelemiter+template.getName()))>=0)
		{
			//TODO Nesting templates
			logger.warn("Nesting Templates are not supported!!");
			int endIndex = wikiText.indexOf(templateEndDelemiter);
			StringBuffer sb = new StringBuffer();
			sb.append(wikiText.substring(0, beginIndex));
			sb.append(createExternalTemplate(template,wikiText.substring(beginIndex+templateStartDelemiter.length()+template.getName().length(), endIndex)));
			sb.append(wikiText.substring(endIndex+templateEndDelemiter.length(), wikiText.length()));
			wikiText = sb.toString();
		}
	}
	
	private String createExternalTemplate(Template templateDef, String templateMarkup) throws OfxInternalProcessingException
	{	
		Template template = new Template();
		template.setName(templateDef.getName());
		template.setId(getNextTemplateId());
		template.setMarkup(new Markup());
		template.getMarkup().setValue(templateMarkup);
		
		File f = new File(this.getDir(WikiProcessor.WikiDir.wikiTemplate), template.getId()+"."+WikiProcessor.WikiFileExtension.xml);
		JaxbUtil.save(f, template, true);
		
		StringBuffer sb = new StringBuffer();
		sb.append(SystemUtils.LINE_SEPARATOR);
		sb.append("<wiki:injection id=\"");
		sb.append(template.getId());
		sb.append("\"/>");
		sb.append(SystemUtils.LINE_SEPARATOR);
		
		return sb.toString();
	}
	
	private void wikiInject(Wikiinjection inject, String article)
	{
		while(wikiText.indexOf("<"+inject.getWikitag()+">")>0)
		{
			logger.warn("Injection should start, but currently NYI");
			inject.setId(""+injectionId);injectionId++;
			inject.setArticle(article);
			
			StringBuffer sbDebug = new StringBuffer();
			String startTag = "<"+inject.getWikitag()+">";
			int from = wikiText.indexOf(startTag);
			int to = wikiText.indexOf("</"+inject.getWikitag()+">");
			
			StringBuffer injectionSb = WikiContentIO.toString(inject);
			logger.debug(injectionSb.toString());
			inject.setWikicontent(of.createWikiinjectionWikicontent());
			inject.getWikicontent().setValue(wikiText.substring(from+startTag.length(), to));
			
			WikiContentIO.toFile(inject,dirInjection);
			
			sbDebug.append("Injection: "+from+" "+to);
			sbDebug.append(" oldSize="+wikiText.length());
			StringBuffer sb = new StringBuffer();
				sb.append(wikiText.substring(0, from-1));
				if(inject.getOfxtag()!=null && inject.getOfxtag().length()>0)
				{
					sb.append(injectionSb);
					sb.append(ls);
				}
				sb.append(wikiText.substring(to+inject.getWikitag().length()+3+1,wikiText.length()));
			wikiText=sb.toString();
			sbDebug.append(" newSize="+wikiText.length());
			logger.debug(sbDebug.toString());
		}
	}
	
	private String getNextTemplateId()
	{
		templateCounter++;
		return ""+templateCounter;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String propFile = "resources/properties/user.properties";
		if(args.length==1){propFile=args[0];}
			
		ConfigLoader.add(propFile);
		Configuration config = ConfigLoader.init();
			
		String fnCmp = config.getString("ofx.xml.cmp");
		String fnWikiTxt = config.getString("wiki.processor.test.markup");
		
		logger.debug("Cmp:  "+fnCmp);
		logger.debug("Wiki: "+fnWikiTxt);
		
		Cmp cmp = (Cmp)JaxbUtil.loadJAXB(fnCmp, Cmp.class);
		WikiMarkupProcessor wpMarkup = new WikiMarkupProcessor(cmp);
		
		String wikiText = StringIO.loadTxt(fnWikiTxt);
		logger.debug("Wiki (Before): "+wikiText);
		
		wikiText=wpMarkup.process(wikiText, "test");
		logger.debug("Wiki (After): "+wikiText);
	}
}