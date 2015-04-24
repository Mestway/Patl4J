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
	
	// If a new variable is declared, we will store it here in the newVariable
	// First field: the type of the variable
	// Second field: the variable name
	Optional<Pair<String, String>> newVariable = Optional.ofNullable(null);
	
	public FullAssignment(String variable, FullExpression exp) {
		this.variable = variable;
		this.exp = exp;
	}

	public FullAssignment(String vartype, String variable, FullExpression exp) {
		this.variable = variable;
		this.exp = exp;
		this.newVariable = Optional.ofNullable(new Pair<String, String>(vartype, variable));
	}
	
	@Override
	public String toString() {
		if (newVariable.isPresent())
			return newVariable.get().getFirst() + " " + newVariable.get().getSecond() + " = " + exp.toString() + ";";
		else return this.variable + " = " + exp.toString() + ";";
	}	

	@Override
	public Pair<List<Pair<String, Name>>, Boolean> tryMatch(Statement s,
			Map<String, String> var2type) {
		
		// There will be an error if we want to match on this pattern.
		ErrorManager.error("FullAssignment@35", "The pattern (FullAssignment) should only be a target pattern!");
		
		return new Pair<List<Pair<String, Name>>, Boolean>(new ArrayList<Pair<String, Name>>(), false);
	}

	
}
