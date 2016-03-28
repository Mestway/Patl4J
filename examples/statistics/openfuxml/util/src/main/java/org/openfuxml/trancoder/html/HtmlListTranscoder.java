package org.openfuxml.trancoder.html;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.content.list.Type;
import org.openfuxml.content.ofx.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlListTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(HtmlListTranscoder.class);
	
	public HtmlListTranscoder()
	{
		
	}
	
	public List transcode(Node list)
	{
		Type type = new Type();
		type.setOrdering("ordered");
		
		List ofxList = new List();
		ofxList.setType(type);
		for(Node node : list.childNodes())
		{
			Element e = (Element)node;
			Paragraph p = new Paragraph();
			p.getContent().add(e.text());
			
			Item item = new Item();
			item.getContent().add(p);
			
			ofxList.getItem().add(item);
		}
		return ofxList;
	}

}
