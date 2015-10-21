package org.openfuxml.model.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OfxDocument implements Serializable
{
	public static final long serialVersionUID=1;
	
	private int id;
	private String name, file;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getFile() {return file;}
	public void setFile(String file) {this.file = file;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" "+name);
			sb.append(" ("+file+")");
		return sb.toString();
	}
}
