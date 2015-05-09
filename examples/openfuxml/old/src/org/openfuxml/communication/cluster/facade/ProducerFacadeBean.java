package org.openfuxml.communication.cluster.facade;

import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.communication.cluster.ejb.EjbObject;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Productionresult;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

@Stateful
@Remote(ProducerFacade.class)
public class ProducerFacadeBean implements ProducerFacade, Serializable
{
	final static Logger logger = LoggerFactory.getLogger(ProducerFacadeBean.class);
	static final long serialVersionUID=11;
	
	public ProducerFacadeBean()
	{
		logger.info("public Constructor. Version="+serialVersionUID);
	}
	
	@PersistenceContext (unitName="openfuxml")
	private EntityManager manager;
	
	public Object newObject(Object o) {manager.persist(o);return o;}
	public int newEjbObject(EjbObject ejbo) {manager.persist(ejbo);return ejbo.getId();}
	
	//neue Methoden
	public List<OfxApplication> getAvailableApplications() throws ProductionHandlerException
	{
		logger.warn("This has be implemented!");
		return new ArrayList<OfxApplication>();
	}
	
/*	public AvailableFormats getAvailableFormats(String application) throws  ProductionHandlerException
	{
		Hashtable<String,Format> htAvailableFormats = new Hashtable<String,Format>();
		try
		{
			Context ctx = new InitialContext();
			OpenFuxmlFacade fO = (OpenFuxmlFacade) ctx.lookup(OpenFuxmlFacadeBean.class.getSimpleName()+"/remote");
			for(Host h : fO.findLastHosts())
			{
				AvailableFormats afs = fO.findLastAvailableFormatsforHost(h);
				for(Format f : afs.getFormats())
				{
					if(htAvailableFormats.containsKey(f.getFormatId()))
					{
						int coresFound=htAvailableFormats.get(f.getFormatId()).getAnzCores();
						int newCores=f.getAnzCores();
						f.setAnzCores(coresFound+newCores);
					}
					else{htAvailableFormats.put(f.getFormatId(), f);}
				}
			}
			fO.close();
		}
		catch (NamingException e) {e.printStackTrace();}
		AvailableFormats afs = new AvailableFormats();
		for(Format f : htAvailableFormats.values()){afs.addFormat(f);}
		afs.setRecord(new Date());
		return afs;
	}
*/
	public ProducedEntities invoke(ProductionRequest request) throws ProductionHandlerException
	{
		logger.error("invoke(org.openfuxml.producer.ejb.ProductionRequest request) noch nicht implementiert");
		return null;
	}
	
	public List<OfxFormat> getAvailableFormats(OfxApplication ofxA) throws ProductionSystemException
	{
		logger.warn("Not implemented for this Handler!!");
		return null;
	}
	
	public Productionresult produce(Sessionpreferences spref) throws ProductionSystemException
	{
		logger.warn("Not implemented for this Handler!!");
		return null;
	}
	
	public ProducibleEntities discoverEntities(Sessionpreferences spref) throws ProductionSystemException, ProductionHandlerException
	{
		logger.warn("Not implemented for this Handler!!");
		return null;
	}
	
	public void setLogWriter(Writer w){}
	
	@Remove
	public void close()
	{
		logger.info("close()");
	}
}