package org.openfuxml.factory.xml.ofx.content.structure;

import org.openfuxml.content.ofx.Paragraph;

public class XmlParagraphFactory
{
	public static Paragraph build()
	{
		return new Paragraph();
	}
	
	public static Paragraph build(String lang)
	{
		Paragraph xml = new Paragraph();
		xml.setLang(lang);
		return xml;
	}
}
