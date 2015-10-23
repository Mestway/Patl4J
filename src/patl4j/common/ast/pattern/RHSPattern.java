package patl4j.common.ast.pattern;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

import patl4j.common.util.Pair;
import patl4j.common.util.VariableContext;

public interface RHSPattern {

	// try to use the RHS patten to match the rhs part of an assignment expression
	Pair<List<Pair<String, Name>>, Boolean> tryMatch(Expression rhsExp, 
			Map<String, String> var2type, 
			VariableContext context);
	
}
