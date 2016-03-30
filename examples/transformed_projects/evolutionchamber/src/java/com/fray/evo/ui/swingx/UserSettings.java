package com.fray.evo.ui.swingx;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;

import org.eclipse.swt.graphics.Point;

import com.fray.evo.EvolutionChamber;

public class UserSettings {
	private static final Logger logger = Logger.getLogger(UserSettings.class.getName());


	private Properties properties;


	private File file;


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
			setPropertyAndSave("version", EvolutionChamber.VERSION);
		}
	}


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


	public void setLocale(Locale locale) {
		String localeStr = locale.getLanguage();
		if (locale.getCountry() != null && !locale.getCountry().isEmpty()){
			localeStr += "_" + locale.getCountry();
		}
		setPropertyAndSave("locale", localeStr);
	}
	
	
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
	
	public Integer getWindowExtensionState(){
		return getAsInteger("gui.windowExtendedState");
	}

	public Point getWindowSize(){
		Integer height =  getAsInteger("gui.windowHeight");
		Integer width =  getAsInteger("gui.windowWidth");
		
		if( height != null && width != null){
			return new Point(width.intValue(), height.intValue());
		}
		return null;

	}
	

	public void setWindowSize(Point point){
		if(point != null){
			properties.setProperty("gui.windowHeight", Integer.toString(point.y));
			properties.setProperty("gui.windowWidth", Integer.toString(point.x));
			save();
		}
	}
	

	public void setWindowExtensionState(int state){
		setPropertyAndSave("gui.windowExtendedState", Integer.toString(state));
	}
	
	public String getVersion() {
		return properties.getProperty("version");
	}

	private void setPropertyAndSave(String key, String value){
		properties.setProperty(key, value);
		save();
	}

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
