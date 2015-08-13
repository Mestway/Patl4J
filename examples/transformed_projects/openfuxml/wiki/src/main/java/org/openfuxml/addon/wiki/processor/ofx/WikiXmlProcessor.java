package org.openfuxml.addon.wiki.processor.ofx;

import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.ofx.xml.WikiCategoryProcessor;
import org.openfuxml.addon.wiki.processor.ofx.xml.WikiPageProcessor;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiXmlProcessor extends AbstractWikiProcessor implements WikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiXmlProcessor.class);
	
	private WikiPageProcessor pageProcessor;
	private WikiCategoryProcessor categoryProcessor;
	
	public WikiXmlProcessor()
	{
		WikiTemplates.init();
	}
	
	@Override
	protected void processCategory(Content content) throws OfxAuthoringException, OfxInternalProcessingException
	{
		getCategoryProcessor().processCategory(content);
	}
	
	@Override
	protected void processPage(Content content) throws OfxAuthoringException, OfxInternalProcessingException
	{
		Page page = content.getPage();
		getPageProcessor().processPage(page);
	}
	
	private WikiPageProcessor getPageProcessor()
	{
		if(pageProcessor==null)
		{
			pageProcessor = new WikiPageProcessor();
			pageProcessor.setDirectories(srcDir, dstDir);
		}
		return pageProcessor;
	}
	
	private WikiCategoryProcessor getCategoryProcessor()
	{
		if(categoryProcessor==null)
		{
			categoryProcessor = new WikiCategoryProcessor(getPageProcessor());
			categoryProcessor.setDirectories(srcDir, dstDir);
		}
		return categoryProcessor;
	}
}