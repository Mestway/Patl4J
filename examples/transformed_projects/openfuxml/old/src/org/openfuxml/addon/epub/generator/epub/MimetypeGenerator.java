package org.openfuxml.addon.epub.generator.epub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.renderer.processor.pre.OfxExternalMerger;

public class MimetypeGenerator
{
	final static Logger logger = LoggerFactory.getLogger(OfxExternalMerger.class);
	
	private File targetDir;
	
	public MimetypeGenerator(File targetDir)
	{
		this.targetDir=targetDir;
	}
	
	public void save()
	{
		try
		{
			File f=new File(targetDir,"mimetype");
			OutputStream os = new FileOutputStream(f);
			Writer w = new OutputStreamWriter(os, "UTF8");
			w.write("application/epub+zip");
			w.close();os.close();
		}
		catch (IOException e) {logger.error("",e);}
	}
}
