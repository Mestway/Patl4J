package org.openfuxml.addon.wiki.emitter.injection;

import java.io.FileNotFoundException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.sf.exlp.util.xml.JaxbUtil;

import org.openfuxml.addon.wiki.data.jaxb.Ofxgallery.Ofximage;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.processor.ofx.EmitterFactory;
import org.openfuxml.addon.wiki.processor.ofx.emitter.NestingEmitter;
import org.openfuxml.addon.wiki.util.JdomXmlStreamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;

public class OfxInjectionEmitter extends NestingEmitter
{
	final static Logger logger = LoggerFactory.getLogger(OfxInjectionEmitter.class);
	
	private String injectionDir;
	
	public OfxInjectionEmitter(EmitterFactory ef, String injectionDir)
	{
		super(ef);
		this.injectionDir=injectionDir;
	}

	@Override
	protected boolean localStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException
	{
		Wikiinjection injection = new Wikiinjection();
		injection.setFormat(atts.getValue("format"));
		injection.setId(atts.getValue("id"));
		injection.setOfxtag(atts.getValue("ofxtag"));
		injection.setWikitag(atts.getValue("wikitag"));
		
		JdomXmlStreamer jdomStreamer = new JdomXmlStreamer(writer);
		if(injection.getOfxtag().equals("ofxchart"))
		{
			OfxChartEmitter chartEmitter = new OfxChartEmitter(injection);
			chartEmitter.transform(jdomStreamer);
		}
		if(injection.getOfxtag().equals("ofxgallery"))
		{
			String injectionName = injection.getId()+"-"+injection.getOfxtag();
			String xmlFile = injectionDir+"/"+injectionName+".xml";
			try
			{
				injection = (Wikiinjection)JaxbUtil.loadJAXB(xmlFile, Wikiinjection.class);
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int i=0;
			for(Ofximage image : injection.getOfxgallery().get(0).getOfximage())
			{
				i++;
				OfxImageEmitter imageEmitter = new OfxImageEmitter(image,injectionName+"-"+i);
				imageEmitter.transform(jdomStreamer);
			}
		}
		else{logger.warn("Unkown ofxtag: "+injection.getOfxtag());}
		return true;
	}
}