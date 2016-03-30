package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.layout.Alignment;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.media.LatexImageRenderer;
import org.openfuxml.renderer.latex.content.text.LatexEmphasisRenderer;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexParagraphRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexParagraphRenderer.class);
	
	
	public LatexParagraphRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm,boolean preBlankLine)
	{
		super(cmm,dsm);
		if(preBlankLine){preTxt.add("");}
	}
	
	
	public void render(Paragraph paragraph) throws OfxAuthoringException
	{	
		int nonInlineCounter = 0;
		Image image=null;
		for(Object o : paragraph.getContent())
		{
			if(o instanceof Image)
			{
				image = (Image)o;
				if(!image.isSetAlignment() || !image.getAlignment().isSetHorizontal())
				{
					throw new OfxAuthoringException(Image.class.getSimpleName()+" in "+Paragraph.class.getSimpleName()+" needs an horizontal "+Alignment.class.getSimpleName());
				}
				XmlAlignmentFactory.Horizontal horizontal = XmlAlignmentFactory.Horizontal.valueOf(image.getAlignment().getHorizontal());
				if(!horizontal.equals(XmlAlignmentFactory.Horizontal.inline)){nonInlineCounter++;}
			}
		}
		if(nonInlineCounter>1){throw new OfxAuthoringException("More than one non-inline "+Image.class.getSimpleName()+" in "+Paragraph.class.getSimpleName()+" not allowed");}
		
		if(nonInlineCounter==1 && image!=null)
		{
			paragraph.getContent().remove(image);
			LatexImageRenderer rImage = new LatexImageRenderer(cmm,dsm);
			rImage.render(this,image);
			txt.add("\\begin{window}[0, r, "+rImage.getSingleLine()+", {}]");
		}
		
		StringBuffer sb = new StringBuffer();
		for(Object o : paragraph.getContent())
		{
			if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
			else if(o instanceof String){sb.append(TexSpecialChars.replace((String)o));}
			else if(o instanceof Emphasis){renderEmphasis(sb, (Emphasis)o);}
			else if(o instanceof Image){logger.info("INLINE Image NYI");}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
		txt.add(sb.toString());
		
		if(nonInlineCounter==1 && image!=null)
		{
			txt.add("\\end{window}");
		}
	}
	
	private void renderEmphasis(StringBuffer sb, Emphasis emphasis) throws OfxAuthoringException
	{
		LatexEmphasisRenderer stf = new LatexEmphasisRenderer(cmm,dsm);
		stf.render(emphasis);
		for(String s : stf.getContent()){sb.append(s);}
	}
}
