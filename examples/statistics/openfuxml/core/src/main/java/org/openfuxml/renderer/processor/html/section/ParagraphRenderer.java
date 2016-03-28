package org.openfuxml.renderer.processor.html.section;

import org.dom4j.Node;
import org.dom4j.Element;
import org.dom4j.Text;
import org.dom4j.DocumentHelper;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.renderer.processor.html.structure.ReferenceRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParagraphRenderer
{
	final static Logger logger = LoggerFactory.getLogger(ParagraphRenderer.class);
	
	public ParagraphRenderer()
	{
		
	}
	
	public Node render(Paragraph ofxParagraph) 
	{
		Element p = DocumentHelper.createElement("p"); 
		
		for(Object o : ofxParagraph.getContent())
		{
			if(o instanceof String){
                //p.addContent(new Text((String)o));
                p.add(DocumentHelper.createText((String)o)); 
                //p.addText((String)o);
            }
			else if(o instanceof Reference){ReferenceRenderer r = new ReferenceRenderer();p.add(r.render((Reference)o));}
			else {logger.warn("Unknown content: "+o.getClass().getSimpleName());}
		}
		return p;
	}
}
