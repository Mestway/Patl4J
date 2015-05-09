package rulesEngine.manager;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.dom4j.Attribute;
import org.dom4j.Node;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;

import rulesEngine.utils.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ContextManager {
	
	private static final String PREFERENCES_SUFFIX = "W4preferences";
	private static final String TIMOUT_VALUE = "1800000";
	private WebResource service_cm;
	//private SAXBuilder sxb = new SAXBuilder();	
	private SAXReader sxb = new SAXReader();	 
	private String context_manager_uri;// = "http://urano.isti.cnr.it:8080/cm";
	private Map<String, CM_Ids> index = new HashMap<String, CM_Ids>();
	private boolean deconectedMode = false;
	private Map<CM_Ids, Element> deconnectedMap = new HashMap<CM_Ids, Element>(); 
	private boolean userChanged = true;
	private Element allUser; 
	
	
	public ContextManager(String context_manager_uri) throws Exception {
		this.context_manager_uri = context_manager_uri;
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		service_cm = client.resource(getBaseURI());
		Logger.log("Base URI : " + getBaseURI());
		//test server is runing
		try{
			
			service_cm.path("rest").path("users").get(String.class);
			Logger.log("context manager --> ok");
		}catch(Exception e){
			deconectedMode = true;
			Logger.log("context manager --> ko ==> ! DECONECTED MODE !");
		}
	}
	
	public void deleteUser(CM_Ids ids) throws Exception, IOException {
		if(deconectedMode){
			deconnectedMap.remove(ids);	
			
		}else{
			executeCM_Http_Method(buildDeleteCommand(ids.getIdPreferences()));
			executeCM_Http_Method(buildDeleteCommand(ids.getIdUser()));	
		}
		userChanged = true;
		List<String> keysToRemove = new ArrayList<String>();		
		for(Entry<String, CM_Ids>  entry: index.entrySet()){
			
			if(deconectedMode){
				if(entry.getValue().getIdUser().hashCode() == ids.getIdUser().hashCode()){
					keysToRemove.add(entry.getKey());
				}
			}else{
				if(entry.getValue().equals(ids)){
					keysToRemove.add(entry.getKey());
				}
			}
			
		}
		for(String key : keysToRemove){
			index.remove(key);
		}
	}
	
	
	private String buildDeleteCommand(String id){
		String deleteCmd =
				"<comet>" +
					"<op>delete</op>" +
					"<entity_id>" + id + "</entity_id>" +
				"</comet>";
		return deleteCmd;
	}
	
	private boolean userExists(String userName) throws Exception, IOException {
		return index.containsKey(userName) || getUser(userName) != null;
	}

	
	public CM_Ids createUser(String userName) throws Exception, IOException{
		return createUser(userName, new ArrayList<CM_Value>());
	}
	
	public CM_Ids createUser(String userName, boolean temp) throws Exception, IOException{
		return createUser(userName, new ArrayList<CM_Value>(), temp);
	}

	public CM_Ids createUser(String userName, CM_Value value) throws Exception, IOException{
	return createUser(userName, value, false);
	}	
	
	public CM_Ids createUser(String userName, CM_Value value, boolean temp) throws Exception, IOException{
		List<CM_Value> values = new ArrayList<CM_Value>();
		values.add(value);
		return createUser(userName, values, temp);
	}
		
	
	public CM_Ids createUser(String userName, List<CM_Value> values) throws Exception, IOException {
		return createUser(userName, values, false);
	}
	
	public CM_Ids createUser(String userName, List<CM_Value> values, boolean temp) throws Exception, IOException {
		if(userExists(userName)){
			return getUserIds(userName);
		}
		userChanged = true;
		
		CM_Ids newId;
		
		if(deconectedMode){
			
			String content = 
					"<user>" +							
							"<userid>" + userName + "</userid>" +
							"<account/>" +
							"<location/>" +
							"<environment>" +
							"<light_level>0</light_level>" +
							"<noise_level>0</noise_level>" +
							"<temperature>0</temperature>" +
							"</environment>" +
							"<preferences>" +
								"<" + userName + PREFERENCES_SUFFIX + ">" +	
								"</" + userName + PREFERENCES_SUFFIX + ">" +	
							"</preferences>" +
							"<deviceBattery>" +
								"<batteryLevel>0</batteryLevel>" +
								"<batteryCharging>false</batteryCharging>" +
								"</deviceBattery>" +
							"</user>";
			
			Element element =  sxb.read(new ByteArrayInputStream(content.getBytes())).getRootElement();  
			
			newId =  new CM_Ids("" + userName.hashCode(), "" + (userName.hashCode()-1));
			
			deconnectedMap.put(newId, element);
			
		}else{
			
			String cm_preferencesName = userName + PREFERENCES_SUFFIX;
			String timout = temp ? "<entity_timeout>" + TIMOUT_VALUE + "</entity_timeout>" : "";
			String createPreferencesCmd = 
					"<comet>" +
						"<op>insert</op>" +
						"<entity_type>user_preferences</entity_type>" +
						timout + 
						"<entity_data>" +
							"<id type='xs:string'>" + cm_preferencesName + "</id>" +	
							buildEntitiesData(values) +
						"</entity_data>" +
					"</comet>";
			
			String response = executeCM_Http_Method(createPreferencesCmd);
			String preferencesId= response.replaceAll("[^\\d]", "");
				
			String createUserCmd = 
					"<comet>" +
						"<op>insert</op>" +
						"<entity_type>user</entity_type>" +
						timout + 
						"<entity_data>" +
							"<userid type='xs:string'>" + userName + "</userid>" +
							"<preferences_" + cm_preferencesName + ">" +
								"<entity_id type='xs:entity'>" + preferencesId + "</entity_id>" +
							"</preferences_" + cm_preferencesName + ">" +
						"</entity_data>" +
					"</comet>";
			
			response = executeCM_Http_Method(createUserCmd);
			String userId= response.replaceAll("[^\\d]", "");
			newId = new CM_Ids(userId, preferencesId);
		}
		
		
		System.out.println("user " + userName + " created");
		index.put(userName, newId);
		if(deconectedMode){
			setValue(newId, values);
		}
		return newId;
	}
	
	public String getValue(String userName, String key) throws Exception, IOException {
		Element userElmt = getUser(userName); 
		if(userElmt == null){
			createUser(userName);
			return getValue(userName, key);
		}
		XPath xpa = new DefaultXPath("//preferences[contains(id, '" + PREFERENCES_SUFFIX + "')]/preference/name[.='" + key + "']/following-sibling::*[1]"); 
		Element e = (Element) xpa.selectSingleNode(userElmt); 
		if(e == null)
			return null;
		return e.getText();
	}
	
	public boolean setValue(String  userName, CM_Value value) throws Exception, IOException {		
		return setValue(getUserIds(userName), value);
	}
		
	
	public boolean setValue(String  userName, List<CM_Value> values) throws Exception, IOException {
		return setValue(getUserIds(userName), values);
	}
	
	public boolean setValue(CM_Ids ids, CM_Value value) throws Exception, IOException {
		List<CM_Value> values = new ArrayList<CM_Value>();
		values.add(value);
		return setValue(ids, values);
	}
		
	
	public boolean setValue(CM_Ids ids, List<CM_Value> values) throws Exception, IOException {
		if(deconectedMode){
			Element userElement = deconnectedMap.get(ids); 
			for(CM_Value value : values){
				XPath xpa = new DefaultXPath("//preferences/*[contains(name(), '" + PREFERENCES_SUFFIX + "')]/@*[name()='" + value.getKey() + "']");///preference/name[.='" + key + "']/following-sibling::*[1] 
				Attribute attr = (Attribute) xpa.selectSingleNode(userElement); 
				if(attr != null){//this attribute already exists
					attr.setValue(value.getValue()); 
				}else{
					xpa = new DefaultXPath("//preferences/*[contains(name(), '" + PREFERENCES_SUFFIX + "')]"); 
					Element e = (Element) xpa.selectSingleNode(userElement);  
					if(value.getValue() != null){
						e.setAttribute(value.getKey(), value.getValue()); 
					}

				}
			}
			return true;
		}
		userChanged = true;
		
		String updateCmd = 
				"<comet>" +
					"<op>update</op>" +
					"<entity_id>" + ids.getIdPreferences() + "</entity_id>" +
					"<entity_data>" +
						buildEntitiesData(values) +						
					"</entity_data>" +
				"</comet>";
		
		String response = executeCM_Http_Method(updateCmd);
		return !response.contains("FAILED");
	}
	
	private String buildEntitiesData(List<CM_Value> values){
		String response = "";
		for(CM_Value value : values){
			response += "<" + value.getKey() + " type='" + value.getType() + "'>" + value.getValue() + "</" + value.getKey() + ">";
		}
		return response;
	}
	

	public CM_Ids getUserIds(String userName) throws Exception, IOException{	
		if(index.containsKey(userName))
			return index.get(userName);
		if(deconectedMode){			
			return createUser(userName);
		}
		Element userElement = getUserElement(userName); 
		if(userElement == null){			
			return createUser(userName);
		}			
		String userId = getUserElement(userName).getChildText("id"); 
		String preferencesId = getUserElement(userName).getChild("entity_data").getChild("sub_entities").getChild("refValue").getChildText("entity_id");  
		CM_Ids userIds = new CM_Ids(userId, preferencesId);
		index.put(userName, userIds);
		return userIds;
	}
	
	@Deprecated
	private Element getUserElement(String userName) throws Exception, IOException{		 
		String response = executeCM_Http_Method("<comet><op>queryall</op></comet>");
		Document allQueries = sxb.read(new ByteArrayInputStream(response.getBytes())); 
		XPath xpa = new DefaultXPath("//entity[type='user' and entity_data/attributes/value='" + userName + "']"); 
		return (Element)xpa.selectSingleNode(allQueries); 
	}



	private Element getUser(String userName) throws Exception, IOException {	 
		if(deconectedMode){		
			if(index.containsKey(userName))
				return deconnectedMap.get(getUserIds(userName));
			return null;
		}
		Element response = null; 
		try{
			String userResponse = executeCM_Rest_Method("user/" + userName);		
			response = sxb.read(new ByteArrayInputStream(userResponse.getBytes())).getRootElement(); 
		}catch(UniformInterfaceException uie){
			System.out.println("the user " + userName + " doesn't exist");
		}
		return response;
	}

	private URI getBaseURI() {
		return UriBuilder.fromUri(context_manager_uri).read();
	}
			
	private String executeCM_Http_Method(String query) {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("cmd", query);
		return service_cm.path("http/context").post(String.class, formData);
	}
	
	private String executeCM_Rest_Method(String path) {		
		return service_cm.path("rest").path(path).get(String.class);
	}	
	
	public static String transformLeonardiId(String id){
		return id.replaceAll("\\\\", "").replaceAll("_", "");
	}
	
	public Element getAllUser() throws Exception, IOException{ 
		if(deconectedMode){
			Document d = DocumentHelper.createDocument();			 
			Element userElement = DocumentHelper.createElement("user"); 
			d.add(userElement); 
			for(Entry<CM_Ids, Element> entry : deconnectedMap.entrySet()){ 
				List<Node> userContent = entry.getValue().content();				 
				for(Content c : userContent){ 
					userElement.add(c.clone()); 
				}			
			}
			return userElement;
		}
		
		if(userChanged){
			allUser = sxb.read(new ByteArrayInputStream(executeCM_Rest_Method("/users").getBytes())).getRootElement();		 
			userChanged = false;
		}
		return allUser;
	}

	public boolean isDeconectedMode() {
		return deconectedMode;
	}
	
	
	
}
