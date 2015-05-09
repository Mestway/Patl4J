package org.openfuxml.addon.wiki.processor.xhtml.mods;

import java.util.ArrayList;

import net.sf.exlp.util.xml.JDomUtil;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Text;
import org.dom4j.DocumentHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxPushUp
{
	final static Logger logger = LoggerFactory.getLogger(OfxPushUp.class);
	
	public OfxPushUp()
	{

	}
	
	public String moveOfxElements(String xHtmlText)
	{
		Document doc = null; 
		try {doc = JDomUtil.txtToDoc(xHtmlText);}
		catch (Exception e) {logger.error("",e);}
	
		Element rootElement = doc.getRootElement(); 
		rootElement.detach(); 
		
		ArrayList<Element> al = moveOfxElement(rootElement,"wikiinjection",0); 
		if(al.size()>1){logger.warn("Moved Elements has a size>1 !!!");}
		rootElement=al.get(0);
			
		doc.add(rootElement); 
		
		xHtmlText=JDomUtil.docToTxt(doc);
		return xHtmlText;
	}
	
	//TODO public here ist only for testing, remove this later!
	public ArrayList<Element> moveOfxElement(Element oldRoot, String tag, int level) 
	{
		ArrayList<Element> movedElements = new ArrayList<Element>();
		Element newRoot = DocumentHelper.createElement(oldRoot.getName()); 
		
		for(Object oAtt : oldRoot.attributes()) 
		{
			Attribute att = (Attribute)oAtt; 
			Attribute newAtt = DocumentHelper.createAttribute(null, att.getName(),att.getValue()); 
			newRoot.addAttribute(newAtt); 
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("Tag="+tag+" Level="+level);
		for(Object o : oldRoot.getContent())
		{
			if(org.jdom2.Text.class.isInstance(o)) 
			{
				Text txt = (Text)o; 
				//Text newText = new Text(txt.getText());
				Text newText = DocumentHelper.createText(txt.getText()); 
				newRoot.add(newText); 
				sb.append(" txt");
			}
			else if(org.jdom2.Element.class.isInstance(o)) 
			{
				Element oldChild = (Element)o; 
				sb.append(" "+oldChild.getName()); 
				if(oldChild.getName().equals(tag)) 
				{
					logger.debug("Detaching "+oldChild.getName()); 
					movedElements.addAll(moveOfxElement(oldChild, tag, level+1)); 
				}
				else
				{
					ArrayList<Element> al =moveOfxElement(oldChild, tag, level+1); 
					//newRoot.addContent(al); 
                    for (int i = 0; i < al.size(); i++)
                        newRoot.add(al[i]);
				}
			}
			else {logger.warn("Unknown content: "+o.getClass().getName());}
		}
		logger.trace(sb.toString());
		
		ArrayList<Element> result = new ArrayList<Element>(); 
		result.add(newRoot);
		result.addAll(movedElements);
		return result;
	}
}
