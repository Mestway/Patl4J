package org.openfuxml.producer.handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.producer.Producer;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.RequestWrapper;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

import de.kisner.util.architecture.EnvironmentParameter;

public class SocketProducer extends AbstractProducer implements Producer
{
	final static Logger logger = LoggerFactory.getLogger(SocketProducer.class);
	
	String serverIP;
	int serverPort;
	
	public SocketProducer(Configuration config)
	{
		super(config, new EnvironmentParameter());
		serverIP=config.getString("net/host");
		serverPort=config.getInt("net/port");
	}
	
	@SuppressWarnings("unchecked")
	public List<OfxApplication> getAvailableApplications() throws ProductionSystemException,ProductionHandlerException
	{
		RequestWrapper po = new RequestWrapper();
		po.setProductionType(RequestWrapper.ProductionType.AVAPPS);
		po.setObject("");
		RequestWrapper productionResult=handle(po);
		logger.debug("Antwort bekommen: "+productionResult.getClass().getSimpleName()+"-"+productionResult.getObject().getClass().getSimpleName());
		return (List<OfxApplication>)productionResult.getObject();  
	}
	
	public List<OfxFormat> getAvailableFormats(OfxApplication ofxA) throws ProductionSystemException, ProductionHandlerException
	{
		logger.debug("getAvailableFormats("+ofxA.getName()+")");
		RequestWrapper po = new RequestWrapper();
		po.setProductionType(RequestWrapper.ProductionType.AVFORMATS);
		po.setObject(ofxA);
		RequestWrapper productionResult=handle(po);
		logger.debug("Antwort bekommen: "+productionResult.getClass().getSimpleName()+"-"+productionResult.getObject().getClass().getSimpleName());
		return (List<OfxFormat>)productionResult.getObject();  
	}
	
	public ProducedEntities invoke(org.openfuxml.producer.ejb.ProductionRequest request) throws ProductionHandlerException
	{
		logger.debug("invoke aufgerufen");
		
		RequestWrapper poOUT = new RequestWrapper();
		poOUT.setProductionType(RequestWrapper.ProductionType.INVOKE);
		poOUT.setObject(request);
		RequestWrapper poIN=handle(poOUT);
		logger.debug("Empfangen "+poIN.getResultType());
		if(poIN.getResultType()==RequestWrapper.ResultType.EXCEPTION)
		{
			throw new ProductionHandlerException(poIN.getException().getMessage());
		}
		logger.debug("Empfangen "+poIN.getObject().getClass().getSimpleName());
		ProducedEntities pe = (ProducedEntities)poIN.getObject();
		return pe;  
	}
		
	private RequestWrapper handle(RequestWrapper po) throws ProductionHandlerException
	{
		Object incommingObject=null;
		logger.info("Erstelle Socket ("+serverIP+":"+serverPort+") und Streams ");
        try
        {
	        	Socket s = new Socket(serverIP, serverPort);
	        	ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				oos.writeObject(po);
	        	oos.reset();
	        	incommingObject = ois.readObject();
        }
        catch (UnknownHostException e)
        {
        		logger.fatal("UnknownHostException" , e);
	        	throw new ProductionHandlerException("UnknownHostException" , e);
        }
        catch (IOException e)
        {
        		logger.fatal("IOException", e);
        		throw new ProductionHandlerException("IOException", e);
        }
        catch (ClassNotFoundException e)
        {
        		logger.fatal("ClassNotFoundException", e);
        		throw new ProductionHandlerException("ClassNotFoundException", e);
        }
        return (RequestWrapper)incommingObject;
	}
}

