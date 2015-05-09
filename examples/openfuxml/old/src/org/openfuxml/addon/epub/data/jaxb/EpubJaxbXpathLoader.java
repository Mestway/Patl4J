package org.openfuxml.addon.epub.data.jaxb;

import java.util.NoSuchElementException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;

import com.aht.util.data.jaxb.AhtNsPrefixMapper;

public class EpubJaxbXpathLoader
{
	final static Logger logger = LoggerFactory.getLogger(EpubJaxbXpathLoader.class);
	
	static AhtNsPrefixMapper prefixMapper = new AhtNsPrefixMapper();
	
	public static synchronized Title getTitle(Section section)
	{
		for(Object s : section.getContent())
		{
			if(s instanceof Title){return (Title)s;}
		}
		throw new NoSuchElementException();
	}
}