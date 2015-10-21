package org.openfuxml.factory.xml.ofx.content;

import org.openfuxml.content.ofx.Raw;

public class XmlRawFactory
{
	public static Raw build(String value)
	{
		Raw xml = new Raw();
		xml.setValue(value);
		return xml;
	}
}
