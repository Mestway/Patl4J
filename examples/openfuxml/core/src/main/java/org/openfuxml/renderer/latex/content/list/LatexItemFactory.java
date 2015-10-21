package org.openfuxml.renderer.latex.content.list;

import org.openfuxml.content.list.Item;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.list.LatexListRenderer.ListType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexItemFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexItemFactory.class);
	
	public LatexItemFactory(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(ListType lt, Item item) throws OfxAuthoringException
	{	
		preTxt.add("");
		StringBuffer sb = new StringBuffer();
		sb.append("\\item");
		
		if(lt==LatexListRenderer.ListType.description)
		{
			if(!item.isSetName()){throw new OfxAuthoringException("<description.list> needss a item@name");}
			sb.append(" [").append(item.getName()).append("]");
		}
		preTxt.add(sb.toString());
		
		
		for(Object s : item.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,false);}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}
	}
}