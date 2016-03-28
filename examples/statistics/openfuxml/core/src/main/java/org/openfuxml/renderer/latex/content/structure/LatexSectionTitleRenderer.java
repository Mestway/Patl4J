package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexSectionTitleRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSectionTitleRenderer.class);
	
	private LatexPreamble latexPreamble;
	int lvl;
	
	public LatexSectionTitleRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm,int lvl, LatexPreamble latexPreamble)
	{
		super(cmm,dsm);
		this.latexPreamble=latexPreamble;
		this.lvl=lvl;
	}

    public void render(Title title){render(null,title);}
	public void render(Section section, Title title)
	{
		logger.trace("Render title");
		if(title.isSetNumbering()){logger.warn("Ignoring numbring");}
		
		txt.add("");

		StringBuffer sb = new StringBuffer();
		sb.append("\\").append(latexPreamble.getSectionHeaderName(lvl));
        sb.append("{").append(TexSpecialChars.replace(title.getValue())).append("}");
		txt.add(sb.toString());

        if(section!=null && section.isSetId())
        {
            txt.add("\\label{"+section.getId()+"}");
        }
	}
}