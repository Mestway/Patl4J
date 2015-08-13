package org.openfuxml.addon.wiki.emitter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.openfuxml.addon.wiki.processor.ofx.EmitterFactory;
import org.openfuxml.addon.wiki.processor.ofx.emitter.SimpleMappingEmitter;
import org.xml.sax.Attributes;

public class BookEmitter extends SimpleMappingEmitter
{
	private String bookTitle;

	public BookEmitter(EmitterFactory ef)
	{
		super(ef,"book");
	}

	@Override
	public boolean localStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException
	{
		boolean ok = super.localStart(writer, htmlElementName, atts);
		if (ok && bookTitle != null)
		{
			writer.writeStartElement("title");
			writer.writeCharacters(bookTitle);
			writer.writeEndElement();
		}
		return ok;
	}

	@Override
	protected boolean localEnd(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException
	{
		//TODO Quick Hack
		HashMap<String, String> acronyms = new HashMap<String, String>();
		if (!acronyms.isEmpty())
		{
			writer.writeStartElement("appendix");
			writer.writeAttribute("id", "glossary");
			writer.writeStartElement("title");
			writer.writeAttribute("id", "glossary-end");
			writer.writeCharacters("Glossary");
			writer.writeEndElement(); // title
			writer.writeStartElement("glosslist");

			for (Map.Entry<String, String> glossEntry : new TreeMap<String, String>(acronyms).entrySet()) {

				writer.writeStartElement("glossentry");

				writer.writeStartElement("glossterm");
				writer.writeCharacters(glossEntry.getKey());
				writer.writeEndElement(); // glossterm

				writer.writeStartElement("glossdef");
				writer.writeStartElement("para");
				writer.writeCharacters(glossEntry.getValue());
				writer.writeEndElement(); // para
				writer.writeEndElement(); // glossdef

				writer.writeEndElement(); // glossentry
			}
			writer.writeEndElement(); // glosslist
			writer.writeEndElement(); // appendix
		}
		return super.localEnd(writer, htmlElementName);
	}

	public String getBookTitle() {return bookTitle;}
	public void setBookTitle(String bookTitle) {this.bookTitle = bookTitle;}

	@Override
	protected boolean permitsMultipleNexting() {return true;}
}