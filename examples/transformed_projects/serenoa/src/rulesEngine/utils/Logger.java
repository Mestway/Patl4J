package rulesEngine.utils;

import leon.app.LySession;

//logger pour debug dans tomcat
public class Logger {
	private static LySession _session;
	public static final String PREFFIX = "[LOGGER] ============== ";
	
	public static void init(LySession session){
		_session = session;		
	}
	
	public static void log(String text){
		if(_session!=null){
			_session.getEnvironment().logErr(PREFFIX + text);
		}
	}
	
}
