// Created on 28.04.2004
package org.openfuxml.server.simple;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.communication.cluster.ejb.Host;
import org.openfuxml.producer.Producer;
import org.openfuxml.producer.handler.DirectProducer;
import org.openfuxml.server.AbstractServer;

/**
 * Server oeffnet den ServerSocket und wartet dann auf Clientverbindungen.
 * Fur jede Clientverbindung wird ein neuer ServerThread der Klasse.
 * {@link org.openfuxml.communication.server.simple.SimpleServerThread.class SimpleServerThread}
 * erzeugt. <br>
 * @author Thorsten
 * @version 0.3
 */
public class SimpleServer extends AbstractServer
{
	final static Logger logger = LoggerFactory.getLogger(SimpleServer.class);
	
	public SimpleServer(Configuration config)
	{
		super(config);
		logger.info("Applikation wird gestartet");
		
		int serverPort = config.getInt("net/port");
		
		setSystemProperties();
		checkSystemProperties();
		
		Host host = getHost();
		
		Producer p = new DirectProducer(config,host,envParameter);
		
		logger.debug("ServerSocket erstellen: "+serverPort);
		ServerSocket serverSocket=null;
		try {serverSocket = new ServerSocket(serverPort);}
		catch (IOException e)
		{
			logger.fatal(e.getMessage());
			logger.fatal("Could not listen on port: "+serverPort);
			logger.fatal("Applikation wird beendet");
            	System.exit(1);
        }
		logger.debug("Server lauscht auf Port "+serverSocket.getLocalPort());
		
		ThreadGroup clientTg = new ThreadGroup("Alle Clients");
		SimpleShutdownThread myShutdownThread = new SimpleShutdownThread(clientTg,serverSocket);
		(Runtime.getRuntime()).addShutdownHook(myShutdownThread);
		try
		{
			while (myShutdownThread.getAppActive())
			{
				SimpleServerThread sst = new SimpleServerThread(clientTg, serverSocket.accept(),new DirectProducer(config,host,envParameter)); 
				sst.start();
			}
		}
		catch (SocketException e)
		{
			if (myShutdownThread.getAppActive())
			{
				logger.error(e.getMessage());
			}
		}
		catch (IOException e){logger.error(e.getMessage());}

		logger.debug(this.getClass().getSimpleName()+" ist beendet");
	}

	public static void main(String[] args) throws IOException
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();

		Configuration config = ConfigLoader.load("openFuXML.xml");

		new SimpleServer(config);
	}
}