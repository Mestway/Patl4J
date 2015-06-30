package patl4j.patl.ast;

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
		System.out.println("带有类型： " + this.name + ":(" + this.oldType + "->" + this.newType + ")");
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	public String getName() { return name; }
	public String getOldType() { return oldType; }
	public String getNewType() { return newType; }
}
