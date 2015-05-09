package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.list.List;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Listing;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.listing.LatexListingRenderer;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexSectionRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSectionRenderer.class);
	
	private LatexPreamble latexPreamble;
	int lvl;
	
	public LatexSectionRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm, int lvl, LatexPreamble latexPreamble)
	{
		super(cmm,dsm);
		this.lvl=lvl;
		this.latexPreamble=latexPreamble;
	}
	
	public void render(Section section) throws OfxAuthoringException, OfxConfigurationException
	{
		if(!section.isSetContainer()){section.setContainer(false);}
		if(section.isContainer()){lvl=lvl-1;}
		
		preTxt.addAll(LatexCommentRenderer.stars());
		if(section.isContainer() && section.isSetInclude())
		{
			if(section.getContent().size()>0)
			{
				preTxt.addAll(LatexCommentRenderer.comment("All content of "+Section.class.getSimpleName()+" will be ignored because of inlcude"));
			}
			LatexIncludeRenderer rComment = new LatexIncludeRenderer(cmm,dsm);
			rComment.render(Section.class,section.getInclude(),true);
			renderer.add(rComment);
			return;
		}

		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Section.class.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		
		for(Object s : section.getContent())
		{
			if (s instanceof Comment)
			{
				LatexCommentRenderer rComment = new LatexCommentRenderer(cmm,dsm);
				rComment.render((Comment)s);
				renderer.add(rComment);
			}
		}
		
		logger.trace("Render section");
		
		
		
		for(Object s : section.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Title){renderTitle(section,(Title)s);}
			else if(s instanceof Section){renderSection((Section)s);}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,true);}
			else if(s instanceof Table){renderTable((Table)s);}
			else if(s instanceof List){renderList((List)s,this);}
            else if(s instanceof Listing){renderListing((Listing)s);}
            else if(s instanceof Image){renderImage((Image)s);}
            else if(s instanceof Comment){}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}
//		if(section.getContent()logger.debug(getSectionHeader("x"));
		
	}
	
	private void renderTitle(Section section,Title title)
	{
		if(!section.isContainer())
		{
			LatexSectionTitleRenderer stf = new LatexSectionTitleRenderer(cmm,dsm,lvl,latexPreamble);
			stf.render(section,title);
			renderer.add(stf);
		}
	}
	
	private void renderTable(Table table) throws OfxAuthoringException
	{
		LatexTableRenderer f = new LatexTableRenderer(cmm,dsm);
		f.render(table);
		renderer.add(f);
	}
		
	private void renderSection(Section section) throws OfxAuthoringException, OfxConfigurationException
	{
		LatexSectionRenderer sf = new LatexSectionRenderer(cmm,dsm,lvl+1,latexPreamble);
		sf.render(section);
		renderer.add(sf);
	}

    private void renderListing(Listing listing) throws OfxAuthoringException, OfxConfigurationException
    {
        LatexListingRenderer r = new LatexListingRenderer(cmm,dsm);
        r.render(listing);
        renderer.add(r);
    }
}
