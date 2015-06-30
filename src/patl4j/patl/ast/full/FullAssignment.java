package patl4j.patl.ast.full;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import patl4j.matcher.Matcher;
import patl4j.util.ErrorManager;
import patl4j.util.Pair;
import patl4j.util.VariableContext;

public class FullAssignment implements FullStatement{

	// TODO: Could be field access????
	String variable;
	FullExpression exp;
	
	// If a new variable is declared, we will store it here in the newVariable
	// First field: the type of the variable
	// Second field: the variable name
	Optional<Pair<String, String>> newVariable = Optional.ofNullable(null);
	
	public String getVariable() {
		return variable;
	}
	
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
			Map<String, String> var2type,
			VariableContext context) {
		
		// There will be an error if we want to match on this pattern.
		ErrorManager.error("FullAssignment@35", "The pattern (FullAssignment) should only be a target pattern!");
		
		return new Pair<List<Pair<String, Name>>, Boolean>(new ArrayList<Pair<String, Name>>(), false);
	}

	@Override
	public Statement toJavaStatement(Matcher m) {
		if (this.isNewlyDefinedVariable()) {
			// In this case, we will translate it to VariableDeclarationStatement 
			// (As the statement pattern is defined as a variable declaration pattern, 
			// take care, it is totally different from the next situation)
			// The pattern itself is in the form: Type variable = exp;
			
			// In this case, we shall first generate a new variable name to the pattern
			m.bindMetaVariableToNewName(this.variable);
			
			AST tAST = AST.newAST(AST.JLS8);
			VariableDeclarationFragment vdf = tAST.newVariableDeclarationFragment();
			vdf.setName(tAST.newSimpleName(m.getMetaVariableImage(this.variable).getFirst().get().getStr()));
			vdf.setInitializer((Expression) ASTNode.copySubtree(tAST, this.exp.toJavaExp(m)));
			VariableDeclarationStatement vds = tAST.newVariableDeclarationStatement(vdf);
			vds.setType(tAST.newSimpleType(tAST.newSimpleName(this.newVariable.get().getFirst())));
			return vds;
		} /*else if (m.markedAsDecl(this.variable)) {
			// In this case, we will translate it to VariableDeclarationStatement, as the variable it mapped to is a variable delcaration statement
			// The assignment pattern is: variable = exp; In this case the variable <-> oldVar and oldVar appeared in a variable declaration statement.
			
			// In this case, we shall first generate a new variable name to the pattern
			m.bindMetaVariableToNewName(this.variable);			
			
			AST tAST = AST.newAST(AST.JLS8);
			VariableDeclarationFragment vdf = tAST.newVariableDeclarationFragment();
			vdf.setName(tAST.newSimpleName(m.getMetaVariableImage(this.variable).getFirst().get().getStr()));
			vdf.setInitializer((Expression) ASTNode.copySubtree(tAST, this.exp.toJavaExp(m)));
			VariableDeclarationStatement vds = tAST.newVariableDeclarationStatement(vdf);
			vds.setType(tAST.newSimpleType(tAST.newSimpleName(m.getMetaVariableImage(this.variable).getSecond().getSecond())));
			// now the variable is declared, so remove the variable from the declaration set to avoid new definition again.
			m.removeFromDecl(this.variable);
			return vds;
		} */ else {
			// In this case, we will translate it to ExpressionStatement(with Assignment)
			AST tAST = AST.newAST(AST.JLS8);
			Assignment newAssignment = tAST.newAssignment();
			newAssignment.setRightHandSide((Expression) ASTNode.copySubtree(tAST, this.exp.toJavaExp(m)));
			newAssignment.setLeftHandSide((Expression) ASTNode.copySubtree(tAST, new FullVariable(this.variable).toJavaExp(m)));
			ExpressionStatement es = tAST.newExpressionStatement(newAssignment);
			return es;
		}
	}
	
	private boolean isNewlyDefinedVariable() {
		return this.newVariable.isPresent();
	}
}
