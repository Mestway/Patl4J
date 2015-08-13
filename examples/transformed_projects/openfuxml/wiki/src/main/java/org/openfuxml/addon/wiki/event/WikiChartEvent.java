package org.openfuxml.addon.wiki.event;

import net.sf.exlp.core.event.AbstractEvent;
import net.sf.exlp.interfaces.LogEvent;

import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiChartEvent extends AbstractEvent implements LogEvent
{
	final static Logger logger = LoggerFactory.getLogger(WikiChartEvent.class);
	static final long serialVersionUID=1;
	
	private Ofxchart ofxChart;
	
	public WikiChartEvent(Ofxchart ofxChart)
	{
		this.ofxChart=ofxChart;
	}
	
	public void debug()
	{
		super.debug();
	}
	
	public Ofxchart getOfxChart() {return ofxChart;}
	public void setOfxChart(Ofxchart ofxChart) {this.ofxChart = ofxChart;}

}