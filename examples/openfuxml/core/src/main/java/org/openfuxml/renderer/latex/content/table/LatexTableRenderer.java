package org.openfuxml.renderer.latex.content.table;

import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.latex.OfxLatexTableRenderer;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTableRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTableRenderer.class);
	
	public static enum Type {grid,line}
	
	boolean preBlankLine;
	public void setPreBlankLine(boolean preBlankLine) {this.preBlankLine = preBlankLine;}
	
	public LatexTableRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Table table) throws OfxAuthoringException
	{		
		if(!table.isSetSpecification()){throw new OfxAuthoringException("<table> without <specification>");}
		if(!table.isSetContent()){throw new OfxAuthoringException("<table> without <content>");}
		if(table.getContent().getBody().size()!=1){throw new OfxAuthoringException("<content> must exactly have 1 body!");}
		
		if(!table.getSpecification().isSetLong()){table.getSpecification().setLong(false);}
		if(!table.getSpecification().isSetFloat()){table.getSpecification().setFloat(XmlFloatFactory.build(false));}
		
		OfxLatexTableRenderer tableRenderer = new LatexTabuRenderer(cmm,dsm); //getRendererForType();
		
		if(preBlankLine){preTxt.add("");}
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a Latex table with: "+tableRenderer.getClass().getSimpleName()));
		if(table.isSetComment())
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer(cmm,dsm);
			rComment.render(table.getComment());
			renderer.add(rComment);
		}
		
		LatexTabluarWidthCalculator tabularWidthCalculator = new LatexTabluarWidthCalculator(table.getSpecification().getColumns());
		boolean longTable = table.getSpecification().isLong();
		boolean floating = table.getSpecification().getFloat().isValue();
		boolean flex = tabularWidthCalculator.isFlexTable();
		
		if(!longTable){renderPre(table,floating);}

		tableRenderer.render(table);
		renderer.add(tableRenderer);
		
		if(!longTable){renderPost(table,floating);}
	}
	
	private void renderPre(Table table,boolean floating)
	{
		preTxt.add("");
		
		if(floating){preTxt.add("\\begin{table}[!htbp]");}
		else{preTxt.add("\\begin{table}[H]");}
	}
	
	private void renderPost(Table table,boolean floating)
	{
		JaxbUtil.trace(table);
		
		
		if(table.isSetId()) {postTxt.add("\\label{"+table.getId()+"}");}
		
		postTxt.add("\\end{table}");
	}
	
	@SuppressWarnings("unused")
	private OfxLatexTableRenderer getRendererForType()
	{
		OfxLatexTableRenderer tableRenderer;
		Type type = Type.line;
		
		switch(type)
		{
			case line: tableRenderer = new LatexLineTableRenderer(cmm,dsm);break;
			default: tableRenderer = new LatexGridTableRenderer(cmm,dsm);break;
		}
		return tableRenderer;
	}
}
