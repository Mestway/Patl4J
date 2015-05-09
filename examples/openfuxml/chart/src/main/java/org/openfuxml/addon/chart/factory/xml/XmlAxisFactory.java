package org.openfuxml.addon.chart.factory.xml;

import org.openfuxml.xml.addon.chart.Axis;

public class XmlAxisFactory
{
	public static enum Orientation{domain,range0,range1};
	
	public static Axis build(Orientation orientation)
	{
		Axis xml = new Axis();
		xml.setCode(orientation.toString());
		
		return xml;
	}
}
