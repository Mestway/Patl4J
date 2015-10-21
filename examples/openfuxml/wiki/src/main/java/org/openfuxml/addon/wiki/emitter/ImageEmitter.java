package org.openfuxml.addon.wiki.emitter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.openfuxml.addon.wiki.processor.ofx.EmitterFactory;
import org.openfuxml.addon.wiki.processor.ofx.emitter.SimpleMappingEmitter;
import org.xml.sax.Attributes;

public class ImageEmitter extends SimpleMappingEmitter
{

	public ImageEmitter(EmitterFactory ef)
	{
		super(ef, "mediaobject", "imageobject", "imagedata");
	}

	@Override
	public boolean localStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException {
		String src = null;
//		String width = null;
//		String height = null;
		if (htmlElementName.equals("img")) {
			src = atts.getValue("src");
		}
		boolean ok = super.localStart(writer, htmlElementName, atts);
		if (ok && src != null) {
			writer.writeAttribute("fileref", src);
//			if (width != null) {
//				writer.writeAttribute("contentwidth", width);
//				writer.writeAttribute("width", width);
//			}
//			if (height != null) {
//				writer.writeAttribute("contentdepth", width);
//				writer.writeAttribute("depth", width);
//			}
		}
		return ok;
	}
}