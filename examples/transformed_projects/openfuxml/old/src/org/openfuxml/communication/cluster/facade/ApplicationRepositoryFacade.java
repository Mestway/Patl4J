package org.openfuxml.communication.cluster.facade;

import javax.ejb.Remove;

import org.openfuxml.communication.cluster.ejb.ApplicationRepository;
import org.openfuxml.communication.cluster.ejb.EjbObject;

public interface ApplicationRepositoryFacade
{
	Object newObject(Object o);
	int newEjbObject(EjbObject ejbo);
	void deleteObject(Object o);
	Object updateObject(Object o);
	Object findObject(Class c,int id);
	
	//ApplicationRepository
	ApplicationRepository findApplicationRepository(String application, int version);
	ApplicationRepository findLastApplicationRepository(String application);
	int newApplicationRepositoryVersion(ApplicationRepository ar);
	
	@Remove void checkout();
}