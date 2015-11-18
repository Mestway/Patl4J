package org.openfuxml.addon.wiki.processor.ofx.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.MessageFormat;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.sf.exlp.util.io.StringIO;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.DocumentHelper;

import org.openfuxml.addon.wiki.FormattingXMLStreamWriter;
import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.ofx.OfxHtmlContentHandler;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.addon.wiki.util.IgnoreDtdEntityResolver;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class WikiPageProcessor extends AbstractWikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiPageProcessor.class);
	
//	private OfxContentTrimmer ofxContentTrimmer;
//	import org.openfuxml.renderer.processor.post.OfxContentTrimmer;
	
	public WikiPageProcessor()
	{
		WikiTemplates.init();
//		ofxContentTrimmer = new OfxContentTrimmer();
	}
	
	public void processPage(Page page) throws OfxAuthoringException, OfxInternalProcessingException
	{
		checkPageConfig(page);
		
		try
		{
			String srcName =  page.getFile()+"."+WikiProcessor.WikiFileExtension.xhtml;
			String dstName = page.getFile()+"."+WikiProcessor.WikiFileExtension.xml;
			String txtMarkup = StringIO.loadTxt(srcDir, srcName);
			String result = process(txtMarkup, page.getName());
			
			File fDst = new File(dstDir, dstName);
			Document doc = JDomUtil.txtToDoc(result); 
			doc = checkTransparent(doc, page.getSection());
			
			logger.warn("Content Trimmer deactivated here");
//			doc = ofxContentTrimmer.trim(doc);
			JDomUtil.save(doc, fDst, new OutputFormat()); 
		}
		catch (IOException e) {logger.error("",e);}
		catch (ParserConfigurationException e) {logger.error("",e);}
		catch (XMLStreamException e) {logger.error("",e);}
		catch (SAXException e) {logger.error("",e);}
		catch (JDOMException e) {logger.error("",e);}
	}
	
	private Document checkTransparent(Document doc, Section section) throws OfxInternalProcessingException 
	{
		if(section.isSetContainer() && section.isContainer())
		{
			Element rootElement = doc.getRootElement(); 
			if(rootElement.getName().equalsIgnoreCase(Section.class.getSimpleName())) 
			{
				rootElement.addAttribute("transparent", "true"); 
				logger.debug(rootElement.getName()); 
			}
			else {throw new OfxInternalProcessingException("Root element <"+rootElement.getName()+"> of Wiki.Processing not expected");} 
		}
		return doc;
	}
	
	public Element process(String xhtmlContent) 
	{
		Element result = null; 
		try
		{
			String xml = process(xhtmlContent, "dummy");
			Document doc = JDomUtil.txtToDoc(xml); 
			result = doc.getRootElement(); 
			result.detach(); 
			Element eTitle = (Element)result.elements().get(0);  
			eTitle.detach(); 
			result.addAttribute("transparent", "true"); 
		}
		catch (IOException e) {logger.error("",e);}
		catch (ParserConfigurationException e) {logger.error("",e);}
		catch (XMLStreamException e) {logger.error("",e);}
		catch (SAXException e) {logger.error("",e);}
		catch (Exception e) {logger.error("",e);}
		return result;
	}

	public String process(String xhtmlContent, String titleText) throws IOException, ParserConfigurationException, XMLStreamException, SAXException
	{
		Object[] objects = new Object[2];
		objects[0] = titleText;
		
		String header = MessageFormat.format(WikiTemplates.htmlHeader, objects);
		
		StringBuffer sb = new StringBuffer();
		sb.append(header);
		sb.append(xhtmlContent);
		sb.append(WikiTemplates.htmlFooter);
		logger.debug("Parsing: "+sb.length()+" characters");

		InputSource inputSource = new InputSource(new StringReader(sb.toString()));

		SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setValidating(false);
		SAXParser saxParser = factory.newSAXParser();

		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setEntityResolver(IgnoreDtdEntityResolver.getInstance());

		StringWriter out = new StringWriter();
		XMLStreamWriter writer = createXMLStreamWriter(out);

		logger.warn("Using dummy String injectionDir");
		OfxHtmlContentHandler contentHandler = new OfxHtmlContentHandler(writer,".");

		xmlReader.setContentHandler(contentHandler);
		xmlReader.parse(inputSource);

		writer.close();

		String result = out.toString();
		result = addNS(result);
//		logger.debug(result);
		return result;
	}
	
	private String addNS(String xml)
	{
		int indexXml = xml.indexOf(">");
		int indexRoot = xml.substring(indexXml+1, xml.length()).indexOf(">");
		
//		logger.debug(xml.substring(indexXml+indexRoot+1));
		StringBuffer sb = new StringBuffer();
		sb.append(xml.substring(0,indexXml+indexRoot+1));
		sb.append(" xmlns:ofx=\"http://www.openfuxml.org\"");
		sb.append(" xmlns:list=\"http://www.openfuxml.org/list\"");
		sb.append(" xmlns:table=\"http://www.openfuxml.org/table\"");
		sb.append(" xmlns:layout=\"http://www.openfuxml.org/layout\"");
		sb.append(" xmlns:wiki=\"http://www.openfuxml.org/wiki\"");
		sb.append(xml.substring(indexXml+indexRoot+1,xml.length()));
//		logger.debug(sb);
		
		return sb.toString();
	}

	protected XMLStreamWriter createXMLStreamWriter(Writer out)
	{
		XMLStreamWriter writer;
		try
		{
			writer = XMLOutputFactory.newInstance().createXMLStreamWriter(out);
		}
		catch (XMLStreamException e1) {throw new IllegalStateException(e1);}
		catch (FactoryConfigurationError e1) {throw new IllegalStateException(e1);}
		return new FormattingXMLStreamWriter(writer);
	}
	
	private void checkPageConfig(Page page) throws OfxAuthoringException
	{
		JaxbUtil.debug(page);
		boolean sSection = page.isSetSection();
		
		if(!sSection){throw new OfxAuthoringException("None of <section>  selected!");}
	}
}
