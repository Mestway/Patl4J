package org.openfuxml.factory.xml.ofx.list;

import org.openfuxml.content.list.Item;
import org.openfuxml.content.ofx.Paragraph;

public class XmlListItemFactory
{	
	public static Item build(String text)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(text);
		
		Item xml = new Item();
		xml.getContent().add(p);

		return xml;
	}
}
