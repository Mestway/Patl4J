// Created on 28.08.2006
package org.openfuxml.server.simple;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.producer.Producer;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.ejb.RequestWrapper;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

/**
 * @author kisner
 * @version 0.9
 */
public class SimpleServerThread extends Thread
{
	final static Logger logger = LoggerFactory.getLogger(SimpleServerThread.class);
	
	private static int anzahlClients = 0;
	private Socket clientSocket = null;
	private Producer p;
	
	/**
	 * Initialisiert den ServerThread und verbindet Ihn mit dem Client.
	 * 
	 * @param clientSocket -
	 *            Die Verbindung zum Client.
	 */
	public SimpleServerThread(ThreadGroup clientThreadGroup, Socket clientSocket,Producer p)
	{
		super(clientThreadGroup, "ServerThread");
		this.p=p;
		this.clientSocket = clientSocket;
		logger.debug("initialisiert");
	}

	/**
	 * Erzeugt einen ObjectInput- und OutputStream vom Client.
	 * Sorgt am Schluss für das Schließen der Streams und des Sockets.
	 */
	public void run()
	{
		Object incommingObject=new Object();
		logger.info("Verbindungsanfrage von "+clientSocket.getInetAddress());
		try
		{
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

			anzahlClients++;
			logger.debug("Server mit "+anzahlClients+" verbunden.");

			
			incommingObject = ois.readObject();
			logger.debug("Objekt empfangen.");
			RequestWrapper poIN = (RequestWrapper)incommingObject;
			logger.debug("Objekt empfangen: "+poIN.getClass().getSimpleName()+"-"+poIN.getObject().getClass().getSimpleName());
			RequestWrapper poOUT = new RequestWrapper();
			poOUT.setProductionType(poIN.getProductionType());
			poOUT.setResultType(RequestWrapper.ResultType.EXCEPTION);
			try
			{
				ProductionRequest pReq;
				ProducedEntities pe;
				switch(poIN.getProductionType())
				{
					case AVAPPS:	poOUT.setObject(p.getAvailableApplications());
									poOUT.setResultType(RequestWrapper.ResultType.OK);
									break;
					
					case AVFORMATS:	OfxApplication ofxA=(OfxApplication)poIN.getObject();
									List<OfxFormat> lOfxF= p.getAvailableFormats(ofxA);
									poOUT.setObject(lOfxF);
									poOUT.setResultType(RequestWrapper.ResultType.OK);
									break;
					
					case INVOKE: 	pReq = (org.openfuxml.producer.ejb.ProductionRequest)poIN.getObject();
									//logger.debug("Empfangener ProductionRequest: "+preq.getMessageString());
									pe = p.invoke(pReq);
									logger.debug("invoke : ");
									poOUT.setObject(pe);
									poOUT.setResultType(RequestWrapper.ResultType.OK);
									break;
									}
			}
			catch (ProductionSystemException e)
			{
				logger.error("ProductionSystemException: "+e.getMessage());
				poOUT.setException(e);
			}
			catch (ProductionHandlerException e)
			{
				logger.error("ProductionHandlerException: "+e.getMessage());
				poOUT.setException(e);
			}
			
			if(poIN.getResultType()==RequestWrapper.ResultType.OK)
			{logger.debug("Auftrag ausgefuhrt: "+poOUT.getProductionType()+ " "+poOUT.getObject().getClass().getSimpleName());}   
			
			oos.writeObject(poOUT);
			oos.reset();
			logger.debug("Objekt gesendet: "+poOUT.getClass().getSimpleName());
			
			try {Thread.sleep(5*1000);}catch (InterruptedException e) {}
			
			
			oos.close();
			ois.close();
			logger.info("Verbind beendet "+clientSocket.getInetAddress().getHostAddress());
			clientSocket.close();
			
		}
		catch (IOException e) {logger.error("Fehler bei Verbindung mit Client:"+e.getMessage());}
		catch (ClassNotFoundException e) {logger.error("FalscherObjekttyp:"+incommingObject.getClass().getSimpleName());}
/*		finally
		{
			anzahlClients--;
			logger.info("Server mit "+anzahlClients+" verbunden.");
		}
*/	}

	public void interrupt()
	{
		logger.info("ServerThread hat interrupt erhalten");
	}

}