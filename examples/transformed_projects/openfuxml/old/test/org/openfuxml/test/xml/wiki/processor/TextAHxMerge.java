package org.openfuxml.test.xml.wiki.processor;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import org.openfuxml.addon.wiki.OfxWikiEngine;
import org.openfuxml.addon.wiki.processor.xhtml.mods.XhtmlAHxMerge;

public class TextAHxMerge
{
	final static Logger logger = LoggerFactory.getLogger(TextAHxMerge.class);
	public static enum Status {txtFetched,txtProcessed,xhtmlRendered,xhtmlProcessed,xhtmlFinal,ofx};

	private Configuration config;
	
	public TextAHxMerge(Configuration config)
	{
		this.config=config;
	}
	
	public Document merge(Document doc)  
	{
		XhtmlAHxMerge merger = new XhtmlAHxMerge();
		logger.debug("Beging merge ...");
		String txt = merger.merge(JDomUtil.docToTxt(doc));
		logger.debug("End merge ...");
		doc = JDomUtil.txtToDoc(txt);
		return doc;
	}
	
	public Document load(String article) 
	{
		Document doc = null; 
		try
		{
			String dirWiki = config.getString("/ofx/dir[@type='wiki']");
			File f = new File(dirWiki+"/"+article+"-"+OfxWikiEngine.Status.xhtmlFinal+".xhtml");
			doc = new SAXReader().read( f ); 
		}
		catch (Exception e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
		logger.debug("Doc loaded ...");
		return doc;
	}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/config/wiki/wiki.xml");
		Configuration config = ConfigLoader.init();
			
		logger.debug("Starting ...");
			
		TextAHxMerge testMerger = new TextAHxMerge(config);
		Document doc = testMerger.load("Bellagio"); 
//		JDomUtil.debugElement(doc.getRootElement());
		doc = testMerger.merge(doc);
		JDomUtil.debugElement(doc.getRootElement());
    }
}
