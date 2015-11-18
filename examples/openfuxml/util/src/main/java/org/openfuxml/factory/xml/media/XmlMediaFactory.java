package org.openfuxml.factory.xml.media;

import org.openfuxml.content.media.Media;

public class XmlMediaFactory
{
	public static Media dst(String dst)
	{
		return build(null,dst);
	}
	
	public static Media build(String src, String dst)
	{
		Media xml = new Media();
		xml.setSrc(src);
		xml.setDst(dst);
		return xml;
	}
}
