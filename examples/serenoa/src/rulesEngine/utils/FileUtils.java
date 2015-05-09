package rulesEngine.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
	
	public static String getFileAsString(String filePath) throws IOException{
		
		InputStream ips=new FileInputStream(filePath); 
		
		return getInputStreamAsString(ips);
	}
	
	public static String getInputStreamAsString(InputStream ips) throws IOException{
		String content="";
		InputStreamReader ipsr=new InputStreamReader(ips);
		BufferedReader br=new BufferedReader(ipsr);
		String line;
		while ((line=br.readLine())!=null){
			content+=line+"\n";
		}
		br.close();
		return content;
	}
	
	public static void setFileFromString(String filePath, String content) throws IOException{		
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		out.write(content);
		out.close();
	}

}
