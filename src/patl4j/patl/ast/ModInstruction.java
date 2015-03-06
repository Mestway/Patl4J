package patl4j.patl.ast;

import java.util.List;
import java.util.Map;

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
	public Pair<List<Pair<String, String>>, Boolean> tryMatch(Statement s,
			Map<String, String> var2type) {
		
		if (!this.isSrcPattern()) {
			ErrorManager.error("encounted fatal error: trying to match a pattern with + label");
		}
		
		return null;
		// TODO: implement try match
		//return p.tryMatch(s, var2type);
	}
	
}
