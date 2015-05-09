package org.openfuxml.producer.ejb;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;

import org.openfuxml.communication.cluster.ejb.EjbObject;
import org.openfuxml.producer.ejb.ProductionRequest.Typ;

import de.kisner.util.xml.XmlElementNotFoundException;
import de.kisner.util.xml.XmlObject;

@Entity
public class ProducedEntities implements Serializable,EjbObject
{
	final static Logger logger = LoggerFactory.getLogger(ProducedEntities.class);
	
	static final long serialVersionUID=1;
	
	private int id;
	private Typ typ;
	private Date record;
	private String logfile;
	private Collection<ProducedEntitiesEntityFile> entityFiles;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="producibleEntities")
	public Collection<ProducedEntitiesEntityFile> getEntityFiles() {return entityFiles;}
	public void setEntityFiles(Collection<ProducedEntitiesEntityFile> entityFiles) {this.entityFiles = entityFiles;}
	
	public Typ getTyp() {return typ;}
	public void setTyp(Typ typ) {this.typ = typ;}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
		
	public String getLogfile() {return logfile;}
	public void setLogfile(String logfile) {this.logfile = logfile;}
	
	public void loadXML(File xmlFile) throws XmlElementNotFoundException
	{
		XmlObject xO = new XmlObject();
		xO.load(xmlFile,"UTF-8");
		logfile=xO.getAttribute("", "logfile");
		
		String xPathFile=null;
		switch (typ)
		{
			case ENTITIES: xPathFile="file";break;
			case PRODUCE:  xPathFile="producedentities/file";break;
		}
		entityFiles = new ArrayList<ProducedEntitiesEntityFile>();
		for(Element elFile : xO.getElements(xPathFile)) 
		{
			ProducedEntitiesEntityFile pef = new ProducedEntitiesEntityFile();
			pef.init(elFile);
			pef.setProducibleEntities(this);
			entityFiles.add(pef);
		}
	}
	
	public Document toXmlDoc() 
	{
		//Document xmlDoc = new Document(toXmlElement());
        Document xmlDoc = DocumentHelper.createDocument(toXmlElement());  
		return xmlDoc;
	}
	
	public Element toXmlElement() 
	{
		Element elRoot = DocumentHelper.createElement(obtainRootElementName()); 
		elRoot.addAttribute("logfile", logfile); 
		if(entityFiles!=null && entityFiles.size()>0)
		{
			switch (typ)
			{
				case ENTITIES:	for(ProducedEntitiesEntityFile pef : entityFiles){elRoot.add(pef.toXML());};break; 
				case PRODUCE:	Element elPe = DocumentHelper.createElement("producedentities");  
								for(ProducedEntitiesEntityFile pef : entityFiles){elPe.add(pef.toXML());} 
								elRoot.add(elPe);break; 
			}
		}
		return elRoot;
	}
	
	private String obtainRootElementName()
	{
		String rootElementName=null;
		switch (typ)
		{
			case ENTITIES:	rootElementName="producibleEntities";break;
			case PRODUCE:	rootElementName="productionresult";break;
		}
		return rootElementName;
	}
}
