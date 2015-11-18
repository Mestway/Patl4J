package org.openfuxml.server.cluster;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.List;

import javax.naming.CommunicationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.communication.cluster.ejb.Host;
import org.openfuxml.communication.cluster.facade.OpenFuxmlFacade;
import org.openfuxml.communication.cluster.facade.OpenFuxmlFacadeBean;
import org.openfuxml.communication.cluster.facade.ProducerFacade;
import org.openfuxml.communication.cluster.facade.ProducerFacadeBean;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.producer.Producer;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.producer.handler.SyncProducer;
import org.openfuxml.server.AbstractServer;
import org.openfuxml.server.simple.SimpleServerThread;
import org.openfuxml.server.simple.SimpleShutdownThread;

import de.kisner.util.Connector;

/**
 * @author Thorsten
 * @version 0.3
 */
public class EnterpriseServer extends AbstractServer
{
	final static Logger logger = LoggerFactory.getLogger(EnterpriseServer.class);
	
	Host host;
	
	public EnterpriseServer(Configuration config)
	{
		super(config);
		logger.info("Applikation wird gestartet");
		host = getHost();
		logger.debug("Host "+host.getHostName()+"("+host.getHostIP()+") id="+host.getId());
		
		int serverPort =config.getInt("net/port");
	
		setSystemProperties();
		checkSystemProperties();
		
		
		String jndiHost = config.getString("net/jndi[@typ='host']","host")+":"+config.getInt("net/jndi[@typ='port']");
		logger.info("Verbinden nach: "+jndiHost);
		InitialContext ctx=null;
		try {ctx = Connector.getContext(jndiHost);}
		catch (CommunicationException e) {exit("No Connection to JNDI-Server",e);}
		try
		{
			ProducerFacade fP = (ProducerFacade) ctx.lookup(ProducerFacadeBean.class.getSimpleName()+"/remote");
			OpenFuxmlFacade fO = (OpenFuxmlFacade) ctx.lookup(OpenFuxmlFacadeBean.class.getSimpleName()+"/remote");
			
			host=fO.updateHost(host);
			Producer p = new SyncProducer(config,host,envParameter);
			List<OfxApplication> lOfxA = p.getAvailableApplications();
			
/*			for(Application a : aas.getApplications())
			{
				fP.newEjbObject(p.getAvailableFormats(a.getName()));
			}
			logger.debug("getAvailableApplications:"+aas.getHost().getHostName());
			fP.newEjbObject(aas);
			fP.close();
*/		}
		catch (javax.naming.NameNotFoundException e){exit("Alle EJB-Beans deployed?",e);}
		catch (NamingException e) {e.printStackTrace();}
		catch (ProductionSystemException e) {e.printStackTrace();}
		catch (ProductionHandlerException e) {e.printStackTrace();}
			
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
				SimpleServerThread sst = new SimpleServerThread(clientTg, serverSocket.accept(),new SyncProducer(config,host,envParameter)); 
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

	public static void exit(String message, Exception e)
	{
		if(e!=null){logger.fatal(e.getMessage());}
		logger.fatal(message);
		logger.fatal("Applikation wird beendet");
		System.exit(-1);
	}
	
	public static void main(String[] args) throws IOException
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		Configuration config = ConfigLoader.load("openFuXML.xml");

		new EnterpriseServer(config);
	}
}