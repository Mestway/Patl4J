package org.openfuxml.factory.xml.ofx.content.text;

import org.openfuxml.content.ofx.Title;

public class XmlTitleFactory
{
    public static Title build(String title){return build(null,title);}

	public static Title build(String lang, String title)
	{
		Title xml = new Title();
		xml.setLang(lang);
		xml.setValue(title);
		return xml;
	}
}
