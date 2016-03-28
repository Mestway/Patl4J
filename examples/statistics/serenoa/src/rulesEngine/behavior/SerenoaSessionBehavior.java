package rulesEngine.behavior;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import leon.app.LyApplication;
import leon.app.LySession;
import leon.app.behavior.LySessionBehavior;
import leon.info.LyFieldInfo;
import leon.info.LyInfo;
import leon.info.LyInfoParser;
import leon.info.LyInfoParser.Type;
import leon.view.web.LyWebSession;

import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.xpath.DefaultXPath;

import rulesEngine.listener.SerenoaListener;
import rulesEngine.manager.AE_ModelId;
import rulesEngine.manager.AdaptationEngine;
import rulesEngine.manager.AdaptationEngineDeconected;
import rulesEngine.manager.AdaptationEngineWithServiceRepository;
import rulesEngine.manager.AdaptationEngineWithServiceRepositoryAndAE;
import rulesEngine.manager.CM_Ids;
import rulesEngine.manager.CM_Value;
import rulesEngine.manager.ContextManager;
import rulesEngine.manager.SERENOA_CONSTANTES;
import rulesEngine.utils.FileUtils;
import rulesEngine.utils.Logger;


public class SerenoaSessionBehavior extends LySessionBehavior{	
	
	private LySession session;
	private AdaptationEngine ae;
	private Element w4Model; 
	private HttpServletRequest request;
	private String currentUser;
	
	public SerenoaSessionBehavior() {
		
	
	}
		
	
	public void updateSessionAndUser() {
		LyWebSession webSession = (LyWebSession) session;
		request = webSession.getCurrentRequest();
		String user;
		if(session.getUser() == null){
			user = "w4" + request.getSession().getId();
		}else{
			user = session.getUser().getId().replaceAll("\\\\", "_");
		}
		currentUser =  user;
		
	}


	@Override
	public void start(LySession session) {	
		this.session = session;		
		
		
		boolean debug = true;
		if(debug)
			Logger.init(session);
		
		Logger.log("Start session behavior");		
		
		
		if(!(session instanceof LyWebSession)){
			Logger.log("Can't load rules when the session isn't a web session");
			return;
		}		
		
		updateSessionAndUser();
		
		String serenoaRulesPath = request.getRealPath(session.getEnvironment().getEnv("SERENOA_RULES_PATH"));
		try {
			Logger.log("AE + CM");
			ae = new AdaptationEngineWithServiceRepositoryAndAE(serenoaRulesPath, session.getEnvironment().getEnv("SERENOA_CONTEXT_MANAGER_URI"), session.getEnvironment().getEnv("SERENOA_SERVICE_REPOSITORY_URI"), false, false, currentUser, session.getEnvironment().getEnv("SERENOA_ADAPTATION_ENGINE_URI"));
			Logger.log("AE + CM (success) ");
			if(((AdaptationEngineWithServiceRepository)ae).isDeconnected()){
				ae = new AdaptationEngineDeconected(serenoaRulesPath, session.getEnvironment().getEnv("SERENOA_CONTEXT_MANAGER_URI"), false, false, currentUser);
				Logger.log("Adaption engine ko ==> ! DECONNECTED MODE !");
			}			
			
		}catch (Exception e) {			
			e.printStackTrace();
			Logger.log("Failed to load ae");
			
		}
		
		
		ContextManager cm = ae.getCm();
		
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		boolean mobile = userAgent.contains("android")
				|| userAgent.contains("blackberry")
				|| userAgent.contains("iphone")
				|| userAgent.contains("ipad")
				|| userAgent.contains("ipod")
				|| userAgent.contains("iemobile");					
		
		String deviceCategory = mobile ? "smartphone" : "desktop";			
		
		
		CM_Ids userIds;
		try {
			userIds = cm.createUser(currentUser, true);
			cm.setValue(userIds, new CM_Value(SERENOA_CONSTANTES.DEVICE_TYPE_PARAMETER, deviceCategory));
					
		} catch (Exception e) {
			e.printStackTrace();
			Logger.log("Failed to create user");
			
		}		
		
		updateModels();
		
		ae.launchListeners(request.getRealPath(session.getEnvironment().getEnv("SERENOA_RULES_PATH")), new SerenoaListener(this))	;	
		
		session.addValue(SERENOA_CONSTANTES.SERENOA_AE_PARAM, ae);
		super.start(session);
	}	
	
	
	public void updateModels(){
			
		
		try {
			
			ae.loadModels(currentUser);			
			ae.executeRules();
			
			w4Model = ae.getModel(new AE_ModelId(session.getEnvironment().getEnv("SERENOA_W4_CONFIG_ID")));			
			
			if(w4Model == null)
				return;
			
			String content="";
			//String file = session.getEnvironment().getEnv("LY_APP_DIR") + "/WEB-INF/classes/" + session.getEnvironment().getEnv("LY_PROJECT_FILE");
			InputStream file = getClass().getResourceAsStream("/" +session.getEnvironment().getEnv("LY_PROJECT_FILE").replaceAll("\\\\", "/"));
			try{
				content = FileUtils.getInputStreamAsString(file);
			}		
			catch (IOException e){
				e.printStackTrace();
				Logger.log("Failed to get project file");
				
			}
			Pattern p = Pattern.compile("<!ENTITY\\s+([^\\s]*)\\s+[^>]*>");		
			Matcher m = p.matcher(content);
			while (m.find()){
				String replacement = session.getEnvironment().getEnv("SERENOA_ENTITY_TO_MODEL_ID#" + m.group(1));
				if(replacement != null){
					String fileName = replacement +".xml";					
					String modelPath = session.getEnvironment().getEnv("LY_APP_DIR") + "/tmp/" + fileName;
					ae.printModel(modelPath, new AE_ModelId(replacement));					
					content = content.replaceFirst("(<!ENTITY\\s+" + m.group(1) + "\\s+SYSTEM\\s+)[^>]*(\\s*>)", "$1'" + fileName + "'$2");
				}				
			}			
			String rootPath = session.getEnvironment().getEnv("LY_APP_DIR") + "/tmp/w4root.xml";
			//session.getEnvironment().getLogger().logOut("rootPath =" + rootPath);
			//session.getEnvironment().getLogger().logOut("content =" + content);
			FileUtils.setFileFromString(rootPath, content);
			
		
			LyApplication serenoaApp = new LyApplication("serenoa copy");
			try{
				LyInfoParser parser = new LyInfoParser(serenoaApp, rootPath, Type.DEFAULT, true);
				session.addValue(SERENOA_CONSTANTES.SERENOA_PARSER_NAME, parser);
			}catch(Exception e){
				Logger.log("impossible to complete the w4 parser");
				e.printStackTrace();
			}
			//session.getEnvironment().getLogger().logOut("Serenoa session behavior started whithout errors");
		
			
			Logger.log("Models updated");	
			updateModes();
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
			Logger.log("error while updating the models");
			
		}
	}
	
	private void updateModes(){
		try {	
			
			List<String> fieldsToHide = new ArrayList<String>();
			if(w4Model != null){
				XPath xpa = new DefaultXPath("//mode"); 
				List<Element> elements = (List<Element>) xpa.selectNodes(w4Model); 
				for(Element element : elements){ 
					boolean modeActive = element.elements().get(0).getName().equals("true"); 
					String elementType = element.attributeValue("type"); 
					session.getEnvironment().putEnv(SERENOA_CONSTANTES.SERENOA_MODE_PREFIX + elementType, modeActive);
					
				}						
				
				xpa = new DeafultXPath("//field_id"); 
				elements = (List<Element>) xpa.selectNodes(w4Model); 
				
				for(Element element : elements){ 
					fieldsToHide.add(element.getText()); 
				}				
			}
			session.addValue(SERENOA_CONSTANTES.SERENOA_HIDDEN_FIELDS, fieldsToHide);
		} catch (Exception e) {
			Logger.log("Can't read w4client model cause by " + e.getMessage() + "  All modes set to false");			
		}	
        
	}
	
	
	public static LyFieldInfo getSerenoaFieldInfo(LySession session, LyFieldInfo fieldInfo) {
		return getSerenoaFieldInfo(session, fieldInfo.getId());
	}

	public static LyFieldInfo getSerenoaFieldInfo(LySession session, String fieldInfoId) {
		LyInfoParser parser = (LyInfoParser) session.getValue(SERENOA_CONSTANTES.SERENOA_PARSER_NAME);
		return (LyFieldInfo) parser.getResults().getInfo(fieldInfoId);
	}
	
	

	public static LyInfo getSerenoaInfo(LySession session, String classInfoId) {
		LyInfoParser parser = (LyInfoParser) session.getValue(SERENOA_CONSTANTES.SERENOA_PARSER_NAME);
		return parser.getResults().getInfo(classInfoId);
	}


	public AdaptationEngine getAdaptationEngine() {
		return ae;
	}
	
	
	
}
