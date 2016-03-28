package org.openfuxml.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.openfuxml.producer.handler.DirectProducer.ProductionCode;

public class FuXmlLogger
{
	final static Logger logger = LoggerFactory.getLogger(FuXmlLogger.class);
	
	public static boolean isInited;
	public static boolean log4jInited=false;
	
	private static Logger Production;
	
	private static java.util.Properties sysprops = System.getProperties();
	private static Logger Error;

	public static Logger server;
	public static Logger producer;
	public static Logger spawn;
	
	public static void setLevel(Level levelServer,Level levelClient,Level levelProducer)
	{
		server.setLevel(levelServer);
		producer.setLevel(levelProducer);
	}
	
	public static void initLogger()
	{
		initLogger("");
	}
	
	public static void initLogger(String logDir)
	{
		
		if (logDir == null)
			logDir = "";
		logDir=logDir+sysprops.getProperty("file.separator");
			
		Production = Logger.getLogger("ProductionLogger");
		Production.setLevel(Level.ALL);
		
		server = Logger.getLogger("ServerLogger");
		producer = Logger.getLogger("ProducerLogger");
		spawn = Logger.getLogger("SpawnLogger");
		
		//Production_Verb = Logger.getLogger("Production_VerbLogger");
		//Production_Verb.setLevel(Level.ALL);

		//Error = Logger.getLogger("ErrorLogger");
		//Error.setLevel(Level.ALL);

		PatternLayout p1 = new PatternLayout();
		p1.setConversionPattern("[%p] %d{dd MMM yyyy HH:mm:ss,SSS}  %n%m%n%n");

		PatternLayout p3 = new PatternLayout();
		p3.setConversionPattern(
			"[%p] Fehlerausgabe vom %d{dd MMM yyyy HH:mm:ss,SSS} %n%n%m%n-------------------------------------------------------------------------------------%n");

		try
		{
			PatternLayout logPattern = new PatternLayout();
			logPattern.setConversionPattern("%d{dd MMM yyyy HH:mm:ss,SSS} [%p] %C{1}: %m%n");
			
			//ConsoleAppender
			PatternLayout pConsole = new PatternLayout();
			pConsole.setConversionPattern("[%p] %d{dd MMM yyyy HH:mm:ss,SSS} [%C{1}] %m%n");
			ConsoleAppender ca = new ConsoleAppender(pConsole);
			
			RollingFileAppender faProduction = new RollingFileAppender(p1, logDir + "Produktion.log", true);
			//org.apache.log4j.RollingFileAppender faProductionVerb =
			//	new org.apache.log4j.RollingFileAppender(p1, LoggerPath + sysprops.getProperty("file.separator") + "Production_Verb.log", true);

			faProduction.setMaxFileSize("1000000");			//ca. 1 MB
			faProduction.setMaxBackupIndex(100);
			//faProductionVerb.setMaxFileSize("10000000");		//ca. 10 MB
			//faProductionVerb.setMaxBackupIndex(100);
			//faError.setMaxFileSize("1000000");				//ca. 1 MB
			//faError.setMaxBackupIndex(100);
			
			Production.addAppender(faProduction);
			//Error.addAppender(faError);
			//Production_Verb.addAppender(faProductionVerb);
			
			//ServerLogger
			PatternLayout pServer = new PatternLayout();
			pServer.setConversionPattern("[%p] %d{dd MMM yyyy HH:mm:ss,SSS} [%C{1}] %m%n");
			RollingFileAppender faServer = new RollingFileAppender(pServer, logDir + "Server.log", true);
			faServer.setMaxFileSize("1000000");			//ca. 1 MB
			faServer.setMaxBackupIndex(100);
			server.addAppender(faServer);
			
			//ProducerLogger
			PatternLayout pProducer = new PatternLayout();
			pProducer.setConversionPattern("[%p] %d{dd MMM yyyy HH:mm:ss,SSS} [%C{1}] %m%n");
			RollingFileAppender faProducer = new RollingFileAppender(pProducer, logDir + "Producer.log", true);
			faProducer.setMaxFileSize("1000000");			//ca. 1 MB
			faProducer.setMaxBackupIndex(100);
			producer.addAppender(faProducer);
			
			//SpawnLogger
			RollingFileAppender faSpawn = new RollingFileAppender(logPattern, logDir + "Spawn.log", true);
			faSpawn.setMaxFileSize("1000000");			//ca. 1 MB
			faSpawn.setMaxBackupIndex(100);
			spawn.addAppender(faSpawn);
		}
		catch (IOException e) {e.printStackTrace();}
		server.debug("ServerLogger ist initialisiert");
		producer.debug("ProducerLogger ist initialisiert");
		spawn.debug("SpawnLogger ist initialisiert");
		isInited=true;
	}
	
	//Beim Starten werden dem Logger informationen über Client und aus dem Request übergeben
	
	public static void productionLog(String method,String application,String project,
			String document,String format, String username,	
			Calendar startTime,Calendar endTime,ProductionCode pc) {
		Production.info(
				"\n\tMethod: " + method + 
				"\n\tApplication: "	+ application+ 
				"\n\tProject: " + project + 
				"\n\tDocument: " + document	+ 
				"\n\tFormat: " + format	+ 
				"\n\tUsername: " + username + 
				"\n\tStart time: " + startTime.getTime().toString()
				+ "\n\tEnd time: " + endTime.getTime().toString()
				+ "\n\tStatus: " + pc				
				);
	}


//Schreibt alle informationen in des Logfile

	/*public static void productionLog_Verb(
		String client,
		String application,
		String project,
		String document,
		String format,
		String username,
		String stdLog, 
		String errLog) {
		Production_Verb.info(
				"\tClient: "
				+ client
				+ "\n\tApplication: "
				+ application
				+ "\n\tProject: "
				+ project
				+ "\n\tDocument: "
				+ document
				+ "\n\tFormat: "
				+ format
				+ "\n\tUsername: "
				+ username + "\n" + 
				"\n[ErrorLog]\n" + errLog +
				"\n[StandardLog]\n" + stdLog);
	}*/

	public static void ErrorLog(Exception e) {
		StringWriter sw = new StringWriter();
		//e.printStackTrace(new PrintWriter(sw));
		String s = sw.toString();
		Error.error(s);
	}
	
	public static void debug(String message){
		Production.debug(message);
	}
	
	public static void info(String message){
		Production.info(message);
	}
	
	public static void error(String message){
		Production.error(message);
	}
	
	public static void fatal(String message){
		Production.fatal(message);
	}

}