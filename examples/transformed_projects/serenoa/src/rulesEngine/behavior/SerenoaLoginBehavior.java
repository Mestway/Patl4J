package rulesEngine.behavior;

import java.io.IOException;

import leon.app.LySession;
import leon.app.behavior.LyLoginBehavior;
import leon.control.LyLoginController;
import leon.data.LyObject;
import leon.view.event.LyParameterValues;
import leon.view.web.LyWebSession;

import org.dom4j.Element;

import rulesEngine.manager.AE_ModelId;
import rulesEngine.manager.AdaptationEngine;
import rulesEngine.manager.ContextManager;
import rulesEngine.manager.ContextManagerParserException;
import rulesEngine.manager.SERENOA_CONSTANTES;

public class SerenoaLoginBehavior extends LyLoginBehavior{
	
	@Override
	public void endLogin(LyLoginController loginController,
			LyParameterValues values) {
		
		LySession session = loginController.getSession();
				
		// in case a victory 
		if (loginController.getStatus() == LyLoginController.Status.LOGIN_OK)
		{
			LyObject user = getUser(loginController, values);
            
			if (user == null)
				return;
			
			String userName = ContextManager.transformLeonardiId(user.getId().toString());
			AdaptationEngine ae = ((SerenoaSessionBehavior) session.getSessionBehavior()).getAdaptationEngine();
			
			
			try {
				ContextManager cm = ae.getCm();
				//CM_Ids userIds = cm.createUser(userName);
				String serenoaLanguage = cm.getValue(userName, SERENOA_CONSTANTES.LANGUAGE_PARAMETER);
				if(serenoaLanguage != null)				
					session.setLanguage(serenoaLanguage);
				
				if(session instanceof LyWebSession){
//					LyWebSession webSession = (LyWebSession) session;
//					HttpServletRequest request = webSession.getCurrentRequest();
//					String userAgent = request.getHeader("User-Agent").toLowerCase();
//					boolean mobile = userAgent.contains("android")
//							|| userAgent.contains("blackberry")
//							|| userAgent.contains("iphone")
//							|| userAgent.contains("ipad")
//							|| userAgent.contains("ipod")
//							|| userAgent.contains("iemobile");					
//					
//					String deviceCategory = mobile ? "smartphone" : "desktop";			
//				
//					cm.setValue(userIds, new CM_Value(SERENOA_CONSTANTES.DEVICE_TYPE_PARAMETER, deviceCategory));
//					SerenoaSessionBehavior sessionBehavior = (SerenoaSessionBehavior) session.getSessionBehavior();
//					ae.loadModels(userName);
//					ae.executeRules();
//					sessionBehavior.updateModels();
					

					boolean daltonizeMode= new Boolean(session.getEnvironment().getEnv(SERENOA_CONSTANTES.SERENOA_MODE_PREFIX + "daltonize"));
					if(daltonizeMode){			
						session.setSkin("aqualight");
					}else{
						Element w4Model = ae.getModel(new AE_ModelId(session.getEnvironment().getEnv("SERENOA_W4_CONFIG_ID")));			 
						String newSkin = w4Model.elementText("skin");
						if(newSkin != null)
							session.setSkin(newSkin);
					}
					SerenoaSessionBehavior sessionBehavior = (SerenoaSessionBehavior) session.getSessionBehavior();
					sessionBehavior.updateSessionAndUser();
					sessionBehavior.updateModels();
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// catch (ruleModelParserException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (URISyntaxException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ContextManagerParserException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			catch (ContextManagerParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
								
		}
		
		super.endLogin(loginController, values);
	}
}
