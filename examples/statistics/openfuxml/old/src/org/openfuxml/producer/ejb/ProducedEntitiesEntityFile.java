package org.openfuxml.producer.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.dom4j.Element;
import org.dom4j.DocumentHelper; 

import org.openfuxml.communication.cluster.ejb.EjbObject;

@Entity
public class ProducedEntitiesEntityFile implements Serializable,EjbObject
{
	static final long serialVersionUID=1;
	
	private int id;
	private ProducedEntities producibleEntities;
	private String directory,filename,description;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	@ManyToOne
	public ProducedEntities getProducibleEntities() {return producibleEntities;}
	public void setProducibleEntities(ProducedEntities producibleEntities) {this.producibleEntities = producibleEntities;}
	
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	public String getDirectory() {return directory;}
	public void setDirectory(String directory) {this.directory = directory;}
	
	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}
	
	public void init(Element elFile) 
	{
		directory=elFile.attributeValue("directory"); 
		filename=elFile.attributeValue("filename"); 
		Element elDescription = elFile.element("description"); 
		description=elDescription.getText(); 
	}
	
	public Element toXML()
	{
		Element elFile = DocumentHelper.createElement("file"); 
		elFile.addAttribute("directory",directory); 
		elFile.addAttribute("filename",filename); 
				
		Element elDescription = DocumentHelper.createElement("description"); 
		elDescription.add(description); 
		elFile.add(elDescription); 
		
		return elFile;
	}	
}
