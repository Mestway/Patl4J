package org.openfuxml.server;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.communication.cluster.ejb.Host;

import de.kisner.util.HostCheck;
import de.kisner.util.architecture.ArchUtil;
import de.kisner.util.architecture.EnvironmentParameter;
import de.kisner.util.io.spawn.Spawn;

public abstract class AbstractServer
{
	final static Logger logger = LoggerFactory.getLogger(AbstractServer.class);
	
	protected static String fs = SystemUtils.FILE_SEPARATOR;
	
	public static String ofxBaseDir;
	protected String  pathLog,pathRepo,pathOutput,pathLib;
	protected EnvironmentParameter envParameter;

	protected Configuration config;
	
	public AbstractServer(Configuration config)
	{
		this.config=config;
	}
	
	protected Host getHost()
	{
		Host host = new Host();
			host.setHostName(HostCheck.getHostName());
			host.setHostIP(HostCheck.getHostIP());
			host.setRecord(new Date());
		return host;
	}
	
	public void setSystemProperties()
	{
		Properties sysprops = System.getProperties();
		Map<String,String> sysenv = System.getenv();
		
		ofxBaseDir = config.getString("dirs/dir[@type='basedir']");

		pathLog = config.getString("dirs/dir[@type='log']");
		pathRepo = config.getString("dirs/dir[@type='repository']");
		pathOutput = config.getString("dirs/dir[@type='output']");
		pathLib = config.getString("dirs/dir[@type='lib']");
		
		if(config.getBoolean("dirs/dir[@type='log']/@rel")){pathLog=ofxBaseDir+fs+pathLog;}
		if(config.getBoolean("dirs/dir[@type='repository']/@rel")){pathRepo=ofxBaseDir+fs+pathRepo;}
		if(config.getBoolean("dirs/dir[@type='output']/@rel")){pathOutput=ofxBaseDir+fs+pathOutput;}
		if(config.getBoolean("dirs/dir[@type='lib']/@rel")){pathLib=ofxBaseDir+fs+pathLib;}
		
//		FuXmlLogger.initLogger(pathLog);
		
		logger.debug("baseDir="+ofxBaseDir);
		logger.debug("pathLog="+pathLog);
		logger.debug("pathRepo="+pathRepo);
		logger.debug("pathOutput="+pathOutput);
		logger.debug("pathLib="+pathLib);
		
		String antHome = sysenv.get("ANT_HOME");
		if(antHome!=null){sysprops.put("ant.home",sysenv.get("ANT_HOME"));}
		else
		{
			antHome = pathLib;
			logger.debug("ANT_HOME not set, using Lib directory from config.xml");
			sysprops.put("ANT_HOME",antHome);
		}
			
		sysprops.put("ilona.home",ofxBaseDir);
		sysprops.put("ant.home",antHome);
		sysprops.put("logger.path",pathLog);
		sysprops.put("ilona.contentstore",pathRepo);
		sysprops.put("ilona.output",pathOutput);
		System.setProperties(sysprops);
		checkDirs();
		setenvP();
	}
	
	private void checkDirs()
	{
		File f = new File(pathLog);
		if(!f.exists()){f.mkdirs();logger.info("Created directory: "+f.getAbsolutePath());}
		
		f = new File(pathOutput);
		if(!f.exists()){f.mkdirs();logger.info("Created directory: "+f.getAbsolutePath());}
		
		f = new File(pathRepo);
		if(!f.exists()){logger.warn("Repository not found! Cheack for "+f.getAbsolutePath());}
		
		f = new File(pathLib);
		if(f.exists())
		{
			if(ArchUtil.isUnixLike())
			{
				File antRun = new File(f,"bin"+fs+"antRun");
				logger.debug("Setting +x antRun ("+antRun.getAbsolutePath()+")");
				Spawn spawn = new Spawn("chmod +x "+antRun.getAbsolutePath());
				spawn.cmd();
			}
		}
		{
			logger.warn("Libraries not found! Check for "+f.getAbsolutePath());
		}
	}
	
	private void setenvP()
	{	
		envParameter = new EnvironmentParameter();
		
		List<String> lLibs = config.getList("files/file[@type='lib']");

		String cp = ArchUtil.getClassPath(lLibs,pathLib);
		envParameter.put("CLASSPATH",cp);
		logger.debug("Setting CLASSPATH for openFuXML Applications: "+cp);
		
		envParameter.put("ANT_HOME", pathLib);
		logger.debug("Setting ANT_HOME for openFuXML Applications: "+envParameter.get("ANT_HOME"));
		
		ArchUtil.setArch();
		switch(ArchUtil.arch)
		{
			case OsX:	try
						{
							String systemPath = config.getString("dirs/dir[@type='path']");
							envParameter.put("PATH", systemPath);
						}
						catch (NoSuchElementException e)
						{
							logger.warn("No PATH defined in config.xml. Using System PATH "+envParameter.get("PATH"));
							logger.warn("   Errors are *EXTREMELY* possible for "+SystemUtils.OS_NAME);
							logger.warn("   Please insert your $PATH <dirs><dir type=\"path\">HERE</dir></dirs>");
							logger.warn("   Get your $PATH in a terminal with: 'echo $PATH'");
						}
						break;
			case Win32:	try
						{
							String systemPath = config.getString("dirs/dir[@type='path']");
							envParameter.put("PATH", systemPath);
						}
						catch (NoSuchElementException e)
						{
							logger.warn("No PATH defined in xmlConfig. Using System PATH "+envParameter.get("PATH"));
							logger.warn("   This must be tested for "+SystemUtils.OS_NAME);
						}
						break;
			default:	logger.fatal("Environment for "+ArchUtil.arch+" must be implemented!");break;
		}	
	}
	
	public void checkSystemProperties()
	{
		Properties sysprops = System.getProperties();
		
		logger.debug("System Properties:");
		logger.debug("\tilona.home="+sysprops.getProperty("ilona.home"));
		logger.debug("\tilona.contentstore="+sysprops.getProperty("ilona.contentstore"));
		logger.debug("\tilona.output="+sysprops.getProperty("ilona.output"));
		logger.debug("\tant.home="+sysprops.getProperty("ant.home"));
		logger.debug("\tANT_HOME="+sysprops.getProperty("ANT_HOME"));
		logger.debug("\tjava.class.path="+sysprops.getProperty("java.class.path"));
		logger.debug("\tPATH="+sysprops.getProperty("PATH"));
		logger.debug("\tlogger.path="+sysprops.getProperty("logger.path"));
		
		logger.debug("\tILONA_HOME="+System.getenv("ILONA_HOME"));
		logger.debug("\tFUXML_HOME="+System.getenv("FUXML_HOME"));
		
		envParameter.debug();
	}
	
	public EnvironmentParameter getEnvParameter() {return envParameter;}
}
