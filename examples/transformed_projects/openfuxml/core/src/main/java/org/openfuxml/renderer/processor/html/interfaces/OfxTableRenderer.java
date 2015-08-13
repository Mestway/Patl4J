package org.openfuxml.renderer.processor.html.interfaces;

import org.dom4j.Element;
import org.openfuxml.content.table.Table;

public interface OfxTableRenderer
{
	public Element render(Table table);
}
