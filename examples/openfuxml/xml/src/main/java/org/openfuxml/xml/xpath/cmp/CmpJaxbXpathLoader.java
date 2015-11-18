package org.openfuxml.xml.xpath.cmp;

import java.util.List;
import java.util.NoSuchElementException;

import org.openfuxml.xml.renderer.cmp.Merge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmpJaxbXpathLoader
{
	final static Logger logger = LoggerFactory.getLogger(CmpJaxbXpathLoader.class);
	
	public static synchronized Merge getMerge(List<Merge> list, String code)
	{
		for(Merge merge : list)
		{
			if(merge.getCode().equals(code)){return merge;}
		}
		throw new NoSuchElementException(" <merge code="+code+"/> not found");
	}
}