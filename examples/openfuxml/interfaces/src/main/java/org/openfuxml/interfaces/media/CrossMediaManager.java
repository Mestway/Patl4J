package org.openfuxml.interfaces.media;

import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;

public interface CrossMediaManager
{
	public static enum Format {SVG,PDF,PNG}
	
	String getImageRef(Media imageMedia);
	
	void transcode() throws OfxAuthoringException;
}
