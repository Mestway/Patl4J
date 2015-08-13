package org.openfuxml.client.control;

import java.net.URL;


public interface ClientGuiCallback
{
	//Actions
	public void setStatus(String status);
	public void entitiesProduced();
	public void entitiesDiscovered();
	public void loescheErgebnis();
	public void openUrl(URL url);
	
	//Messages
	public void error(String s);
	public void addLogline(String s);
	public void clearLog();
	
	//Combo-Actions
	public void cboFormatSelected();
	public void cboApplicationSelected();
	public void cboProjectSelected();
	
	//Controls
	public void setProductionControlsEnabled(boolean isEnabled);
	
	
}
