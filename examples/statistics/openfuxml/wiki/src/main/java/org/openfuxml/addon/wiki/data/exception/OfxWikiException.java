package org.openfuxml.addon.wiki.data.exception;

import java.io.Serializable;

public class OfxWikiException extends Exception implements Serializable
{
	private static final long serialVersionUID = 1;

	public OfxWikiException() 
	{ 
	} 
 
	public OfxWikiException(String s) 
	{ 
		super(s); 
	} 
}
