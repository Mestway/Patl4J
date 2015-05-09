package org.openfuxml.util.filter;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.dom4j.Attribute;
import org.dom4j.NodeFilters;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxLangFilter
{
	final static Logger logger = LoggerFactory.getLogger(OfxLangFilter.class);

	private String lang;
	
	public OfxLangFilter(String lang)
	{
		this.lang=lang;
	}
	
	public Section filterLang(Section section)
	{
		org.jdom2.Document j2Doc = JaxbUtil.toDocument(section); 
		filterLang(j2Doc);
		return (Section)JDomUtil.toJaxb(j2Doc, Section.class);
	}
	
	public void filterLang(Document ofxDocument)
	{
		org.jdom2.Document j2Doc = JaxbUtil.toDocument(ofxDocument); 
		filterLang(j2Doc);
	}
	
	private void filterLang(org.jdom2.Document j2Doc) 
	{
		//XPathFactory xpfac = XPathFactory.instance(); 
		//XPathExpression<Attribute> xp = xpfac.compile("//*/@lang", Filters.attribute()); 
        /*
		for (Attribute att : xp.evaluate(j2Doc))  
		{
			if(!att.getValue().equals(lang)) 
			{
				att.getParent().detach(); 
			}
		}
       */ 
	}
}
