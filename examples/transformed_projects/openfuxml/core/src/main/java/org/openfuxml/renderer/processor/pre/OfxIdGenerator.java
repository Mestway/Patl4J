package org.openfuxml.renderer.processor.pre;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.XPath;
import org.dom4j.DocumentHelper;
import org.dom4j.NamespaceStack;

import org.openfuxml.exception.OfxInternalProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxIdGenerator
{
	final static Logger logger = LoggerFactory.getLogger(OfxIdGenerator.class);
	
	private Document doc; 
	private XPath xpath; 
	private int autoId;
	
	public OfxIdGenerator()
	{
		autoId = 1;
		try
		{
			
			xpath = DocumentHelper.createXPath("//ofx:section"); 
            NamespaceStack nstk = new NamespaceStack();
			nstk.push(Namespace.getNamespace("ofx", "http://www.openfuxml.org"));   
			nstk.push(Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki"));  
			
//			List<?> list = xpath.selectNodes(doc.getRootElement());
//			logger.debug(list.size()+" hits");
			
		}
		catch (Exception e) {logger.error("",e);}
	}
	
	public void createIds(File srcFile, File dstFile) throws OfxInternalProcessingException
	{
		if(srcFile==null){throw new OfxInternalProcessingException("FileNoteFound: "+srcFile.getAbsolutePath());}
		doc = JDomUtil.load(srcFile);
		if(doc==null){throw new OfxInternalProcessingException("FileNoteFound: "+srcFile.getAbsolutePath());}
		
		try
		{
			idCreator();
			//JDomUtil.save(doc, dstFile, Format.getRawFormat());
		    JDomUtil.save(doc, dstFile, new OutputFormat()); 
            
		}
		catch (Exception e)
		{
			logger.error("",e);
		}
	}
	
	private void idCreator() throws Exception
	{
		List<?> list = xpath.selectNodes(doc.getRootElement()); 
		logger.debug(list.size()+" sections for idTagging");
		
		for (Iterator<?> iter = list.iterator(); iter.hasNext();)
		{
			Element e = (Element) iter.next(); 
			if(e.attribute("id")==null) 
			{
				e.addAttribute("id", "autoId"+autoId);autoId++; 
			}
		}
	}
	
}
