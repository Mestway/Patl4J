package patl4j.util;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

import patl4j.matcher.WrappedName;
import patl4j.patl.ast.MetaVariable;

public class VariableContext {
	
	// TODO: add matched statements here
	public boolean variableMatchCheck(Expression exp, MetaVariable mv) {
		try {
			if (mv.getOldType().equals("") && mv.getNewType().equals("")) {
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
	
	// Notice that: here, we only store one alias map in varMap, as the alias relation commutes. 
	public boolean checkAliasRelation(Pair<String, Name> matchPair,  Map<String, Optional<WrappedName>> varMap) {
		
		// This is a special case for static match mapping: e.g. Format --> Format (both of them are types)
		if (!varMap.containsKey(matchPair.getFirst())) {
			return true;
		}
		
		if (varMap.get(matchPair.getFirst()).isPresent() 
				&& aliasCheck(matchPair.getSecond(), varMap.get(matchPair.getFirst()).get().getName())==false) {
			return false;
		}
		
		return true;
	}
	
	// TODO: alias check here
	public boolean aliasCheck(Expression a, Expression b) {
		if (!(a instanceof Name) || !(b instanceof Name))
			return false;
		System.out.println("VariableContext56==> alias check: " + ((Name)a).getFullyQualifiedName() + " :: " + ((Name)b).getFullyQualifiedName());
		if (((Name)a).getFullyQualifiedName().equals(((Name)b).getFullyQualifiedName())) {
			return true;
		}
		return false;
	}
	
}
