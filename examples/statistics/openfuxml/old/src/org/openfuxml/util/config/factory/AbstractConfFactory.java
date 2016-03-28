package org.openfuxml.util.config.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.model.factory.AbstractJaxbFactory;

import de.kisner.util.architecture.ArchUtil;
import de.kisner.util.io.resourceloader.MultiResourceLoader;

public abstract class AbstractConfFactory extends AbstractJaxbFactory
{
	final static Logger logger = LoggerFactory.getLogger(AbstractConfFactory.class);
	
	protected static String fs = SystemUtils.FILE_SEPARATOR;
	public static enum StartUpEnv {DEVELOPER,PRODUCTION}
	protected StartUpEnv startupenv;

	public String openFuxmlVersion;
	public File openFuxmlBaseDir;
	
	public AbstractConfFactory()
	{
		getVersion();
		getBaseDir();
	}
	
	private void getBaseDir()
	{
		switch(startupenv)
		{
			case DEVELOPER:		String path = (new File(".")).getAbsolutePath();
								openFuxmlBaseDir=new File(path.substring(0,path.length()-2));
								break;
			case PRODUCTION:	openFuxmlBaseDir = new File(ArchUtil.getAppSettingsDir("openFuXML")+fs+openFuxmlVersion);
								if(!openFuxmlBaseDir.exists())
								{
									logger.warn("openFuXML directory does not exit: "+openFuxmlBaseDir.getAbsolutePath());
									logger.info("I will create it ...");
									openFuxmlBaseDir.mkdirs();
								} break;
		}
	}
	
	private void getVersion()
	{
		openFuxmlVersion = this.getClass().getPackage().getImplementationVersion();
		if(openFuxmlVersion==null)
		{
			startupenv = StartUpEnv.DEVELOPER;
			String propName = "resources/properties/ant.properties";
			try
			{
				logger.info("Developing Environment. Using "+propName+" to get Version");
				MultiResourceLoader mrl = new MultiResourceLoader();
				InputStream is = mrl.searchIs(propName);
				Properties versionProperties = new Properties();
				versionProperties.load(is);
				openFuxmlVersion = versionProperties.getProperty("openfuxml-version");
			}
			catch (FileNotFoundException e) {logger.error("File is missing! "+propName);}
			catch (IOException e) {logger.error("",e);}
		}
		else {startupenv = StartUpEnv.PRODUCTION;}
//		startupenv = StartUpEnv.PRODUCTION;
		logger.info("OpenFuXML Version: "+openFuxmlVersion+" ("+startupenv.toString().toLowerCase()+")");
	}
	
	public StartUpEnv getStartupenv() {return startupenv;}	
	public String getOpenFuxmlVersion() {return openFuxmlVersion;}
	
	protected abstract Configuration getConfiguration();
}
