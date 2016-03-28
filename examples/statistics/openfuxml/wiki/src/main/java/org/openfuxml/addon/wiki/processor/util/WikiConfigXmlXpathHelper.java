package org.openfuxml.addon.wiki.processor.util;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.NamespaceStack;
import org.dom4j.XPath;
import org.dom4j.DocumentHelper;

import org.openfuxml.addon.wiki.data.jaxb.Injections;
import org.openfuxml.addon.wiki.data.jaxb.Replacements;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.exception.OfxConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiConfigXmlXpathHelper
{
	final static Logger logger = LoggerFactory.getLogger(WikiConfigXmlXpathHelper.class);
	
	public static synchronized Template getTemplate(Templates templates, String name) throws OfxConfigurationException 
	{
		Template result = new Template();
		try
		{
			//XPath xpath = XPath.newInstance( "//wiki:template[@name='"+name+"']" );
			XPath xpath = DocumentHelper.createXPath( "//wiki:template[@name='"+name+"']" ); 
            NamespaceStack nstk = new NamespaceStack();
			//xpath.addNamespace(Namespace.getNamespace("ofx", "http://www.openfuxml.org"));
			nstk.push(Namespace.getNamespace("ofx", "http://www.openfuxml.org")); 
			//xpath.addNamespace(Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki"));
			nstk.push(Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki"));  
			
			Document doc = JaxbUtil.toDocument(templates); 
			Element e = (Element)xpath.selectSingleNode(doc); 
			if(e!=null){result = (Template)JDomUtil.toJaxb(e, Template.class);}
			else{throw new OfxConfigurationException("No template definition for templateName="+name);}
		}
		catch (Exception e) {logger.error("",e);}
        return result;
	}
	
	public static synchronized Replacements initReplacements(Replacements replacements) throws OfxConfigurationException
	{
		if(replacements.isSetExternal() && replacements.isExternal())
		{
			try
			{
				if(replacements.isSetSource())
				{
					replacements = (Replacements)JaxbUtil.loadJAXB(replacements.getSource(), Replacements.class);
				}
				else {throw new OfxConfigurationException("Replacement is set to external, but no source definded");}
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
				throw new OfxConfigurationException(e.getMessage());
			}
		}
		return replacements;
	}
	
	public static synchronized  Injections initInjections(Injections injections) throws OfxConfigurationException
	{
		if(injections.isSetExternal() && injections.isExternal())
		{
			try
			{
				if(injections.isSetSource())
				{
					logger.debug("Loading external "+Injections.class.getSimpleName()+" file: "+injections.getSource());
					injections = (Injections)JaxbUtil.loadJAXB(injections.getSource(), Injections.class);
				}
				else {throw new OfxConfigurationException(Injections.class.getSimpleName()+" is set to external, but no source definded");}
			}
			catch (FileNotFoundException e)
			{
				//TODO nested exception
				throw new OfxConfigurationException(e.getMessage());
			}
		}
		logger.debug(JaxbUtil.toString(injections));
		return injections;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String fnInjections = "resources/config/wiki/wikiinjection.xml";
			
		Injections injections = (Injections)JaxbUtil.loadJAXB(fnInjections, Injections.class);
		
		logger.debug(JaxbUtil.toString(injections));
	}
}
