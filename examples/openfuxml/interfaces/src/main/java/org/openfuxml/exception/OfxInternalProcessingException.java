package org.openfuxml.exception;

import java.io.Serializable;

public class OfxInternalProcessingException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public OfxInternalProcessingException() 
	{ 
	} 
 
	public OfxInternalProcessingException(String s) 
	{ 
		super(s); 
	} 
}
