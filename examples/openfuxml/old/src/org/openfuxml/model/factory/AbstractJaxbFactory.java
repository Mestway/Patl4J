package org.openfuxml.model.factory;

import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.client.control.OfxClientControl;

public class AbstractJaxbFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxClientControl.class);
	
	public void writeJaxb(OutputStream os, Object o)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(o.getClass());
			Marshaller m = context.createMarshaller(); 
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE ); 
			m.marshal( o , os);
			os.close();
		}
		catch (JAXBException e) {logger.error("",e);}
		catch (IOException e) {logger.error("",e);}
	}
}
