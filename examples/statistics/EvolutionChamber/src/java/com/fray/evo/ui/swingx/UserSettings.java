package com.fray.evo.ui.swingx;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;

import com.fray.evo.EvolutionChamber;

/**
 * Stores and retrieves the user's personal settings.
 * 
 * @author mike.angstadt
 * 
 */
public class UserSettings {
	private static final Logger logger = Logger.getLogger(UserSettings.class.getName());

	/**
	 * The settings.
	 */
	private Properties properties;

	/**
	 * The properties file that the settings are stored in.
	 */
	private File file;

	/**
	 * Loads a user settings file at the specified location.
	 * @param file the location of the settings file
	 */
	public UserSettings(File file) {
		properties = new Properties();
		this.file = file;
		if (file.exists()) {
			try {
				FileInputStream inStream = new FileInputStream(file);
				properties.load(inStream);
				inStream.close();
			} catch (IOException e) {
				logger.severe("Cannot read user settings.");
			}
		}

		if (getVersion() == null) {
			//if the format of the file changes from version to version, then this property can be used to convert the file to the new format
			setPropertyAndSave("version", EvolutionChamber.VERSION);
		}
	}

	/**
	 * Gets the user's preferred Locale.
	 * 
	 * @return
	 */
	public Locale getLocale() {
		String localeStr = properties.getProperty("locale");
		if (localeStr != null) {
			int pos = localeStr.indexOf('_');
			if (pos >= 0) {
				return new Locale(localeStr.substring(0, pos), localeStr.substring(pos + 1));
			} else if (localeStr.length() > 0) {
				return new Locale(localeStr);
			}
		}
		return null;
	}

	/**
	 * Sets the user's preferred Locale.
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		String localeStr = locale.getLanguage();
		if (locale.getCountry() != null && !locale.getCountry().isEmpty()){
			localeStr += "_" + locale.getCountry();
		}
		setPropertyAndSave("locale", localeStr);
	}
	
	
	/**
	 * retrieves a property as integer
	 * 
	 * @param propertyName the property key
	 * @return the integer value or null if it is not set or could not be parsed
	 */
	private Integer getAsInteger(String propertyName){
		String stringVal = properties.getProperty(propertyName);
		try {
			Integer integerVal = Integer.valueOf(stringVal);
			return integerVal;
		} catch (NumberFormatException e) {
			logger.warning("The value of the UserSetting '" + propertyName + "' could not be parsed to a integer");
		}
		return null;
	}
	
	/**
	 * Gets the preferred window extension state (normal, maximized)
	 * @return
	 */
	public Integer getWindowExtensionState(){
		return getAsInteger("gui.windowExtendedState");
	}
	
	/**
	 * Gets the preferred window extension state (normal, maximized)
	 * @return
	 */
	public Dimension getWindowSize(){
		Integer height =  getAsInteger("gui.windowHeight");
		Integer width =  getAsInteger("gui.windowWidth");
		
		if( height != null && width != null){
			return new Dimension(width.intValue(), height.intValue());//Y
		}
		return null;

	}
	
	/**
	 * save the window dimensions
	 * @param dimension
	 */
	public void setWindowSize(Dimension dimension){
		if(dimension != null){
			properties.setProperty("gui.windowHeight", Integer.toString(dimension.height));
			properties.setProperty("gui.windowWidth", Integer.toString(dimension.width));
			save();
		}
	}
	
	/**
	 * save the preferred window state (normal,maximized)
	 * @param state
	 */
	public void setWindowExtensionState(int state){
		setPropertyAndSave("gui.windowExtendedState", Integer.toString(state));
	}
	

	/**
	 * Gets the version of the file.
	 * @return
	 */
	public String getVersion() {
		return properties.getProperty("version");
	}
	
	/**
	 * Sets a property and saves the file.
	 * @param key
	 * @param value
	 */
	private void setPropertyAndSave(String key, String value){
		properties.setProperty(key, value);
		save();
	}

	/**
	 * Writes the settings to disk.
	 */
	public void save() {
		try {
			FileOutputStream outStream = new FileOutputStream(file);
			properties.store(outStream, "EvolutionChamber user settings file");
			outStream.close();
		} catch (IOException e) {
			logger.severe("Cannot save user settings.");
		}
	}
}
