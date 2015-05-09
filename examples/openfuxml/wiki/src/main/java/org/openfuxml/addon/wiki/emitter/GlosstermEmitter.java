package org.openfuxml.addon.wiki.emitter;

import java.util.HashMap;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.openfuxml.addon.wiki.processor.ofx.EmitterFactory;
import org.openfuxml.addon.wiki.processor.ofx.emitter.SimpleMappingEmitter;
import org.xml.sax.Attributes;

public class GlosstermEmitter extends SimpleMappingEmitter {

	private String acronymTitle;

	private StringBuilder acronym = new StringBuilder();

	public GlosstermEmitter(EmitterFactory ef)
	{
		super(ef,"glossterm");
	}

	@Override
	protected void localContent(XMLStreamWriter writer, char[] ch, int start, int length) throws XMLStreamException {
		if (length > 0) {
			acronym.append(ch, start, length);
		}
		super.localContent(writer, ch, start, length);
	}

	@Override
	public boolean localEnd(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException {
		HashMap<String, String> acronyms = new HashMap<String, String>();
		if (acronym.length() > 0) {
			String acronym = this.acronym.toString().trim();
			if (acronym.length() > 0) {
				String previousTitle = acronyms.put(acronym, acronymTitle);
				if (previousTitle != null) {
					if (acronymTitle == null || previousTitle.length() > acronymTitle.length()) {
						acronyms.put(acronym, previousTitle);
					}
				}
			}
		}
		return super.localEnd(writer, htmlElementName);
	}

	@Override
	public boolean localStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException {
		if (htmlElementName.equals("acronym")) {
			acronymTitle = atts.getValue("title");
		}
		return super.localStart(writer, htmlElementName, atts);
	}

}