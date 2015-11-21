package patl4j.normalize.core.normalizer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.ui.SharedASTProvider.WAIT_FLAG;

import jas.Method;
import patl4j.common.tools.ErrorManager;
import patl4j.common.util.Pair;
import patl4j.common.util.VariableGenerator;
import patl4j.normalize.core.normalizer.phases.Generator;
import patl4j.normalize.core.normalizer.phases.WrappedStatement;

public class Normalizer {
	
	// TODO: Some statement nodes are not implemented, maybe I will deal with them later.
	@SuppressWarnings("unchecked")
	public static WrappedStatement normalizeStmt(Statement input) {
		
		if (input instanceof AssertStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @19 Statement(AssertStatment):"+input);
			
			AssertStatement stmt = (AssertStatement) input;
			Pair<List<Statement>, Expression> wrappedExp = wrapExpression(normalizeExp(stmt.getExpression()));
			stmt.setExpression((Expression)ASTNode.copySubtree(stmt.getAST(), wrappedExp.getSecond()));
			wrappedExp.getFirst().add(stmt);
			return wrapStatement(wrappedExp.getFirst());
			
		} else if (input instanceof Block) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @29 Statement(Block):"+input);
			
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
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @48 Statement(ConstructorInvocation):"+input);
			
			ConstructorInvocation node = (ConstructorInvocation) input;
			List<Expression> argList = new ArrayList<Expression>();
			List<Statement> stmtList = new ArrayList<Statement>();
			for (Expression i : (List<Expression>)node.arguments()) {
				Pair<List<Statement>, Expression> pair = wrapExpression(normalizeExp(i));
				for (Statement j : pair.getFirst()) {
					stmtList.add(j);
				}
				argList.add(pair.getSecond());
			}
			node.arguments().clear();
			for (Expression i : argList) {
				node.arguments().add(ASTNode.copySubtree(node.getAST(), i));
			}
			stmtList.add(node);
			return wrapStatement(stmtList);
			
		} else if (input instanceof DoStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @69 Statement(DoStatement):"+input);
			
			DoStatement node = (DoStatement) input;
			ArrayList<Statement> result = new ArrayList<Statement>();
			Pair<List<Statement>, Expression> pair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression((Name) ASTNode.copySubtree(node.getAST(), pair.getSecond()));
			Statement doStmt = node.getBody();
			node.setBody((Statement) ASTNode.copySubtree(node.getAST(), wrapToBlock(normalizeStmt(doStmt).getStatement())));
			result.add(node);
			return wrapStatement(result);
			
		} else if (input instanceof EnhancedForStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @82 Statement(EnhancedForStatement):"+input);
			
			EnhancedForStatement node = (EnhancedForStatement) input;
			List<Statement> stmtList = new ArrayList<Statement>();
			Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getExpression()));
			for (Statement i : expPair.getFirst()) {
				stmtList.add(i);
			}
			
			WrappedStatement wrappedBody = normalizeStmt(node.getBody());
			node.setBody((Statement) ASTNode.copySubtree(node.getAST(), wrappedBody.getStatement()));
			
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			
			stmtList.add(node);
			return wrapStatement(stmtList);
			
		} else if (input instanceof ExpressionStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @101 Statement(ExpressionStatement):"+input);
			
			ExpressionStatement stmt = (ExpressionStatement) input;
			Pair<List<Statement>, Expression> wrappedExp = normalizeExp(stmt.getExpression());
			
			AST tempAst = AST.newAST(AST.JLS8);
			ExpressionStatement expStmt = tempAst.newExpressionStatement((Expression) ASTNode.copySubtree(tempAst, wrappedExp.getSecond()));
			
			wrappedExp.getFirst().add(expStmt);
			return wrapStatement(wrappedExp.getFirst());
			
		} else if (input instanceof ForStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @114 Statement(ForStatement):"+input);
			
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
						ErrorManager.error("Normalizer@line115", "Empty variable declaration fragment?");
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
			
			Statement body = wrapToBlock(normalizeStmt(node.getBody()).getStatement());
			node.setBody((Statement) ASTNode.copySubtree(node.getAST(), body));
			
			result.add(node);
			
			return wrapStatement(result);
			
		} else if (input instanceof IfStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @170 Statement(IfStatement):"+input);
			
			IfStatement node = (IfStatement) input;
			
			Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getExpression()));
			
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			
			Statement thenBlock = wrapToBlock(normalizeStmt(node.getThenStatement()).getStatement());
			
			((IfStatement)node).setThenStatement(
					(Statement) ASTNode.copySubtree(
							node.getAST(),
							thenBlock));
			
			// The elseBlock may not exist
			Statement elseBlock;
			if (node.getElseStatement() != null) {
				elseBlock = wrapToBlock(normalizeStmt(node.getElseStatement()).getStatement());
				((IfStatement)node).setElseStatement(
						(Statement) ASTNode.copySubtree(
								node.getAST(),
								elseBlock));
			} else {
				// Add a block with only empty statements to the else block
				elseBlock = wrapToBlock(normalizeStmt(AST.newAST(AST.JLS8).newEmptyStatement()).getStatement());
				((IfStatement)node).setElseStatement(
						(Statement) ASTNode.copySubtree(
								node.getAST(),
								elseBlock));
			}
			
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
			if (node.getExpression() != null) {
				Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getExpression()));
				node.setExpression(
					(Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
				expPair.getFirst().add(node);
				return wrapStatement(expPair.getFirst());
			} else {
				List<Statement> arrayList = new ArrayList<Statement>();
				arrayList.add(node);
				return wrapStatement(arrayList);
			} 
			
			
		} else if (input instanceof SuperConstructorInvocation) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @232 Statement(SuperConstructorInvocation):"+input);
			
			SuperConstructorInvocation node = (SuperConstructorInvocation) input;
			List<Statement> result = new ArrayList<Statement>();
			
			//Pair<List<Statement>, Name> expPair = wrapExpression(normalizeExp(node.getExpression()));
			//node.setExpression(
					//(Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			
			List<Expression> argList = new ArrayList<Expression>();
			for (Expression i : (List<Expression>)node.arguments()) {
				Pair<List<Statement>, Expression> tempPair = wrapExpression(normalizeExp(i));
				argList.add(tempPair.getSecond());
				for (Statement j : tempPair.getFirst()) {
					result.add(j);
				}
			}
			
			node.arguments().clear();
			for (Expression i : argList) {
				node.arguments().add(ASTNode.copySubtree(node.getAST(), i));
			}
			
			result.add(node);
			
			return wrapStatement(result); 
			
		} else if (input instanceof SwitchCase) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @261 Statement(SwitchCase):"+input);
			
			// Not sure how to normalize it
			return wrapStatement(input);
			
		} else if (input instanceof SwitchStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @268 Statement(SwitchStatement):"+input);
			
			// Not sure how to normalize it
			// I don't want to adapt it to IfStatement
			return wrapStatement(input);
		} else if (input instanceof SynchronizedStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @275 Statement(SynchronizedStatement):"+input);
			
			SynchronizedStatement node = (SynchronizedStatement) input;
	
			Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getExpression()));
			
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			node.setBody((Block) ASTNode.copySubtree(node.getAST(), normalizeStmt(node.getBody()).getStatement()));
			
			expPair.getFirst().add(node);
			
			return wrapStatement(expPair.getFirst());
			
		} else if (input instanceof ThrowStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @290 Statement(ThrowStatement):"+input);
			
			ThrowStatement node = (ThrowStatement) input;
			Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			expPair.getFirst().add(node);
			
			return wrapStatement(expPair.getFirst());
			
		} else if (input instanceof TryStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @301 Statement(TryStatement):"+input);
			
			TryStatement node = (TryStatement) input;
			node.setBody((Block) ASTNode.copySubtree(node.getAST(), normalizeStmt(node.getBody()).getStatement()));
			node.setFinally((Block) ASTNode.copySubtree(node.getAST(), normalizeStmt(node.getFinally()).getStatement()));
			
			for(CatchClause i : (List<CatchClause>)node.catchClauses()) {
				i.setBody((Block)ASTNode.copySubtree(i.getAST(), normalizeStmt(i.getBody()).getStatement()));
			}
			
			return wrapStatement(node);
			
		} else if (input instanceof TypeDeclarationStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @315 Statement(TypeDeclarationStatement):"+input);
			
			// not supported yet
			ErrorManager.unsupported("Normalizer@line265", "TypeDeclaration statement", "The statement is: " + input);
			return wrapStatement(input);
			
		} else if (input instanceof  VariableDeclarationStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @323 Statement(VariableDeclarationStatement):"+input);
			
			VariableDeclarationStatement node = (VariableDeclarationStatement)input;
			
			List<Statement> result = new ArrayList<Statement>();
			for(VariableDeclarationFragment i : (List<VariableDeclarationFragment>)node.fragments()) {

				AST tempAST = AST.newAST(AST.JLS8);
								
				/* We don't add any modifier 
				for (IExtendedModifier j : (List<IExtendedModifier>)node.modifiers()) {
					vs.modifiers().add(j);
				}
				*/
				
//				if (i.getInitializer() != null) {
//					Pair<List<Statement>, Expression> expPair = normalizeExp(i.getInitializer());
//					i.setInitializer((Expression) ASTNode.copySubtree(i.getAST(), expPair.getSecond()));
//					for (Statement j : expPair.getFirst()) {
//						result.add(j);
//					}
//				}
				
				
				// Add the normalized statements first
				if (i.getInitializer() != null) {
					Pair<List<Statement>, Expression> expPair = normalizeExp(i.getInitializer());
					i.setInitializer((Expression) ASTNode.copySubtree(i.getAST(), expPair.getSecond()));
					for (Statement j : expPair.getFirst()) {
						result.add(j);
					}
				}
				
				
				

				// Separate the declaration with the initializer into Decl + Assignment
				// E.g. A a = new A(b,c); will be transformed into A a; a = new A(b,c);
				VariableDeclarationFragment declPart = tempAST.newVariableDeclarationFragment();
				declPart.setName((SimpleName) ASTNode.copySubtree(tempAST, i.getName()));
				
				// Add the assginment statement
				if (i.getInitializer() != null) {
					declPart.setInitializer((Expression) ASTNode.copySubtree(tempAST, i.getInitializer()));
				}
				
				VariableDeclarationStatement vs = tempAST.newVariableDeclarationStatement((VariableDeclarationFragment) ASTNode.copySubtree(tempAST, declPart));
				vs.setType((Type) ASTNode.copySubtree(vs.getAST(), node.getType())); 
				
				result.add(vs);
				
			}
			
			return wrapStatement(result);
			
		} else if (input instanceof WhileStatement) {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @378 Statement(WhileStatement):"+input);
			
			WhileStatement node = (WhileStatement) input;
			
//			Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getExpression()));
			
			Pair<List<Statement>, Expression> expPair = new Pair(new ArrayList<>(), node.getExpression());
			
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			
			
			node.setBody((Block) ASTNode.copySubtree(node.getAST(), wrapToBlock(normalizeStmt(node.getBody()).getStatement())));
			
			expPair.getFirst().add(node);
			
			return wrapStatement(expPair.getFirst());
		} else {
			
			System.out.println("Normalizer >> method >> normalizeStmt >> @392 Statement(others):"+input);
			
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
			
			System.out.println("Normalizer >> method >> normalizeExp >> @407 Expression(ArrayAccess):"+exp);
			
			ArrayAccess node = (ArrayAccess) exp;
			Pair<List<Statement>, Expression> wrappedTarget = wrapExpression(normalizeExp(node.getArray()));
			Pair<List<Statement>, Expression> wrappedIndex = wrapExpression(normalizeExp(node.getIndex()));
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
			
			System.out.println("Normalizer >> method >> normalizeExp >> @430 Expression(ArrayCreation):"+exp);
			
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
			
			System.out.println("Normalizer >> method >> normalizeExp >> @448 Expression(ArrayInitializer):"+exp);
			
			ArrayInitializer node = (ArrayInitializer) exp;
			ArrayList<Expression> expList = new ArrayList<Expression>();
			ArrayList<Statement> stmtList = new ArrayList<Statement>();
			for (Expression i : (List<Expression>) node.expressions()) {
				Pair<List<Statement>, Expression> pair = wrapExpression(normalizeExp(i));
				//expList.add(pair.getSecond());
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
			
			System.out.println("Normalizer >> method >> normalizeExp >> @469 Expression(Assignment):"+exp);
			
			// Note that the left hand side expression of the assignment expression is always a name: 
			//		either a simple name or a qualified name
			
			// There are several ways to interpret FieldAccess terms, so take care of them
			// http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fjdt%2Fcore%2Fdom%2FFieldAccess.html
			
			Assignment node = (Assignment) exp;
			Pair<List<Statement>, Expression> lhsPair = normalizeExp(node.getLeftHandSide());
			if (lhsPair.getSecond() instanceof FieldAccess) {
				
				FieldAccess fa = (FieldAccess) lhsPair.getSecond();
				if (fa.getExpression() instanceof Name) {
					AST tempAST = AST.newAST(AST.JLS8);
					
					// Convert a field access term to a qualified name
					// TODO: double check if this is an right action::this conversion is dangerous
					QualifiedName qn = tempAST.newQualifiedName(
							(Name) ASTNode.copySubtree(tempAST,  fa.getExpression()), 
							(SimpleName )ASTNode.copySubtree(tempAST, fa.getName()));
					
					lhsPair.setSecond(qn);
				} else {
					ErrorManager.error("Normalizer@line394", "The field access term is neither of type Name.");
				}
			}
			
			Pair<List<Statement>, Expression> rhsPair = normalizeExp(node.getRightHandSide());
			
			for (Statement i : rhsPair.getFirst()) {
				lhsPair.getFirst().add(i);
			}
			
			node.setLeftHandSide((Expression) ASTNode.copySubtree(node.getAST(), lhsPair.getSecond()));
			node.setRightHandSide((Expression) ASTNode.copySubtree(node.getAST(), rhsPair.getSecond()));
			
			return new Pair<List<Statement>, Expression>(lhsPair.getFirst(), node); 
			
		} else if (exp instanceof CastExpression) {
			
			System.out.println("Normalizer >> method >> normalizeExp >> @510 Expression(CastExpression):"+exp);
			
			CastExpression node = (CastExpression) exp;
			Pair<List<Statement>, Expression> pair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), pair.getSecond()));
			return new Pair<List<Statement>, Expression>(pair.getFirst(), node);
			
		} else if (exp instanceof ClassInstanceCreation) {
			
			System.out.println("Normalizer >> method >> normalizeExp >> @519 Expression(ClassInstanceCreation):"+exp);
			
			ClassInstanceCreation node = (ClassInstanceCreation) exp;
			List<Statement> stmtList = new ArrayList<Statement>();
			
			if (node.getExpression() != null) {
				Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getExpression()));
				for (Statement i : expPair.getFirst()) {
					stmtList.add(i);
				}
				node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			}
			
			List<Expression> argList = new ArrayList<Expression>();
			for (Expression arg : (List<Expression>)node.arguments()) {
				Pair<List<Statement>, Expression> tempPair = wrapExpression(normalizeExp(arg));
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
			
			System.out.println("Normalizer >> method >> normalizeExp >> @550 Expression(ConditionalExpression):"+exp);
			
			ConditionalExpression node = (ConditionalExpression) exp;
			Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getExpression()));
			Pair<List<Statement>, Expression> thenPair = wrapExpression(normalizeExp(node.getThenExpression()));
			Pair<List<Statement>, Expression> elsePair = wrapExpression(normalizeExp(node.getElseExpression()));
			
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
		
			System.out.println("Normalizer >> method >> normalizeExp >> @572 Expression(FieldAccess):"+exp);
			
			FieldAccess node = (FieldAccess) exp;
			Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			return new Pair<List<Statement>, Expression>(expPair.getFirst(), node);
			
		} else if (exp instanceof InfixExpression) {
			
			//to avoid the situation of if(A && A.m()),while A is null, the A.m() will cause exception 
			if(exp.toString().contains("&&")){
				return new Pair<List<Statement>, Expression>(new ArrayList<>(), exp);
			}
			
			System.out.println("Normalizer >> method >> normalizeExp >> @581 Expression(InfixExpression):"+exp);
			
			InfixExpression node = (InfixExpression) exp;
			Pair<List<Statement>, Expression> lhsPair = wrapExpression(normalizeExp(node.getLeftOperand()));
			Pair<List<Statement>, Expression> rhsPair = wrapExpression(normalizeExp(node.getRightOperand()));
			
			for (Statement i : rhsPair.getFirst()) {
				lhsPair.getFirst().add(i);
			}
			
			// if there are extended operands
			if (node.hasExtendedOperands()) {
				List<Expression> extArgs = new ArrayList<Expression>();
				for (Expression i : (List<Expression>)node.extendedOperands()) {
					Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(i));
					for (Statement j : expPair.getFirst()) {
						lhsPair.getFirst().add(j);
					}
					extArgs.add(expPair.getSecond());
				}
				node.extendedOperands().clear();
				for (Expression i : extArgs) {
					node.extendedOperands().add(ASTNode.copySubtree(node.getAST(), i));
				}
			}
			
			node.setLeftOperand((Expression) ASTNode.copySubtree(node.getAST(), lhsPair.getSecond()));
			node.setRightOperand((Expression) ASTNode.copySubtree(node.getAST(), rhsPair.getSecond()));
			
			return new Pair<List<Statement>, Expression>(lhsPair.getFirst(), node);
			
		} else if (exp instanceof InstanceofExpression) {
			
			System.out.println("Normalizer >> method >> normalizeExp >> @614 Expression(InstanceofExpression):"+exp);
			
			InstanceofExpression node = (InstanceofExpression) exp;
			Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getLeftOperand()));
			node.setLeftOperand((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			return new Pair<List<Statement>, Expression>(expPair.getFirst(), node);
			
		} else if (exp instanceof MethodInvocation) {
			
			System.out.println("Normalizer >> method >> normalizeExp >> @623 Expression(MethodInvocation):"+exp);
			
			MethodInvocation methodInvk = (MethodInvocation) exp;
			
			// To hold all generated expression
			List<Statement> result = new ArrayList<Statement>();
			
			Pair<List<Statement>, Expression> wrappedTarget = null;
			// Deal with the target expression
			if (methodInvk.getExpression() == null) {
				methodInvk.setExpression((Expression) ASTNode.copySubtree(methodInvk.getAST(), AST.newAST(AST.JLS8).newThisExpression()));
				
				ASTNode node = methodInvk;
				Type varType = AST.newAST(AST.JLS8).newWildcardType();
				while(node != null && !(node instanceof TypeDeclaration)){
					node = node.getParent();
				}
				if(node != null){
					TypeDeclaration typeDeclaration = (TypeDeclaration) node;
//					System.out.println(typeDeclaration.getName());
					AST ast = AST.newAST(AST.JLS8);
					varType = ast.newSimpleType((SimpleName)ASTNode.copySubtree(ast, typeDeclaration.getName()));
				}
				
				
				/*
				 * 		AST varDeclFragAST = AST.newAST(AST.JLS8);
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
				 */
				List<Statement> list = new ArrayList<>();
				SimpleName name = Generator.genSimpleName(VariableGenerator.genVar());
				VariableDeclarationFragment varDecl = AST.newAST(AST.JLS8).newVariableDeclarationFragment();
				varDecl.setName((SimpleName)ASTNode.copySubtree(varDecl.getAST(), name));
				
				AST varDeclAST = AST.newAST(AST.JLS8);
				VariableDeclarationStatement varDeclStatement = varDeclAST.newVariableDeclarationStatement((VariableDeclarationFragment)ASTNode.copySubtree(varDeclAST, varDecl));
				varDeclStatement.setType((Type)ASTNode.copySubtree(varDeclAST, varType));
				list.add(varDeclStatement);
				
				Assignment assign = varDeclAST.newAssignment();
				assign.setLeftHandSide((Expression)ASTNode.copySubtree(varDeclAST, varDecl.getName()));
				assign.setRightHandSide((Expression)ASTNode.copySubtree(varDeclAST, methodInvk.getExpression()));
				ExpressionStatement es = varDeclAST.newExpressionStatement(assign);
				list.add(es);
				
				wrappedTarget = new Pair<List<Statement>, Expression>(list, name);
				
			}else{
				wrappedTarget = wrapExpression(normalizeExp(methodInvk.getExpression()));
			}
			
//			Pair<List<Statement>, Expression> wrappedTarget = wrapExpression(normalizeExp(methodInvk.getExpression()));
			
			for (Statement s : wrappedTarget.getFirst()) {
				result.add(s);
			}
			methodInvk.setExpression(
					(Name)ASTNode.copySubtree(
							methodInvk.getAST(), 
							wrappedTarget.getSecond()));
			
			// Deal with the arguments
			List<Pair<List<Statement>,Expression>> argList = new ArrayList<Pair<List<Statement>, Expression>>();
			for (Expression e : (List<Expression>) methodInvk.arguments()) {
				argList.add(wrapExpression(normalizeExp(e)));
			}
			methodInvk.arguments().clear();
			for (Pair<List<Statement>, Expression> i : argList) {
				// Add new arguments into
				// May be NullLiteral
				methodInvk.arguments().add(
						ASTNode.copySubtree(
								methodInvk.getAST(), 
								i.getSecond()
								));
				for (Statement s : i.getFirst()) {
					result.add(s);
				}
			}
			return new Pair<List<Statement>, Expression>(result, methodInvk); 
		
		} else if (exp instanceof ParenthesizedExpression) {
			
			System.out.println("Normalizer >> method >> normalizeExp >> @666 Expression(ParenthesizedExpression):"+exp);
			
			ParenthesizedExpression node = (ParenthesizedExpression) exp;
			Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getExpression()));
			node.setExpression(((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond())));
			return new Pair<List<Statement>, Expression>(expPair.getFirst(), node);
			
		} else if (exp instanceof PostfixExpression) {
			
			System.out.println("Normalizer >> method >> normalizeExp >> @675 Expression(PostfixExpression):"+exp);
			
			PostfixExpression node = (PostfixExpression) exp;
			Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getOperand()));
			node.setOperand((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			return new Pair<List<Statement>, Expression>(expPair.getFirst(), node);
			
		} else if (exp instanceof PrefixExpression) {
			
			System.out.println("Normalizer >> method >> normalizeExp >> @684 Expression(PrefixExpression):"+exp);
			
			PrefixExpression node = (PrefixExpression) exp;
			Pair<List<Statement>, Expression> expPair = wrapExpression(normalizeExp(node.getOperand()));
			node.setOperand((Expression) ASTNode.copySubtree(node.getAST(), expPair.getSecond()));
			return new Pair<List<Statement>, Expression>(expPair.getFirst(), node);
			
		} else if (exp instanceof SuperMethodInvocation) {
			
			System.out.println("Normalizer >> method >> normalizeExp >> @693 Expression(SuperMethodInvocation):"+exp);
			
			SuperMethodInvocation node = (SuperMethodInvocation) exp;
			
			List<Statement> stmtList = new ArrayList<Statement>();
			List<Expression> argList = new ArrayList<Expression>();
			for (Expression arg : (List<Expression>)node.arguments()) {
				Pair<List<Statement>, Expression> tempPair = wrapExpression(normalizeExp(arg));
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
			
			System.out.println("Normalizer >> method >> normalizeExp >> @716 Expression(VariableDeclarationExpression):"+exp);
			
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
			
		} else if(exp instanceof ThisExpression){
			
			System.out.println("Normalizer >> method >> normalizeExp >> @742 Expression(ThisExpression):"+exp);
			
			ThisExpression node = (ThisExpression)exp;
//			ASTNode e = exp.getParent();
//			while(!(e instanceof TypeDeclaration)){
//				e = e.getParent();
//			}
//			
//			TypeDeclaration typeDeclaration = (TypeDeclaration)e;
			
			return new Pair<List<Statement>, Expression>(new ArrayList<>(), node);
			
		}else {
			
			System.out.println("Normalizer >> method >> normalizeExp >> @749 Expression(others):"+exp);
			
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
	
	// Wrap an expression with a variable declaration and an assignment
	// The wrapped part should either be a name or a null
	private static Pair<List<Statement>, Expression> wrapExpression(Pair<List<Statement>,Expression> pair) {
		if (pair.getSecond() instanceof Name) {
			return new Pair<List<Statement>, Expression>(pair.getFirst(), (Name)pair.getSecond());
		} else if (pair.getSecond() instanceof NullLiteral) {
			return new Pair<List<Statement>, Expression>(pair.getFirst(), pair.getSecond());
		} /*else if (pair.getSecond() instanceof ThisExpression) {
			return new Pair<List<Statement>, Expression>(pair.getFirst(), pair.getSecond());
		}*/
		
		List<Statement> declStmts = Generator.genVarDeclStatement(pair.getSecond());
		
		for (Statement s : declStmts) {
			pair.getFirst().add(s);
		}
		
		// The name is always in the last of the result list
		VariableDeclarationStatement vds = (VariableDeclarationStatement) declStmts.get(0);

		return new Pair<List<Statement>, Expression>(pair.getFirst(), 
				((VariableDeclarationFragment) vds.fragments().get(0)).getName());
	}
	
	@SuppressWarnings("unchecked")
	private static Block wrapToBlock(Statement stmt) {
		if (stmt instanceof Block) {
			return (Block)stmt;
		} else {
			AST tAST = AST.newAST(AST.JLS8);
			Block blk = tAST.newBlock();
			blk.statements().add(ASTNode.copySubtree(tAST, stmt));
			return blk;
		}
	}
}
