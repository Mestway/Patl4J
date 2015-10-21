package org.openfuxml.addon.wiki.processor.template.transformator;

import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.dom4j.Element;

import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.processor.markup.WikiInlineProcessor;

public interface WikiTemplateTransformator
{
	void setWikiInlineProcessor(WikiInlineProcessor wikiInlineProcessor);
	void setNsPrefixMapperInterface(NsPrefixMapperInterface nsPrefixMapper);
	Element transform(Template template);
}
