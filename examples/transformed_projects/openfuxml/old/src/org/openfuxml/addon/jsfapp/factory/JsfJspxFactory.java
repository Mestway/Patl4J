package org.openfuxml.addon.jsfapp.factory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.NamespaceStack;
import org.dom4j.DocumentHelper;

public class JsfJspxFactory
{
	public static Document createDOMjspx() 
	{
		Namespace html  = Namespace.getNamespace("http://www.w3.org/1999/xhtml");		 
		Namespace jsp   = Namespace.getNamespace("jsp", "http://java.sun.com/JSP/Page"); 
		
		Document doc    = DocumentHelper.createDocument(); 
			
		Element rootElement = DocumentHelper.createElement("root"); 
		//rootElement.setNamespace(jsp);
		rootElement.setQName(new QName(rootElement.getName(), jsp)); 
		//rootElement.addNamespaceDeclaration(html);
		rootElement.addNamespace(html.getPrefix(), html.getURI()); 
		for(Namespace ns : NsFactory.getNs("f","h","a4j","rich")) 
		{
			//rootElement.addNamespaceDeclaration(ns);
			rootElement.addNamespace(ns.getPrefix(), ns.getURI()); 
		}
		rootElement.addAttribute("version","2.0"); 
		
		Element output = DocumentHelper.createElement(new QName("output", jsp)); 
		output.addAttribute("doctype-root-element","html"); 
		output.addAttribute("doctype-public", "-//W3C//DTD XHTML 1.1//EN"); 
		output.addAttribute("doctype-system", "http://www.w3c.org/TR/xhtml11/DTD/xhtml11.dtd"); 
		
		rootElement.add(output); 
		
		Element directivePage = DocumentHelper.createElement(new QName("directive.page",jsp)); 
		directivePage.setAttribute("contentType", "text/html; charset=ISO-8859-1"); 
		directivePage.setAttribute("language", "java"); 
		
		rootElement.add(directivePage); 
    	
		doc.setRootElement(rootElement); 
		return doc;
	}
}
