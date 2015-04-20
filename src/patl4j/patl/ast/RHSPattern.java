package patl4j.patl.ast;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

import patl4j.util.Pair;

public interface RHSPattern {

	// try to use the RHS patten to match the rhs part of an assignment expression
	Pair<List<Pair<String, Name>>, Boolean> tryMatch(Expression rhsExp, Map<String, String> var2type);
	
}
