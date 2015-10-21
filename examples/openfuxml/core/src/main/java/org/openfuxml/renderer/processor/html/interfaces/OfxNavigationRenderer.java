package org.openfuxml.renderer.processor.html.interfaces;

import org.dom4j.Element;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Section;

public interface OfxNavigationRenderer
{
	public Element render(Document ofxDoc, Section actualSection);
}
