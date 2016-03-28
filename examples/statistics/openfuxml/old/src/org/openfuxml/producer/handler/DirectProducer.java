package org.openfuxml.producer.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.client.control.formats.FormatFactory;
import org.openfuxml.client.control.formats.FormatFactoryDirect;
import org.openfuxml.communication.cluster.ejb.Host;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.factory.OfxProductionResultFactory;
import org.openfuxml.model.factory.OfxRequestFactory;
import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Productionresult;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.producer.Producer;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.util.config.OfxPathHelper;

import de.kisner.util.architecture.EnvironmentParameter;
import de.kisner.util.io.spawn.Spawn;

/**
 * This class encapsulates the production server which produces output documents 
 * for a specified XML document. For every service request an Ant build process is started.
 * After finishing the build process a {@link SSIMessage} is returned to the client which
 * contains the result of the service.
 * 
 */
public class DirectProducer extends AbstractProducer implements Producer
{
	final static Logger logger = LoggerFactory.getLogger(DirectProducer.class);
	public static enum Typ {PRODUCE,ENTITIES};
	
	private static Properties sysprops = System.getProperties();
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	public static enum ProductionCode {Ok, InternalError, BuildError};
	
	private Host host;
	private String dirOutput;
	private String baseDir;
	
	public DirectProducer(Configuration config,EnvironmentParameter envP){this(config,null,envP);}
	public DirectProducer(Configuration config,Host host,EnvironmentParameter envP)
	{
		super(config,envP);
		this.host=host;
		baseDir = config.getString("dirs/dir[@type='basedir']");
		dirOutput = OfxPathHelper.getDir(config, "output");
	}
	
	public List<OfxApplication> getAvailableApplications() throws ProductionSystemException,ProductionHandlerException
	{
		List<OfxApplication> result = new ArrayList<OfxApplication>();
		
		File dirApp = new File(config.getString("dirs/dir[@type='basedir']")+fs+"applications");
		logger.debug("Suche in "+dirApp);
		if (dirApp.exists() && dirApp.isDirectory())
		{
			for(File dirEntry : dirApp.listFiles())
			{
				boolean isDir = dirEntry.isDirectory();
				boolean isSvn = dirEntry.getAbsolutePath().endsWith(".svn");
				boolean isOutput = dirEntry.getName().equals("output");
				if(isDir && !isSvn && !isOutput)
				{
					OfxApplication ofxA = new OfxApplication();
					ofxA.setName(dirEntry.getName());
					File dirSessionpreferences = new File(OfxPathHelper.getDir(config, "output")+fs+dirEntry.getName()+fs+"sessionpreferences");
					if(!dirSessionpreferences.exists())
					{
						logger.debug("Creating "+dirSessionpreferences.getAbsolutePath());
						dirSessionpreferences.mkdirs();
					}
					result.add(ofxA);
				}
			}
		}
		return result;
	}
	
	/**
	 * Gibt verfügbare Formate zurück
	 * @author Thorsten
	 */
	public List<OfxFormat> getAvailableFormats(OfxApplication ofxA) throws ProductionSystemException
	{
		List<OfxApplication> result = new ArrayList<OfxApplication>();
		FormatFactory ff = new FormatFactoryDirect(config);
		return ff.getFormat(ofxA);
	}
	
	public ProducibleEntities discoverEntities(Sessionpreferences spref) throws ProductionSystemException, ProductionHandlerException
	{
		int suffixindex = spref.getDocument().indexOf(".xml");
		String docName = spref.getDocument().substring(0,suffixindex);
		
		invoke(spref,Typ.ENTITIES);
		String proDir = spref.getProject()+fs+spref.getFormat()+fs+docName;
		File fResult = new File(dirOutput+fs+spref.getApplication()+fs+proDir+fs+"producableEntities.xml");
		OfxProductionResultFactory oprf = new OfxProductionResultFactory();
		ProducibleEntities result = oprf.getProducibleEntities(fResult);
		return result;
	}
	
	public Productionresult produce(Sessionpreferences spref) throws ProductionSystemException
	{
		int suffixindex = spref.getDocument().indexOf(".xml");
		String docName = spref.getDocument().substring(0,suffixindex);
		
		invoke(spref,Typ.PRODUCE);
		String proDir = spref.getProject()+fs+spref.getFormat()+fs+docName;
		File fResult = new File(dirOutput+fs+spref.getApplication()+fs+proDir+fs+"result.xml");
		OfxProductionResultFactory oprf = new OfxProductionResultFactory();
		Productionresult result = oprf.getProductionResult(fResult);
		return result;
	}
	
	private void invoke(Sessionpreferences spref, Typ invokeType) throws ProductionSystemException
	{
		logger.debug("Invoke aufgerufen mit "+invokeType);
		
		Calendar startTime = Calendar.getInstance();
		ProductionCode pc=ProductionCode.Ok;
		
		String buildfile = baseDir+fs+"applications"+fs+spref.getApplication()+fs+"formats"+fs+spref.getFormat()+fs+"build.xml";
		
		StringBuffer sb = new StringBuffer();
			sb.append(dirOutput);
			sb.append(fs+spref.getApplication());
			sb.append(fs+"sessionpreferences");
			sb.append(fs+spref.getUsername()+"-"+spref.getProject()+"-"+"request.xml");
			
		File fRequest = new File(sb.toString());
		OfxRequestFactory orf = new OfxRequestFactory();
		orf.write(spref, fRequest);
		
		StringBuffer sbParameters = new StringBuffer();
		
		boolean writeLogFile = false;
		if(writeLogFile)
		{
			switch (invokeType)
			{
				case PRODUCE: 	String logfile= OfxPathHelper.getDir(config, "log")+fs+spref.getProject()+"_"+spref.getDocument() + ".log";
								sbParameters.append(" -logfile " + logfile);
								break;
			}
		}
		sbParameters.append(" -Dilona.home="+ sysprops.getProperty("ilona.home"));
		sbParameters.append(" -Dilona.contentstore="+ OfxPathHelper.getDir(config, "repository")+fs+spref.getApplication());
		sbParameters.append(" -Dofx.lib="+ OfxPathHelper.getDir(config, "lib")+fs+config.getString("files/file[@type='ofxlib']"));
		sbParameters.append(" -Dilona.output="+dirOutput+fs+spref.getApplication());
		sbParameters.append(" -Dapplication="+ spref.getApplication());
		sbParameters.append(" -Dcoursename="+ spref.getProject());
		sbParameters.append(" -Dmasterfile="+ spref.getDocument());
			//TODO Thorsten productionDir Methode erstmal nicht berücksichtigt
			//+ " -Ddocumentdir="	+ request.getProductionDir()
		sbParameters.append(" -Ddocumentdir= ");
		sbParameters.append(" -Dformat="+ spref.getFormat());
		sbParameters.append(" -Dusername="+ spref.getUsername());
				
		logger.debug("Parameters: " + sbParameters);
		logger.debug("Buildfile:" +buildfile);
		logger.debug("Ant Home:" + sysprops.getProperty("ant.home"));
		
		StringBuffer sbCmd = new StringBuffer(); 
		/* For working correctly with maven, this comand has changed.
		
		sbCmd.append("java ");
		sbCmd.append(" -Dant.home="+sysprops.getProperty("ant.home"));
		sbCmd.append(" org.apache.tools.ant.Main ");
		sbCmd.append("-buildfile "	+ buildfile);
		*/
		sbCmd.append("java ");
		sbCmd.append(" -jar dependency/ant-launcher-1.7.1.jar ");
		sbCmd.append("-buildfile "	+ buildfile);
		
		sbCmd.append(" "+ sbParameters.toString()+ " ");
		switch (invokeType)
		{
			case ENTITIES: 	sbCmd.append(" producableEntities ");break;
		}
		logger.debug("Spawn: "+sbCmd.toString());

//		Unter Windows m�ssen die Backslashes ersetzt werden
		pc=spawn(sbCmd.toString().replace('\\','/'));
	}
	
	public ProducedEntities invoke(org.openfuxml.producer.ejb.ProductionRequest request) throws ProductionSystemException
	{
		logger.warn("This is deprecated. Should never happen ...");
		return null;
	}
	
	private ProductionCode spawn(String cmd) throws ProductionSystemException
	{
		ProductionCode pc;
		
		Spawn spawn = new Spawn(cmd);
			spawn.setEnvParameter(envP);
			spawn.setWriter(w);
			spawn.cmd();
		int exitValue = spawn.getExitValue();
		if (exitValue != 0) 
			pc = ProductionCode.BuildError;
		else 
			pc = ProductionCode.Ok;
		w=null;
		return pc;
	}
}