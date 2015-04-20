package patl4j.patl.ast.full;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.util.ErrorManager;
import patl4j.util.Pair;

public class FullExpressionStatement implements FullStatement {

	FullExpression exp;
	
	public FullExpressionStatement(FullExpression exp) {
		this.exp = exp;
	}
	
	@Override 
	public String toString() {
		return exp.toString();
	}

	@Override
	public Pair<List<Pair<String, Name>>, Boolean> tryMatch(Statement s,
			Map<String, String> var2type) {
		
		// There will be an error if we want to match on this pattern.
		ErrorManager.error("[pattern type mistake] this pattern (FullExpressionStatement) should only be a target pattern!");
		
		return new Pair<List<Pair<String, Name>>, Boolean>(new ArrayList<Pair<String, Name>>(), false);
	}

}
