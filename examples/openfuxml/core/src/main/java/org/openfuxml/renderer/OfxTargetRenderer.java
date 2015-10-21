package org.openfuxml.renderer;

import java.io.File;

import net.sf.exlp.util.io.txt.ExlpTxtWriter;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxImplementationException;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.OfxRenderProcessor.DirCode;
import org.openfuxml.renderer.OfxRenderProcessor.FileCode;
import org.openfuxml.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.processor.html.OfxHtmlRenderer;
import org.openfuxml.renderer.util.OfxRenderConfiguration;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.openfuxml.xml.renderer.cmp.Html;
import org.openfuxml.xml.renderer.cmp.Pdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxTargetRenderer
{
	public static enum PdfDir {latex};
	public static enum PdfFile {latex};
	
	final static Logger logger = LoggerFactory.getLogger(OfxTargetRenderer.class);
		
	public static enum Phase {iniMerge,wikiIntegrate,wikiMerge,containerMerge,externalMerge,phaseTemplate,mergeTemplate};
	
	private OfxRenderConfiguration cmpConfigUtil;
	private Cmp cmp;
	
	public OfxTargetRenderer(OfxRenderConfiguration cmpConfigUtil)
	{
		this.cmpConfigUtil=cmpConfigUtil;
		cmp = cmpConfigUtil.getCmp();
	}
	
	public void renderTargets() throws OfxAuthoringException, OfxImplementationException, OfxConfigurationException
	{
		File fPreProcessingFinished = cmpConfigUtil.getFile(cmp.getPreprocessor().getDir(), DirCode.working.toString(), FileCode.ofxPreFinished.toString(),false);
		if(cmp.getTargets().isSetPdf())
		{
			int i=0;
			for(Pdf pdf : cmp.getTargets().getPdf())
			{
				if(!pdf.isSetActive()){throw new OfxConfigurationException("No @active set for <pdf>");}
				if(pdf.isActive())
				{
					if(!pdf.isSetCode()){pdf.setCode(""+i);}
					logger.info("Rendering PDF ("+pdf.getCode()+")");
					phaseLatex(pdf, fPreProcessingFinished);
					i++;
				}
			}	
		}
		if(cmp.getTargets().isSetHtml())
		{
			int i=0;
			for(Html html : cmp.getTargets().getHtml())
			{
				if(!html.isSetActive()){throw new OfxConfigurationException("No @active set for <html>");}
				if(html.isActive())
				{
					if(!html.isSetCode()){html.setCode(""+i);}
					logger.info("Rendering HTML ("+html.getCode()+")");
					renderHtml(html, fPreProcessingFinished);
					i++;
				}
			}	
		}
	}
	
	public void renderHtml(Html html, File fSrc) throws OfxAuthoringException, OfxConfigurationException, OfxImplementationException
	{
		OfxHtmlRenderer htmlRenderer = new OfxHtmlRenderer(cmpConfigUtil,html);
		htmlRenderer.render(fSrc.getAbsolutePath());
	}
	
	private void phaseLatex(Pdf pdf, File fSrc) throws OfxAuthoringException, OfxConfigurationException
	{
		OfxLatexRenderer renderer = new OfxLatexRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager(), cmp.getTargets().getPdf().get(0));
  		renderer.render(fSrc.getAbsolutePath());
		
		File dstFile = cmpConfigUtil.getFile(pdf.getDir(), PdfDir.latex.toString(), PdfFile.latex.toString(),true);
		
		logger.debug("pdf."+pdf.getCode()+" dstFile="+dstFile.getAbsolutePath());
		
		ExlpTxtWriter txtWriter = new ExlpTxtWriter();
		txtWriter.add(renderer.getContent());
		txtWriter.add("test");
		txtWriter.writeFile(dstFile);
	}
}