package com.fray.evo.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;

public abstract class Utf8ResourceBundle {
	private static final Logger logger = Logger.getLogger(Utf8ResourceBundle.class.getName());

	public static final ResourceBundle getBundle(String baseName) {
		ResourceBundle bundle = ResourceBundle.getBundle(baseName);
		return createUtf8PropertyResourceBundle(bundle);
	}

	public static final ResourceBundle getBundle(String baseName, Locale locale) {
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		return createUtf8PropertyResourceBundle(bundle);
	}

	public static ResourceBundle getBundle(String baseName, Locale locale, ClassLoader loader) {
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);
		return createUtf8PropertyResourceBundle(bundle);
	}

	public static ResourceBundle getBundle(String baseName, Locale locale, ResourceBundle.Control control) {
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, control);
		return createUtf8PropertyResourceBundle(bundle);
	}

	private static ResourceBundle createUtf8PropertyResourceBundle(ResourceBundle bundle) {
		if (!(bundle instanceof PropertyResourceBundle))
			return bundle;
		
		String lang = bundle.getLocale().getLanguage();
		if ("fr".equals(lang) || "es".equals(lang))
			return bundle;

		return new Utf8PropertyResourceBundle((PropertyResourceBundle) bundle);
	}

	private static class Utf8PropertyResourceBundle extends ResourceBundle {
		PropertyResourceBundle bundle;

		private Utf8PropertyResourceBundle(PropertyResourceBundle bundle) {
			this.bundle = bundle;
			
		}

		public Enumeration<String> getKeys() {
			return bundle.getKeys();
		}

		protected Object handleGetObject(String key) {
			String value = (String) bundle.handleGetObject(key);
			try {
				return new String(value.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return null;
			} catch (NullPointerException n) {
				logger.severe("Externalize " + key);
				return key;
			}
		}
		
		@Override
		public Locale getLocale() {
		    return bundle.getLocale();
		}
		
		@Override
		public Set<String> keySet() {
		    return bundle.keySet();
		}
		
		@Override
		public boolean containsKey(String aKey) {
		    return bundle.containsKey(aKey);
		}
	}
}