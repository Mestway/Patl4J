package rulesEngine.utils;

public class JavaUtils {
	
	public static String getStackTrace(Throwable throwable){		
		return getStackTrace(throwable, "");
	}
	
	private static String getStackTrace(Throwable throwable, String message){
		message += "Message : " + throwable.getMessage() + "\n";
		for(StackTraceElement stack : throwable.getStackTrace()){
			message += stack.toString() + "\n";			
		}		
		Throwable cause = throwable.getCause();
		if(cause != null)
			message += getStackTrace(cause, message);
		
		return message;
	}

}
