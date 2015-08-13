package org.openfuxml.renderer.processor.post;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.configuration.Configuration;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;
import org.dom4j.Namespace;
import org.dom4j.XPath;
import org.dom4j.NamespaceStack;

import org.openfuxml.exception.OfxInternalProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxContentTrimmer
{
	final static Logger logger = LoggerFactory.getLogger(OfxContentTrimmer.class);
	
	private List<XPath> lXpath; 
	
	
	public OfxContentTrimmer()
	{
		lXpath = new ArrayList<XPath>(); 
		try
		{
			Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
			Namespace nsWiki = Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki");
			
			XPath xpSections  = DocumentHelper.createXPath("//ofx:paragraph"); 
            NamespaceStack nstk = new NamespaceStack();
            nstk.push(nsOfx); nstk.push(nsWiki);
			//xpSections.addNamespace(nsOfx); xpSections.addNamespace(nsWiki); 
			lXpath.add(xpSections);
		}
		catch (Exception e) {logger.error("",e);}
	}
	
	public Document trim(Document doc) throws OfxInternalProcessingException 
	{
		for(XPath xpath : lXpath) 
		{
			Element result = mergeRecursive(doc.getRootElement(),xpath); 
			result.detach(); 
			doc.setRootElement(result); 
		}
		return doc;
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
				boolean noChilds = (e.elements().size()==0); 
				boolean noContent = (e.getText().length()==0); 
				logger.trace(e.getName()+" "+e.elements().size()+" "+e.getText().length()); 
				if(noChilds && noContent){e.detach();} 
				else{e.setText(e.getTextTrim());} 
			}
		}
		catch (Exception e) {logger.error("",e);}
		return rootElement;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String propFile = "resources/properties/user.properties";
		if(args.length==1){propFile=args[0];}
			
		ConfigLoader.add(propFile);
		Configuration config = ConfigLoader.init();
			
		String fnOfx = config.getString("wiki.processor.test.contenttrimmer");
		Document doc = JDomUtil.load(fnOfx); 
		
		OfxContentTrimmer test = new OfxContentTrimmer();
		test.trim(doc);
		JDomUtil.debug(doc);
	}
}
