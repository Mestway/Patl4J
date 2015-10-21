package org.openfuxml.addon.wiki.processor.ofx.emitter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.openfuxml.addon.wiki.processor.ofx.EmitterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;

public class AnchorEmitter extends NestingEmitter
{
	final static Logger logger = LoggerFactory.getLogger(AnchorEmitter.class);
	
	private boolean openTag = false;

	private static final String tag = "ofx:reference";
	
	public AnchorEmitter(EmitterFactory ef)
	{
		super(ef);
	}

	@Override
	public boolean localStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException
	{
		String href = null;
		String name = null;
		if (htmlElementName.equals("a"))
		{
			href = atts.getValue("href");
			name = atts.getValue("name");
		}
		
		logger.debug("href: "+href);
		logger.debug("name: "+name);
		
		if (href != null)
		{
			openTag = true;
			writer.writeStartElement(tag);
			writer.writeAttribute("target", href);
		}
		else {logger.warn("HREF==null");}
		return true;
	}

	@Override
	protected boolean localEnd(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException
	{
		closeTag(writer);
		return true;
	}

	private void closeTag(XMLStreamWriter writer) throws XMLStreamException
	{
		if (openTag)
		{
			writer.writeEndElement();
			openTag = false;
		}
	}

	@Override
	public void close() throws XMLStreamException
	{
		super.close();
		closeTag(ef.getWriter());
	}
}