package org.openfuxml.addon.jsf.factory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;

import org.dom4j.DataConversionException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.nodeFilter;

import org.openfuxml.addon.jsf.controller.factory.xml.XmlAttributeFactory;
import org.openfuxml.xml.addon.jsf.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsfComponentFactory
{
	final static Logger logger = LoggerFactory.getLogger(JsfComponentFactory.class);
	
	private Component component;
	private Namespace ns; 
	
	public JsfComponentFactory()
	{
		ns = Namespace.getNamespace("composite", "http://java.sun.com/jsf/composite"); 
	}
	
	public Component buildComponent(String resourceName)
	{
		component = new Component();
		
		try {read(resourceName);}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (Exception e) {e.printStackTrace();}
		
		return component;
	}
	
	private void read(String resourceName) throws FileNotFoundException, Exception
	{
		MultiResourceLoader mrl = new MultiResourceLoader();
		logger.trace("MRL: "+mrl.isAvailable(resourceName));
		InputStream is = mrl.searchIs(resourceName);
		
		Document doc = JDomUtil.load(is,"UTF-8"); 
//		JDomUtil.debug(doc);

		String xpath = "//composite:attribute";
		//XPathExpression<Element> xpe = XPathFactory.instance().compile(xpath,Filters.element(), null,ns); 

		//List<Element> elements = xpe.evaluate(doc);  
		/*for (Element e : elements) 
		{	
			addAttribute(e);
		}
        */
	}
	
	private void addAttribute(Element e) throws DataConversionException 
	{
		org.jdom2.Attribute attribute; 
	
		String name = e.attributeValue("name"); 
		
		boolean required = false;
		attribute = e.attribute("required"); 
		if(attribute!=null){required = attribute.getBooleanValue();} 
		
		String sDefault = null;
		attribute = e.attribute("default"); 
		if(attribute!=null){sDefault=attribute.getValue();} 
		
		String sDescription = null;
		attribute = e.attribute("shortDescription"); 
		if(attribute!=null){sDescription=attribute.getValue();} 

		//component.getAttribute().add(XmlAttributeFactory.create(name,required,sDefault,sDescription));
	}
}
