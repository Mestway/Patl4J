package org.openfuxml.addon.wiki.event;

import net.sf.exlp.core.event.AbstractEvent;
import net.sf.exlp.interfaces.LogEvent;

import org.openfuxml.addon.wiki.data.jaxb.Ofxgallery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiImageEvent extends AbstractEvent implements LogEvent
{
	final static Logger logger = LoggerFactory.getLogger(WikiImageEvent.class);
	static final long serialVersionUID=1;
	
	private Ofxgallery.Ofximage ofxImage;

	public WikiImageEvent(Ofxgallery.Ofximage ofxImage)
	{
		this.ofxImage=ofxImage;
	}
	
	public void debug()
	{
		super.debug();
		logger.debug("** Link\t"+ofxImage.getWikilink());
		logger.debug("** Desc\t"+ofxImage.getValue());
	}
	
	public Ofxgallery.Ofximage getOfxImage() {return ofxImage;}
	public void setOfxImage(Ofxgallery.Ofximage ofxImage) {this.ofxImage = ofxImage;}
}