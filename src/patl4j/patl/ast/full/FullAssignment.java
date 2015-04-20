package patl4j.patl.ast.full;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.util.ErrorManager;
import patl4j.util.Pair;

public class FullAssignment implements FullStatement{

	// TODO: Could be field access????
	String variable;
	FullExpression exp;
	
	public FullAssignment(String variable, FullExpression exp) {
		this.variable = variable;
		this.exp = exp;
	}

	@Override
	public String toString() {
		return variable + "=" + exp.toString() + ";";
	}

	@Override
	public Pair<List<Pair<String, Name>>, Boolean> tryMatch(Statement s,
			Map<String, String> var2type) {
		
		// There will be an error if we want to match on this pattern.
		ErrorManager.error("[FullAssignment@35] The pattern (FullAssignment) should only be a target pattern!");
		
		return new Pair<List<Pair<String, Name>>, Boolean>(new ArrayList<Pair<String, Name>>(), false);
	}

	
}
