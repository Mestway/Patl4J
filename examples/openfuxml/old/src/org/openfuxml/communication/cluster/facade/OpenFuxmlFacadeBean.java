package org.openfuxml.communication.cluster.facade;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.communication.cluster.ejb.EjbObject;
import org.openfuxml.communication.cluster.ejb.Host;

@Stateful
@Remote(OpenFuxmlFacade.class)
public class OpenFuxmlFacadeBean implements OpenFuxmlFacade, Serializable
{
	final static Logger logger = LoggerFactory.getLogger(OpenFuxmlFacadeBean.class);
	static final long serialVersionUID=13;
	
	@PersistenceContext (unitName="openfuxml")
	private EntityManager manager;
	
	public OpenFuxmlFacadeBean()
	{
		logger.info("public Constructor. Version="+serialVersionUID);
	}
	
	public Object newObject(Object o) {manager.persist(o);return o;}
	public int newEjbObject(EjbObject ejbo) {manager.persist(ejbo);return ejbo.getId();}
	public Object updateObject(Object o) {manager.merge(o);return o;}
	public Object findObject(Class c,int id){return manager.find(c,id);}
	
	public Host updateHost(Host h)
	{
		if(h.getId()==0)
		{
			Host hDb=findLastHost(h.getHostName());
			if(hDb==null){h=(Host)newObject(h);}
			else
			{
				hDb.setRecord(h.getRecord());
				hDb.setHostIP(h.getHostIP());
				h=(Host)updateObject(hDb);
			}
		}
		else
		{
			h=(Host)updateObject(h);
		}
		return h;
	}
	public Host findLastHost(String hostName)
	{
		Host h =null;
		StringBuffer sb = new StringBuffer();
			sb.append("SELECT * FROM "+Host.class.getSimpleName());
			sb.append(" WHERE hostName = :hostName");
			sb.append(" ORDER BY record DESC");
			sb.append(" LIMIT 1");
		Query nq=manager.createNativeQuery(sb.toString(),Host.class);
		nq.setParameter("hostName", hostName);
		try{h=(Host)nq.getSingleResult();}
		catch (NoResultException ex){}
		return h;
	}
	public List<Host> findLastHosts()
	{
		List<Host> hosts=null;
		StringBuffer sb = new StringBuffer();
			sb.append("SELECT * FROM "+Host.class.getSimpleName());
			sb.append(" WHERE record>DATE_SUB(NOW(),INTERVAL 60 MINUTE)");
		Query nq=manager.createNativeQuery(sb.toString(),Host.class);
		try{hosts=(List<Host>)nq.getResultList();}
		catch (NoResultException ex){}
		return hosts;
	}
	
	// AvailableApplications
/*	public AvailableApplications findLastAvailableApplicationsforHost(Host h)
	{
		AvailableApplications aas=null;
		StringBuffer sb = new StringBuffer();
			sb.append("SELECT * FROM "+AvailableApplications.class.getSimpleName());
			sb.append(" WHERE host_id = :hostid");
			sb.append(" ORDER BY record DESC");
			sb.append(" LIMIT 1");
		Query nq=manager.createNativeQuery(sb.toString(),AvailableApplications.class);
		nq.setParameter("hostid", h.getId());
		try{aas=(AvailableApplications)nq.getSingleResult();}
		catch (NoResultException ex){}
		return aas;
	}
*/
/*	public AvailableFormats findLastAvailableFormatsforHost(Host h)
	{
		AvailableFormats afs=null;
		StringBuffer sb = new StringBuffer();
			sb.append("SELECT * FROM "+AvailableFormats.class.getSimpleName());
			sb.append(" WHERE host_id = :hostid");
			sb.append(" ORDER BY record DESC");
			sb.append(" LIMIT 1");
		Query nq=manager.createNativeQuery(sb.toString(),AvailableFormats.class);
		nq.setParameter("hostid", h.getId());
		try{afs=(AvailableFormats)nq.getSingleResult();}
		catch (NoResultException ex){}
		return afs;
	}
*/	
	@Remove
	public void close()
	{
		logger.info("close()");
	}
}