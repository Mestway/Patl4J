package org.openfuxml.exception;

import java.io.Serializable;

public class OfxConfigurationException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public OfxConfigurationException() 
	{ 
	} 
 
	public OfxConfigurationException(String s) 
	{ 
		super(s); 
	} 
}
