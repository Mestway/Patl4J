package rulesEngine.manager;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.xpath.DefaultXPath;
import org.dom4j.XPath;

import rulesEngine.utils.Logger;

public class AdaptationEngineWithServiceRepositoryAndAE extends AdaptationEngineWithServiceRepository{
	
	AdaptationEngineSerenoa ae;	
	
	public AdaptationEngineWithServiceRepositoryAndAE(String rulePath, String context_manager_uri, String service_repository_uri, boolean loadModels, boolean executeRules, String currentUser, String adaptation_engine_uri) throws Exception, IOException, ContextManagerParserException{	
		super(rulePath, context_manager_uri, service_repository_uri, loadModels, executeRules, currentUser);
		Logger.log("new adaptation engine");
		ae = new AdaptationEngineSerenoa(adaptation_engine_uri, getSR(currentUser).applicationId);		
		Logger.log("ae loaded");
		if(cm.isDeconectedMode()){
			ae.setDeconectedMode(true);
		}
	}
	
	@Override
	public boolean isDeconnected() throws Exception, IOException, ContextManagerParserException{
		return super.isDeconnected() || ae.isDeconectedMode();
	}

	@Override
	protected List<Element> getActions() throws Exception, 
			ruleModelParserException, IOException {
		String response = ae.handleActions(currentUser);
		//Document actionDocument = new SAXBuilder().build(new ByteArrayInputStream(response.getBytes()));	
		//XPath xpa = new JDOMXPath("//action");	        
        //return (List<Element>) xpa.selectNodes(actionDocument.getRootElement());
		Document actionDocument = new SAXReader().read(new ByteArrayInputStream(response.getBytes()));	  
		XPath xpa = new DefaultXPath("//action");	         
        return (List<Element>) xpa.selectNodes(actionDocument.getRootElement());  
        //alternative transformation
		Document actionDocument = new SAXReader().read(new ByteArrayInputStream(response.getBytes()));	  
        return (List<Element>) actionDocument.selectNodes("//action"); 
	}
}
