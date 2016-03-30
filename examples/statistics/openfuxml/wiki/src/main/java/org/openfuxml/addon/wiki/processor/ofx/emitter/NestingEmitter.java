package org.openfuxml.addon.wiki.processor.ofx.emitter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.openfuxml.addon.wiki.emitter.Emitter;
import org.openfuxml.addon.wiki.processor.ofx.EmitterFactory;
import org.xml.sax.Attributes;

public class NestingEmitter implements Emitter
{
	private Emitter next = null;
	private String elementName = null;
	private int nestLevel = 0;

	protected EmitterFactory ef;
	
	public NestingEmitter(EmitterFactory ef)
	{
		this.ef = ef;
	}
	
	public void content(XMLStreamWriter writer, char[] ch, int start, int length) throws XMLStreamException
	{
		if (next != null) {next.content(writer, ch, start, length);}
		else {localContent(writer, ch, start, length);}
	}

	public final boolean end(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException {
		--nestLevel;
		if (nestLevel > 0 && next != null) {
			if (next.end(writer, htmlElementName)) {
				next.close();
				next = null;
			}
			return false;
		} else {
			if (next != null) {
				next.close();
				next = null;
			}
			return localEnd(writer, htmlElementName);
		}
	}

	protected boolean localEnd(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException {
		return true;
	}

	protected boolean localStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException {
		return true;
	}

	protected void localContent(XMLStreamWriter writer, char[] ch, int start, int length) throws XMLStreamException {
		writer.writeCharacters(ch, start, length);
	}

	public final boolean start(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException {
		++nestLevel;
		if (elementName == null) {
			elementName = htmlElementName;
			return localStart(writer, htmlElementName, atts);
		} else {
			if (next == null) {
				next = ef.getEmitter(htmlElementName);
			}
			if (!next.start(writer, htmlElementName, atts)) {
				next.close();
				next = null;
				if (permitsMultipleNexting()) {
					next = ef.getEmitter(htmlElementName);
					if (!next.start(writer, htmlElementName, atts)) {
						throw new IllegalStateException();
					}
					return true;
				} else {
					return false;
				}
			}
			return true;
		}
	}

	protected boolean permitsMultipleNexting() {
		return true;
	}

	public void close() throws XMLStreamException {
		if (next != null) {
			next.close();
			next = null;
		}
	}
}