package org.openfuxml.addon.wiki.processor.net;

import java.io.File;

import net.sf.exlp.util.io.txt.ExlpTxtWriter;

import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.net.fetcher.WikiCategoryFetcher;
import org.openfuxml.addon.wiki.processor.net.fetcher.WikiPageFetcher;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiBotFactory;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiContentFetcher extends AbstractWikiProcessor implements WikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiContentFetcher.class);
	
	private WikiBotFactory wbf;
	private ExlpTxtWriter txtWriter;

	public WikiContentFetcher(WikiBotFactory wbf)
	{
		this.wbf=wbf;
		txtWriter = new ExlpTxtWriter();
	}
	
	@Override
	public void setDirectories(File srcDir, File dstDir)
	{
		super.setDirectories(srcDir, dstDir);
		txtWriter.setDirName(dstDir.getAbsolutePath());
	}
	
	@Override
	protected void processPage(Content content)
	{
		txtWriter.clear();
		Page page = content.getPage();
		page.setFile(WikiContentIO.getFileFromSource(content.getSource()));
		txtWriter.setFileName(page.getFile()+".txt");
		WikiPageFetcher wpf = new WikiPageFetcher(wbf.getBot());
		wpf.fetchText(page.getName());
		wpf.save(txtWriter);
	}
	
	@Override
	protected void processCategory(Content content)
	{
		Category category = content.getCategory();
		WikiCategoryFetcher wcf = new WikiCategoryFetcher(wbf.getBot());
		wcf.fetchCategory(category.getName());
		wcf.setTargetFilePrefix(WikiContentIO.getFileFromSource(content.getSource(), ""));
		wcf.fetchArticles(txtWriter,category);
	}
}