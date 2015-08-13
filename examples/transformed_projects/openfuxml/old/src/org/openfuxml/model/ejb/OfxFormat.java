package org.openfuxml.model.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.openfuxml.model.jaxb.Format;

@Entity
public class OfxFormat implements Serializable
{
public static final long serialVersionUID=1;
	
	private int id;
	private Format format;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public Format getFormat() {return format;}
	public void setFormat(Format format) {this.format = format;}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(" "+format.getOutputformat());
		return sb.toString();
	}
}
