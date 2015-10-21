package org.openfuxml.producer.ejb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.openfuxml.communication.cluster.ejb.EjbObject;

@Entity
public class RequestWrapper implements Serializable,EjbObject
{
	static final long serialVersionUID=1;
	public static enum ProductionType { AVAPPS, AVFORMATS, INVOKE};
    public static enum ResultType {OK,EXCEPTION};
	
	private int id;
	private ProductionType productionType;
	private ResultType resultType;
	private Exception exception;
	private Object object;
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {return id;}
	public void setId(int id){this.id = id;}
	
	@Lob @Column(columnDefinition="LONGBLOB") 
	public Object getObject() {return object;}
	public void setObject(Object object) {this.object = object;}
	
	@Lob @Column(columnDefinition="LONGBLOB") 
	public Exception getException() {return exception;}
	public void setException(Exception exception) {this.exception = exception;}
	
	public ProductionType getProductionType() {return productionType;}
	public void setProductionType(ProductionType typ) {this.productionType = typ;}
		
	public ResultType getResultType() {return resultType;}
	public void setResultType(ResultType resultType) {this.resultType = resultType;}
	
	
}
