package org.openfuxml.addon.wiki.media.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.mediawiki.actions.queries.ImageInfo;
import net.sourceforge.jwbf.mediawiki.actions.util.VersionException;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import org.apache.commons.configuration.Configuration;
import org.apache.xmlgraphics.java2d.ps.EPSDocumentGraphics2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiImageProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiImageProcessor.class);
	
	private BufferedImage image;
	private Configuration config;
	
	public WikiImageProcessor(Configuration config)
	{
		this.config=config;
	}
	
	public void fetch(String imageName)
	{
		try
		{
			MediaWikiBot bot = new MediaWikiBot("http://de.wikipedia.org/w/");
			ImageInfo wikiImage = new ImageInfo(bot, imageName);
			logger.debug(wikiImage.getUrlAsString());
			image = wikiImage.getAsImage();
		} 
		catch (MalformedURLException e) {logger.error("",e);}
		catch (VersionException e) {logger.error("",e);}
		catch (ProcessException e) {logger.error("",e);}
		catch (ActionException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
	}
	
	public void save(String fileName)
	{
		savePNG(fileName);
		saveEPS(fileName);
	}
	
	private void savePNG(String fileName)
	{
		File f = new File(config.getString("/ofx/dir[@type='image-web']")+"/"+fileName+".png");
		try {ImageIO.write( image, "png", f);}
		catch (IOException e) {logger.error("",e);}
	}
	
	private void saveEPS(String fileName)
	{	
		File f = new File(config.getString("/ofx/dir[@type='image-eps']")+"/"+fileName+".eps");
		try
		{
			EPSDocumentGraphics2D g2d = new EPSDocumentGraphics2D(false);
	        g2d.setGraphicContext(new org.apache.xmlgraphics.java2d.GraphicContext());
			FileOutputStream out = new FileOutputStream(f);
			g2d.setupDocument(out, image.getWidth(), image.getHeight());
			g2d.drawImage(image, 0, 0, null);
	        g2d.finish();
	        out.close();
		}
		catch (FileNotFoundException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
	}
}