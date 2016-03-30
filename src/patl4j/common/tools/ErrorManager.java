package patl4j.common.tools;

import java.util.ArrayList;
import java.util.List;

public class ErrorManager {
	
	private static List<String> errorLog = new ArrayList<String>();
	
	public static void error(String pos, String s) {
		printInfoToScreen("ERROR", pos, s, "");
		errorLog.add("[" + pos + "] " + s);
	}
	
	public static void error(String pos, String s, String debugInfo) {
		printInfoToScreen("ERROR", pos, s, debugInfo);
		errorLog.add("[" + pos + "]" + s + "\n\t" + debugInfo);
	}
	
	public static void unsupported(String pos, String s, String debugInfo) {
		printInfoToScreen("UNSUPPORTED",pos, s, debugInfo);
		errorLog.add("[" + pos + "]" + s + "\n\t" + debugInfo);
	}
	
	private static void printInfoToScreen(String errorType, String pos, String message, String nextlines) {
		if (nextlines != "") {
			nextlines = "\n\t" + nextlines;
		}
		System.out.println("[" + errorType + "]" + "[" + pos + "]" + message + nextlines);
	}
	
	public static List<String> getErrorLog() {
		return errorLog;
	}
	
	public static void Message(String problem, String message) {
		System.out.println("<<" + problem + ">>" + message);
	}
}
