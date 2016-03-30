package org.openfuxml.renderer.latex.content.graph;

import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTreeNodeRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTreeNodeRenderer.class);
	
	public LatexTreeNodeRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
/*	
	public void render(Node node) throws OfxAuthoringException
	{	
		StringBuffer sb = new StringBuffer();
		sb.append(" [");
		if(node.isSetLabel())
		{
			sb.append(".{");
			sb.append(node.getLabel());
			sb.append("}" );
		}
		
		preTxt.add(sb.toString());
		for(Node child : node.getNode())
		{
			LatexTreeNodeRenderer childRenderer = new LatexTreeNodeRenderer();
			childRenderer.render(child);
			renderer.add(childRenderer);
		}
		postTxt.add("]");
	}
	*/
}