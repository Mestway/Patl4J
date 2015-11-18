package org.openfuxml.exception;

import java.io.Serializable;

public class OfxAuthoringException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public OfxAuthoringException() 
	{ 
	} 
 
	public OfxAuthoringException(String s) 
	{ 
		super(s); 
	} 
}
