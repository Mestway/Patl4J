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
import de.kisner.util.xml.XmlElementNotFoundException;
import de.kisner.util.xml.XmlObject;

@Entity
public class ProductionRequest implements Serializable,EjbObject
{
	final static Logger logger = LoggerFactory.getLogger(ProductionRequest.class);
	
	static final long serialVersionUID=2;
	public static enum Typ {PRODUCE,ENTITIES}
	public static enum Sync {NOSYNC,UNISON}
	
	private final static String rootElementName="sessionpreferences";
	
	private int id;
	private Typ typ;
	private Sync sync;
	private Date record;
	private String application,project,format,document,username;
	private Collection<ProductionRequestEntityFile> entityFiles;
	private Collection<FormatOption> formatOptions;
	private Collection<SyncLocation> syncLocations;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="productionRequest")
	public Collection<ProductionRequestEntityFile> getEntityFiles() {return entityFiles;}
	public void setEntityFiles(Collection<ProductionRequestEntityFile> entityFiles) {this.entityFiles = entityFiles;}
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="productionRequest")
	public Collection<SyncLocation> getSyncLocations() {return syncLocations;}
	public void setSyncLocations(Collection<SyncLocation> syncLocations) {this.syncLocations = syncLocations;}	
	
	public Collection<FormatOption> getFormatOptions() {return formatOptions;}
	public void setFormatOptions(Collection<FormatOption> formatOptions) {this.formatOptions = formatOptions;}
	
	public String getApplication() {return application;}
	public void setApplication(String application) {this.application = application;}
	
	public String getDocument() {return document;}
	public void setDocument(String document) {this.document = document;}
	
	public String getProject() {return project;}
	public void setProject(String project) {this.project = project;}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	
	public String getFormat() {return format;}
	public void setFormat(String format) {this.format = format;}

	public Typ getTyp() {return typ;}
	public void setTyp(Typ typ) {this.typ = typ;}
	
	public Sync getSync() {return sync;}
	public void setSync(Sync sync) {this.sync = sync;}
	
	public void loadXML(File xmlFile) throws XmlElementNotFoundException
	{
		XmlObject xO = new XmlObject();
		xO.load(xmlFile);
		
		setApplication(xO.getText("application"));
		setProject(xO.getText("project"));
		setDocument(xO.getText("document"));
		setFormat(xO.getText("format"));
		setUsername(xO.getText("username"));
			
		switch (typ)
		{
			case ENTITIES: logger.warn("Das Laden eines ENTITIES-ProductionRequests ist nicht getestet!!!!");break;
		}
			
		entityFiles = new ArrayList<ProductionRequestEntityFile>();
		for(Element elFile : xO.getElements("productionentities/file")) 
		{
			ProductionRequestEntityFile pef = new ProductionRequestEntityFile();
			pef.init(elFile);
			pef.setProductionRequest(this);
			entityFiles.add(pef);
		}
		
		ArrayList<Element> alOptions = new ArrayList<Element>(); 
		try {alOptions = (ArrayList<Element>)xO.getElements("options/option");} 
		catch (XmlElementNotFoundException e) {}
		for(Element elFormatOption : alOptions) 
		{
			FormatOption fo = new FormatOption();
			fo.init(elFormatOption);
			addFormatOption(fo);
		}
	}
	
	public Document toXmlDoc() 
	{
		Document xmlDoc = DocumentHelper.createDocument(toXmlElement()); 
		return xmlDoc;
	}
	
	public String toDocumentName()
	{
		int suffixindex = document.indexOf(".xml");
		return document.substring(0,suffixindex);
	}
	
	public Element toXmlElement() 
	{
		Element elSessionpreferences = DocumentHelper.createElement(rootElementName); 
		
		Element elApplication = DocumentHelper.createElement("application"); 
		Element elProject = DocumentHelper.createElement("project"); 
		Element elDocument = DocumentHelper.createElement("document"); 
		Element elUsername = DocumentHelper.createElement("username"); 
		Element elFormat = DocumentHelper.createElement("format"); 
		
		elApplication.setText(application); 
		elProject.setText(project); 
		elDocument.setText(document); 
		elUsername.setText(username); 
		elFormat.setText(format); 
		
		elSessionpreferences.add(elApplication); 
		elSessionpreferences.add(elProject); 
		elSessionpreferences.add(elDocument); 
		elSessionpreferences.add(elUsername); 
		elSessionpreferences.add(elFormat); 
		
		if(sync!=Sync.NOSYNC && syncLocations!=null && syncLocations.size()>0)
		{
			Element elSync = DocumentHelper.createElement("sync"); 
			elSync.addAttribute("typ",sync.name()); 
			for(SyncLocation sl : syncLocations)
			{
				elSync.add(sl.toXML()); 
			}
			elSessionpreferences.add(elSync); 
		}
		
		switch (typ)
		{
			case PRODUCE:	if(entityFiles!=null && entityFiles.size()>0)
							{
								Element elProductionentities = DocumentHelper.createElement("productionentities"); 
								for(ProductionRequestEntityFile ef : entityFiles)
								{
									elProductionentities.add(ef.toXML()); 
								}
								elSessionpreferences.add(elProductionentities); 
							}
							if(formatOptions!=null && formatOptions.size()>0)
							{
								Element elFormatOptions = DocumentHelper.createElement("options"); 
								for(FormatOption fo : formatOptions)
								{
									elFormatOptions.add(fo.toXML()); 
								}
								elSessionpreferences.add(elFormatOptions); 
							}
							break;				
		}
		return elSessionpreferences;
	}
	
	public void addEntityFile(ProductionRequestEntityFile ef)
	{
		if(entityFiles==null){entityFiles = new ArrayList<ProductionRequestEntityFile>();}
		entityFiles.add(ef);
	}
	
	public void addFormatOption(FormatOption fo)
	{
		if(formatOptions==null){formatOptions = new ArrayList<FormatOption>();}
		formatOptions.add(fo);
	}
	
	public void addSyncLocation(String url, SyncLocation.Typ typ)
	{
		SyncLocation sl = new SyncLocation();
		sl.setProductionRequest(this);
		sl.setTyp(typ);
		sl.setUrl(url);
				
		if(syncLocations==null){syncLocations = new ArrayList<SyncLocation>();}
		syncLocations.add(sl);
	}
}
