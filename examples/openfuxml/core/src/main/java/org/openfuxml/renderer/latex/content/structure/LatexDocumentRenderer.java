package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.xml.renderer.cmp.Pdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexDocumentRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexDocumentRenderer.class);
	
	private int lvl;
	private LatexPreamble latexPreamble;
	private Pdf pdf;
	
	@Deprecated
	public LatexDocumentRenderer(Pdf pdf, LatexPreamble latexPreamble)
	{
		this(new NoOpCrossMediaManager(),new OfxDefaultSettingsManager(),pdf,latexPreamble);
	}
	
	public LatexDocumentRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm,Pdf pdf, LatexPreamble latexPreamble)
	{
		super(cmm,dsm);
		this.cmm=cmm;
		this.pdf=pdf;
		this.latexPreamble=latexPreamble;
	}
	
	public void render(Content content) throws OfxAuthoringException, OfxConfigurationException
	{
		lvl = 0;
		
		preTxt.add("\\begin{document}");
		renderToc();
		
		
		for(Object s : content.getContent())
		{
			if(s instanceof Section){renderSection((Section)s);}
		}
		postTxt.add("\\end{document}");
	}
	
	private void renderSection(Section section) throws OfxAuthoringException, OfxConfigurationException
	{
		LatexSectionRenderer sf = new LatexSectionRenderer(cmm,dsm,lvl+1,latexPreamble);
		sf.render(section);
		renderer.add(sf);
	}
	
	private void renderToc()
	{
		if(pdf.isSetToc() && pdf.getToc().isPrint())
		{
			preTxt.add("\\tableofcontents");
		}
	}
}
