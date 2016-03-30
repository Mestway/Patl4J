package org.openfuxml.addon.jsf.controller.factory.xml;

import org.openfuxml.xml.addon.jsf.Attribute;

public class XmlAttributeFactory
{
	public static Attribute create(String name, boolean required, String _default, String shortDescription)
	{
		Attribute xml = new Attribute();
		xml.setName(name);
		xml.setRequired(required);
		xml.setDefault(_default);
		xml.setShortDescription(shortDescription);
		return xml;
	}
}
