package org.openfuxml.util.config.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.util.config.PreviousConfigFinder;
import org.openfuxml.util.config.jaxb.Openfuxml;
import org.openfuxml.util.config.jaxb.Openfuxml.Net;

public class ClientConfFactory extends AbstractConfFactory
{
	final static Logger logger = LoggerFactory.getLogger(ClientConfFactory.class);
	
	private Openfuxml openfuxml;
	private String mainConf;
	
	public ClientConfFactory()
	{

	}
	
	public Configuration getConfiguration()
	{
		ConfigLoader.clear();
		ConfigLoader.add(openFuxmlBaseDir+fs+mainConf);
		ConfigLoader.add(openFuxmlBaseDir+fs+"resources"+fs+"config"+fs+"client-images.xml");
		ConfigLoader.add(openFuxmlBaseDir+fs+"resources"+fs+"config"+fs+"client-highlight.xml");
		Configuration config = ConfigLoader.init();
		return config;
	}
	
	public void init(String mainConf)
	{
		this.mainConf=mainConf;
		File fConf = new File(openFuxmlBaseDir,mainConf);
		logger.debug("Searching "+fConf);
		try
		{
			if(!fConf.exists())
			{				
				PreviousConfigFinder pcf = new PreviousConfigFinder(mainConf); 
				Configuration previousConfig=pcf.find(openFuxmlBaseDir.getParentFile(),openFuxmlVersion);
				
				logger.info("No "+mainConf+" found. I will create a default one.");
				createConfig(previousConfig);
				FileOutputStream fos = new FileOutputStream(fConf);
				writeConfig(fos);
			}
		}
		catch (FileNotFoundException e) {logger.error("",e);}
	}
	
	private Net getNet()
	{
		Net net =  new Net();
		net.setHost("host");
		net.setPort(4444);
		return net;
	}
	
	public void createConfig(Configuration previousConfig)
	{
		ConfDirFactory cdf = new ConfDirFactory(previousConfig);
		ConfFileFactory cff = new ConfFileFactory();
		
		openfuxml = new Openfuxml();
		
		Openfuxml.Server server = new Openfuxml.Server();
			server.setContent("direct");
			server.setVersion(openFuxmlVersion);
		
		openfuxml.setServer(server);
		openfuxml.setNet(getNet());
		openfuxml.setDirs(cdf.getDirs(startupenv,openFuxmlBaseDir.getAbsolutePath(),openFuxmlVersion));
		openfuxml.setFiles(cff.getFiles());
	}
	
	public void writeConfig(OutputStream os){writeJaxb(os,openfuxml);}
	
	public static void main(String args[]) 
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		ClientConfFactory ccf = new ClientConfFactory();
			ccf.createConfig(null);
			ccf.writeConfig(System.out);
	}
}
