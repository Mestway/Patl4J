package patl4j.common.ast.full;

import org.eclipse.jdt.core.dom.Expression;

import patl4j.core.matcher.Matcher;

public interface FullExpression {
	/**
	 * Translate the expression into a Java expression, where the metavariables are substituted with variables matched to them in the Matcher m.
	 * Correspondence:
	 * 		FullAddExpression -> InfixExpression
	 * 		FullBinaryOp -> InfixExpression
	 * 		FullFieldAccess -> QualifiedName/FieldAccessExpression
	 * 		FullMethodInvk -> MethodInvocation
	 * 		FullNew -> ClassInstanceCreation
	 * 		FullVariable -> SimpleName
	 * @param m
	 * @return the expression 
	 */
	public Expression toJavaExp(Matcher m);
}
