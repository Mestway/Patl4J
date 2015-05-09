package org.openfuxml.producer.exception;

public class ProductionHandlerException extends Exception
{
	static final long serialVersionUID=1;
	
	public ProductionHandlerException(String message)
	{
		super(message);
	}
	
	public ProductionHandlerException(Throwable cause)
	{
		super(cause);
	}
	
	public ProductionHandlerException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
}
