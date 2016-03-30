package org.openfuxml.factory.xml.ofx.list;

import org.openfuxml.content.list.List;
import org.openfuxml.content.list.Type;

public class XmlListFactory
{
	public static enum Ordering {ordered,unordered}
	
	public static List build(String ordering)
	{
		Type type = new Type();
		type.setOrdering("unordered");
		
		List xml = new List();
		xml.setType(type);

		return xml;
	}
}
