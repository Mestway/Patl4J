package org.openfuxml.renderer.latex;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.content.list.LatexListRenderer;
import org.openfuxml.renderer.latex.content.media.LatexImageRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSectionRenderer.class);
	
	protected CrossMediaManager cmm;
	protected DefaultSettingsManager dsm;
	
	protected List<String> preTxt;
	protected List<String> txt;
	protected List<String> postTxt;
	
	protected List<OfxLatexRenderer> renderer;
	
	public AbstractOfxLatexRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		this.cmm=cmm;
		this.dsm=dsm;
		preTxt = new ArrayList<String>();
		txt = new ArrayList<String>();
		postTxt = new ArrayList<String>();
		renderer = new ArrayList<OfxLatexRenderer>();
	}
	
	protected void addPackages9()
	{
		
	}
	
	public String getSingleLine() throws OfxAuthoringException
	{
		List<String> resultTxt = getContent();
		if(resultTxt.size()!=1){throw new OfxAuthoringException("Result is not a single line");}
		return resultTxt.get(0);
	}
	
	public List<String> getContent()
	{
		List<String> resultTxt = new ArrayList<String>();
		resultTxt.addAll(preTxt);
		
		resultTxt.addAll(txt);
		for(OfxLatexRenderer r : renderer)
		{
			resultTxt.addAll(r.getContent());
		}
		
		resultTxt.addAll(postTxt);
		
		return resultTxt;
	}
	
	public void write(Writer w) throws IOException
	{
		for(String s : getContent())
		{
			w.write(s+SystemUtils.LINE_SEPARATOR);
		}
		w.flush();
	}
	
	protected void paragraphRenderer(Paragraph paragraph, boolean preBlankLine) throws OfxAuthoringException
	{
		LatexParagraphRenderer f = new LatexParagraphRenderer(cmm,dsm,preBlankLine);
		f.render(paragraph);
		renderer.add(f);
	}
	
	protected void renderList(org.openfuxml.content.list.List list,OfxLatexRenderer parent) throws OfxAuthoringException
	{
		LatexListRenderer f = new LatexListRenderer(cmm,dsm);
		f.render(list,parent);
		renderer.add(f);
	}
	
	protected void renderImage(Image image) throws OfxAuthoringException
	{
		LatexImageRenderer f = new LatexImageRenderer(cmm,dsm);
		f.render(this,image);
		renderer.add(f);
	}

    protected void addString(String s)
    {
        txt.add(TexSpecialChars.replace(s));
    }
}
