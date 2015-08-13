package org.openfuxml.producer.handler;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.communication.cluster.ejb.Host;
import org.openfuxml.communication.cluster.sync.NoSync;
import org.openfuxml.communication.cluster.sync.ServerSync;
import org.openfuxml.communication.cluster.sync.unison.UnisonSync;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.producer.Producer;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

import de.kisner.util.architecture.EnvironmentParameter;

public class SyncProducer extends AbstractProducer implements Producer
{
	final static Logger logger = LoggerFactory.getLogger(DirectProducer.class);
	
	private Producer p;
	private ServerSync unisonSync,noSync;
	
	public SyncProducer(Configuration config,Host host,EnvironmentParameter envP)
	{	
		super(config,envP);
		unisonSync = new UnisonSync(config);
		noSync = new NoSync();
		p = new DirectProducer(config,host,envP);
	}
	
	public List<OfxApplication> getAvailableApplications() throws ProductionSystemException,ProductionHandlerException
	{
		return p.getAvailableApplications();
	}
	
	public List<OfxFormat> getAvailableFormats(OfxApplication ofxA) throws ProductionSystemException, ProductionHandlerException
	{
		return p.getAvailableFormats(ofxA);
	}
	
	public ProducedEntities invoke(ProductionRequest pReq) throws ProductionSystemException,ProductionHandlerException
	{
		ServerSync sync=null;
		logger.debug("invoke aufgerufen: Sync="+pReq.getSync());
		switch(pReq.getSync())
		{
			case UNISON: sync=unisonSync;break;
			case NOSYNC: sync=noSync;break;
		}
		sync.setSyncLocations(pReq.getSyncLocations());
		if(sync.available())
		{
			sync.getRepo(pReq);
			sync.getOutput(pReq);
		}
		ProducedEntities producedEntities=p.invoke(pReq);
		sync.upOutput(pReq);
		return producedEntities;
	}
}
