package patl4j.java.normalizer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.*;

import patl4j.util.ErrorManager;
import patl4j.util.Pair;

public class Normalizer {
	
	// TODO: Some statement nodes are not implemented, maybe I will deal with them later.
	@SuppressWarnings("unchecked")
	public static WrappedStatement normalizeStmt(Statement input) {
		
		if (input instanceof AssertStatement) {
			
			AssertStatement stmt = (AssertStatement) input;
			Pair<List<Statement>, Name> wrappedExp = wrapExpression(normalizeExp(stmt.getExpression()));
			stmt.setExpression((Name)ASTNode.copySubtree(stmt.getAST(), wrappedExp.getSecond()));
			wrappedExp.getFirst().add(stmt);
			return wrapStatement(wrappedExp.getFirst());
			
		} else if (input instanceof Block) {
			
			Block node = (Block) input;
			ArrayList<WrappedStatement> blockStmts = new ArrayList<WrappedStatement>();
			for (Object i : node.statements()) {
				blockStmts.add(normalizeStmt((Statement)i));
			}
			node.statements().clear();
			for (WrappedStatement i : blockStmts) {
				for (Statement j : i.decomposeBlock()) {
					node.statements().add(
						(Statement)ASTNode.copySubtree(
								node.getAST(), j));
				}
			}
			return wrapStatement(node);
			
		} else if (input instanceof ConstructorInvocation) {
			
			ConstructorInvocation node = (ConstructorInvocation) input;
			List<Name> argList = new ArrayList<Name>();
			List<Statement> stmtList = new ArrayList<Statement>();
			for (Expression i : (List<Expression>)node.arguments()) {
				Pair<List<Statement>, Name> pair = wrapExpression(normalizeExp(i));
				for (Statement j : pair.getFirst()) {
					stmtList.add(j);
				}
				argList.add(pair.getSecond());
			}
			node.arguments().clear();
			for (Statement i : stmtList) {
				node.arguments().add(i);
			}
			stmtList.add(node);
			return wrapStatement(stmtList);
			
		} else if (input instanceof DoStatement) {
			
			DoStatement node = (DoStatement) input;
			ArrayList<Statement> result = new ArrayList<Statement>();
			Pair<List<Statement>, Name> pair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression((Name) ASTNode.copySubtree(node.getAST(), pair.getSecond()));
			Statement doStmt = node.getBody();
			node.setBody((Statement) ASTNode.copySubtree(node.getAST(), normalizeStmt(doStmt).getStatement()));
			result.add(node);
			return wrapStatement(result);
			
		} else if (input instanceof EnhancedForStatement) {
			
			EnhancedForStatement node = (EnhancedForStatement) input;
			List<Statement> stmtList = new ArrayList<Statement>();
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
			for (Statement i : expPair.getFirst()) {
				stmtList.add(i);
			}
			
			WrappedStatement wrappedBody = normalizeStmt(node.getBody());
			node.setBody((Statement) ASTNode.copySubtree(node.getAST(), wrappedBody.getStatement()));
			
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			
			stmtList.add(node);
			return wrapStatement(stmtList);
			
		} else if (input instanceof ExpressionStatement) {
			
			ExpressionStatement stmt = (ExpressionStatement) input;
			Pair<List<Statement>, Expression> wrappedExp = normalizeExp(stmt.getExpression());
			
			AST tempAst = AST.newAST(AST.JLS8);
			ExpressionStatement expStmt = tempAst.newExpressionStatement((Expression) ASTNode.copySubtree(tempAst, wrappedExp.getSecond()));
			
			wrappedExp.getFirst().add(expStmt);
			return wrapStatement(wrappedExp.getFirst());
			
		} else if (input instanceof ForStatement) {
			
			ForStatement node = (ForStatement) input;
			
			List<Statement> result = new ArrayList<Statement>();
			
			// pop out the initializers
			for (Expression i : (List<Expression>)node.initializers()) {
				Pair<List<Statement>, Expression> initializer = normalizeExp(i);
				// If they are VariableDeclaraionExpression, we generate corresponding variable declaration statements
				if (initializer.getSecond() instanceof VariableDeclarationExpression) {
					VariableDeclarationExpression vd = (VariableDeclarationExpression) initializer.getSecond();
					AST tmpAST = AST.newAST(AST.JLS8);
					if (vd.fragments().isEmpty()) {
						ErrorManager.error("Strange things happend: An empty variable declaration fragment");
					}
					
					VariableDeclarationStatement vs = tmpAST.newVariableDeclarationStatement(
							(VariableDeclarationFragment) ASTNode.copySubtree(
									tmpAST, 
									(VariableDeclarationFragment)vd.fragments().get(0)));
					
					vs.fragments().clear();
					for (VariableDeclarationFragment j : (List<VariableDeclarationFragment>)vd.fragments()) {
						vs.fragments().add(ASTNode.copySubtree(vs.getAST(), j));
					}
					vs.setType((Type) ASTNode.copySubtree(vs.getAST(), vd.getType()));
					initializer.getFirst().add(vs);
				}
				else {
					// other cases, they are ExpressionStatement, just pop out them
					AST tmpAST = AST.newAST(AST.JLS8);
					ExpressionStatement es = tmpAST.newExpressionStatement(
							(Expression) ASTNode.copySubtree(tmpAST, initializer.getSecond()));
					initializer.getFirst().add(es);
				}
				
				for (Statement j : (List<Statement>)initializer.getFirst()) {
					result.add(j);
				}
			}
			
			// clear initializers, as they are already popped out
			node.initializers().clear();
			
			Statement body = normalizeStmt(node.getBody()).getStatement();
			node.setBody((Statement) ASTNode.copySubtree(node.getAST(), body));
			
			result.add(node);
			
			return wrapStatement(result);
			
		} else if (input instanceof IfStatement) {
			
			IfStatement node = (IfStatement) input;
			
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
			
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			
			Statement thenBlock = normalizeStmt(node.getThenStatement()).getStatement();
			Statement elseBlock = normalizeStmt(node.getElseStatement()).getStatement();
			
			((IfStatement)node).setThenStatement(
					(Statement) ASTNode.copySubtree(
							node.getAST(),
							thenBlock));
			
			((IfStatement)node).setElseStatement(
					(Statement) ASTNode.copySubtree(
							node.getAST(),
							elseBlock));
			
			expPair.getFirst().add(node);
			return wrapStatement(expPair.getFirst());
		
		} else if (input instanceof LabeledStatement) {
			
			LabeledStatement node = (LabeledStatement) input;
			List<Statement> result = normalizeStmt(node.getBody()).decomposeBlock();
			node.setBody((Statement) ASTNode.copySubtree(node.getAST(),result.get(result.size()-1)));
			result.remove(result.size()-1);
			result.add(node);
			return wrapStatement(result);
			
		} else if (input instanceof ReturnStatement) {
			
			ReturnStatement node = (ReturnStatement) input;
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression(
					(Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			expPair.getFirst().add(node);
			return wrapStatement(expPair.getFirst());
			
		} else if (input instanceof SuperConstructorInvocation) {
			
			SuperConstructorInvocation node = (SuperConstructorInvocation) input;
			List<Statement> result = new ArrayList<Statement>();
			
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression(
					(Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			
			List<Name> argList = new ArrayList<Name>();
			for (Expression i : (List<Expression>)node.arguments()) {
				Pair<List<Statement>, Name> tempPair = wrapExpression(normalizeExp(i));
				argList.add(tempPair.getSecond());
				for (Statement j : tempPair.getFirst()) {
					result.add(j);
				}
			}
			
			node.arguments().clear();
			for (Name i : argList) {
				node.arguments().add(ASTNode.copySubtree(node.getAST(), i));
			}
			
			result.add(node);
			
			return wrapStatement(result); 
			
		} else if (input instanceof SwitchCase) {
			// Not sure how to normalize it
			return wrapStatement(input);
			
		} else if (input instanceof SwitchStatement) {
			// Not sure how to normalize it
			// I don't want to adapt it to IfStatement
			return wrapStatement(input);
		} else if (input instanceof SynchronizedStatement) {
			SynchronizedStatement node = (SynchronizedStatement) input;
	
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
			
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			node.setBody((Block) ASTNode.copySubtree(node.getAST(), normalizeStmt(node.getBody()).getStatement()));
			
			expPair.getFirst().add(node);
			
			return wrapStatement(expPair.getFirst());
			
		} else if (input instanceof ThrowStatement) {
			
			ThrowStatement node = (ThrowStatement) input;
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			expPair.getFirst().add(node);
			
			return wrapStatement(expPair.getFirst());
			
		} else if (input instanceof TryStatement) {
			
			TryStatement node = (TryStatement) input;
			node.setBody((Block) ASTNode.copySubtree(node.getAST(), normalizeStmt(node.getBody()).getStatement()));
			node.setFinally((Block) ASTNode.copySubtree(node.getAST(), normalizeStmt(node.getFinally()).getStatement()));
			
			for(CatchClause i : (List<CatchClause>)node.catchClauses()) {
				i.setBody((Block)ASTNode.copySubtree(i.getAST(), normalizeStmt(i.getBody()).getStatement()));
			}
			
			return wrapStatement(node);
			
		} else if (input instanceof TypeDeclarationStatement) {
			// not supported yet
			ErrorManager.error("TypeDeclaration statement");
			return wrapStatement(input);
			
		} else if (input instanceof  VariableDeclarationStatement) {
			
			VariableDeclarationStatement node = (VariableDeclarationStatement)input;
			
			List<Statement> result = new ArrayList<Statement>();
			for(VariableDeclarationFragment i : (List<VariableDeclarationFragment>)node.fragments()) {
				
				if (i.getInitializer() != null) {
					Pair<List<Statement>, Expression> expPair = normalizeExp(i.getInitializer());
					i.setInitializer((Expression) ASTNode.copySubtree(i.getAST(), expPair.getSecond()));
					for (Statement j : expPair.getFirst()) {
						result.add(j);
					}
				}
				
				AST tempAST = AST.newAST(AST.JLS8);
				VariableDeclarationStatement vs = tempAST.newVariableDeclarationStatement((VariableDeclarationFragment) ASTNode.copySubtree(tempAST, i));
				vs.setType((Type) ASTNode.copySubtree(vs.getAST(), node.getType())); 
				
				for (IExtendedModifier j : (List<IExtendedModifier>)node.modifiers()) {
					vs.modifiers().add(j);
				}
				
				result.add(vs);
			}
			
			return wrapStatement(result);
			
		} else if (input instanceof WhileStatement) {
			WhileStatement node = (WhileStatement) input;
			
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
			
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			node.setBody((Block) ASTNode.copySubtree(node.getAST(), normalizeStmt(node.getBody()).getStatement()));
			
			expPair.getFirst().add(node);
			
			return wrapStatement(expPair.getFirst());
		} else {
			// default case, including:
			//		BreakStatement
			//		ContinueStatement
			// 		EmptyStatement
			return wrapStatement(input);
		}
	}
	
	@SuppressWarnings("unchecked")
	// normalize an expression 
	private static Pair<List<Statement>,Expression> normalizeExp(Expression exp) {
		if (exp instanceof ArrayAccess) {
			
			ArrayAccess node = (ArrayAccess) exp;
			Pair<List<Statement>, Name> wrappedTarget = wrapExpression(normalizeExp(node.getArray()));
			Pair<List<Statement>, Name> wrappedIndex = wrapExpression(normalizeExp(node.getIndex()));
			for (Statement i : wrappedIndex.getFirst()) {
				wrappedTarget.getFirst().add(i);
			}
			
			node.setArray(
					(Expression) ASTNode.copySubtree(
							node.getAST(), 
							wrappedTarget.getSecond()));
			
			node.setIndex(
					(Expression) ASTNode.copySubtree(
							node.getAST(), 
							wrappedIndex.getSecond()));
			
			return new Pair<List<Statement>, Expression>(wrappedTarget.getFirst(), node);
			
		} else if (exp instanceof ArrayCreation) {
			
			ArrayCreation node = (ArrayCreation) exp;
			if (node.getInitializer() != null) {
				Pair<List<Statement>, Expression> pair = normalizeExp(node.getInitializer());
				
				node.setInitializer(
						(ArrayInitializer) ASTNode.copySubtree(
								node.getAST(), 
								pair.getSecond()));
				
				return new Pair<List<Statement>, Expression>(pair.getFirst(), node);
			}
			
			return new Pair<List<Statement>, Expression>(new ArrayList<Statement>(), exp);
			
		} else if (exp instanceof ArrayInitializer) {
			
			ArrayInitializer node = (ArrayInitializer) exp;
			ArrayList<Expression> expList = new ArrayList<Expression>();
			ArrayList<Statement> stmtList = new ArrayList<Statement>();
			for (Expression i : (List<Expression>) node.expressions()) {
				Pair<List<Statement>, Name> pair = wrapExpression(normalizeExp(i));
				expList.add(pair.getSecond());
				for (Statement j : pair.getFirst()) {	
					stmtList.add(j);
				}
				expList.add(pair.getSecond());
			}
			node.expressions().clear();
			for (Expression i : expList) {
				node.expressions().add(ASTNode.copySubtree(node.getAST(), i));
			}
			return new Pair<List<Statement>, Expression>(stmtList, node);
			
		} else if (exp instanceof Assignment) {
			
			Assignment node = (Assignment) exp;
			Pair<List<Statement>, Name> lhsPair = wrapExpression(normalizeExp(node.getLeftHandSide()));
			Pair<List<Statement>, Expression> rhsPair = normalizeExp(node.getRightHandSide());
			
			for (Statement i : rhsPair.getFirst()) {
				lhsPair.getFirst().add(i);
			}
			
			node.setLeftHandSide((Expression) ASTNode.copySubtree(node.getAST(), lhsPair.getSecond()));
			node.setRightHandSide((Expression) ASTNode.copySubtree(node.getAST(), rhsPair.getSecond()));
			
			return new Pair<List<Statement>, Expression>(lhsPair.getFirst(), node); 
			
		} else if (exp instanceof CastExpression) {
			
			CastExpression node = (CastExpression) exp;
			Pair<List<Statement>, Name> pair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), pair.getSecond()));
			return new Pair<List<Statement>, Expression>(pair.getFirst(), node);
			
		} else if (exp instanceof ClassInstanceCreation) {
			
			ClassInstanceCreation node = (ClassInstanceCreation) exp;
			List<Statement> stmtList = new ArrayList<Statement>();
			
			if (node.getExpression() != null) {
				Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
				for (Statement i : expPair.getFirst()) {
					stmtList.add(i);
				}
				node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			}
			
			List<Expression> argList = new ArrayList<Expression>();
			for (Expression arg : (List<Expression>)node.arguments()) {
				Pair<List<Statement>, Name> tempPair = wrapExpression(normalizeExp(arg));
				for (Statement i : tempPair.getFirst()) {
					stmtList.add(i);
				}
				argList.add(tempPair.getSecond());
			}
			
			node.arguments().clear();
			for (Expression i : argList) {
				node.arguments().add((Expression)ASTNode.copySubtree(node.getAST(), i));
			}
			
			return new Pair<List<Statement>, Expression>(stmtList, node);
			
		} else if (exp instanceof ConditionalExpression) {
			
			ConditionalExpression node = (ConditionalExpression) exp;
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
			Pair<List<Statement>, Name> thenPair = wrapExpression(normalizeExp(node.getThenExpression()));
			Pair<List<Statement>, Name> elsePair = wrapExpression(normalizeExp(node.getElseExpression()));
			
			for (Statement i : thenPair.getFirst()) {
				expPair.getFirst().add(i);
			}
			for (Statement i : elsePair.getFirst()) {
				expPair.getFirst().add(i);
			}
			
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			node.setThenExpression((Expression) ASTNode.copySubtree(node.getAST(), thenPair.getSecond()));
			node.setElseExpression((Expression) ASTNode.copySubtree(node.getAST(), elsePair.getSecond()));
			
			return new Pair<List<Statement>, Expression>(expPair.getFirst(), node);
			
		} else if (exp instanceof FieldAccess) {
			
			FieldAccess node = (FieldAccess) exp;
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			return new Pair<List<Statement>, Expression>(expPair.getFirst(), node);
			
		} else if (exp instanceof InfixExpression) {
			
			InfixExpression node = (InfixExpression) exp;
			Pair<List<Statement>, Name> lhsPair = wrapExpression(normalizeExp(node.getLeftOperand()));
			Pair<List<Statement>, Name> rhsPair = wrapExpression(normalizeExp(node.getRightOperand()));
			
			for (Statement i : rhsPair.getFirst()) {
				lhsPair.getFirst().add(i);
			}
			
			// if there are extended operands
			if (node.hasExtendedOperands()) {
				List<Name> extArgs = new ArrayList<Name>();
				for (Expression i : (List<Expression>)node.extendedOperands()) {
					Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(i));
					for (Statement j : expPair.getFirst()) {
						lhsPair.getFirst().add(j);
					}
					extArgs.add(expPair.getSecond());
				}
				node.extendedOperands().clear();
				for (Name i : extArgs) {
					node.extendedOperands().add(ASTNode.copySubtree(node.getAST(), i));
				}
			}
			
			node.setLeftOperand((Expression) ASTNode.copySubtree(node.getAST(), lhsPair.getSecond()));
			node.setRightOperand((Expression) ASTNode.copySubtree(node.getAST(), rhsPair.getSecond()));
			
			return new Pair<List<Statement>, Expression>(lhsPair.getFirst(), node);
			
		} else if (exp instanceof InstanceofExpression) {
			
			InstanceofExpression node = (InstanceofExpression) exp;
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getLeftOperand()));
			node.setLeftOperand((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			return new Pair<List<Statement>, Expression>(expPair.getFirst(), node);
			
		} else if (exp instanceof MethodInvocation) {
			MethodInvocation methodInvk = (MethodInvocation) exp;
			
			// To hold all generated expression
			List<Statement> result = new ArrayList<Statement>();
			
			// Deal with the target expression
			Pair<List<Statement>, Name> wrappedTarget = wrapExpression(normalizeExp(methodInvk.getExpression()));
			for (Statement s : wrappedTarget.getFirst()) {
				result.add(s);
			}
			methodInvk.setExpression(
					(Name)ASTNode.copySubtree(
							methodInvk.getAST(), 
							wrappedTarget.getSecond()));
			
			// Deal with the arguments
			List<Pair<List<Statement>,Name>> argList = new ArrayList<Pair<List<Statement>, Name>>();
			for (Expression e : (List<Expression>) methodInvk.arguments()) {
				argList.add(wrapExpression(normalizeExp(e)));
			}
			methodInvk.arguments().clear();
			for (Pair<List<Statement>, Name> i : argList) {
				// Add new arguments into 
				methodInvk.arguments().add(
						(Name)ASTNode.copySubtree(
								methodInvk.getAST(), 
								i.getSecond()
								));
				for (Statement s : i.getFirst()) {
					result.add(s);
				}
			}
			return new Pair<List<Statement>, Expression>(result, methodInvk); 
		
		} else if (exp instanceof ParenthesizedExpression) {
			
			ParenthesizedExpression node = (ParenthesizedExpression) exp;
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression(((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond())));
			return new Pair<List<Statement>, Expression>(expPair.getFirst(), node);
			
		} else if (exp instanceof PostfixExpression) {
			
			PostfixExpression node = (PostfixExpression) exp;
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getOperand()));
			node.setOperand((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			return new Pair<List<Statement>, Expression>(expPair.getFirst(), node);
			
		} else if (exp instanceof PrefixExpression) {
			
			PrefixExpression node = (PrefixExpression) exp;
			Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getOperand()));
			node.setOperand((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			return new Pair<List<Statement>, Expression>(expPair.getFirst(), node);
			
		} else if (exp instanceof SuperMethodInvocation) {
			
			SuperMethodInvocation node = (SuperMethodInvocation) exp;
			
			List<Statement> stmtList = new ArrayList<Statement>();
			List<Expression> argList = new ArrayList<Expression>();
			for (Expression arg : (List<Expression>)node.arguments()) {
				Pair<List<Statement>, Name> tempPair = wrapExpression(normalizeExp(arg));
				for (Statement i : tempPair.getFirst()) {
					stmtList.add(i);
				}
				argList.add(tempPair.getSecond());
			}
			
			node.arguments().clear();
			for (Expression i : argList) {
				node.arguments().add((Expression)ASTNode.copySubtree(node.getAST(), i));
			}
			
			return new Pair<List<Statement>, Expression>(stmtList, node);
			
		} else if (exp instanceof VariableDeclarationExpression) {
			
			VariableDeclarationExpression node = (VariableDeclarationExpression) exp;
			List<Statement> stmtList = new ArrayList<Statement>();
			List<VariableDeclarationFragment> vfList = new ArrayList<VariableDeclarationFragment>();
			
			for (VariableDeclarationFragment i : (List<VariableDeclarationFragment>)node.fragments()) {
				Pair<List<Statement>, VariableDeclarationFragment> vfPair = normalizeExp(i);
				for (Statement j : vfPair.getFirst()) {
					stmtList.add(j);
				}
				vfList.add(vfPair.getSecond());
			}
			
			node.fragments().clear();
			for (VariableDeclarationFragment i : vfList) {
				node.fragments().add(ASTNode.copySubtree(node.getAST(), i));
			}
			return new Pair<List<Statement>, Expression>(stmtList, node);
			
		} else {
			// The default case, including:
			// 		Annotation, 
			// 		BooleanLiteral
			// 		CharacterLiteral
			// 		Name
			// 		NullLiteral
			//		NumberLiteral
			// 		StringLiteral
			// 		SuperFieldAccess
			// 		ThisExpression
			// 		TypeLiteral
			return new Pair<List<Statement>, Expression>(new ArrayList<Statement>(), exp);
		}
	}
	
	private static Pair<List<Statement>, VariableDeclarationFragment> normalizeExp(VariableDeclarationFragment vf) {
		Pair<List<Statement>, Expression> expPair = normalizeExp(vf.getInitializer());
		vf.setInitializer((Expression) ASTNode.copySubtree(vf.getAST(), expPair.getSecond()));
		return new Pair<List<Statement>, VariableDeclarationFragment>(expPair.getFirst(), vf);
	}
	
	@SuppressWarnings("unchecked")
	private static WrappedStatement wrapStatement(List<Statement> stmtList) {
		// If there is no statement in the list, generate an empty statement
		if (stmtList.size() == 0) {
			return new WrappedStatement(AST.newAST(AST.JLS8).newEmptyStatement());
		} else if (stmtList.size() == 1) {
			return new WrappedStatement(stmtList.get(0));
		} else {
			Block block = AST.newAST(AST.JLS8).newBlock();
			for (Statement stmt : stmtList) {
				block.statements().add(ASTNode.copySubtree(block.getAST(), stmt));
			}
			WrappedStatement ws = new WrappedStatement(block);
			ws.setGenBlock();
			return ws;
		}
	}
	
	private static WrappedStatement wrapStatement(Statement stmt) {
		List<Statement> stmtList = new ArrayList<Statement>();
		stmtList.add(stmt);
		return wrapStatement(stmtList);
	}
	
	private static Pair<List<Statement>, Name> wrapExpression(Pair<List<Statement>,Expression> pair) {
		if (pair.getSecond() instanceof Name) {
			return new Pair<List<Statement>, Name>(pair.getFirst(), (Name)pair.getSecond());
		}
		
		VariableDeclarationStatement declStmt = Generator.genVarDeclStatement(pair.getSecond());
		pair.getFirst().add(declStmt);
		return new Pair<List<Statement>, Name>(pair.getFirst(), 
				((VariableDeclarationFragment) declStmt.fragments().get(0)).getName());
	}
}