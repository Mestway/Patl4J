package org.openfuxml.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.exlp.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.producer.postprocessors.HtmlPrettyFormatter;
import org.openfuxml.producer.postprocessors.HtmlTagSubstitutor;

public class OfxApp
{
	public static enum Target {HtmlPrettyFormatter, HtmlTagSubstitutor};
	
	final static Logger logger = LoggerFactory.getLogger(OfxApp.class);
	
	public static String version;
	
	public OfxApp()
	{

	}
	
	public void start(String args[])
	{
		try
		{
			Target target = Target.valueOf(args[0]);
			logger.info("Target "+target);
			switch (target)
			{
				case HtmlPrettyFormatter:	runHtmlPrettyFormatter(args);break;
				case HtmlTagSubstitutor:	runHtmlTagSubstitutor(args);break;
				default:		logger.warn("Unknown target");break;
			}
		}
		catch (IllegalArgumentException e){exit("Invalid Paramter: "+args[0]);}
	}
	
	private void runHtmlPrettyFormatter(String[] args)
	{
		File startDir = new File(args[1]);
		if(startDir.exists() && startDir.isDirectory())
		{
			HtmlPrettyFormatter hpf = new HtmlPrettyFormatter(args[2]);
			Collection<String> results = new ArrayList<String>();
			hpf.start(startDir, results);
		}
	}
	
	private void runHtmlTagSubstitutor(String[] args)
	{
		File startDir = new File(args[1]);
		if(startDir.exists() && startDir.isDirectory())
		{
			HtmlTagSubstitutor hts = new HtmlTagSubstitutor(args[2]);
			Collection<String> results = new ArrayList<String>();
			hts.start(startDir, results, args[3]);
		}
	}
	
	public static void exit(String s)
	{
		logger.fatal(s);
		logger.info(getArgs());
		System.exit(-1);
	}
	
	public static String getArgs()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		for(Target t : Target.values())
		{
			sb.append(t+"|");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(">");
		return sb.toString();
	}
	
	public static void main(String args[])
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
//		Configuration config = ConfigLoader.load("notifier.xml");
		
		OfxApp nApp = new OfxApp();
		nApp.start(args);
	}
}
