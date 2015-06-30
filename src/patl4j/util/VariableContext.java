package patl4j.util;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

import patl4j.patl.ast.MetaVariable;

public class VariableContext {
	
	// TODO: add matched statements here
	public boolean variableMatchCheck(Expression exp, MetaVariable mv) {
		try {
			if (mv.getOldType().equals("") && mv.getNewType().equals("")) {
				System.out.println("Ð¡ÐÄ½÷É÷£º" + exp.toString() + ":" + mv);
				if (exp.toString().equals(mv.getName()))
					return true;
				return false;
			}
			if (!TypeHandler.typeMatchCheck(exp.resolveTypeBinding(), mv.getOldType())) {
				return false;
			}
		} catch (Exception e) {
			System.out.println("[Type unresolved] The type of the expression is not resolved: " + exp.toString() + "@" + exp.getStartPosition());	
			e.printStackTrace();
		}
		
		return true;
	}
}
