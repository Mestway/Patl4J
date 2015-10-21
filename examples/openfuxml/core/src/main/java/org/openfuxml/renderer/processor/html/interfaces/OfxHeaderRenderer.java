package org.openfuxml.renderer.processor.html.interfaces;

import org.dom4j.Node;
import org.openfuxml.content.ofx.Section;

public interface OfxHeaderRenderer
{
	public Node render(Section actualSection);
}
