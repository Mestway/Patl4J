package org.openfuxml.client.gui.simple;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Diese Klasse erstellt einen FilenameFilter, der eine Datei dann akzeptiert,
 * wenn der Name mit der extension endet. 
 * @author Andrea Frank
 */
public class ExtensionFilenameFilter implements FilenameFilter
{
	private String extension;
	
	public ExtensionFilenameFilter(String ext)
	{
		this.extension = ext;
	}
	
	public boolean accept(File dir, String name) {
		return (name.endsWith(extension));		
	}
}
