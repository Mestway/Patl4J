package org.openfuxml.communication.cluster.facade;

import javax.ejb.Remove;

import org.openfuxml.communication.cluster.ejb.EjbObject;
import org.openfuxml.producer.Producer;

public interface ProducerFacade extends Producer
{
	int newEjbObject(EjbObject ejbo);
	
	@Remove void close();
}