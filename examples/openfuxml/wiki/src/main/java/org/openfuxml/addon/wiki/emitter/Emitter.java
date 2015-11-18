package org.openfuxml.addon.wiki.emitter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.Attributes;

public interface Emitter
{
	/**
	 * 
	 * @return true if the emitter started successfully, otherwise false
	 */
	public boolean start(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException;

	public void content(XMLStreamWriter writer, char[] ch, int start, int length) throws XMLStreamException;

	/**
	 * 
	 * @return true if the emitter is done
	 */
	public boolean end(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException;

	public void close() throws XMLStreamException;

}