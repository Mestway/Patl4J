package org.openfuxml.media.cross;

import org.openfuxml.content.media.Media;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.latex.content.media.LatexImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoOpCrossMediaManager implements CrossMediaManager
{
	final static Logger logger = LoggerFactory.getLogger(LatexImageRenderer.class);
	
	@Override
	public String getImageRef(Media imageMedia)
	{
		logger.warn("Only adding the src for "+imageMedia.getId());
		return imageMedia.getSrc();
	}

	@Override public void transcode() {}

}
