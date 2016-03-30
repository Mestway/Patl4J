package org.openfuxml.producer.handler;

import java.io.Writer;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Productionresult;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

import de.kisner.util.architecture.EnvironmentParameter;

public abstract class AbstractProducer
{
	final static Logger logger = LoggerFactory.getLogger(AbstractProducer.class);
	protected EnvironmentParameter envP;
	protected Configuration config;
	protected Writer w;
	
	public AbstractProducer(Configuration config, EnvironmentParameter envP)
	{
		this.config=config;
		this.envP=envP;
		w=null;
	}
	
	public void close()
	{
		logger.debug("close im "+AbstractProducer.class.getSimpleName()+" nicht implementiert.");
	}
	
	public List<OfxApplication> getAvailableApplications22() throws ProductionSystemException,ProductionHandlerException
	{
		logger.warn("Not implemented for this Handler!!");
		return null;
	}
	
	public Productionresult produce(Sessionpreferences ofxR) throws ProductionSystemException
	{
		logger.warn("Not implemented for this Handler!!");
		return null;
	}
	
	public ProducibleEntities discoverEntities(Sessionpreferences spref) throws ProductionSystemException, ProductionHandlerException
	{
		logger.warn("Not implemented for this Handler!!");
		return null;
	}
	
	public void setLogWriter(Writer w)
	{
		logger.trace("Setting Writer: "+w.getClass().getSimpleName());
		this.w=w;
	}
}
