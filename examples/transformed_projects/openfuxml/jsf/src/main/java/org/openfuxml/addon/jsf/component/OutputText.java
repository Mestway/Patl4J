package org.openfuxml.addon.jsf.component;

import java.io.IOException;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesComponent("org.openfuxml.addon.jsf.component.OutputText")
@ResourceDependency(library="ofxCss", name="ofxBasic.css")
public class OutputText extends UIPanel
{	
	final static Logger logger = LoggerFactory.getLogger(OutputText.class);
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException
	{			
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.startElement("span", this);
		
		StringBuffer sb = new StringBuffer();
		
		logger.warn("Attributes corrently disables");
		boolean bold = false;//ComponentAttribute.getBoolean("bold", false, context, this);
		boolean italic = false;//ComponentAttribute.getBoolean("italic", false, context, this);
		
//		logger.info("******************");
//		logger.info("bold: "+bold);
//		logger.info("italic "+italic);
		
		if(bold){sb.append(" ofxBold");}
		if(italic){sb.append(" ofxItalic");}
		
		responseWriter.writeAttribute("class",sb.toString(),null);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException
	{
		ResponseWriter responseWriter = context.getResponseWriter();
		responseWriter.endElement("div");
	}
}