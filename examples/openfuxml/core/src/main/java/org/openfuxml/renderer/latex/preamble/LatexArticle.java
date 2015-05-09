package org.openfuxml.renderer.latex.preamble;

import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.interfaces.renderer.latex.SectionHeaderNameFactory;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexArticle extends AbstractOfxLatexRenderer implements OfxLatexRenderer,SectionHeaderNameFactory
{
	final static Logger logger = LoggerFactory.getLogger(LatexArticle.class);
	
	public LatexArticle(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
		txt.add("\\documentclass[12pt]{article}");
		
		LatexPackages renderPackages = new LatexPackages(cmm,dsm);
		renderer.add(renderPackages);		
		
		txt.add("\\title{\\LaTeX}");
		txt.add("\\date{}");
	}
	
	// http://en.wikibooks.org/wiki/LaTeX/Document_Structure
	public String getSectionHeaderName(int lvl)
	{
		if      (lvl==1){return "section";}
		else if (lvl==2){return "subsection";}
		else if (lvl==3){return "subsubsection";}
		else if (lvl==4){return "paragraph";}
		else if (lvl==5){return "subparagraph";}
		
		logger.warn("Level "+lvl+" not supported by "+LatexArticle.class.getSimpleName());
		return "section";
	}
}
