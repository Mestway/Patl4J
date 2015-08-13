package org.openfuxml.renderer.html.section;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Node;
import org.dom4j.Element;
import org.dom4j.Text;
import org.dom4j.DocumentHelper;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.Table;
import org.openfuxml.renderer.processor.html.interfaces.OfxSectionRenderer;
import org.openfuxml.renderer.processor.html.interfaces.OfxTableRenderer;
import org.openfuxml.renderer.processor.html.section.ParagraphRenderer;
import org.openfuxml.renderer.processor.html.structure.ReferenceRenderer;
import org.openfuxml.renderer.processor.html.table.DefaultTableRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SectionRenderer implements OfxSectionRenderer
{
	final static Logger logger = LoggerFactory.getLogger(SectionRenderer.class);
	
	private List<Content> list;  
	private List<Content> subsections; 
	private Element divParagraph; 
	private Element eHeader; 
	
	public SectionRenderer()
	{
		list = new ArrayList<Content>(); 
		subsections = new ArrayList<Content>(); 
		
		divParagraph = DocumentHelper.createElement("div"); 
		divParagraph.addAttribute("class", "text3"); 
	}
	
	public List<Content> render(Section section) 
	{
		for(Object o : section.getContent())
		{
			if(o instanceof String){}
			else if(o instanceof Title){createHeader((Title)o);}
			else if(o instanceof Paragraph){addParagrah((Paragraph)o);}
			else if(o instanceof Section){addSection((Section)o);}
			else if(o instanceof Table){OfxTableRenderer r = new DefaultTableRenderer();divParagraph.addContent(r.render((Table)o));} 
			else {logger.warn("Unknown content: "+o.getClass().getSimpleName());}
		}

		if(eHeader!=null){list.add(eHeader);}
		
		if(divParagraph.getContentSize()>0){list.add(divParagraph);} 
		list.addAll(subsections);
		
		return list;
	}
	
	private void addParagrah(Paragraph ofxParagraph)
	{
		ParagraphRenderer pR = new ParagraphRenderer();
		Node c = pR.render(ofxParagraph); 
		divParagraph.add(c); 
	}
	
	private void addSection(Section section)
	{
		DefaultSection2Renderer render2 = new DefaultSection2Renderer();
		subsections.add(render2.render(section));
	}
	
	private void createHeader(Title title)
	{
		Element h1 = DocumentHelper.createElement("h1"); 
		h1.setText(title.getValue()); 
		
		Element hgroup = DocumentHelper.createElement("hgroup"); 
		//hgroup.setContent(h1); 
		
		Element header = DocumentHelper.createElement("header"); 
		//header.setContent(hgroup); 
		
		eHeader = DocumentHelper.createElement("div"); 
		eHeader.addAttribute("class", "text1"); 
		//eHeader.setContent(header); 
	}
	
	private class DefaultSection2Renderer
	{
		private Element divParagraph; 
		
		public DefaultSection2Renderer()
		{
			divParagraph = DocumentHelper.createElement("div"); 
			divParagraph.addAttribute("class", "text2"); 
		}
		
		public Content render(Section section)
		{
			for(Object o : section.getContent())
			{
				if(o instanceof String){}
				else if(o instanceof Title){createHeader((Title)o);}
				else if(o instanceof Paragraph){addParagrah((Paragraph)o);}
				else if(o instanceof Table){OfxTableRenderer r = new DefaultTableRenderer();divParagraph.add(r.render((Table)o));} 
				else {logger.warn("Unknown content: "+o.getClass().getSimpleName());}
			}			
			return divParagraph;
		}
		
		private void addParagrah(Paragraph ofxParagraph)
		{
			Element p = DocumentHelper.createElement("p"); 
			
			for(Object o : ofxParagraph.getContent())
			{
				if(o instanceof String){p.add(new Text((String)o));} 
				else if(o instanceof Reference){ReferenceRenderer r = new ReferenceRenderer();p.add(r.render((Reference)o));} 
				else {logger.warn("Unknown content: "+o.getClass().getSimpleName());}
			}
			divParagraph.addContent(p); 
		}
		
		private void createHeader(Title title)
		{
			Element h1 = DocumentHelper.createElement("h3"); 
			h1.setText(title.getValue()); 
			
			Element hgroup = DocumentHelper.createElement("hgroup"); 
			//hgroup.setContent(h1); 
			
			Element eHeader = DocumentHelper.createElement("header"); 
			//eHeader.setContent(hgroup); 
			divParagraph.addContent(eHeader); 
			
		}
	}
}
