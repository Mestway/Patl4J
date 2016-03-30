package org.openfuxml.communication.cluster.facade;

import java.io.Serializable;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.communication.cluster.ejb.ApplicationRepository;
import org.openfuxml.communication.cluster.ejb.EjbObject;

@Stateful
@Remote(ApplicationRepositoryFacade.class)
public class ApplicationRepositoryFacadeBean implements ApplicationRepositoryFacade, Serializable
{
	final static Logger logger = LoggerFactory.getLogger(ApplicationRepositoryFacadeBean.class);
	static final long serialVersionUID=10;
	
	@PersistenceContext (unitName="openfuxml")
	private EntityManager manager;
   	
	public Object newObject(Object o) {manager.persist(o);return o;}
	public int newEjbObject(EjbObject ejbo) {manager.persist(ejbo);return ejbo.getId();}
	public void deleteObject(Object o) {manager.remove(o);}
	public Object updateObject(Object o) {manager.merge(o);return o;}
	public Object findObject(Class c,int id){return manager.find(c,id);}
	
	public ApplicationRepository findApplicationRepository(String application, int version)
	{
		StringBuffer sb = new StringBuffer();
			sb.append("SELECT * FROM "+ApplicationRepository.class.getSimpleName()+" ");
			sb.append("WHERE (application = :application ");
			sb.append(" AND version = :version)");
		ApplicationRepository ar=null;
		Query nq=manager.createNativeQuery(sb.toString(),ApplicationRepository.class);
		nq.setParameter("application", application );
		nq.setParameter("version", version);
		try	{ar=(ApplicationRepository)nq.getSingleResult();}
		catch (NoResultException ex){}
		return ar;
	}
	
	public ApplicationRepository findLastApplicationRepository(String application)
	{
		StringBuffer sb = new StringBuffer();
			sb.append("SELECT * FROM "+ApplicationRepository.class.getSimpleName()+" ");
			sb.append("WHERE (application = :application) ");
			sb.append(" ORDER BY record DESC ");
			sb.append(" LIMIT 1");
		ApplicationRepository ar=null;
		Query nq=manager.createNativeQuery(sb.toString(),ApplicationRepository.class);
		nq.setParameter("application", application );
		try	{ar=(ApplicationRepository)nq.getSingleResult();}
		catch (NoResultException ex){}
		return ar;
	}
	
	public int newApplicationRepositoryVersion(ApplicationRepository ar)
	{
		ApplicationRepository arLast = findLastApplicationRepository(ar.getApplication());
		int newVersion=1;
		if(arLast!=null){newVersion=arLast.getVersion()+1;}
		ar.setVersion(newVersion);
		return newEjbObject(ar);
	}
	
	@Remove
	public void checkout()
	{
		logger.info("Checkout");
	}
}