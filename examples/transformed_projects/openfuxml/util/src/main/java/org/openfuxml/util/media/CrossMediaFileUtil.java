package org.openfuxml.util.media;

import java.io.File;

import org.openfuxml.interfaces.media.CrossMediaManager;

public class CrossMediaFileUtil
{
	public static CrossMediaManager.Format getFormat(String fileName)
	{
		if(fileName.endsWith(".pdf")){return CrossMediaManager.Format.PDF;}
		if(fileName.endsWith(".svg")){return CrossMediaManager.Format.SVG;}
		if(fileName.endsWith(".png")){return CrossMediaManager.Format.PNG;}
		return null;
	}
	
	public static void createParentDirs(File file)
	{
		if(!file.getParentFile().exists()){file.getParentFile().mkdirs();}
	}
	
	public static boolean isParentDirectory(File file)
	{
		return (file.getParentFile().exists() && file.getParentFile().isDirectory());
	}
}
