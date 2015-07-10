package patl4j.patl.ast;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.matcher.Matcher;
import patl4j.patl.ast.full.FullAssignment;
import patl4j.util.ErrorManager;
import patl4j.util.Pair;
import patl4j.util.VariableContext;

public class ModInstruction {
	
	public enum Mod { ADD, DELETE, MATCH };
	
	StatementPattern p;
	Mod type;
	
	public ModInstruction(StatementPattern p, String type) {
		this.p = p;
		if (type.equals("+")) {
			this.type = Mod.ADD;
		} else if (type.equals("-")) {
			this.type = Mod.DELETE;
		} else if (type.equals("m")) {
			this.type = Mod.MATCH;
		}
	}
	
	/**
	 * If the pattern is "m" or "-"
	 * @return
	 */
	public boolean isSrcPattern() {
		if (this.type.equals(Mod.DELETE) || this.type.equals(Mod.MATCH)) 
			return true;
		else return false;
	}
	
	/**
	 * If the patern is of "-"
	 * @return
	 */
	public boolean isMinus() {
		if (this.type.equals(Mod.DELETE))
			return true;
		else return false;
	}
	
	/**
	 * Indicates whether the pattern is a target pattern, with "+"
	 * @return
	 */
	public boolean isTgtPattern() {
		if (this.type.equals(Mod.ADD))
			return true;
		return false;
	}
	
	public boolean isAssignmentPattern() {
		if (this.p instanceof AssignStmtPattern || this.p instanceof FullAssignment) {
			return true;
		}
		return false;
	}
	
	public Statement patternToStatement(Matcher m) {
		return this.p.toJavaStatement(m);
	}
	
	// only used to get the LHS variable if the statement is an assignment statement. 
	public String assignmentLHSVariableName() {
		if (this.p instanceof AssignStmtPattern) {
			return ((AssignStmtPattern) this.p).getVariable();
		} else if (this.p instanceof FullAssignment) {
			return ((FullAssignment) this.p).getVariable();
		} else {
			ErrorManager.error("ModInstruction@48", "The statement pattern is not assignment.");
			return "";
		}
	} 
	
	@Override
	public String toString() {
		switch (type) {
		case ADD: return "+ " + p.toString();
		case DELETE: return "- " + p.toString();
		case MATCH: return "m " + p.toString();
		default: return null;
		}
	}

	// only for those labeled with "-" and "m"
	/**
	 * Note that when the boolean variable is false, the list is invalid, no matter it is empty or not
	 * @param s: The statement used to match the mod instruction
	 * @param var2type: the variable name to type map
	 * @return a pair containing the variable binding list and an boolean variable indicating whether the match success
	 */
	public Pair<List<Pair<String, Name>>, Boolean> tryMatch(Statement s,
			Map<String, String> var2type,
			VariableContext context) {
		
		
		if (!this.isSrcPattern()) {
			ErrorManager.error("ModInstruction@line58", "Trying to match a pattern with + label");
		}
		
		Pair<List<Pair<String,Name>>, Boolean> result = p.tryMatch(s, var2type, context);
		
		if (result.getSecond()) {
			//System.out.println("[MonInstruction@line64] Match success: \n\tStatement: " + s + "\n\tPattern: " + this.p.toString());
		}
		
		return result;
	}
	
}
