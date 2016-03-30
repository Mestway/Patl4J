package com.fray.evo.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public final class EcMessages {
	private final String bundleName;
	private ResourceBundle resourceBundle;
	private Locale locale;
	
	private static EcMessages messages;
	
	public static void init(String bundleName){
		messages = new EcMessages(bundleName);
	}
	
	public static EcMessages getMessages(){
		return messages;
	}
	
	ResourceBundle.Control localeCandidateSelector = new ResourceBundle.Control() {
		@Override
		public List<Locale> getCandidateLocales(String baseName, Locale locale) {
			if (baseName == null)
				throw new NullPointerException();

			List<Locale> defaultCandidates =  super.getCandidateLocales(baseName, locale);
			
			
			List<Locale> enhancedCandidates = new ArrayList<Locale>();
			for (Locale defaultLocale : defaultCandidates) {
				if( Locale.ROOT.equals(defaultLocale)){
	            	enhancedCandidates.add(Locale.ENGLISH);
	            }
				
	            enhancedCandidates.add(defaultLocale);
            }
			return enhancedCandidates;
		}
	};


	public EcMessages(String bundleName) {
		this.bundleName = bundleName;
		
		Locale localeToLoad = Locale.getDefault();
		resourceBundle = Utf8ResourceBundle.getBundle(bundleName, localeToLoad, localeCandidateSelector);
		locale = resourceBundle.getLocale();
		if (locale == null){
			locale = Locale.getDefault();
		}
	}

	public String getString(String key) {
		return resourceBundle.getString(key);
	}

	public String getString(String key, Object... arguments) {
		return MessageFormat.format(getString(key), arguments);
	}
	
	public void changeLocale(Locale locale){
		this.locale = locale;
		resourceBundle = Utf8ResourceBundle.getBundle(bundleName, locale);
	}
	public Locale getLocale(){
		return locale;
	}
}
