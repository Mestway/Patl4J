package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexIncludeRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexIncludeRenderer.class);
	
	public LatexIncludeRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Class<?> parent, String include, boolean postBlankLine) throws OfxAuthoringException
	{
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+parent.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		
		StringBuffer sb = new StringBuffer();
		sb.append("\\input{").append(include).append("}");
		
		txt.add(sb.toString());
		if(postBlankLine){txt.add("");}
	}
}
