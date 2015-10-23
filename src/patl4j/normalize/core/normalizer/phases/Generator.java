package patl4j.normalize.core.normalizer.phases;

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
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WildcardType;

import patl4j.common.util.VariableGenerator;

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
				case "double": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.DOUBLE); break;
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
				
				System.out.println("In Generator.java @60 genVarDeclStatement "+exp+","+exp.resolveTypeBinding().getQualifiedName());

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
		System.out.println("In the Generator.java @115 genSimpleName  "+s);
		return AST.newAST(AST.JLS8).newSimpleName(s);
	}
	
	public static Type resolveQualifiedType(String s) {
		
		System.out.println("In the Generator.java @124 resolveQualifiedType  "+s);
		
		Type varType = null;
		switch (s) {
			case "void": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.VOID); return varType;
			case "int": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.INT); return varType;
			case "char": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.CHAR); return varType;
			case "long": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.LONG); return varType;
			case "boolean": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.BOOLEAN); return varType;
			case "double": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.DOUBLE); return varType;
			case "float": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.FLOAT); return varType;
			case "short" :varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.SHORT); return varType;
			case "byte": varType = AST.newAST(AST.JLS8).newPrimitiveType(PrimitiveType.BYTE); return varType;
		}
		
		if(s.startsWith("?")){
			String a[] = s.split(" ");
			if(a.length <= 1){
				return AST.newAST(AST.JLS8).newWildcardType();
			}
			return resolveQualifiedType(a[a.length-1]);
		}
		
		if (s.equals("")) {
			return AST.newAST(AST.JLS8).newWildcardType();
		}
		
		// It's a type parameter
		if (s.contains("<") && s.contains(">")) {
			// TODO: resolve type parameters
			String s0 = s.substring(0, s.indexOf("<"));
			Type hd = resolveQualifiedType(s0);
			AST tAST = AST.newAST(AST.JLS8);
			ParameterizedType pt = tAST.newParameterizedType((Type) ASTNode.copySubtree(tAST, hd));

			for (String i : splitTypeArgs(s.substring(s.indexOf("<")+1,s.lastIndexOf(">")))) {
				pt.typeArguments().add(ASTNode.copySubtree(tAST, resolveQualifiedType(i)));
			}
			return pt;
		}
		
		// It's an array type
		if(s.contains("[") && s.contains("]")) {
			String s0 = s.substring(0, s.indexOf("["));
			
			System.out.println("In Generator @145 resolveQualifiedType "+s0);
			
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
	
	private static List<String> splitTypeArgs(String argStr) {
		List<String> args = new ArrayList<String>();
		if (argStr.equals(""))
			return args;
		int i = 0;
		int levelCount = 0, lastLeft = 0;
		while(i < argStr.length()) {
			if (argStr.charAt(i) == '<')
				levelCount ++;
			else if (argStr.charAt(i) == '>')
				levelCount --;
			else if (argStr.charAt(i) == ',') {
				if (levelCount == 0) {
					args.add(argStr.substring(lastLeft, i).trim());
					lastLeft = i + 1;
				}
			}
			i ++;
		}
		args.add(argStr.substring(lastLeft, i));
		return args;
	}

}
