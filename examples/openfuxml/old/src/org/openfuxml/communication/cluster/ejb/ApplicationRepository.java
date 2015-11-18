package org.openfuxml.communication.cluster.ejb;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class ApplicationRepository implements Serializable,EjbObject
{ 
	static final long serialVersionUID=1;
		
	private int id;
	private int version;
	private String name,application;
	private Date record;
	private long crc32;
	private byte[] b;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
		
	@Lob @Column(columnDefinition="LONGBLOB")
	public byte[] getB() {return b;}
	public void setB(byte[] b) {this.b = b;}
	
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
	
	public long getCrc32() {return crc32;}
	public void setCrc32(long crc32) {this.crc32 = crc32;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public int getVersion() {return version;}
	public void setVersion(int version) {this.version = version;}
	
	public String getApplication() {return application;}
	public void setApplication(String application) {this.application = application;}
}
