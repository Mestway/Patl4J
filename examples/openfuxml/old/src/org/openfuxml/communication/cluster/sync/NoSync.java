/*
 * Created on 2006-12-09
 */
package org.openfuxml.communication.cluster.sync;

import java.util.Collection;

import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.ejb.SyncLocation;

/**
 * @author Thorsten
 */
public class NoSync implements ServerSync
{
	
	public NoSync()	{}

	public void getRepo(ProductionRequest request) {}
	public void getOutput(ProductionRequest request) {}
	
	public void upOutput(ProductionRequest request){}//,ProductionResult result) {}
	
	public boolean available() {return true;}
	public void setSyncLocations(Collection<SyncLocation> sls){}
}
