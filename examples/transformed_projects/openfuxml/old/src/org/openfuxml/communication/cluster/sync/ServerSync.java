/*
 * Created on 2006-12-09
 */
package org.openfuxml.communication.cluster.sync;

import java.util.Collection;

import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.ejb.SyncLocation;
import org.openfuxml.producer.exception.ProductionHandlerException;

/**
 * @author Thorsten
 */
public interface ServerSync
{
	/**
	 * @author Thorsten
	 * Das gesamte Repository für das aktive Projeckt wird mit dem Server
	 * synchronisiert. Der Server überschreibt lokale Änderungen.
	 */
	public void getRepo(ProductionRequest request);
	
	/**
	 * @author Thorsten
	 * Das gesamte Output für das aktive Projeckt wird mit dem Server
	 * synchronisiert. Der Server überschreibt lokale Änderungen.
	 */
	public void getOutput(ProductionRequest request);
	
	public void upOutput(ProductionRequest request);//,ProductionResult result);
	
	/**
	 * @author Thorsten
	 * Prüft, ob die Synchronisationsmethode verfügbar ist.
	 */
	public boolean available() throws ProductionHandlerException;
	
	/**
	 * @author Thorsten
	 * Definiert die Sync-Ziele für die aktuelle Verbindung.
	 */
	public void setSyncLocations(Collection<SyncLocation> sls);
}

