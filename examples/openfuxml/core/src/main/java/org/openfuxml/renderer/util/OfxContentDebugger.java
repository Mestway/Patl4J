package org.openfuxml.renderer.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxContentDebugger
{
	final static Logger logger = LoggerFactory.getLogger(OfxContentDebugger.class);
	
	public static void debug(List<String> content) 
	{
		for(String s : content)
		{
			logger.debug(s);
		}
	}
}