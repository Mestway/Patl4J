package org.openfuxml.addon.wiki.processor.ofx;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.processor.ofx.emitter.NestingEmitter;
import org.openfuxml.addon.wiki.processor.ofx.emitter.OfxSectionEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

 
public class OfxHtmlContentHandler implements ContentHandler
{
	final static Logger logger = LoggerFactory.getLogger(OfxHtmlContentHandler.class);
	
	private OfxSectionEmitter sctionEmitter;
	private NestingEmitter emitter;
	private XMLStreamWriter writer;
	
	private Map<String, String> acronyms = new HashMap<String, String>();
	private EmitterFactory ef;

	public OfxHtmlContentHandler(XMLStreamWriter writer,String injectionDir)
	{
		this.writer=writer;
		ef = new EmitterFactory(writer,injectionDir);
		sctionEmitter = new OfxSectionEmitter(ef);
		emitter = sctionEmitter;
	}
	
	public void characters(char ch[], int start, int length) throws SAXException
	{
		try
		{
			emitter.content(writer, ch, start, length);
		}
		catch (XMLStreamException e) {throw new SAXException(e);}
	}

	public void endDocument() throws SAXException
	{
		try
		{
			emitter.close();
			emitter = null;
			writer.writeEndDocument();

			acronyms.clear();
		}
		catch (XMLStreamException e) {throw new SAXException(e);}
	}

	public void endElement(String uri, String localName, String name) throws SAXException
	{
		try
		{
			if (emitter.end(writer, localName))
			{
				emitter.close();
				emitter = new NestingEmitter(ef);
			}
		}
		catch (XMLStreamException e) {throw new SAXException(e);}
	}

	public void endPrefixMapping(String prefix) throws SAXException {}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}

	public void processingInstruction(String target, String data) throws SAXException {}

	public void setDocumentLocator(Locator locator) {}

	public void skippedEntity(String name) throws SAXException {}

	public void startDocument() throws SAXException
	{
		try
		{
			writer.writeStartDocument();
			writer.writeDTD(WikiTemplates.xmlDoctype);
		}
		catch (XMLStreamException e) {throw new SAXException(e);}
	}

	public void startElement(String uri, String localName, String name, Attributes atts) throws SAXException
	{
		try
		{
			if (emitter==null){System.out.println("em==null");}
			if (!emitter.start(writer, localName, atts))
			{
				throw new IllegalStateException();
			}
		}
		catch (XMLStreamException e) {throw new SAXException(e);}
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {}
}