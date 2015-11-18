package org.openfuxml.renderer.latex;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.content.structure.LatexDocumentRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.util.OfxDocumentStructureVerifier;
import org.openfuxml.xml.renderer.cmp.Pdf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxLatexRenderer.class);
	
	private LatexPreamble latexPreamble;
	private LatexDocumentRenderer rDocument;
	private List<String> txt;
	
	public OfxLatexRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm,Pdf pdf)
	{
		latexPreamble = new LatexPreamble(cmm,dsm);
		rDocument = new LatexDocumentRenderer(pdf,latexPreamble);
		
		txt = new ArrayList<String>();
	}
	
	public void render(String ofxDocFileName) throws OfxAuthoringException, OfxConfigurationException
	{
		try
		{
			logger.debug("Processing: "+ofxDocFileName);
			Document ofxdoc = JaxbUtil.loadJAXB(ofxDocFileName, Document.class);
			render(ofxdoc);
		}
		catch (FileNotFoundException e) {logger.error("",e);}
	}
	
	public void render(Document ofxDocument) throws OfxAuthoringException, OfxConfigurationException
	{
		OfxDocumentStructureVerifier.checkForContent(ofxDocument);
		rDocument.render(ofxDocument.getContent());
		latexPreamble.render();
		
		txt.addAll(latexPreamble.getContent());
		txt.addAll(rDocument.getContent());		
	}
	
	public List<String> getContent(){return txt;}
}