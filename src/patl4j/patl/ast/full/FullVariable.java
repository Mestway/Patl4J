package patl4j.patl.ast.full;

import java.util.Optional;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.StringLiteral;

import patl4j.matcher.Matcher;
import patl4j.matcher.WrappedName;
import patl4j.util.Pair;

public class FullVariable implements FullExpression {
	String variable;
	
	public FullVariable(String variable) {
		this.variable = variable;
	}
	
	@Override
	public String toString() {
		return variable;
	}
	
	@Override
	public Expression toJavaExp(Matcher m) {
		// Depending on the binding of the variable, return its corresponding value.
		Pair<Optional<WrappedName>, Pair<String, String>> vi = m.getMetaVariableImage(this.variable);
		if (vi.getFirst() == null || !vi.getFirst().isPresent() || vi.getFirst().get().equals("%empty%")) {
			
			if (this.isNumberLiteral(this.variable)) {	
				// The variable is a numerical literal
				NumberLiteral numberNode = AST.newAST(AST.JLS8).newNumberLiteral();
				numberNode.setToken(this.variable);
				return numberNode;
			} else if (this.isQuotedString(this.variable)) {
				StringLiteral stringNode = AST.newAST(AST.JLS8).newStringLiteral();
				stringNode.setEscapedValue(this.variable);
				return stringNode;
			} else if (this.variable.equals("null")) {
				return AST.newAST(AST.JLS8).newNullLiteral();
			} else {
				return AST.newAST(AST.JLS8).newSimpleName(this.variable);
			}
		} else {
			return vi.getFirst().get().getName();
		}
	}
	
	/**
	 * TODO: Should be improved to actually match the real case.
	 * ######### Important #############
	 * @param input
	 * @return
	 */
	private boolean isQuotedString(String input) {
		if (input.startsWith("\"") && input.endsWith("\""))
			return true;
		return false;
	}

	/**
	 * TODO: Should be improved to actually match the real case.
	 * ######### Important #############
	 * @param input
	 * @return
	 */
	private boolean isNumberLiteral(String input) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch(Exception e) {
	    	try {
	    		Double.parseDouble(input);
	    		return true;
	    	} catch (Exception e2){
	    		return false;
	    	}
	    }
	}
	
}
