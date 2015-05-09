package org.openfuxml.factory.xml.layout;

import org.openfuxml.content.layout.Width;

public class XmlWidthFactory
{
	public static enum Unit {cm}
	
	public static Width cm(double value)
	{
		Width xml = new Width();
		xml.setValue(value);
		xml.setUnit(Unit.cm.toString());
		return xml;
	}
}
