package org.openfuxml.communication.cluster.facade;

import java.util.List;

import javax.ejb.Remove;

import org.openfuxml.communication.cluster.ejb.EjbObject;
import org.openfuxml.communication.cluster.ejb.Host;

public interface OpenFuxmlFacade
{
	Object newObject(Object o);
	int newEjbObject(EjbObject ejbo);
	Object findObject(Class c,int id);
	
	
	//Hosts
	Host findLastHost(String hostName);
	List<Host> findLastHosts();
	Host updateHost(Host h);
	
	//Applications und Formats
//	AvailableApplications findLastAvailableApplicationsforHost(Host h);
//	AvailableFormats findLastAvailableFormatsforHost(Host h);
	
	@Remove void close();
}