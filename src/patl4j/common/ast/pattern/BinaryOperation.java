package patl4j.common.ast.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;

import patl4j.common.tools.ErrorManager;
import patl4j.common.util.Pair;
import patl4j.common.util.VariableContext;

public class BinaryOperation implements RHSPattern {
	
	MetaVariable first;
	MetaVariable second;
	// For simplicity consideration, just make it one with 
	String operator;
	
	public BinaryOperation(MetaVariable op1, MetaVariable op2, String operator) {
		this.first = op1;
		this.second = op2;
		this.operator = operator;
	}
	
	@Override
	public String toString() {
		return this.first.getName() + " " + operator + " " + this.second.getName();
	}

	@Override
	public Pair<List<Pair<String, Name>>, Boolean> tryMatch(Expression exp,
			Map<String, String> var2type,
			VariableContext context) {
		List<Pair<String, Name>> matchedVarList = new ArrayList<Pair<String, Name>>();
		Boolean matchedSuccessful = true;
		
		if (exp instanceof InfixExpression) {
			InfixExpression ie = (InfixExpression) exp;
			if (this.operator.equals(ie.getOperator().toString())) {
				Expression left = ie.getLeftOperand();
				Expression right = ie.getRightOperand();
				if (!(left instanceof SimpleName && right instanceof SimpleName)) {
					// TODO: add type check
					System.out.println("[Typeinfo BinaryOperation 46] " + left.resolveTypeBinding() + " " + var2type.get(this.first));
					matchedVarList.add(new Pair<String, Name>(this.first.getName(), (SimpleName)left));
					// TODO: add type check
					System.out.println("[Typeinfo BinaryOperation 49] " + right.resolveTypeBinding() + " " + var2type.get(this.second));
					matchedVarList.add(new Pair<String, Name>(this.second.getName(), (SimpleName)right));
				} else {
					// The argument of the expression is not normalized
					ErrorManager.error("BinaryOperation@lien53", "Operands are not normalized.");
					matchedSuccessful = false;
				}
			} else {
				// The operator does not match
				matchedSuccessful = false;
			}
		} else {
			// It is not an infix expression
			matchedSuccessful = false;
		}
		
		return new Pair<List<Pair<String, Name>>, Boolean>(matchedVarList, matchedSuccessful);
	} 
}
