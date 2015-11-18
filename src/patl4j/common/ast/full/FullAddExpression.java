package patl4j.common.ast.full;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;

import patl4j.common.tools.ErrorManager;
import patl4j.core.matcher.Matcher;

public class FullAddExpression implements FullExpression {
	List<FullVariable> operands = new ArrayList<FullVariable>();
	List<Boolean> operators = new ArrayList<Boolean>(); 
	
	public FullAddExpression(List<String> operands, List<Boolean> operators) {
		this.operands = new ArrayList<FullVariable>();
		for (String s : operands) {
			this.operands.add(new FullVariable(s));
		}
		this.operators = operators;
	}
	
	public List<FullVariable> getOperands() {
		return operands;
	}
	
	public List<Boolean> getOperators() {
		return operators;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < operands.size(); i ++) {
			if (i > 0)
				str += (operators.get(i-1)?"+":"-") + operands.get(i);
			else 
				str += operands.get(i);
		}
		return str;
	}

	@Override
	public Expression toJavaExp(Matcher m) {
		return listToInfixAddExp(this.operands, this.operators, m);
	}
	
	/**
	 * Recursively translate the expression model to real expressions.
	 * @param operands: The list of operands
	 * @param operators: the list of operators, the size is 1 less than the size of operands
	 * @param m: the matcher for translation
	 * @return the translated expression
	 */
	private Expression listToInfixAddExp(List<FullVariable> operands, List<Boolean> operators, Matcher m) {
		if (operands.size() != operators.size() + 1) {
			ErrorManager.error("FullAddExpression@54", "The size of operands does not match the size of operators.");
		}
		
		if (operands.size() == 1) {
			return operands.get(0).toJavaExp(m);
		}
		
		Expression left = listToInfixAddExp(operands.subList(0, operands.size()-1), operators.subList(0, operators.size()-1), m);
		Expression right = operands.get(operands.size()-1).toJavaExp(m);
		InfixExpression.Operator op = InfixExpression.Operator.toOperator(operators.get(operators.size()-1) ? "+" : "-");
		
		AST tAST = AST.newAST(AST.JLS8);
		InfixExpression ie = tAST.newInfixExpression();
		ie.setLeftOperand((Expression) ASTNode.copySubtree(tAST, left));
		ie.setRightOperand((Expression) ASTNode.copySubtree(tAST, right));
		ie.setOperator(op);
		
		return ie;
	}
	
}
