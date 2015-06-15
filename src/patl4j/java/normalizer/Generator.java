package patl4j.java.normalizer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import patl4j.util.VariableGenerator;

public class Generator {
	
	// generate a variable declaration with an initializer specified by the given expression
	// e.g. given x.f(a,b) ==> int y = x.f(a,b);
	public static List<Statement> genVarDeclStatement(Expression exp) {
		
		List<Statement> result = new ArrayList<Statement>();
		
		VariableDeclarationFragment fragment = AST.newAST(AST.JLS8).newVariableDeclarationFragment();
		
		
		
		ExpressionStatement assignmentStmt = genAssignmentStatement(exp);
		
		// The type of the generated variable
		Type varType = AST.newAST(AST.JLS8).newWildcardType();
		if (exp.resolveTypeBinding() != null) {
			if (exp.resolveTypeBinding().isPrimitive()) {
				switch (exp.resolveTypeBinding().getName()) {
				case "void": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.VOID); break;
				case "int": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.INT); break;
				case "char": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.CHAR); break;
				case "long": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.LONG); break;
				case "boolean": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.BOOLEAN); break;
				case "float": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.FLOAT); break;
				case "short" :varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.SHORT); break;
				case "byte": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.BYTE); break;
				}
			} else {
				
				// Option 1: only use the simplename
				/*
				SimpleName typeName = AST.newAST(AST.JLS8).newSimpleName(exp.resolveTypeBinding().getName());
				AST tempAST = AST.newAST(AST.JLS8);
				varType = tempAST.newSimpleType((Name) ASTNode.copySubtree(tempAST, typeName));
				*/

				varType = resolveQualifiedType(exp.resolveTypeBinding().getQualifiedName());
			}
		}
		// Declaration Fragment
		fragment.setName(
				(SimpleName) ASTNode.copySubtree(
						fragment.getAST(), 
						((SimpleName)((Assignment)assignmentStmt.getExpression()).getLeftHandSide())));
		//fragment.setInitializer((Expression) ASTNode.copySubtree(fragment.getAST(), exp));
		
		AST varDeclFragAST = AST.newAST(AST.JLS8);
		VariableDeclarationStatement decl = varDeclFragAST.newVariableDeclarationStatement(
				(VariableDeclarationFragment) ASTNode.copySubtree(varDeclFragAST, fragment));
		
		decl.setType((Type) ASTNode.copySubtree(decl.getAST(), varType));
		
		result.add(decl);
		
		// initializer is defined here as a separate statement
		Assignment assign = varDeclFragAST.newAssignment();
		assign.setLeftHandSide((Expression) ASTNode.copySubtree(varDeclFragAST, fragment.getName()));
		assign.setRightHandSide((Expression) ASTNode.copySubtree(varDeclFragAST, exp));
		ExpressionStatement assignStmt = varDeclFragAST.newExpressionStatement(assign);
		
		result.add(assignStmt);
		
		return result;
	}
	
	// Generate a statement with assignment
	// E.g. input: x.f(y) ==> x = x.f(y);
	public static ExpressionStatement genAssignmentStatement(Expression exp) {
		Assignment assignExp = AST.newAST(AST.JLS8).newAssignment();
		// generate a new left hand side variable
		assignExp.setLeftHandSide(
				(Expression) ASTNode.copySubtree(
						assignExp.getAST(), 
						genSimpleName(VariableGenerator.genVar())));

		assignExp.setRightHandSide(
				(Expression) ASTNode.copySubtree(assignExp.getAST(), 
						exp));
		assignExp.setOperator(Assignment.Operator.ASSIGN);
		
		AST newAst = AST.newAST(AST.JLS8);
		return newAst.newExpressionStatement((Expression) ASTNode.copySubtree(newAst, assignExp));
	}
	
	// generate an empty block
	public static Block genBlock() {
		return AST.newAST(AST.JLS8).newBlock();
	}
	
	// generate a SimpleName node
	public static SimpleName genSimpleName(String s) {
		return AST.newAST(AST.JLS8).newSimpleName(s);
	}
	
	private static Type resolveQualifiedType(String s) {
		System.out.println("Œ“‘⁄ƒƒ " + s);
		if (s.contains("<") && s.contains(">")) {
			// TODO: resolve type parameters
			String s0 = s.substring(0, s.indexOf("<"));
			Type hd = resolveQualifiedType(s0);
			System.out.println(hd);
			AST tast = AST.newAST(AST.JLS8);
			ParameterizedType pt = tast.newParameterizedType((Type) ASTNode.copySubtree(tast, hd));
			return pt;
		}
		
		if(s.contains("[") && s.contains("]")) {
			String s0 = s.substring(0, s.indexOf("["));
			Type hd = resolveQualifiedType(s0);
			AST tast = AST.newAST(AST.JLS8);
			ArrayType pt = tast.newArrayType((Type) ASTNode.copySubtree(tast, hd));
			return pt;
		}
		
		if (!s.contains(".")) {
			AST ast = AST.newAST(AST.JLS8);
			if (s == null || s.equals("null")) {
				return ast.newSimpleType((Name) ASTNode.copySubtree(ast, genSimpleName("String")));
			}
			return ast.newSimpleType((Name) ASTNode.copySubtree(ast, genSimpleName(s)));
		} else {
			int last = s.lastIndexOf(".");
			AST ast = AST.newAST(AST.JLS8);
			String lastFrag = s.substring(last+1);
			return ast.newQualifiedType(
					(Type)ASTNode.copySubtree(ast, resolveQualifiedType(s.substring(0,last))), 
					(SimpleName)ASTNode.copySubtree(ast, genSimpleName(lastFrag)));
		}
	}

}
