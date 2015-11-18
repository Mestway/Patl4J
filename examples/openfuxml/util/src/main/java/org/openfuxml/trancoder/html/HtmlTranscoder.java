package org.openfuxml.trancoder.html;

import java.io.Serializable;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(HtmlTranscoder.class);
	
	public HtmlTranscoder()
	{
		
	}
	
	public Section transcode(String html)
	{
		Section section = XmlSectionFactory.build();
		Document doc = Jsoup.parse(html);
		List<Node> childs = doc.body().childNodes();
		for(Node node : childs)
		{
			section.getContent().add(transcodeNode(node));
		}
		return section;
	}
	
	private Serializable transcodeNode(Node node)
	{
//		System.out.println(node.nodeName());
		if(node.nodeName().equals("ol"))
		{
			HtmlListTranscoder t = new HtmlListTranscoder();
			return t.transcode(node);
		}
		return new Paragraph();
	}

}
