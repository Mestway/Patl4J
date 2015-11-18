package patl4j.common.util;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

import patl4j.common.ast.pattern.MetaVariable;
import patl4j.common.java.analyzer.AliasAnalysis;
import patl4j.core.matcher.WrappedName;
import soot.Body;
import soot.Local;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.util.Chain;

public class VariableContext {
	
	Chain<Local> locals;
	AliasAnalysis analysis;
	
	public VariableContext(String className, String methodName) {
		SootClass s = null;
		for (SootClass x : Scene.v().getClasses()) {
			if(x.getName().equals(className))
				s = x;
        }
		SootMethod m = s.getMethodByName(methodName);
		if (m.getSource() == null) return;
		Body body = m.retrieveActiveBody();
		locals = body.getLocals();
		analysis = new AliasAnalysis();
	}
	
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
		String nameA = ((Name) a).getFullyQualifiedName();
		String nameB = ((Name) b).getFullyQualifiedName();
		if (locals == null) {
			if (nameA.equals(nameB)) {
				return true;
			}
			return false;
		} else {
			Local la = null, lb = null;
			for (Local l: locals) {
				if (l.getName() == nameA) la = l;
				if (l.getName() == nameB) lb = l;
			}
			return analysis.mayAlias(la, lb);
		}
	}
	
}
