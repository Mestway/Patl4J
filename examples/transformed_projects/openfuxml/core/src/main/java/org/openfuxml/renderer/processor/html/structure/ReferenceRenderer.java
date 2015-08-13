package org.openfuxml.renderer.processor.html.structure;

import org.dom4j.Node;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;

import org.openfuxml.content.ofx.Reference;
import org.openfuxml.renderer.processor.html.interfaces.OfxReferenceRenderer;

public class ReferenceRenderer implements OfxReferenceRenderer
{
	public Node render(Reference reference)
	{
		Element a = DocumentHelper.createElement("a"); 
		a.addAttribute("href", reference.getTarget()); 
		a.setText(reference.getValue()); 
		return a;
	}
}
