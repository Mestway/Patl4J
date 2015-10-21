package rulesEngine.manager;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.xpath.DeafultXPath;

import rulesEngine.listener.FileListener;
import rulesEngine.utils.Logger;

public class AdaptationEngineWithServiceRepository extends AdaptationEngine{
	
	protected Map<String, ServiceRepository> serviceRepositories = new HashMap<String, ServiceRepository>();
	protected String currentUser;
	private String service_repository_uri;
	private String rulePath;
	
	@Override
	public void loadModels(String user) throws Exception, IOException, URISyntaxException, ContextManagerParserException{
		ServiceRepository sr = getSR(user);
		Document rulesModel= sr.getAaldl();   	     
		rulesRoot = rulesModel.getRootElement(); 
	}
	
	protected ServiceRepository getSR(String user) throws Exception, IOException, ContextManagerParserException{
		ServiceRepository sr = serviceRepositories.get(user);
		if(sr == null){
			sr = new ServiceRepository(service_repository_uri, rulePath, currentUser, undefinedModelPrefix);
			serviceRepositories.put(currentUser, sr);
			currentUser = user;
		}		
		return sr;
	}
	
	public AdaptationEngineWithServiceRepository(String rulePath, String context_manager_uri, String service_repository_uri, boolean loadModels, boolean executeRules, String currentUser) throws Exception, IOException, ContextManagerParserException{	
		this.rulePath = rulePath;
		this.service_repository_uri = service_repository_uri;
		this.currentUser = currentUser;
		getSR(currentUser);
	    try
	    {    	
	    	init(context_manager_uri, loadModels, executeRules, currentUser);
	    	Logger.log("ae whith sr --> ok");
	    }
	    catch(Exception e){	    	
	    	e.printStackTrace();
	    	Logger.log("ae whith sr --> ko");
	    }	
	   
	}

	@Override
	public Element getModel(AE_ModelId modelId) throws Exception, 
			IOException, ContextManagerParserException {		
		ServiceRepository sr = getSR(currentUser);
		
		Element model = sr.getModel(modelId); 
		if(model == null){
			return cm.getAllUser();	
		}
		return model;
	}

	@Override
	public void launchListeners(String rulesPath, FileListener listener) {}
		
	public boolean isDeconnected() throws Exception, IOException, ContextManagerParserException{
		ServiceRepository sr = getSR(currentUser);
		
		return sr.isDeconectedMode();
	}
	
	@Override
	protected void replaceModel(AE_Target modelToUpdate) throws Exception, IOException, ContextManagerParserException {
		ServiceRepository sr = getSR(currentUser);
		sr.updateModel(modelToUpdate.getModelId(), modelToUpdate.getModel().getDocument());
	}
}
