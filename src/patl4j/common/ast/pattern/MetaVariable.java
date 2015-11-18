package patl4j.common.ast.pattern;

import java.util.List;

public class MetaVariable {
	String name = "";
	String oldType = "";
	String newType = "";
	
	public MetaVariable(String name, String oldType, String newType) {
		this.name = name;
		this.oldType = oldType;
		this.newType = newType;
	}
	
	public MetaVariable(String name, List<VarDecl> decls) {
		this.name = name;
		for (VarDecl d : decls) {
			if (d.getName().equals(this.name)) {
				this.oldType = d.getOldType();
				this.newType = d.getNewType();
			}
		}
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	public String getName() { return name; }
	public String getOldType() { return oldType; }
	public String getNewType() { return newType; }
}
