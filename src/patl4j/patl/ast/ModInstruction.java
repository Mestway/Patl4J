package patl4j.patl.ast;

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
	
	public boolean srcPattern() {
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
	
}
