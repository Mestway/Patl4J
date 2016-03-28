package org.openfuxml.addon.wiki.processor.template.exlp.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.exlp.core.parser.AbstractLogParser;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogEventHandler;
import net.sf.exlp.interfaces.LogParser;
import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.lang.SystemUtils;
import org.openfuxml.addon.wiki.data.jaxb.Markup;
import org.openfuxml.addon.wiki.data.jaxb.TemplateKv;
import org.openfuxml.addon.wiki.processor.template.exlp.event.WikiKeyValueEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiKeyValueParser extends AbstractLogParser implements LogParser  
{
	final static Logger logger = LoggerFactory.getLogger(WikiKeyValueParser.class);
	
	private TemplateKv wikiKV;
	
	public WikiKeyValueParser(LogEventHandler leh)
	{
		super(leh);
		pattern.add(Pattern.compile("^$"));
		pattern.add(Pattern.compile("^\\|([a-zA-Z]*)=(.*)"));
		pattern.add(Pattern.compile("(.*)"));
		logger.debug("Pattern defined: "+pattern.size());
	}

	public void parseLine(String line)
	{
		allLines++;
		boolean unknownPattern = true;
		for(int i=0;i<pattern.size();i++)
		{
			Matcher m=pattern.get(i).matcher(line);
			if(m.matches())
			{
				switch(i)
				{
					case 0: blank();break;
					case 1: key(m);break;
					case 2: value(m);break;
				}
				i=pattern.size();
				unknownPattern=false;
			}
		}
		if(unknownPattern)
		{
			logger.warn("Unknown pattern: " +line);
			unknownLines++;
		}
	}
	
	private void blank()
	{
		if(wikiKV!=null)
		{
			
		}
	}
	
	private void key(Matcher m)
	{
		if(wikiKV!=null){event();}
		
		wikiKV = new TemplateKv();
		wikiKV.setKey(m.group(1));
		wikiKV.setMarkup(new Markup());
		wikiKV.getMarkup().setValue(m.group(2));
	}
	
	public void value(Matcher m)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(wikiKV.getMarkup().getValue());
		sb.append(SystemUtils.LINE_SEPARATOR);
		sb.append(m.group(0));
		wikiKV.getMarkup().setValue(sb.toString());
	}
	
	@Override
	public void close()
	{
		if(wikiKV!=null){event();};
	}
	
	public void event()
	{
		LogEvent event = new WikiKeyValueEvent(wikiKV);
		leh.handleEvent(event);
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		logger.warn("This is only a pattern test-class!");
		
		String sPattern = "^\\|([a-zA-Z]*)=(.*)";
		String sTest    = "|Goal=blabla bla blablub";
		
		logger.debug("Pattern: "+sPattern);
		logger.debug("Test:    "+sTest);
		
		Pattern p = Pattern.compile(sPattern);
		Matcher m = p.matcher(sTest);
		logger.debug(""+m.matches());
		if(m.matches())
		{
			logger.debug("Group Count "+m.groupCount());
			for(int i=0;i<=m.groupCount();i++)
			{
				logger.debug(i+" "+m.group(i));
			}
		}
	}
}