package org.openfuxml.renderer.latex.content.graph;

import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTreeRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTreeRenderer.class);
	
	private LatexTreeNodeRenderer treeNodeRenderer;
	
	public LatexTreeRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
		treeNodeRenderer = new LatexTreeNodeRenderer(cmm,dsm);
	}
	
/*	public void render(Tree tree) throws OfxAuthoringException
	{	
		preTxt.add("\\Tree ");
		treeNodeRenderer.render(tree.getNode());
		renderer.add(treeNodeRenderer);
	}
	*/
}