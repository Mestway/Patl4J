package org.openfuxml.addon.wiki.processor.template.exlp.event;

import net.sf.exlp.core.event.AbstractEvent;
import net.sf.exlp.interfaces.LogEvent;

import org.openfuxml.addon.wiki.data.jaxb.TemplateKv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiKeyValueEvent extends AbstractEvent implements LogEvent
{
	final static Logger logger = LoggerFactory.getLogger(WikiKeyValueEvent.class);
	static final long serialVersionUID=1;
	
	private TemplateKv kv;

	public WikiKeyValueEvent(TemplateKv kv)
	{
		this.kv=kv;
	}
	
	public void debug()
	{
		super.debug();
		logger.debug("Key: "+kv.getKey());
		logger.debug("Value: "+kv.getMarkup().getValue());
	}
	
	public TemplateKv getKv() {return kv;}
}