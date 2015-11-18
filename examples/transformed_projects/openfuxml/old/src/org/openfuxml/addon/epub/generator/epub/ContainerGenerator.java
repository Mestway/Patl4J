package org.openfuxml.addon.epub.generator.epub;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.DocumentHelper;
import org.dom4j.QName;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class ContainerGenerator
{
	final static Logger logger = LoggerFactory.getLogger(OfxExternalMerger.class);
	
	private File targetDir;
	private Document doc; 
	private Namespace nsContainer; 
	
	public ContainerGenerator(File targetDir)
	{
		this.targetDir=targetDir;
		nsContainer = Namespace.getNamespace("urn:oasis:names:tc:opendocument:xmlns:container"); 
	}
	
	public void create()
	{
		doc = new Document(); 
		
		//Element eContainer = new Element("container");
		Element eContainer = DocumentHelper.createElement("container"); 
		eContainer.addAttribute("version","1.0"); 
		//eContainer.setNamespace(nsContainer);
		eContainer.setQName(new QName(eContainer.getName(), nsContainer)); 
		
		eContainer.add(getRootfiles()); 
		doc.setRootElement(eContainer); 
		save();
	}
	
	private Element getRootfiles() 
	{
		//Element eRootfiles = new Element("rootfiles",nsContainer);
		Element eRootfiles = DocumentHelper.createElement(new QName("rootfiles",nsContainer)); 
		for(Element e : getRootfile()){eRootfiles.add(e);} 
		return eRootfiles;
	}
	
	private List<Element> getRootfile() 
	{
		List<Element> lRootfiles = new ArrayList<Element>(); 
		
		//Element eRootfile = new Element("rootfile",nsContainer);
		Element eRootfile = DocumentHelper.createElement(new QName("rootfile",nsContainer)); 
		eRootfile.addAttribute("media-type", "application/oebps-package+xml"); 
		eRootfile.addAttribute("full-path","content.opf"); 
		lRootfiles.add(eRootfile); 
		return lRootfiles;
	}
	
	private void save()
	{
		File dMetainf = new File(targetDir,"META-INF");
		dMetainf.mkdir();
		File f = new File(dMetainf,"container.xml");
		JDomUtil.save(doc, f, Format.getPrettyFormat()); 
//		JDomUtil.debug(doc);
	}
}
