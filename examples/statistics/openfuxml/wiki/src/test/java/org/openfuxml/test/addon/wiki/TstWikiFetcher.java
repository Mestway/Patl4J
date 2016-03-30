package org.openfuxml.test.addon.wiki;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.addon.wiki.processor.net.fetcher.WikiPageFetcher;
import org.openfuxml.addon.wiki.processor.util.WikiBotFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TstWikiFetcher
{
	final static Logger logger = LoggerFactory.getLogger(TstWikiFetcher.class);
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/properties/user.properties");
		Configuration config = ConfigLoader.init();	
				
		WikiBotFactory wbf = new WikiBotFactory();
		wbf.setUrl(config.getString("wiki.url"));
		wbf.setHttpDigestAuth(config.getString("wiki.http.user"), config.getString("wiki.http.password"));
		wbf.setWikiAuth(config.getString("wiki.user"), config.getString("wiki.password"));
		
		WikiPageFetcher tw = new WikiPageFetcher(wbf.createBot());
		tw.fetchText("Category:Use_Case");
		String wikiText = tw.getWikiText();
		logger.debug(wikiText);
//		WikiContentIO.writeTxt(dirWiki, article+"-"+Status.txtFetched+".txt", wikiText);
    }
}