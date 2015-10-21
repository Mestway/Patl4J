package org.openfuxml.renderer.processor.epub.factory;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.DocumentHelper;

import org.openfuxml.content.ofx.Title;
import org.openfuxml.processor.pre.ExternalContentEagerLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EpubTitleFactory
{
	final static Logger logger = LoggerFactory.getLogger(ExternalContentEagerLoader.class);
	
	private Namespace ns;
	
	public EpubTitleFactory(Namespace ns)
	{
		this.ns=ns;
	}
	
	public Element createTitle(Title title)
	{
		Element eTitle = DocumentHelper.createElement("h1",ns);
		eTitle.setText(title.getValue());
		return eTitle;
	}
}
