package org.openfuxml.client.control;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.client.control.log.QueueLogConsumer;
import org.openfuxml.client.control.log.QueueLogFetcher;
import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Productionresult;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.producer.Producer;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.producer.handler.DirectProducer;
import org.openfuxml.util.config.OfxPathHelper;

/**
 * ProducerThread implementiert den Thread, in dem die Methoden      
 * producableEntities bzw. produce der DispatcherBean ausgeführt werden.
 *   
 * @author Thorsten Kisner
 */
public class ProducerThread extends Thread
{
	final static Logger logger = LoggerFactory.getLogger(ProducerThread.class);
	
	private OfxClientControl ofxCC;
	private Configuration config;
	
	private ClientGuiCallback guiCallback;
	private Producer producer;
	private DirectProducer.Typ typ;
	private Sessionpreferences spref;

	/**
	 * Im Konstruktor wird die DispatcherBean erzeugt.
	 * typ wird auf UNDEFINED gesetzt.
	 * @param Parent - das  aufrufende Element
	 * @param JndiHost - String, der Host und Port bestimmt (Syntax host:port).
	 */
	public ProducerThread(OfxClientControl ofxCC, ClientGuiCallback guiCallback, Producer producer, Configuration config)
	{
		this.ofxCC=ofxCC;
		this.guiCallback = guiCallback;
		this.producer = producer;
		this.config=config;
	}

	/**
	 * In der Methode run wird unterschieden (typ), welche Methode der DispatcherBean
	 * aufgerufen wird. Nach  dem Aufruf wird die Bean entfernt und das Ergebnis 
	 * dem aufrufenden Element übergeben (Parent.fuelleTableProductionEntities 
	 * bzw. Parent.ProduktionEnde).
	 * Zum Schluss wird der Thread gestoppt.
	 */
    public void run()
	{
		try
		{
			guiCallback.clearLog();
			LinkedBlockingQueue<String> q = new LinkedBlockingQueue<String>();
			QueueLogConsumer qlc = new QueueLogConsumer(q,guiCallback);
			QueueLogFetcher qlf = new QueueLogFetcher(q);
			producer.setLogWriter(qlf);
			qlc.start();
			
			switch(typ)
			{
				case ENTITIES:	guiCallback.setStatus("Discovering entities ...");
								guiCallback.setProductionControlsEnabled(false);
								ProducibleEntities pe = producer.discoverEntities(spref);
								guiCallback.setStatus("Entities discovered");
								ofxCC.setDiscoveredEntities(pe);
								guiCallback.entitiesDiscovered();
								guiCallback.setProductionControlsEnabled(true);break;
								
				case PRODUCE:	guiCallback.setStatus("Producing ...");
								guiCallback.setProductionControlsEnabled(false);
								Productionresult presult = producer.produce(spref);
								guiCallback.setStatus("Entities produced");
								ofxCC.setProducedEntities(presult);
								guiCallback.entitiesProduced();
								if(spref.getFormat().equals("validation")){validationFinished(spref,presult);}
								guiCallback.setProductionControlsEnabled(true);break;
			}
		}
			catch (ProductionSystemException e)
			{
				logger.fatal("ProductionSystemException", e);
//    				Parent.setProducedEntities(pe, ERROR);
			}
			catch (ProductionHandlerException e)
			{
				logger.fatal("ProductionHandlerException", e);
//    				Parent.setProducedEntities(pe, ERROR);
			}		
	}

    private void validationFinished(Sessionpreferences spref,Productionresult presult)
    {
		try
		{
			
			StringBuffer sb = new StringBuffer();
				sb.append(OfxPathHelper.getDir(config, "output")+"/");
				sb.append(spref.getApplication()+"/");
				sb.append(spref.getProject()+"/");
				sb.append("validation/");
				sb.append(spref.getDocument().substring(0, spref.getDocument().indexOf(".xml"))+"/");
				sb.append(presult.getProducedentities().getFile().get(0).getFilename());
				
			URL url = new URL("file","localhost",sb.toString());
			logger.debug(url);
			guiCallback.openUrl(url);
		}
		catch (MalformedURLException e) {logger.error("",e);}
    	
    }
    
    public void produce(Sessionpreferences spref)
    {
    	typ = DirectProducer.Typ.PRODUCE;
    	this.spref=spref;
    	this.start();
    }
    
    public void getProducibleEntities(Sessionpreferences spref)
    {
    	typ = DirectProducer.Typ.ENTITIES;
    	this.spref=spref;
    	this.start();
    }

}