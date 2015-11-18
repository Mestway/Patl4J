package org.openfuxml.interfaces;

import org.openfuxml.content.ofx.Listing;
import org.openfuxml.exception.OfxConfigurationException;

public interface DefaultSettingsManager
{
	public void apply(Listing listing) throws OfxConfigurationException;
}
