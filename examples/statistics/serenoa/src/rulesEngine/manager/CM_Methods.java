package rulesEngine.manager;

import java.io.IOException;

import rulesEngine.utils.FileUtils;

public class CM_Methods {
	
	

	public static void AddUser(String contextFile, String contextUserXML) throws IOException{
		String file = FileUtils.getFileAsString(contextFile);
		file = file.replaceFirst("(<\\s*/\\s*users\\s*>)", contextUserXML + "$1");
		FileUtils.setFileFromString(contextFile, file);
	}
	
	public static void RemoveUser(String contextFile, String userId) throws IOException{
		String file = FileUtils.getFileAsString(contextFile);
		file = file.replaceFirst("<\\s*user\\s*id\\s*=\\s*[\"']" + userId + "[\"']\\s*>[\\s\\S]*?<\\s*/\\s*user\\s*>", "");
		FileUtils.setFileFromString(contextFile, file);
	}
	
	public static void UpdateUser(String contextFile, String contextUserXML, String userId) throws IOException{
		RemoveUser(contextFile, userId); 
		AddUser(contextFile, contextUserXML);
	}
			
	public static void main(String[] args){
		try {
			AddUser("C:\\Users\\mducass\\workspace\\bicycleshop\\WebContent\\w4serenoa\\temp\\context.xml", "cool");
			RemoveUser("C:\\Users\\mducass\\workspace\\bicycleshop\\WebContent\\w4serenoa\\temp\\context.xml", "0");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
