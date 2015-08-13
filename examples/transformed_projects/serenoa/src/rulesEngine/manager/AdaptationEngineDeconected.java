package rulesEngine.manager;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;

import rulesEngine.listener.FileListener;
import rulesEngine.listener.FileMonitor;
import rulesEngine.listener.SerenoaListener;

//TODO merge with adaptation engine with service repository and del this class
@SuppressWarnings("deprecation")
public class AdaptationEngineDeconected extends AdaptationEngine
{	
	//SAXReader new Instance
	private SAXReader sxb = new SAXReader();	 
	private Map<AE_ModelId, Document> models; 
	private List<File> relatedFiles = new ArrayList<File>();

	
	@Override
	public Element getModel(AE_ModelId modelId) throws Exception, IOException { 
		
		for(Entry<AE_ModelId, Document>  model : models.entrySet()){ 
			if(modelId.equals(model.getKey())){
				modelId = model.getKey();
				break;
			}
		}
		if(modelId.isContextManager()){		
			return cm.getAllUser();		
				
		}
		return models.get(modelId).getRootElement(); 
	}
	
	public String getAllModelsAsXMLString() throws Exception, IOException, ContextManagerParserException {
		String response = "";				
		for(Entry<AE_ModelId, Document> entry : models.entrySet()){			 
			response += getModelAsXMLString(entry.getKey());						
		}
		return response;
	}
	

	public static void main(String[] args){
		try {
			AdaptationEngineDeconected ae = new AdaptationEngineDeconected("C:\\Users\\mducass\\workspace\\bicycleshop\\WebContent\\w4serenoa\\rules\\w4rules.xml", "http://urano.isti.cnr.it:8080/cm", "userW4");
			
			System.out.println(ae.getAllModelsAsXMLString());
			String action = 						 				
						"<create>" +
							"<containingEntityReference xPath='//temp' externalModelId='temp'/>" +
							"<simpleType/>" +
							"<value>" +
								"<constant type='boolean' value='true'/>" +
								"</value>" +
						"</create> ";
		
			ae.updateModel(action);
			System.out.println(ae.getAllModelsAsXMLString());
		} catch (Exception e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ruleModelParserException e) {			
			e.printStackTrace();
		} catch (ContextManagerParserException e) {
			e.printStackTrace();
		}
		
		
	   
	}
	
	@Override
	public void loadModels(String user) throws Exception, IOException, URISyntaxException, ContextManagerParserException{
		XPath xpa = new DefaultXPath("//ext_model_ref");	         
       
		models = new HashMap<AE_ModelId, Document>();	 
		CM_Parser cmp = new CM_Parser(user);
		String contentParsed = cmp.parse(rulesFile);
    	Document rulesModel= sxb.read(new ByteArrayInputStream(contentParsed.getBytes()));   	 
    	rulesRoot = rulesModel.getRootElement(); 
    	
    	List<Element> modelsList = (List<Element>) xpa.selectNodes(rulesRoot);	 
 		
    	
		int modelUndefinedIndex = 0;
        for(Element e : modelsList){  	        	 
            
        	String modelId = e.attributeValue("model_id");	        	 
        	if(modelId == null || modelId.length() == 0){
        		modelId = undefinedModelPrefix + modelUndefinedIndex;
        		modelUndefinedIndex++;
        	}	       	     	
        		  
        	String uri = e.attributeValue("URI");   
        	if(uri.contains("%" + CM_Parser.tokens.CONTEXT_MANAGER + "%")){        		     		
        		models.put(new AE_ModelId(modelId, true), null);		
        		continue;
        	}
        	File f = null;
        	//load uri
        	try{
        		f = new File(rulesFile.getParent(), uri);
        		if(!f.exists()){
        			throw new Exception();//to be catched
        		}
        	}catch(Exception exp){
        		try{
	        		URL oracle = new URL(uri);
	        		f = new File(oracle.toURI());
        		}catch(Exception exp2){
        			InputStream input = this.getClass().getResourceAsStream(uri);        			
        			f = new File(rulesFile.getParent() + input.toString());
        			OutputStream out = new FileOutputStream(f);
        			int read = 0;
        			byte[] bytes = new byte[1024];
        		 
        			while ((read = input.read(bytes)) != -1) {
        				out.write(bytes, 0, read);
        			}
        			input.close();
        			out.flush();
        			out.close();
        		}
        	}
        	
        	try{        		
        		relatedFiles.add(f);
        		System.out.println("[INFO] FILE LISTENED =" + f.getAbsolutePath());
        	}catch(IllegalArgumentException iae){
        		System.out.println("[WARNING]: " + uri + " is not 'file', therefore this resource will not be listened");
        	}
        	
        	//CM_Parser cmp = new CM_Parser(currentUser);
        	//cmp.parse(f);
        	Document model = sxb.read(f); 
            //Document model = sxb.build(new ByteArrayInputStream(contentParsed.getBytes()));
            
            models.put(new AE_ModelId(modelId), model);		     
        }
	}
		
	//do nothing for Adaptation engine with service repository
	public AdaptationEngineDeconected(){
		
	}
	
	public AdaptationEngineDeconected(String rulePath, String context_manager_uri, String currentUser) throws Exception, IOException{
		this(rulePath, context_manager_uri, true, currentUser);
	}
	
	public AdaptationEngineDeconected(String rulePath, String context_manager_uri, boolean loadModels, String currentUser) throws Exception, IOException{
		this(rulePath, context_manager_uri, loadModels, true, currentUser);
	}
	
	
	
	public AdaptationEngineDeconected(String rulePath,  String context_manager_uri, boolean loadModels, boolean executeRules, String currentUser) throws Exception, IOException{	
		
		models = new HashMap<AE_ModelId, Document>();	 
	    try
	    {    	
	       //Load rules
	    	
	    	rulesFile = new File(rulePath);	    		        
	       init(context_manager_uri, loadModels, executeRules, currentUser);
	      
	    }
	    catch(Exception e){	    	
	    	e.printStackTrace();	    	
	    }	
	   
	}	
	

	public List<File> getRelatedFiles() {
		return relatedFiles;
	}

	@Override
	public void launchListeners(String rulesPath, FileListener listener) {
		  // Create the monitor
	    FileMonitor monitor = new FileMonitor (1000);
	    
	    // Add some files to listen for
	    File f = new File (rulesPath);
	    monitor.addFile (f);
	    System.out.println("[INFO] FILE LISTENED =" + f.getAbsolutePath());
	    for(File file : getRelatedFiles()){
	    	 monitor.addFile (file);
	    }
	    
	    // Add a dummy listener
	    monitor.addListener (listener);	    
		
	}
	
		
}
