package org.openfuxml.processor.settings;

import org.openfuxml.content.ofx.Listing;
import org.openfuxml.interfaces.DefaultSettingsManager;

public class OfxDefaultSettingsManager extends AbstractDefaultSettingsManager implements DefaultSettingsManager
{
	public OfxDefaultSettingsManager()
	{
		initListing();
	}
	
	private void initListing()
	{
		Listing xml = new Listing();
		xml.setCodeLang("XML");
		xml.setNumbering(false);
		xml.setLinebreak(false);
		mapDefaults.put(Listing.class.getName(), xml);
	}

}
