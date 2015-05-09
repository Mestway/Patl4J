package org.openfuxml.addon.epub.data.factory;

import org.openfuxml.addon.epub.data.jaxb.ncx.DocTitle;
import org.openfuxml.addon.epub.data.jaxb.ncx.Head;
import org.openfuxml.addon.epub.data.jaxb.ncx.Text;
import org.openfuxml.addon.epub.data.jaxb.ncx.NavMap.NavPoint;
import org.openfuxml.addon.epub.data.jaxb.ncx.NavMap.NavPoint.Content;
import org.openfuxml.addon.epub.data.jaxb.ncx.NavMap.NavPoint.NavLabel;

public class NcxFactory
{
	public static Head.Meta getHeadMeta(String name, String content)
	{
		Head.Meta meta = new Head.Meta();
		meta.setName(name);
		meta.setContent(content);
		return meta;
	}
	
	public static DocTitle getTitle(String value)
	{
		DocTitle title = new DocTitle();
		Text text = new Text();
		text.setValue(value);
		title.setText(text);
		return title;
	}
	
	public static NavPoint getNavPoint(String id, int playOrder, String label, String src)
	{
		NavPoint navpoint = new NavPoint();
		navpoint.setId(id);
		navpoint.setPlayOrder(playOrder);
		
		NavLabel navlabel = new NavLabel();
		Text text = new Text();
		text.setValue(label);
		navlabel.setText(text);
		navpoint.setNavLabel(navlabel);
		
		Content content = new Content();
		content.setSrc(src);
		navpoint.setContent(content);
		
		return navpoint;
	}
}
