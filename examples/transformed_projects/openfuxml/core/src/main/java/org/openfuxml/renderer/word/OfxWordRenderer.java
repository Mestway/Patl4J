package org.openfuxml.renderer.word;

import java.io.File;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.util.OfxDocumentStructureVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxWordRenderer
{
	final static Logger logger = LoggerFactory.getLogger(OfxWordRenderer.class);
	
	public OfxWordRenderer()
	{
		
	}
	
	public void render(Document ofxDocument, File target) throws OfxAuthoringException
	{
		OfxDocumentStructureVerifier.checkForContent(ofxDocument);
		
	}
}