package org.openfuxml.addon.epub.generator.epub;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.QName;

import org.openfuxml.content.ofx.Metadata;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class OpfGenerator
{
	final static Logger logger = LoggerFactory.getLogger(OfxExternalMerger.class);
	
	private File targetDir;
	private Document doc; 
	private Namespace nsXsi,nsOpf,nsDc; 
	
	private List<String> spineItemRef;
	
	public OpfGenerator(File targetDir)
	{
		this.targetDir=targetDir;
		nsXsi = Namespace.getNamespace("xsi","http://www.w3.org/2001/XMLSchema-instance"); 
		nsOpf = Namespace.getNamespace("http://www.idpf.org/2007/opf"); 
		nsDc = Namespace.getNamespace("dc","http://purl.org/dc/elements/1.1/"); 
		spineItemRef = new ArrayList<String>();
	}
	
	public void create(Ofxdoc ofxDoc)
	{
		doc = new Document(); 
		
		Element ePackage = DocumentHelper.createElement("package"); 
		ePackage.addAttribute("version","2.0"); 
		//ePackage.setNamespace(nsOpf); 
		ePackage.setQName(new QName(ePackage.getName(), nsOpf));
		
		ePackage.addNamespace(nsXsi.getPrefix(), nsXsi.getURI()); 
		ePackage.addNamespace(nsDc.getPrefix(), nsDc.getURI()); 
		
		ePackage.add(getMetadata(ofxDoc.getMetadata())); 
		ePackage.add(getManifest(ofxDoc)); 
		ePackage.add(getSpine(ofxDoc)); 
		
		doc.setRootElement(ePackage); 
		save();
	}
	
	private Element getMetadata(Metadata metadata) 
	{
		Element eMetadata = DocumentHelper.createElement(new QName("metadata",nsOpf)); 
		
		Element eLanguage = DocumentHelper.createElement(new QName("language",nsDc)); 
		eLanguage.add("de-DE"); 
		//Attribute aType = new Attribute("type","dcterms:RFC3066",nsXsi);  scatter
		//eLanguage.setAttribute(aType);
        eLanguage.addAttribute(new QName("type", nsXsi), "determs:RFC3066"); 
        //alternative:
        //Attribute aType = DocumentHelper.createAttribute(eLanguage, new QName("type", nsXsi), determs:RFC3066);
        //eLanguage.add(aType);

		eMetadata.add(eLanguage); 
		
		Element eTitle = DocumentHelper.createElement(new QName("title",nsDc)); 
		eTitle.add(metadata.getTitle().getValue()); 
		eMetadata.add(eTitle); 
		
		Element eId = DocumentHelper.createElement(new QName("identifier",nsDc)); 
		eId.addAttribute("id", "idddddd"); 
		eId.addText("identifierssss"); 
		eMetadata.add(eId); 
		
		return eMetadata;
	}
	
	private Element getManifest(Ofxdoc ofxDoc) 
	{
		Element eManifest = DocumentHelper.createElement(new QName("manifest",nsOpf)); 
		
		eManifest.add(getItem("ncx","toc.ncx","application/x-dtbncx+xml")); 
		
		int partNr=1;
		for(Object s : ofxDoc.getContent().getContent())
		{
			if(s instanceof Section)
			{
				Section section = (Section)s;
				eManifest.add(getItem(section.getId(),"part-"+partNr+".xhtml","application/xhtml+xml")); 
				partNr++;
			}
		}
		return eManifest;
	}
	
	private Element getItem(String id,String href, String mediaType) 
	{
		Element item = DocumentHelper.createElement(new QName("item",nsOpf)); 
		item.addAttribute("id", id); 
		item.addAttribute("href", href); 
		item.addAttribute("media-type", mediaType); 
		return item;
	}
	
	private Element getSpine(Ofxdoc ofxDoc) 
	{
		Element eSpine = DocumentHelper.createElement(new QName("spine",nsOpf)); 
		eSpine.addAttribute("toc","ncx"); 
		
		for(Object s : ofxDoc.getContent().getContent())
		{
			if(s instanceof Section)
			{
				Section section = (Section)s;
				Element eRef = DocumentHelper.createElement(new QName("itemref",nsOpf)); 
				eRef.addAttribute("idref",section.getId()); 
				eSpine.add(eRef); 
			}
		}
		
		return eSpine;
	}
	
	private void save()
	{
		File f = new File(targetDir,"inhalt.opf");
		JDomUtil.save(doc, f, Format.getPrettyFormat()); 
	}
}
