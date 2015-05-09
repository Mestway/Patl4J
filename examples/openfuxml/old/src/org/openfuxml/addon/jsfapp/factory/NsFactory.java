package org.openfuxml.addon.jsfapp.factory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Namespace;

public class NsFactory
{
	final static Logger logger = LoggerFactory.getLogger(NsFactory.class);
	
	private static Map<String,Namespace> mNs; 
		
	private static void init()
	{
		mNs = new Hashtable<String,Namespace>(); 
		mNs.put("html", Namespace.getNamespace("html","http://www.w3.org/1999/xhtml")); 
		mNs.put("jsp", Namespace.getNamespace("jsp", "http://java.sun.com/JSP/Page")); 
		mNs.put("f", Namespace.getNamespace("f","http://java.sun.com/jsf/core")); 
		mNs.put("h", Namespace.getNamespace("h","http://java.sun.com/jsf/html")); 
		mNs.put("a4j", Namespace.getNamespace("a4j","http://richfaces.org/a4j")); 
		mNs.put("rich", Namespace.getNamespace("rich","http://richfaces.org/rich")); 
	}
	
	public static synchronized List<Namespace> getNs(String... x) 
	{
		List<Namespace> lNs = new ArrayList<Namespace>(); 
		if(mNs==null){init();}
		for(String key : x)
		{
			if(mNs.containsKey(key)){lNs.add(mNs.get(key));}
			else{logger.warn("Namespace "+key+" not defined");}
		}
		return lNs;
	}
	
	public static synchronized Namespace getSingelNs(String x) 
	{
		Namespace ns = null;  
		if(mNs==null){init();}
		if(mNs.containsKey(x)){ns=mNs.get(x);}
		else{logger.warn("Namespace "+x+" not defined");}
		return ns;
	}
	
}
