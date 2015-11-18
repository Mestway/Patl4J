/*
 * Created on 23.03.2005
 */
package org.openfuxml.client.gui.util;

import java.util.ArrayList;

import net.sf.exlp.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.openfuxml.util.config.factory.ClientConfFactory;


/**
 * @author Thorsten Kisner
 */
public class LogSyntaxHighlightner
{
	final static Logger logger = LoggerFactory.getLogger(LogSyntaxHighlightner.class);
	
	private StyledText textLog;
	private int aktuellerIndex;
	private Display display;
	
	private ArrayList<ArrayList<String>> alKeys;
	private ArrayList<Integer> alColorcode;
	
	public LogSyntaxHighlightner(StyledText textLog,Display display,Configuration config)
	{
		this.textLog=textLog;
		this.display=display;
		aktuellerIndex=0;
		initPattern(config);
	}
	
	public LogSyntaxHighlightner(Configuration config)
	{
		initPattern(config);
	}
	
	private void initPattern(Configuration config)
	{
		alKeys = new ArrayList<ArrayList<String>>();
		alColorcode = new ArrayList<Integer>();
		String[] colors = config.getStringArray("highlight/color/@swt");
		for(String color : colors)
		{
			alColorcode.add(new Integer(color));
			ArrayList<String> alColorKey = new ArrayList<String>();
			String[] keys = config.getStringArray("highlight/color[@swt='"+color+"']/key");
			for(String key : keys)
			{
				alColorKey.add(key);
			}
			alKeys.add(alColorKey);
		}
	}
	
	public void clear()	{aktuellerIndex=0;}
	
	public void highlight(String highlightline)
	{
		int colorCode=0;
		for(ArrayList<String> alColorkeys : alKeys)
		{
			for(String key : alColorkeys)
			{

				int index = highlightline.indexOf(key);
				if(index!=-1)
				{
					StyleRange styleRange = new StyleRange();
		        	styleRange.start = aktuellerIndex+index;
		        	styleRange.length = key.length();
//		        	styleRange.fontStyle = SWT.BOLD;
		        	styleRange.foreground = display.getSystemColor(alColorcode.get(colorCode));
		        	textLog.setStyleRange(styleRange);
		        	aktuellerIndex=aktuellerIndex+highlightline.length();
					return;
				}
			}
			colorCode++;
		}        
        aktuellerIndex=aktuellerIndex+highlightline.length();
	}
	
	public static void main(String[] args)
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ClientConfFactory ccf = new ClientConfFactory();
		ccf.init("openFuXML.xml");
		
		Configuration config = ccf.getConfiguration();
		
		LogSyntaxHighlightner lsh = new LogSyntaxHighlightner(config);
		lsh.highlight("test");
	}
}
