package org.openfuxml.addon.epub.generator.content;

import java.io.File;
import java.io.Serializable;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.DocumentHelper;
import org.dom4j.QName;
import org.dom4j.NamespaceStack;

import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class ContentGenerator
{
	final static Logger logger = LoggerFactory.getLogger(OfxExternalMerger.class);
	
	private File targetDir;
	private Namespace nsXhtml; 
	private PartXhtmlFactory partFactory;
	
	public ContentGenerator(File targetDir)
	{
		this.targetDir=targetDir;
		nsXhtml = Namespace.getNamespace("http://www.w3.org/1999/xhtml"); 
		partFactory = new PartXhtmlFactory(nsXhtml);
	}
	
	public void create(Ofxdoc ofxDoc)
	{
		int partNr=1;
		for(Object s : ofxDoc.getContent().getContent())
		{
			if(s instanceof Section)
			{
				Section section = (Section)s;
				File f = new File(targetDir,"part-"+partNr+".xhtml");
				Document doc = DocumentHelper.createDocument();
			
				Element rootElement = partFactory.createPart(section); 
				doc.setRootElement(rootElement); 
			
				JDomUtil.save(doc, f, Format.getPrettyFormat()); 
				partNr++;
			}
		}
	}

}
