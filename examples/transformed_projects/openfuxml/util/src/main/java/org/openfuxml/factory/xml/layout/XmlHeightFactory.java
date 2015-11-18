package org.openfuxml.factory.xml.layout;

import org.openfuxml.content.layout.Height;

public class XmlHeightFactory
{
	public static enum Unit {em}
	
	public static Height em(double value)
	{
		Height xml = new Height();
		xml.setValue(value);
		xml.setUnit(Unit.em.toString());
		return xml;
	}
}
