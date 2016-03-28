package org.openfuxml.addon.wiki.processor.net.fetcher;

import java.text.DecimalFormat;
import java.util.Locale;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.io.txt.ExlpTxtWriter;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.processor.util.WikiBotFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiPageFetcher
{
	final static Logger logger = LoggerFactory.getLogger(WikiPageFetcher.class);
	
	private MediaWikiBot bot;
	private String wikiText;

	private DecimalFormat df;
	
	public WikiPageFetcher(MediaWikiBot bot)
	{
		this.bot=bot;
		df = (DecimalFormat)DecimalFormat.getInstance(Locale.GERMAN);
		df.applyPattern( "#,###,##0" );
	}
	
	public void fetchText(String article)
	{
		try
		{	
			SimpleArticle sa = new SimpleArticle(bot.readContent(article));
				
		    wikiText = sa.getText();
		}
		catch (ActionException e) {logger.error("",e);}
		catch (ProcessException e) {logger.error("",e);}
		
		logger.debug("Article "+article+" fetched. "+df.format(wikiText.length())+" characters.");
	}
	
	public void save(ExlpTxtWriter txtWriter)
	{
		txtWriter.add(wikiText);
		txtWriter.write();
	}
	
	public String getWikiText() {return wikiText;}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		WikiTemplates.init();	
			
		WikiBotFactory wbf = new WikiBotFactory();
		WikiPageFetcher wtf = new WikiPageFetcher(wbf.createBot());
		wtf.fetchText("Bellagio");
    }
}