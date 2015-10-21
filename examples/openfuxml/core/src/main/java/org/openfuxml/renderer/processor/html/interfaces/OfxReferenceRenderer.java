package org.openfuxml.renderer.processor.html.interfaces;

import org.dom4j.Node;
import org.openfuxml.content.ofx.Reference;

public interface OfxReferenceRenderer
{
	public Node render(Reference refernce);
}
