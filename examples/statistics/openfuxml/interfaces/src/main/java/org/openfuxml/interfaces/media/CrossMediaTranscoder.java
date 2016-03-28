package org.openfuxml.interfaces.media;

import java.io.File;

import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;

public interface CrossMediaTranscoder
{
	void transcode(Media media) throws OfxAuthoringException;
	File buildTarget(Media media);
	boolean isTargetExisting(Media media);
}
