package patl4j.patl.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.Expression;

import patl4j.util.Pair;

public interface PEPattern extends RHSPattern {
	
	public List<Pair<String,String>> syntaxMatch(Expression exp);
	
}
