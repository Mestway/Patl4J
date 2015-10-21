package org.openfuxml.client.control;

import java.io.File;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.client.control.documents.DocumentFactory;
import org.openfuxml.client.control.documents.DocumentFactoryDirect;
import org.openfuxml.client.control.projects.ProjectFactory;
import org.openfuxml.client.control.projects.ProjectFactoryDirect;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.factory.OfxRequestFactory;
import org.openfuxml.model.jaxb.Format.Options.Option;
import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Productionresult;
import org.openfuxml.model.jaxb.Sessionpreferences;
import org.openfuxml.producer.Producer;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.producer.handler.DirectProducer;
import org.openfuxml.producer.handler.SocketProducer;
import org.openfuxml.server.DummyServer;
import org.openfuxml.util.config.OfxPathHelper;

import de.kisner.util.architecture.ArchOpen;

public class OfxClientControl implements OfxGuiAction
{
	final static Logger logger = LoggerFactory.getLogger(OfxClientControl.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	private Configuration config;
	private ProjectFactory ofxProjectFactory;
	private DocumentFactory ofxDocumentFactory;
	private Producer producer;
	private ClientGuiCallback guiCallback;

	private String comboUid;
	
	private OfxProject selectedOfxP;
	private OfxApplication selectedOfxA;
	private OfxDocument selectedOfxD;
	private OfxFormat selectedOfxF;
	private ProducibleEntities selectedPe;
	private Hashtable<String,Hashtable<String,Option>> htSelectedOptions;
	
	private Hashtable<String, ProducibleEntities> htDiscoveredEntities;

	public OfxClientControl(Configuration config, ClientGuiCallback guiCallback)
	{
		this.config=config;
		this.guiCallback=guiCallback;
		ofxProjectFactory = new ProjectFactoryDirect(config);
		ofxDocumentFactory = new DocumentFactoryDirect(config);
		htDiscoveredEntities = new Hashtable<String, ProducibleEntities>();
		htSelectedOptions = new Hashtable<String,Hashtable<String,Option>>();
		
		initProducer();
		
		//DummyServer server = new DummyServer(config);
		//producer = new DirectProducer(config,server.getEnvParameter());
		try {producer.getAvailableApplications();}
		catch (ProductionSystemException e) {logger.error("",e);}
		catch (ProductionHandlerException e) {logger.error("",e);}
	}
	
	public void initProducer()
	{	
		if(config.getString("server").equals("direct"))
		{
			logger.info("Using "+DirectProducer.class.getSimpleName());
			DummyServer server = new DummyServer(config);
			producer = new DirectProducer(config,server.getEnvParameter());
		}
		else
		{
			try
			{
				logger.info("Using "+SocketProducer.class.getSimpleName());
				producer = new SocketProducer(config);
				logger.info("[OK] ProducerThread");
			}
			catch (Exception e)	{logger.fatal("Exception", e);}
		}
	}
	
	public ProducibleEntities getCachedProducibleEntities()
	{
		setComboUid(selectedOfxA, selectedOfxP, selectedOfxD, selectedOfxF);
		if(htDiscoveredEntities==null){logger.debug("ht==null");}
		if(!htDiscoveredEntities.containsKey(comboUid)){return null;}
		return htDiscoveredEntities.get(comboUid);
	}
	
	public List<OfxFormat> getAvailableFormats()
	{
		List<OfxFormat> lOfxF=null;
		try
		{
			lOfxF = producer.getAvailableFormats(selectedOfxA);
		}
		catch (ProductionSystemException e) {logger.error("",e);}
		catch (ProductionHandlerException e) {logger.error("",e);}
		return lOfxF;
	}
	
	public void getProducibleEntities() throws IllegalArgumentException
	{
		if(selectedOfxA==null){throw new IllegalArgumentException("You have to chose a Application");}
		if(selectedOfxP==null){throw new IllegalArgumentException("You have to chose a Project");}
		if(selectedOfxD==null){throw new IllegalArgumentException("You have to chose a Document");}
		if(selectedOfxF==null){throw new IllegalArgumentException("You have to chose a Format");}

		setComboUid(selectedOfxA, selectedOfxP, selectedOfxD, selectedOfxF);
		OfxRequestFactory ofxReqF = new OfxRequestFactory();
			ofxReqF.setOfxA(selectedOfxA);
			ofxReqF.setOfxP(selectedOfxP);
			ofxReqF.setOfxD(selectedOfxD);
			ofxReqF.setOfxF(selectedOfxF);
		Sessionpreferences spref = ofxReqF.create();
		ProducerThread pt = new ProducerThread(this,guiCallback,producer,config);
		pt.getProducibleEntities(spref);
	}
	
	public void produce()
	{
		if(selectedOfxA==null){throw new IllegalArgumentException("You have to chose a Application");}
		if(selectedOfxP==null){throw new IllegalArgumentException("You have to chose a Project");}
		if(selectedOfxD==null){throw new IllegalArgumentException("You have to chose a Document");}
		if(selectedOfxF==null){throw new IllegalArgumentException("You have to chose a Format");}
		if(selectedPe==null || selectedPe.getFile()==null || selectedPe.getFile().size()<1){throw new IllegalArgumentException("You have to chose som entities");}
		
		OfxRequestFactory orf = new OfxRequestFactory();
			orf.setOfxA(selectedOfxA);
			orf.setOfxP(selectedOfxP);
			orf.setOfxD(selectedOfxD);
			orf.setOfxF(selectedOfxF);
			orf.setProducibleEntities(selectedPe);
			if(htSelectedOptions.containsKey(selectedOfxF.getFormat().getId()))
			{
				orf.setOptions(htSelectedOptions.get(selectedOfxF.getFormat().getId()).values());
			}
		Sessionpreferences spref = orf.create();
		ProducerThread pt = new ProducerThread(this,guiCallback,producer,config);
		pt.produce(spref);
	}
	
	public ProjectFactory getOfxProjectFactory() {return ofxProjectFactory;}
	
	public List<OfxDocument> lDocuments() throws IllegalArgumentException
	{
		if(selectedOfxA==null){throw new IllegalArgumentException("You have to chose a Application");}
		if(selectedOfxP==null){throw new IllegalArgumentException("You have to chose a Project");}
		List<OfxDocument> lOfxD = ofxDocumentFactory.lDocuments(selectedOfxA, selectedOfxP);
		return lOfxD;
	}
	
	public Producer getProducer() {return producer;}
	
	public void setDiscoveredEntities(ProducibleEntities pe)
	{
		htDiscoveredEntities.put(comboUid, pe);
	}
	
	public void setProducedEntities(Productionresult rResult)
	{
		
	}
	
	private void setComboUid(OfxApplication ofxA, OfxProject ofxP, OfxDocument ofxD, OfxFormat ofxF)
	{
		if(ofxA!=null && ofxP!=null && ofxD!=null && ofxF !=null)
		{
			StringBuffer sb = new StringBuffer();
				sb.append(ofxA.getName()+"-");
				sb.append(ofxP.getName()+"-");
				sb.append(ofxD.getName()+"-");
				sb.append(ofxF.getFormat().getId());
			comboUid = sb.toString();
		}
		else {comboUid = "";}
	}
	
	public void setGuiCallback(ClientGuiCallback guiCallback) {this.guiCallback = guiCallback;}
	
	public void cboApplicationSelected(OfxApplication selectedOfxA)
	{
		this.selectedOfxA=selectedOfxA;
		guiCallback.cboApplicationSelected();
	}
	
	public void cboProjectSelected(OfxProject selectedOfxP)
	{
		this.selectedOfxP=selectedOfxP;
		guiCallback.cboProjectSelected();
	}
	
	public void cboDocumentSelected(OfxDocument selectedOfxD)
	{
		this.selectedOfxD=selectedOfxD;
		guiCallback.entitiesDiscovered();
		guiCallback.loescheErgebnis();
	}
	
	public void cboFormateSelected(OfxFormat selectedOfxF)
	{
		this.selectedOfxF=selectedOfxF;
		guiCallback.entitiesDiscovered();
		guiCallback.loescheErgebnis();
		guiCallback.cboFormatSelected();
	}
	
	public void boxOptionsSelected(OfxFormat ofxF,Option o)
	{
		String formatId = ofxF.getFormat().getId();
		Hashtable<String,Option> htOption;
		if(htSelectedOptions.containsKey(formatId)){htOption = htSelectedOptions.get(formatId);}
		else{htOption = new Hashtable<String,Option>();}
		htOption.put(o.getName(), o);
		htSelectedOptions.put(formatId, htOption);
	}
	
	public void tblEntitiesSelected(ProducibleEntities pe)
	{
		logger.debug("Einträge: "+pe.getFile().size());
		this.selectedPe=pe;
	}
	
	public void btnUpdate()
	{
		guiCallback.loescheErgebnis();
		try{getProducibleEntities();}
		catch(IllegalArgumentException e){guiCallback.error(e.getMessage());}
	}
	
	public void btnProduce()
	{
		guiCallback.loescheErgebnis();
		try{produce();}
		catch(IllegalArgumentException e){guiCallback.error(e.getMessage());}
	}
	
	public void openDocument(ProducibleEntities.File f)
	{
		StringBuffer sb = new StringBuffer();
			sb.append(OfxPathHelper.getDir(config, "output"));
			sb.append(fs+selectedOfxA.getName());
			sb.append(fs+f.getDirectory());
			sb.append(f.getFilename());
		File openF = new File(sb.toString());
		if(openF.exists() && openF.isFile()){ArchOpen.open(openF.getAbsolutePath());}
		else{logger.warn("Trying to open, but does not exist: "+openF.getAbsolutePath());}
	}
}
