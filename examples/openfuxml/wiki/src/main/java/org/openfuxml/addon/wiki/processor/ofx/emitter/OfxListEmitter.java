package org.openfuxml.addon.wiki.processor.ofx.emitter;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.openfuxml.addon.wiki.processor.ofx.EmitterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;

public class OfxListEmitter extends NestingEmitter
{
	final static Logger logger = LoggerFactory.getLogger(OfxListEmitter.class);

	public static enum Ordering {ordered,unordered}
	
	private Map<String, String> attributes;
	private Ordering ordering;
	
	private final static String tag = "list:list";
	
	public OfxListEmitter(EmitterFactory ef, Ordering ordering)
	{
		super(ef);
		this.ordering=ordering;
	}

	public void setAttribute(String name, String value)
	{
		if (attributes == null)
		{
			attributes = new TreeMap<String, String>();
		}
		attributes.put(name, value);
	}

	@Override
	protected boolean localStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException
	{
		writer.writeStartElement(tag);

		switch(ordering)
		{
			case ordered: typeOrdered(writer);break;
			case unordered: typeUnordered(writer);break;
		}
		
		boolean hasId = false;
		if (attributes != null)
		{
			for (Map.Entry<String, String> attr : attributes.entrySet())
			{
				writer.writeAttribute(attr.getKey(), attr.getValue());
			}
			if (attributes.containsKey("id")) {hasId = true;}
		}
		if(!hasId)
		{
			String elementId = atts.getValue("id");
			if (elementId != null)
			{
				writer.writeAttribute("id", elementId);
			}
		}
		return true;
	}
	
	@Override
	protected boolean localEnd(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException
	{
		writer.writeEndElement();
		return true;
	}
	
	private void typeOrdered(XMLStreamWriter writer) throws XMLStreamException
	{
		writer.writeStartElement("list:type");
		writer.writeAttribute("ordering", "ordered");
		writer.writeEndElement();
	}
	
	private void typeUnordered(XMLStreamWriter writer) throws XMLStreamException
	{
		writer.writeStartElement("list:type");
		writer.writeAttribute("ordering", "unordered");
		writer.writeEndElement();
	}
}