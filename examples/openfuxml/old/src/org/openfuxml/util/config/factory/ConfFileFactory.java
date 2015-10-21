package org.openfuxml.util.config.factory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.util.config.jaxb.Files;
import org.openfuxml.util.config.jaxb.Files.File;

public class ConfFileFactory
{
	final static Logger logger = LoggerFactory.getLogger(ConfFileFactory.class);
	
	private Properties properties;
	
	public ConfFileFactory()
	{
		String resource = "resources/properties/ant.properties";
		try
		{
			MultiResourceLoader mrl = new MultiResourceLoader();
			InputStream is = mrl.searchIs(resource);
			properties = new Properties();
			properties.load(is);
		}
		catch (FileNotFoundException e) {logger.warn("This should only happen in Developing Environments",e);}
		catch (IOException e) {logger.error("",e);}
	}
	
	public Files getFiles()
	{
		Files files = new Files();

		File file = new File();
			file.setType("lib");
			file.setContent("ant-"+properties.getProperty("ant-version")+".jar");
			files.getFile().add(file);
		file = new File();
			file.setType("lib");
			file.setContent("ant-launcher-"+properties.getProperty("ant-version")+".jar");
			files.getFile().add(file);
		file = new File();
			file.setType("lib");
			file.setContent("openfuxml-"+properties.getProperty("openfuxml-version")+".jar");
			files.getFile().add(file);
		file = new File();
			file.setType("ofxlib");
			file.setContent("openfuxml-"+properties.getProperty("openfuxml-version")+".jar");
			files.getFile().add(file);
		file = new File();
			file.setType("lib");
			file.setContent("saxon-"+properties.getProperty("saxon-version")+".jar");
			files.getFile().add(file);
		
		return files;
	}
}
