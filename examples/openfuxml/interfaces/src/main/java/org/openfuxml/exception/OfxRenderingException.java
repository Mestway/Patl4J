package org.openfuxml.exception;

import java.io.Serializable;

public class OfxRenderingException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public OfxRenderingException() 
	{ 
	} 
 
	public OfxRenderingException(String s) 
	{ 
		super(s); 
	} 
}
