package org.openfuxml.factory.xml.ofx.content.structure;

import org.openfuxml.content.ofx.Section;

public class XmlSectionFactory
{
	public static Section build()
	{
		return new Section();
	}
	
	public static Section build(String lang)
	{
		Section xml = new Section();
		xml.setLang(lang);
		return xml;
	}
	
	public static Section build(String id, String lang, String classifier)
	{
		Section xml = new Section();
		xml.setId(id);
		xml.setLang(lang);
		xml.setClassifier(classifier);
		return xml;
	}
}
