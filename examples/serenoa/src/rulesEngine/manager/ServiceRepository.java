package rulesEngine.manager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ServiceRepository {

	public static void main(String[] args){
		try {
			new ServiceRepository("http://195.235.93.35:8080/SerenoaSR", 
					"C:\\Users\\mducass\\workspace\\TestsWeb2\\src\\serenoa\\testAaldl.xml", "user1", "undefined ");
		//} catch (Exception e) {		
		//	e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ContextManagerParserException e) {			
			e.printStackTrace();
		}
	}
	
	private static final String APPLICATION_ID_PREFIX = "w4serenoa";
	protected String applicationId;
	private  String service_repository_uri;// = "http://urano.isti.cnr.it:8080/cm";
	private WebResource service_sr;
	private boolean deconectedMode = false;
	private Document asfedl; 
	private Document aaldl; 
	private static SAXReader sxb = new SAXReader();	 
	private static Map<AE_ModelId, Document> models = new HashMap<AE_ModelId, Document>();	 
	
	
	public ServiceRepository(String service_repository_uri, String rulesPath, String user, String undefinedModelPrefix) throws Exception, IOException, ContextManagerParserException{
		applicationId = APPLICATION_ID_PREFIX + user;
		this.service_repository_uri = service_repository_uri;
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		service_sr = client.resource(getBaseURI());
		
		String rules = "";
		String auiModel = "";
		File rulesFile;
		String rulesToRegister = "";
	    try {	    	
	    	rulesFile = new File(rulesPath);
	    	CM_Parser cmp = new CM_Parser(user);
	    	rules = cmp.parse(rulesFile);	
	    	aaldl= sxb.read(new ByteArrayInputStream(rules.getBytes()));   
	    	Element rulesRoot = aaldl.getRootElement(); 
	    	auiModel = contructASFE_DL(rulesRoot, rulesFile, undefinedModelPrefix);
	    	List<Element> rulesElements = rulesRoot.elements("rule"); 

	    	//XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());	
	    	for(Element e : rulesElements){	    		  
	    		//rulesToRegister += out.outputString(e) + "\n";	    		
	    		rulesToRegister += e.asXML() + "\n";	    		
	    	}

	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
	    formData.add("username", user);    
		formData.add("asfe_dl", auiModel);
		formData.add("aal_dl", rulesToRegister);
		
		//test server is runing
		try{			
			service_sr.path("rest").path("services").path("undefined").get(String.class);
		}catch(Exception e){
			deconectedMode = true;
		}
		
		if(!deconectedMode){
			service_sr.path("rest").path("services").path(applicationId).type("application/x-www-form-urlencoded").post(ClientResponse.class, formData);
		    String response = service_sr.path("rest").path("services").path(applicationId).accept(MediaType.TEXT_PLAIN).get(String.class);
		    response = response.replaceFirst("<aaldl>[\\s\\S]*</aaldl>", "");
		    String responseUTF8 = new String(response.getBytes(),"UTF-8");	
		    Document rulesModel= sxb.read(new ByteArrayInputStream(responseUTF8.getBytes()));	        
		   
		    asfedl = new Document(rulesModel.getRootElement().element("asfedl").clone());   
		}
		
	}
	
	private URI getBaseURI() {
		return UriBuilder.fromUri(service_repository_uri).build();
	}

	public Document getAaldl() { 
		return aaldl;
	}
	
	private String contructASFE_DL(Element rulesRoot, File rulesFile, String undefinedModelPrefix) throws IOException{ 
				    
	    XPath xpa = new DefaultXPath("//ext_model_ref");	         
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
        	Document model = sxb.read(f); 
        	models.put(new AE_ModelId(modelId), model);	
        }
        return getAllModelsAsXMLString();
	}
	
	private String getAllModelsAsXMLString() throws IOException {
		String response = "";				
		for(Entry<AE_ModelId, Document> entry : models.entrySet()){			 
			response += getModelAsXMLString(entry.getKey());						
		}
		return response;
	}
	
	private String getModelAsXMLString(AE_ModelId modelId) throws IOException {
		Element model = getModel(modelId); 
		String response = "";
		if(model != null){
			XMLWriter sortie = new XMLWriter(OutputFormat.createPrettyPrint());		  
			Element modelElement = new Element("model");		 
			modelElement.addAttribute("id", modelId.getId()); 
			modelElement.add(model.clone()); 
			//response = sortie.outputString(modelElement);
            response = modelElement.asXML();
			response += "\n";	
		}
		return response;
	}
	
	
	public Element getModel(AE_ModelId modelId) throws IOException { 
		
		for(Entry<AE_ModelId, Document>  model : models.entrySet()){ 
			if(modelId.equals(model.getKey())){
				modelId = model.getKey();
				break;
			}
		}
		if(modelId.isContextManager()){		
			return null;		
				
		}
		return models.get(modelId).getRootElement(); 
	}

	public boolean isDeconectedMode() {
		return deconectedMode;
	}
	
	public void updateModel(AE_ModelId id, Document document){ 
		models.put(id, document);
	}
}
