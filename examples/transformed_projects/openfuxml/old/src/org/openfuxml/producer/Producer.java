package org.openfuxml.producer;

import java.io.Writer;
import java.util.List;

import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Productionresult;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

public interface Producer
{
	//neue Methoden
	ProducedEntities invoke(ProductionRequest request) throws ProductionSystemException,ProductionHandlerException;
	
	//ganz neue Methoden
	List<OfxApplication> getAvailableApplications() throws ProductionSystemException,ProductionHandlerException;
	public List<OfxFormat> getAvailableFormats(OfxApplication ofxA) throws ProductionSystemException, ProductionHandlerException;
	public Productionresult produce(Sessionpreferences spref) throws ProductionSystemException, ProductionHandlerException;
	public ProducibleEntities discoverEntities(Sessionpreferences spref) throws ProductionSystemException, ProductionHandlerException;
	
	public void setLogWriter(Writer w);
	public void close();
}

