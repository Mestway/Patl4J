package org.openfuxml.renderer.processor.html.navigation;

import net.sf.exlp.exception.ExlpXpathNotFoundException;

import org.dom4j.Element;
import org.dom4j.DocumentHelper;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.renderer.processor.html.interfaces.OfxNavigationRenderer;
import org.openfuxml.xml.xpath.content.SectionXpath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavUnorderedListRenderer implements OfxNavigationRenderer
{
	final static Logger logger = LoggerFactory.getLogger(NavUnorderedListRenderer.class);
	
	public NavUnorderedListRenderer()
	{

	}
	
	public Element render(Document ofxDoc, Section actualSection) 
	{
		Element rootElement = DocumentHelper.createElement("ul"); 
		
		for(Object o : ofxDoc.getContent().getContent()) 
		{
			if(o instanceof Section){rootElement.add(getSection((Section)o));} 
		}
		
		return rootElement;
	}
	
	private Element getSection(Section section) 
	{
		
		Element a = DocumentHelper.createElement("a"); 
		try
		{
			Title title = SectionXpath.getTitle(section);
			a.addAttribute("href", section.getId()+".html"); 
			a.setText(title.getValue()); 
		}
		catch (ExlpXpathNotFoundException e)
		{
			logger.debug("",e);
		}
		Element li = DocumentHelper.createElement("li"); 
		//li.setContent(a); 
		return li;
	}
}
