package org.openfuxml.addon.wiki.processor.xhtml.mods;

import java.util.regex.Pattern;

import net.sf.exlp.util.xml.JDomUtil;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Text;
import org.dom4j.DocumentHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XhtmlAHxMerge
{
	final static Logger logger = LoggerFactory.getLogger(XhtmlAHxMerge.class);
	
	private Pattern p;
	
	public XhtmlAHxMerge()
	{
		p = Pattern.compile("h[\\d](.*)");
	}
	
	public String merge(String xHtmlText)
	{
		Document doc = null; //1zz
		try {doc = JDomUtil.txtToDoc(xHtmlText);}
		catch (Exception e) {logger.error("",e);}
		Element rootElement = doc.getRootElement();	 //2zz
		rootElement.detach(); //1zz
		
		doc.setRootElement(merge(rootElement)); //1zz
		
		xHtmlText=JDomUtil.docToTxt(doc);
		return xHtmlText;
	}
	
	public Element merge(Element oldRoot) //1zz
	{
		Element newRoot = DocumentHelper.createElement(oldRoot.getName()); //2zz
		
		for(Object oAtt : oldRoot.attributes()) //1zz
		{
			Attribute att = (Attribute)oAtt; //2zz
			Attribute newAtt = DocumentHelper.createAttribute(null, att.getName(),att.getValue()); //4zz
			newRoot.addAttribute(newAtt.getName(), newAtt.getValue()); //3zz
		}
		
		Element prevChild = null; //1zz
		
		for(Object o : oldRoot.content()) //1zz
		{
			if(org.jdom2.Text.class.isInstance(o)) //1zz
			{
				if(prevChild!=null){newRoot.add(prevChild);prevChild = null;} //1zz
				Text txt = (Text)o; //2zz
				Text newText = DocumentHelper.createText(txt.getText()); //3zz
				newRoot.add(newText); //1zz
			}
			else if(org.jdom2.Element.class.isInstance(o)) //1zz
			{
				Element oldChild = (Element)o; //2zz
				Element newChild = null; //1zz
				if(prevChild!=null)
				{
					boolean prevA = prevChild.getName().equals("a"); //1zz
					boolean thisH = p.matcher(oldChild.getName()).matches(); //1zz
					logger.debug(prevChild.getName()+"-"+oldChild.getName()+" :"+prevA+"-"+thisH); //2zz
					if(prevA && thisH)
					{
						
					}
					else
					{
						newRoot.add(prevChild);prevChild = null; //1zz
					}
				}
				prevChild = (merge(oldChild));
			}
			else {logger.warn("Unknown content: "+o.getClass().getName());}
		}
		if(prevChild!=null){newRoot.add(prevChild);prevChild = null;} //1zz
		return newRoot;
	}
}
