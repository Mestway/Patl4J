package rulesEngine.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadProperties {
	
	public static String getProperty(String propertiesPath, String key){
		String[] keys = {key};
		Map<String, String> responses = getProperty(propertiesPath, keys);
		return responses.get(key);
	}
	
	public static Map<String, String> getProperty(String propertiesPath, String[] keys){
		if(keys.length == 0)
			throw new IllegalArgumentException("At least a key must be entered");
		Properties prop = new Properties();
		FileInputStream in;
		try {
			in = new FileInputStream(propertiesPath);
			prop.load(in);
			in.close();
		} catch (FileNotFoundException e) {		
			throw new IllegalArgumentException("File not found", e);
		} catch (IOException e) {			
			throw new IllegalArgumentException("IO problem", e);
		}
		
		Map<String, String> responses = new HashMap<String, String>();
		for(int i = 0; i < keys.length; i++){
			// Extraction des propriétés			
			responses.put(keys[i], prop.getProperty(keys[i]));	
		}
		
		return responses;
	}
}
