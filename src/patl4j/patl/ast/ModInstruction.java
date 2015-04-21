package patl4j.patl.ast;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.util.ErrorManager;
import patl4j.util.Pair;

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
	
	// If the pattern is "m" or "-"
	public boolean isSrcPattern() {
		if (this.type.equals(Mod.DELETE) || this.type.equals(Mod.MATCH)) 
			return true;
		else return false;
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
			Map<String, String> var2type) {
		
		if (!this.isSrcPattern()) {
			ErrorManager.error("ModInstruction@line58", "Trying to match a pattern with + label");
		}

		return p.tryMatch(s, var2type);
	}
	
}
