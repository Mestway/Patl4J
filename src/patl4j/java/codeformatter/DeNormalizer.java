package patl4j.java.codeformatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

// This visitor is used in patl4j.tools.ProjectTransformer
public class DeNormalizer extends ASTVisitor {
	
	CompilationUnit file;
	Map<String, Expression> name2exp = new HashMap<String, Expression>();
	
	public DeNormalizer(CompilationUnit icu) {
		file = icu;
	}
	
	// Only visit top level statements, note that the constructor is also a method declaration statement.
	public boolean visit(VariableDeclarationStatement node) {
		// TODO: collect the generated variables
		// name2exp.add(....);
		
		return false;
	}
	
	/* This is special, as you also want to collect the map between variables and the expression they stand for*/
	public boolean visit(Assignment assign) {
		
		// TODO...
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean visit(MethodInvocation mi) {
		Name target = (Name) mi.getExpression(); // The target of the method invocation term. e.g. x.f() => x
		if (name2exp.containsKey(target.getFullyQualifiedName())) {
			// To set an expression, you need to copy the expression with ASTNode.copySubtree(), 
			//  where the first argument is the ast of the parent, and the second is the expression to be copied.
			
			mi.setExpression((Expression) ASTNode.copySubtree(
					mi.getAST(), 
					name2exp.get(target.getFullyQualifiedName())));
		}
		
		// A list to store arguments after transformation
		// As I said, all arguements are names!!
		List<Expression> argList = new ArrayList<Expression>();
		for (Name e : (List<Name>)(mi.arguments())) {
			if (name2exp.containsKey(e.getFullyQualifiedName())) {
				argList.add(name2exp.get(e.getFullyQualifiedName()));
			} else {
				argList.add(e);
			}
		}
		// Then copy the arguments back to the method invocation
		mi.arguments().clear();
		for (Expression e : argList) {
			// Still, don't forget to copy the node using ASTNode.copySubtree()
			mi.arguments().add(ASTNode.copySubtree(mi.getAST(), e));
		}
		
		// Note that all names in a method invocation expression are names, so we do not need to visit its children,
		return false;
	}

}
