package org.openfuxml.media.transcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.sf.exlp.util.io.FileIO;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;

import org.apache.commons.io.IOUtils;
import org.openfuxml.content.media.Media;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.media.CrossMediaTranscoder;
import org.openfuxml.media.cross.LatexCrossMediaManager;
import org.openfuxml.util.media.CrossMediaFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Png2PngTranscoder implements CrossMediaTranscoder
{
	final static Logger logger = LoggerFactory.getLogger(LatexCrossMediaManager.class);
	
	private File dir;
	private MultiResourceLoader mrl;
	
	public Png2PngTranscoder(File dir)
	{
		this.dir=new File(dir,"png");
		mrl = new MultiResourceLoader();
	}
	
	@Override
	public void transcode(Media media) throws OfxAuthoringException
	{
		File file = buildTarget(media);
		CrossMediaFileUtil.createParentDirs(file);
		logger.info("Transcoding to :"+file.getAbsolutePath());
		
		try
		{
			InputStream is = mrl.searchIs(media.getSrc());
			byte[] bytes = IOUtils.toByteArray(is);
			FileIO.writeFileIfDiffers(bytes, file);
		}
		catch (FileNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
		catch (IOException e) {throw new OfxAuthoringException(e.getMessage());}
		
//		FileIO.writeFileIfDiffers(bytes, fTarget);
	}
	


	@Override
	public boolean isTargetExisting(Media media)
	{
		File file = buildTarget(media);
		return file.exists() && file.isFile();
	}

	@Override
	public File buildTarget(Media media)
	{
		return new File(dir,media.getDst()+".png");
	}
}
