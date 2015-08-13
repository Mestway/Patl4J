package org.openfuxml.media.cross;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.media.CrossMediaTranscoder;
import org.openfuxml.media.transcode.Pdf2PdfTranscoder;
import org.openfuxml.media.transcode.Png2PngTranscoder;
import org.openfuxml.media.transcode.Svg2PdfTranscoder;
import org.openfuxml.util.media.CrossMediaFileUtil;
import org.openfuxml.util.media.MediaSourceModificationTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexCrossMediaManager implements CrossMediaManager
{
	final static Logger logger = LoggerFactory.getLogger(LatexCrossMediaManager.class);
	
	private File texBase;
	private List<Media> listMedia;
	
	private MediaSourceModificationTracker msmt;
	
	public LatexCrossMediaManager(File texBase)
	{
		this(texBase,null);
	}
	public LatexCrossMediaManager(File texBase, MediaSourceModificationTracker msmt)
	{
		this.texBase=texBase;
		this.msmt=msmt;
		listMedia = new ArrayList<Media>();
	}
	
	@Override
	public String getImageRef(Media imageMedia)
	{
		if(imageMedia.isSetSrc())
		{
			listMedia.add(imageMedia);
		}
		StringBuffer sb = new StringBuffer();
//		sb.append(imageBaseDir).append(SystemUtils.FILE_SEPARATOR);
		sb.append(imageMedia.getDst());
//		sb.append(".pdf");
		return sb.toString();
	}

	@Override
	public void transcode() throws OfxAuthoringException
	{
		logger.info("Transcoding "+listMedia.size());
		
		CrossMediaTranscoder transcoder = null;
		
		for(Media media : listMedia)
		{
			CrossMediaManager.Format format = CrossMediaFileUtil.getFormat(media.getSrc());
			switch(format)
			{
				case PDF:	transcoder = new Pdf2PdfTranscoder(texBase);break;
				case SVG:	transcoder = new Svg2PdfTranscoder(texBase);break;
				case PNG:	transcoder = new Png2PngTranscoder(texBase);break;
				default:	logger.warn("Format "+format+" Not Implemented");break;
			}
			if(isSourceChanged(media) || !transcoder.isTargetExisting(media))
			{
				transcoder.transcode(media);
			}
		}
		if(msmt!=null){msmt.persist();}
	}
	
	private boolean isSourceChanged(Media media)
	{
		if(msmt==null){return true;}
		else {return msmt.isChanged(media);}
	}
}
