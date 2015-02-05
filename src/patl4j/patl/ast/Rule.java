package patl4j.patl.ast;

import java.util.ArrayList;
import java.util.List;

// A transformation rule in the Patl language
public class Rule {
	
	List<VarDecl> declList = new ArrayList<VarDecl>();
	List<ModInstruction> instructions = new ArrayList<ModInstruction>();

	public Rule(List<VarDecl> decls, List<ModInstruction> instrs) {
		declList = decls;
		instructions = instrs;
	}
	
	@Override
	public String toString() {
		String args = "(";
		boolean flag = true;
		for (VarDecl i : declList) {
			if (!flag)
				args += "," + i.toString();
			else {
				args += i;
				flag = false;
			}
		}
		args += ")";
	
		String body = "{\n";
		for (ModInstruction i : instructions) {
			body += "	" + i.toString() + "\n";
		}
		body += "}\n";
		
		return args + body;
	}
}
