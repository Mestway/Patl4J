package org.openfuxml.factory.xml.layout;


public class XmlFloatFactory
{
	public static enum Horizontal {center}
	
	public static org.openfuxml.content.layout.Float build(boolean floating)
	{
		org.openfuxml.content.layout.Float xml = new org.openfuxml.content.layout.Float();
		xml.setValue(floating);
		return xml;
	}
}
