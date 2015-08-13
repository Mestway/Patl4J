package org.openfuxml.factory.xml.ofx.layout;

import org.openfuxml.content.layout.Line;

public class XmlLineFactory
{
	public static enum Orientation{top,bottom,left,right};
	
	public static Line buildTop()
	{
		Line xml = new Line();
		xml.setOrientation(Orientation.top.toString());
		return xml;
	}
}
