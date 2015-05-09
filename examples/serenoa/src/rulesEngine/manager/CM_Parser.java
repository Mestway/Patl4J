package rulesEngine.manager;



import java.io.File;
import java.io.IOException;

import rulesEngine.utils.FileUtils;

public class CM_Parser {
	

	private String currentUser;
	
	
	
	public CM_Parser(String currentUser) {
		super();
		this.currentUser = currentUser;
	}

	public enum tokens{
		CONTEXT_MANAGER,
		CURRENT_USER
	}
	
	public String parse(File f) throws ContextManagerParserException, IOException{
		String fileContent = FileUtils.getFileAsString(f.getAbsolutePath());
		return parse(fileContent);
	}
	
//	public void parse(File f) throws ContextManagerParserException, IOException{
//		String content = "";
//		InputStream ips=new FileInputStream(f); 
//		InputStreamReader ipsr=new InputStreamReader(ips);
//		BufferedReader br=new BufferedReader(ipsr);
//		String line;
//		while ((line=br.readLine())!=null){
//			content+=line+"\n";
//		}
//		br.close();
//		FileWriter fw = new FileWriter (f);
//		BufferedWriter bw = new BufferedWriter (fw);
//		PrintWriter outputFile = new PrintWriter (bw); 
//		outputFile.print (content); 
//		outputFile.close();
//	}
	
	public String parse(String string) throws ContextManagerParserException{
		String response = "";
		for(int i = 0; i < string.length(); i++){
			char c = string.charAt(i);
			if(c == '%'){
				i++;
				char new_c;
				String token = "";
				while( (new_c = string.charAt(i)) != '%'){
					token += new_c;
					i++;
				}
				if(token.equals(tokens.CURRENT_USER.name())){
					response += currentUser;
				}else if(token.equals(tokens.CONTEXT_MANAGER.name())){
					response += "%" + token + "%";
				}
				else{	
					throw new ContextManagerParserException("[ERROR] Token %" + token + "% isn't recognized by the context manager parser");
				}
			}else{
				response += c;
			}
		}
		return response;
	}
	
}
