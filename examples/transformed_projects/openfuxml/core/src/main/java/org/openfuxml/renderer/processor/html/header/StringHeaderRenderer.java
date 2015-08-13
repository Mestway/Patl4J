package org.openfuxml.renderer.processor.html.header;

import net.sf.exlp.exception.ExlpXpathNotFoundException;

import org.dom4j.Node;
import org.dom4j.Text;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.renderer.processor.html.interfaces.OfxHeaderRenderer;
import org.openfuxml.xml.xpath.content.SectionXpath;

public class StringHeaderRenderer implements OfxHeaderRenderer
{
	public Node render(Section section)
	{
		Title title = null;
		try {
			title = SectionXpath.getTitle(section);
		} catch (ExlpXpathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Text(title.getValue());
	}
}
