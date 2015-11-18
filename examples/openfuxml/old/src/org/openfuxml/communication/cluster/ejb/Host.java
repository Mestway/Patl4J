package org.openfuxml.communication.cluster.ejb;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Host implements java.io.Serializable
{ 
	static final long serialVersionUID=1;
	
	private int id;
	private Date record;
	private String hostName,hostIP;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	public String getHostName() {return hostName;}
	public void setHostName(String hostName) {this.hostName = hostName;}

	public String getHostIP() {return hostIP;}
	public void setHostIP(String hostIP) {this.hostIP = hostIP;}

	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
}
