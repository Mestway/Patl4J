package patl4j.util;

public class VariableGenerator {
	
	// Consider to add the variable metadata in the class later.
	// private static Map<String, VarMetadata> VarTable = new HashMap<String, VarMetadata>();
	
	private static Integer VarCounter= 0;
	
	public static String genVar() {
		String var =  "___var" + VarCounter.toString();
		VarCounter = VarCounter + 1;
		return var;
	}
	
	public static Integer getCurrentCount() {
		return VarCounter;
	}
	
	public static void reset() {
		VarCounter = 0;
	}
	
}
