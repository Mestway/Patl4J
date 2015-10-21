package org.openfuxml.renderer.processor.html.interfaces;

import java.util.List;

import org.dom4j.Node;
import org.openfuxml.content.ofx.Section;

public interface OfxSectionRenderer
{
	public List<Node> render(Section section);
}
