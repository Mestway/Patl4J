package org.openfuxml.model.factory;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.exlp.io.LoggerInit;

import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Productionresult;

public class OfxProductionResultFactory extends AbstractJaxbFactory
{
	public OfxProductionResultFactory()
	{
		
	}
	
	public ProducibleEntities getProducibleEntities(File fResult)
	{
		ProducibleEntities result=null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(ProducibleEntities.class);
			Unmarshaller u = jc.createUnmarshaller();
			result = (ProducibleEntities)u.unmarshal(fResult);
		}
		catch (JAXBException e) {logger.error("",e);}
		return result;
	}
	
	public Productionresult getProductionResult(File fResult)
	{
		Productionresult result=null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Productionresult.class);
			Unmarshaller u = jc.createUnmarshaller();
			result = (Productionresult)u.unmarshal(fResult);
		}
		catch (JAXBException e) {logger.error("",e);}
		return result;
	}
	
	public static void main(String args[]) 
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		OfxProductionResultFactory orf = new OfxProductionResultFactory();
		File f = new File("/Users/thorsten/Documents/workspace/3.4.1/openFuXML/dist/output/fuxml/helloworld/latexpdf/HelloWorld/result.xml");
		orf.getProductionResult(f);
	}
}
