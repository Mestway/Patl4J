package org.openfuxml.renderer.processor.pre;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.XPath;
import org.dom4j.DocumentHelper;
import org.dom4j.NamespaceStack;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxContainerMerger
{
	final static Logger logger = LoggerFactory.getLogger(OfxContainerMerger.class);
	
	private List<XPath> lXpath; 
	
	
	public OfxContainerMerger()
	{
		lXpath = new ArrayList<XPath>(); 
		try
		{
			Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org"); 
			Namespace nsWiki = Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki"); 
			
			XPath xpSections  = DocumentHelper.createXPath("//ofx:sections"); 
            NamespaceStack nstk = new NamespaceStack(); 
			nstk.push(nsOfx); nstk.push(nsWiki);
			lXpath.add(xpSections);
			
			XPath xpSectionTransparent  = DocumentHelper.createXPath("//ofx:section[@container='true']"); 
            NamespaceStack nstkk = new NamespaceStack(); 
			nstkk.push(nsOfx); nstkk.push(nsWiki);
			lXpath.add(xpSectionTransparent);
		}
		catch (Exception e) {logger.error("",e);}
	}
	
	public Document merge(Document ofxDoc) throws OfxInternalProcessingException
	{
		org.dom4j.Document doc = JaxbUtil.toDocument(ofxDoc); 

		for(XPath xpath : lXpath) 
		{
			Element result = mergeRecursive(doc.getRootElement(),xpath); 
			result.detach(); 
			doc.setRootElement(result); 
		}
		
		ofxDoc = (Document)JDomUtil.toJaxb(doc, Document.class);
		return ofxDoc;
	}
	
	private Element mergeRecursive(Element rootElement, XPath xpath) throws OfxInternalProcessingException 
	{
		try
		{
			List<?> list = xpath.selectNodes(rootElement); 
			logger.debug(list.size()+" sections");
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element e = (Element) iter.next(); 
				
				int index = e.getParent().indexOf(e); 
				List<Element> lChilds = new ArrayList<Element>(); 
				
				if     (e.getName().equalsIgnoreCase(Sections.class.getSimpleName())){lChilds = processSections(e.elements());} 
				else if(e.getName().equalsIgnoreCase(Section.class.getSimpleName())){lChilds = processSection(e.elements());} 
				else {throw new OfxInternalProcessingException("Root element <"+e.getName()+"> of Wiki.Processing not expected");} 
				
				//e.getParent().addContent(index, lChilds);
                Element ee = e.getParent().node(index); 
                ee.add(lChilds);
				e.getParent().remove(e);
			}
		}
		catch (Exception e) {logger.error("",e);}
		return rootElement;
	}
	
	private List<Element> processSections(List<?> lChilds) 
	{
		List<Element> lSection = new ArrayList<Element>(); 
		for(Object o : lChilds)
		{
			Element e = (Element)o;				 
			lSection.add(e);
		}
		for(Element e : lSection){e.detach();} 
		return lSection;
	}
	
	private List<Element> processSection(List<?> lChilds) 
	{
		List<Element> lSection = new ArrayList<Element>(); 
		for(Object o : lChilds)
		{
			Element e = (Element)o; 
			boolean add = true;
			if(e.getName().equalsIgnoreCase(Title.class.getSimpleName())){add=false;} 
			if(add){lSection.add(e);} 
		}
		for(Element e : lSection){e.detach();} 
		return lSection;
	}
			
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing ExternalMerger");
		
		String fName;
//		fName = "resources/data/xml/preprocessor/merge/container/sections.xml";
		fName = "resources/data/xml/preprocessor/merge/container/transparent.xml";
		if(args.length == 1 ){fName = args[0];}
		
		Document ofxDocOriginal = JaxbUtil.loadJAXB(fName, Document.class);
		JaxbUtil.debug(ofxDocOriginal);
		
		OfxContainerMerger containerMerger = new OfxContainerMerger();
		Document ofxDocContainer = containerMerger.merge(ofxDocOriginal);
		JaxbUtil.debug(ofxDocContainer);
	}
}
