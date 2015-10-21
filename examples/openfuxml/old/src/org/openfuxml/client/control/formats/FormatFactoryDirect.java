package org.openfuxml.client.control.formats;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.jaxb.Format;

public class FormatFactoryDirect implements FormatFactory
{
	final static Logger logger = LoggerFactory.getLogger(FormatFactoryDirect.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	private Configuration config;
	
	public FormatFactoryDirect(Configuration config)
	{
		this.config=config;
	}
	
	public List<OfxFormat> getFormat(OfxApplication ofxA)
	{
		List<OfxFormat> lFormats = new ArrayList<OfxFormat>();
		File dirFormats = new File(config.getString("dirs/dir[@type='basedir']")+fs+"applications"+fs+ofxA.getName()+fs+"formats");
		logger.debug("Suche in "+dirFormats);
		if (dirFormats.exists() && dirFormats.isDirectory())
		{
			for(File dirEntry : dirFormats.listFiles())
			{
				File fFormat = new File(dirEntry.getAbsoluteFile() + File.separator + "format.xml");
				if (fFormat.exists())
				{
					Format format = createFormat(fFormat);
					OfxFormat ofxFormat = new OfxFormat();
					ofxFormat.setFormat(format);
					lFormats.add(ofxFormat);
				}
			}
		}
		return lFormats;
	}
	
	private Format createFormat(File fFormat)
	{
		Format format=null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Format.class);
			Unmarshaller u = jc.createUnmarshaller();
			format = (Format)u.unmarshal(fFormat);
			logger.debug(format.getOutputformat()+" "+format.getDescription());
		}
		catch (JAXBException e) {logger.error("",e);}
		return format;
	}
}
