package org.openfuxml.producer.preprocessors;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.NamespaceStack;
import org.dom4j.XPath;

public class IdTagger
{
	final static Logger logger = LoggerFactory.getLogger(IdTagger.class);
		
	private Namespace ns; 
	private XPath xpath; 
	private int idCounter;
	private List<String> alElementNames;
	
	public IdTagger()
	{
		ns = Namespace.getNamespace("ofx", "http://www.openfuxml.org"); 
		
		alElementNames = new ArrayList<String>();
		alElementNames.add("section");
	}
	
	public void tag(Document doc) 
	{
		for(String eName : alElementNames)
		{
			try
			{
				//xpath = XPath.newInstance("//ofx:"+eName);
				xpath = DocumentHelper.createXPath("//ofx:"+eName); 
				//xpath.addNamespace(ns);
                NamespaceStack nstk = new NamespaceStack(); 
                nstk.push(ns);
				idTag(doc.getRootElement(), eName); 
			}
			catch (Exception e) {logger.error("",e);}
		}
		
	}
	
	private void idTag(Element rootElement, String idPrefix) 
	{
		idCounter = 0;
		try
		{
			List<?> list = xpath.selectNodes(rootElement); 
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element childElement = (Element) iter.next(); 
				Attribute att = childElement.attribute("id"); 
				if(att!=null)
				{
					if(att.getValue().length()==0){addId(childElement, idPrefix);} 
				}
				else if(att==null){addId(childElement, idPrefix);}
			}
		}
		catch (Exception e) {logger.error("",e);}
		logger.debug(idCounter+" ids generated for "+idPrefix);
	}
	
	private void addId(Element e, String idPrefix) 
	{
		idCounter++;
		e.addAttribute("id", idPrefix+"-"+idCounter+"-auto");
	}
			
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing ExternalMerger");
		
		File f = new File("resources/data/xml/exmerge/chapter-1.xml");
		Document doc = JDomUtil.load(f); 
		
		IdTagger idTagger = new IdTagger();
		idTagger.tag(doc);
		JDomUtil.debug(doc);
	}
}
