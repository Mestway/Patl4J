package org.openfuxml.exception;

import java.io.Serializable;

public class OfxImplementationException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public OfxImplementationException() 
	{ 
	} 
 
	public OfxImplementationException(String s) 
	{ 
		super(s); 
	} 
}
