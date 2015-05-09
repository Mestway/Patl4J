package rulesEngine.manager;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class AdaptationEngineSerenoa{

	private  String adaptation_engine_uri;// = "http://195.235.93.35:8080/SerenoaAE";
	private WebResource service_ae;
	private boolean deconectedMode = false;
	private String applicationName;
	
	
	public AdaptationEngineSerenoa(String adaptation_engine_uri, String applicationName){
		this.adaptation_engine_uri = adaptation_engine_uri;
		this.applicationName = applicationName;
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		service_ae = client.resource(getBaseURI());
		
		//test server is running
		try{			
			service_ae.path("rest").path("service").path("undefined").path("status").get(String.class);
		}catch(Exception e){
			deconectedMode = true;
		}
	}
	
	private URI getBaseURI() {
		return UriBuilder.fromUri(adaptation_engine_uri).build();
	}	
	

	public boolean isDeconectedMode() {
		return deconectedMode;
	}

	public String handleActions(String userId){		
		if(!deconectedMode){		
		    
			ClientResponse response_cr = service_ae.path("rest").path("service").path(applicationName).queryParam("user_id", userId).get(ClientResponse.class);
			String newLocation = response_cr.getHeaders().get("Location").get(0);
			String[] splitsPath = newLocation.replaceAll("[/\\\\]", "").split("\\?");
			String[] splitsArgs = splitsPath[1].split("=");
			String reponse_s = "";
			try {
				int test = 0;
				do{
					if(test != 0){
						Thread.sleep(500);
					}
					reponse_s = service_ae.path("rest").path("service").path(applicationName).path(splitsPath[0]).queryParam(splitsArgs[0], splitsArgs[1]).get(String.class);
					test ++;
					System.out.println("getting from ae (test " + test + ")");
				}while(reponse_s.contains("No result yet") && test < 15);				
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
			return reponse_s;
		}
		return "";
	}
	
	public static void main(String[] args){
		AdaptationEngineSerenoa ae = new AdaptationEngineSerenoa("http://195.235.93.35:8080/SerenoaAE", "w4serenoa");
		ae.handleActions("employeeclass17");
	}

	public void setDeconectedMode(boolean deconectedMode) {
		this.deconectedMode = deconectedMode;
	}
	
	
	
}
