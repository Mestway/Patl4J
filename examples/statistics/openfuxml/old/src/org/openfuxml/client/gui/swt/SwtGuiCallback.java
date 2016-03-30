package org.openfuxml.client.gui.swt;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.client.control.ClientGuiCallback;
import org.openfuxml.client.gui.swt.composites.BrowserComposite;
import org.openfuxml.client.gui.swt.composites.LogComposite;
import org.openfuxml.client.gui.swt.composites.ProducerComposite;

public class SwtGuiCallback implements ClientGuiCallback
{
	final static Logger logger = LoggerFactory.getLogger(SwtGuiCallback.class);
	 
	private ProducerComposite compP;
	private LogComposite compL;
	private BrowserComposite compBrowser;
	
	public SwtGuiCallback(){}
	public SwtGuiCallback(ProducerComposite compP, LogComposite compL, BrowserComposite compBrowser)
	{
		this.compP=compP;
		this.compL=compL;
		this.compBrowser=compBrowser;
	}
	
	public void error(String s){{logger.debug("DISPLAY "+s);}}
	public void addLogline(String s){compL.addLogline(s);}
	public void clearLog(){compL.clearLog();}
	
	public void cboFormatSelected(){compP.zeigeOptionen();}
	public void cboApplicationSelected(){}
	public void cboProjectSelected(){}
	
	public void openUrl(URL url){compBrowser.open(url);}
	public void entitiesProduced(){logger.debug("entitiesProduced");}
	public void setStatus(String status){compP.setStatus(status);}
	public void entitiesDiscovered(){compP.entitiesDiscovered();}
	public void setProductionControlsEnabled(boolean isEnabled){compP.setControlsEnabled(isEnabled);}
	public void loescheErgebnis(){logger.debug("loescheErgebnis");}
}
