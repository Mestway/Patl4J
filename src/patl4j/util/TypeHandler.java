package patl4j.util;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;

public class TypeHandler {

	// Decide whether to use type information in the matching process.
	private static boolean typeCheckSwitcher = true;
	
	// Decide whether to print the type match information
	private static boolean debugTypeInfo = true;
	
	public static boolean typeMatchCheck(ITypeBinding itb, String typeName) throws Exception {
		
		// If we disable the type checker
		if (typeCheckSwitcher == false) {
			return true;
		}
		
		if (itb == null){
			throw new Exception();
		}
		
		if (!itb.getName().equals("") && isSimpleNameString(typeName)) {
			return itb.getName().equals(extractSimpleName(typeName));
		} else {
			return itb.getQualifiedName().equals(typeName);
		}
	}
	
	private static String extractSimpleName(String typeName) {
		String[] strs = typeName.split("\\.");
		return strs[strs.length-1];
	}
	
	private static boolean isSimpleNameString(String typeName) {
		if (extractSimpleName(typeName).equals(typeName)) {
			return true;
		}
		return false;
	}
	
	public static void EnableTypeCheckInMatching() {
		typeCheckSwitcher = true;
	}
	
	public static void DisableTypeCheckInMatching() {
		typeCheckSwitcher = false;
	}
	
	// For debugging purpose, print the type matching information
	public static void printTypeMatchInfo(Expression exp, String typeName, String debugInfo) {
		if (!debugTypeInfo)
			return;
		
		String expTypeName = "";
		if (exp.resolveTypeBinding() != null)
			expTypeName = exp.resolveTypeBinding().getQualifiedName();
		else
			expTypeName = "%nonexsit%";
		System.out.println("[" + debugInfo + "]" + " " + "ExpressionType: " 
				+ expTypeName + ", " + "PatternType: " + typeName);
	}
	
}
