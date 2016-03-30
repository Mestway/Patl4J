package org.openfuxml.addon.wiki.processor.xhtml;

import net.sf.exlp.util.io.StringIO;

//import org.jdom2.JDOMException;
//
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.mods.OfxPushUp;
import org.openfuxml.addon.wiki.processor.xhtml.mods.XhtmlAHxMerge;
import org.openfuxml.addon.wiki.processor.xhtml.mods.XhtmlCodePreMover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XhtmlFinalProcessor extends AbstractWikiProcessor implements WikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(XhtmlFinalProcessor.class);
	
	public XhtmlFinalProcessor()
	{
		
	}
	
	@Override
	protected void processCategory(Content content)
	{
		Category category = content.getCategory();
		for(Page page : category.getPage())
		{
			processPage(page);
		}
	}
	
	@Override
	protected void processPage(Content content)
	{
		Page page = content.getPage();
		processPage(page);
	}
	
	public void processPage(Page page)
	{
		String fNameXhtml = page.getFile()+"."+WikiProcessor.WikiFileExtension.xhtml;
		String txtMarkup = StringIO.loadTxt(srcDir, fNameXhtml);
		String result = process(txtMarkup);
		StringIO.writeTxt(dstDir, fNameXhtml, result);
	}
	
	public String process(String xHtml)
	{
		OfxPushUp pushUp = new OfxPushUp();
		XhtmlAHxMerge merger = new XhtmlAHxMerge();
		XhtmlCodePreMover moveCodePre = new XhtmlCodePreMover();
		
		xHtml = pushUp.moveOfxElements(xHtml);
		xHtml = merger.merge(xHtml);
		try
		{
			xHtml = moveCodePre.move(xHtml);
		}
		catch (Exception e)
		{
			//TODO Exception Handling
			e.printStackTrace();
		}
		
		
		xHtml = removeWellFormed(xHtml);
		return xHtml;
	}
	
	public String removeWellFormed(String text)
	{
		int testIndex = text.indexOf("<wiki />");
		if(testIndex>0)
		{
			return "";
		}
		else
		{
			int from = text.indexOf("<wiki>")+6;
			int to = text.lastIndexOf("</wiki>");
			return text.substring(from,to);
		}
	}
}
