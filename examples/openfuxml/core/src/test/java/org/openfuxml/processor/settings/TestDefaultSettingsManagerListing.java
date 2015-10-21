package org.openfuxml.processor.settings;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Listing;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDefaultSettingsManagerListing
{
	final static Logger logger = LoggerFactory.getLogger(TestDefaultSettingsManagerListing.class);
	
	private OfxDefaultSettingsManager dsm;
	private Listing listing,setting;
	
	@Before
	public void init()
	{	
		setting = new Listing();
		setting.setSetting("test");
		setting.setCodeLang("myX");
		setting.setNumbering(false);
		setting.setLinebreak(false);
		
		dsm = new OfxDefaultSettingsManager();
		dsm.addSetting(Listing.class.getName(),setting.getSetting(),setting);
		listing = new Listing();
	}
	
	@Test(expected=OfxConfigurationException.class)
    public void withoutSetting() throws OfxConfigurationException 
    {
    	listing.setSetting("na");
    	dsm.apply(listing);
    }
	
	@Test
    public void codeLang() throws OfxConfigurationException 
    {
    	Assert.assertFalse(listing.isSetCodeLang());
    	dsm.apply(listing);
    	Assert.assertTrue(listing.isSetCodeLang());
    	Assert.assertEquals("XML", listing.getCodeLang());
    }
	
	@Test
    public void settingCodeLang() throws OfxConfigurationException 
    {
    	listing.setSetting(setting.getSetting());
    	dsm.apply(listing);
    	Assert.assertEquals(setting.getCodeLang(), listing.getCodeLang());
    }
}