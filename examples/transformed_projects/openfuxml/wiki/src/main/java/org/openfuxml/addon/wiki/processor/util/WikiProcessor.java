package org.openfuxml.addon.wiki.processor.util;

import java.io.File;

import org.openfuxml.addon.wiki.data.exception.OfxWikiException;
import org.openfuxml.addon.wiki.data.jaxb.Contents;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxInternalProcessingException;

public interface WikiProcessor
{
	public static enum WikiFileExtension {txt,xhtml,xml}
	public static enum WikiDir{wikiTemplate, ofxTemplate}
	
	void setDirectory(WikiDir type, File dir) throws OfxInternalProcessingException;
	void setDirectories(File srcDir, File dstDir);
	void process(Contents wikiQueries) throws OfxWikiException, OfxAuthoringException, OfxInternalProcessingException;
}
