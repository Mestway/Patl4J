package org.openfuxml.renderer.latex.preamble;

import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexPackages extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexPackages.class);
	
	public LatexPackages(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render()
	{
		logger.trace("Render title");
		txt.add("");
		txt.add("%% Packages");
		txt.add("\\usepackage{amsmath}");
		txt.add("\\usepackage{hyperref}");
		txt.add("\\usepackage{array}");
		txt.add("\\usepackage{ifthen}");
		txt.add("\\usepackage{paralist}");
		txt.add("");
	}
}
