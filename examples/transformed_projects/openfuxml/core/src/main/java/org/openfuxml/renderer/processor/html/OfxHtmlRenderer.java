package org.openfuxml.renderer.processor.html;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.dom4j.Content;
import org.dom4j.Element;
import org.dom4j.NodeFilter;
import org.dom4j.io.OutputFormat;
import org.dom4j.DocumentHelper;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxImplementationException;
import org.openfuxml.renderer.processor.html.interfaces.OfxHeaderRenderer;
import org.openfuxml.renderer.processor.html.interfaces.OfxNavigationRenderer;
import org.openfuxml.renderer.processor.html.interfaces.OfxSectionRenderer;
import org.openfuxml.renderer.util.OfxRenderConfiguration;
import org.openfuxml.xml.renderer.cmp.Html;
import org.openfuxml.xml.renderer.html.Renderer;
import org.openfuxml.xml.renderer.html.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxHtmlRenderer.class);
	
	public static enum HtmlDir {template,web};
	
	private Html html;
	private OfxRenderConfiguration cmpConfigUtil;
	
	public OfxHtmlRenderer(OfxRenderConfiguration cmpConfigUtil, Html html)
	{
		this.html=html;
		this.cmpConfigUtil=cmpConfigUtil;
	}
	
	public void render(String ofxDocFileName) throws OfxAuthoringException, OfxConfigurationException, OfxImplementationException
	{
		try
		{
			logger.debug("Processing: "+ofxDocFileName);
			Document ofxdoc = JaxbUtil.loadJAXB(ofxDocFileName, Document.class);
			
			for(Template template : html.getTemplate())
			{
				for(Object o : ofxdoc.getContent().getContent()) 
				{
					//TODO Flexible Template Processing
					//All Templates are processed for all Sections
					if(o instanceof Section){processTemplate((Section)o, ofxdoc, template);}
				}
			}
		}
		catch (FileNotFoundException e) {logger.error("",e);}
	}
	
	private void processTemplate(Section section, Document ofxDoc, Template template) throws OfxConfigurationException, OfxImplementationException
	{
		File fTemplate = cmpConfigUtil.getFile(html.getDir(), HtmlDir.template.toString(), template.getFileCode(),false);
		org.jdom2.Document doc = JDomUtil.load(fTemplate);

		{
			//XPathExpression<Element> xpath = XPathFactory.instance().compile("//ofx:renderer", Filters.element()); 
			//List<Element> list = xpath.evaluate(doc); 
            List<Element> list; 
			logger.debug(list.size()+" <ofx:renderer/> Elements found in template");
			for (Element eRenderer : list) 
			{
				Renderer r = (Renderer)JDomUtil.toJaxb(eRenderer, Renderer.class);
				r =  cmpConfigUtil.getHtmlRenderer(html, r);
				
				try
				{
					Class cl = Class.forName(r.getClassName());
					Object oRenderer = cl.getConstructor().newInstance();
					if     (oRenderer instanceof OfxNavigationRenderer) {renderNav(eRenderer,(OfxNavigationRenderer)oRenderer,ofxDoc, section);}
					else if(oRenderer instanceof OfxHeaderRenderer) {renderHeader(eRenderer,(OfxHeaderRenderer)oRenderer,ofxDoc, section);}
					else if(oRenderer instanceof OfxSectionRenderer) {renderSection(eRenderer,(OfxSectionRenderer)oRenderer,ofxDoc, section);}
				}
				catch (ClassNotFoundException e) {throw new OfxConfigurationException("Renderer class not found: "+e.getMessage());}
				catch (IllegalArgumentException e) {logger.error("",e);}
				catch (SecurityException e) {logger.error("",e);}
				catch (InstantiationException e) {logger.error("",e);}
				catch (IllegalAccessException e) {logger.error("",e);}
				catch (InvocationTargetException e) {logger.error("",e);}
				catch (NoSuchMethodException e) {throw new OfxImplementationException("Renderer implementation does not have a empty constructor: "+r.getClassName());}
			}
		}
		File fWeb = cmpConfigUtil.getDir(html.getDir(), HtmlDir.web.toString());
		File fHtml = new File(fWeb,section.getId()+".html");
//		JDomUtil.debug(doc);
		JDomUtil.save(doc, fHtml, Format.getRawFormat()); 
	}
	
	private void renderNav(Element eRenderer, OfxNavigationRenderer navRenderer, Document ofxDoc, Section section) 
	{
		Element renderedElement = navRenderer.render(ofxDoc, section); 
		int index = eRenderer.getParent().indexOf(eRenderer); 
		//eRenderer.getParent().setContent(index, renderedElement);  1zz
		eRenderer.detach(); 
	}
	
	private void renderHeader(Element eRenderer, OfxHeaderRenderer headerRenderer, Document ofxDoc, Section section) 
	{
		Content content = headerRenderer.render(section); 
		int index = eRenderer.getParent().indexOf(eRenderer); 
		//eRenderer.getParentElement().setContent(index, content);  1zz
		eRenderer.detach(); 
	}
	
	private void renderSection(Element eRenderer, OfxSectionRenderer sectionRenderer, Document ofxDoc, Section section) 
	{
		List<Content> contents = sectionRenderer.render(section); 
		int index = eRenderer.getParent().indexOf(eRenderer); 
		//eRenderer.getParentElement().setContent(index, contents);  1zz
		eRenderer.detach(); 
	}
}
