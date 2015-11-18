package org.openfuxml.renderer.util;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxDocumentStructureVerifier
{
	final static Logger logger = LoggerFactory.getLogger(OfxDocumentStructureVerifier.class);
	
	public static void checkForContent(Document ofxdoc) throws OfxAuthoringException
	{
		if(!ofxdoc.isSetContent())
		{
			throw new OfxAuthoringException("No content available");
		}
	}
}