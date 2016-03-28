package org.openfuxml.server.simple;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author kisner
 */
public class SimpleShutdownThread  extends Thread {

	final static Logger logger = LoggerFactory.getLogger(SimpleShutdownThread.class);
	
	ServerSocket serverSocket;
	boolean active;
	ThreadGroup myThreadGroup;
	
    public SimpleShutdownThread(ThreadGroup myThreadGroup,ServerSocket serverSocket)
	{
	    	super("ShutdownThread");
	    	this.serverSocket=serverSocket;
	    	this.myThreadGroup=myThreadGroup;
	    	active=true;
	    logger.info(this.getClass().getSimpleName()+" erstellt.");
    }

    public boolean getAppActive()
    {
    	return active;
    }
    
    public void run()
	{  
	    	active=false;
	    	myThreadGroup.interrupt();
	    	try	{serverSocket.close();}
	    	catch (IOException e){logger.error(e.getMessage());}
	    	
	    	logger.info("Server Socket (Port="+serverSocket.getLocalPort()+") geschlossen.");
	    	logger.info(this.getClass().getSimpleName()+" beendet.");
	}
}


