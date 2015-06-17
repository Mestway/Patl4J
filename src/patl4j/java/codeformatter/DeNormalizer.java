package patl4j.java.codeformatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.*;

// This visitor is used in patl4j.tools.ProjectTransformer
public class DeNormalizer extends ASTVisitor {
	
	CompilationUnit file;
	Map<String, Expression> name2exp;
	Map<String, Integer> nameCount;

	boolean firstRound;
	
	public DeNormalizer(CompilationUnit icu) {
		System.out.println("[[BEFORE DENORMALIZER]]");
		System.out.println(icu);
		System.out.println("[[AFTER DENORMALIZER]]");
		file = icu;
		name2exp = new HashMap<String, Expression>();
		nameCount = new HashMap<String, Integer>();
		firstRound = true;
	}
	
	public void setSecondRound() {
		firstRound = false;
	}
	
	boolean isAuxilVariable(String name) {
		return name.startsWith("genVar");
	}
	
	public boolean visit(VariableDeclarationStatement node) {
		List<VariableDeclarationFragment> fragments = node.fragments();
		String name = fragments.get(0).getName().getIdentifier();
		Expression initExp = fragments.get(0).getInitializer();
		if (!firstRound) {
			if (isAuxilVariable(name) && (nameCount.get(name) == null || nameCount.get(name).intValue() == 1)) {
				System.out.println("delete " + node);
				node.delete();
				return false;
			}
		} else {
			if (initExp != null) {
				tryToRecord(name, initExp);
			}
		}
		return true;
	}
	
	void tryToRecord(String name, Expression exp) {
		if (isAuxilVariable(name)) {
			Integer count = nameCount.get(name);
			System.out.println("put " + name + " | " + exp);
			if (count == null) {
				nameCount.put(name, new Integer(1));
				name2exp.put(name, exp);
			} else {
				count = new Integer(count.intValue() + 1);
			}
		}
	}
	
	public boolean visit(Assignment assign) {
		if (firstRound) {
			Expression leftHand = assign.getLeftHandSide();
			Expression rightHand = assign.getRightHandSide();
			if (leftHand instanceof Name) {
				String name = ((Name) leftHand).getFullyQualifiedName();
				tryToRecord(name, rightHand);
			}
			return false;
		}
		return true;
	}

	public void endVisit(Assignment assign) {
		if (!firstRound) {
			Expression leftHand = assign.getLeftHandSide();
			if (leftHand instanceof Name) {
				String name = ((Name) leftHand).getFullyQualifiedName();
				if (isAuxilVariable(name) && (nameCount.get(name) == null || nameCount.get(name).intValue() == 1)) {
					if (assign.getParent() instanceof ExpressionStatement) {
						System.out.println("delete " + assign.getParent());
						assign.getParent().delete();
					}
				}
			}
		}
	}
	
	public boolean visit(SimpleName exp) {
		if (!firstRound) {
			String name = exp.getFullyQualifiedName();
			System.out.println("$$$" + name);
			System.out.println("[" + name + "] Class: " + exp.getClass().getName() + " [" + exp.getParent() + "] pClass " + exp.getParent().getClass().getName());

			if (isAuxilVariable(name) && nameCount.get(name).intValue() == 1) {
				ASTNode p = exp.getParent();
				Expression newExp = (Expression) ASTNode.copySubtree(exp.getAST(), name2exp.get(name));
				
				System.out.println("[" + exp + "] -> " + "[" + p + "]");
				
				if (p instanceof AssertStatement) {
					System.out.println("@AssignStatement -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((AssertStatement) p).setExpression(newExp);
				}
				if (p instanceof ConstructorInvocation) {
					System.out.println("@ConstructorInvocation -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					List<Expression> args = ((ConstructorInvocation) p).arguments();
					args.set(args.indexOf(exp), newExp);
				}
				if (p instanceof DoStatement) {
					System.out.println("@DoStatement -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((DoStatement) p).setExpression(newExp);
				}
				if (p instanceof ExpressionStatement) {
					System.out.println("@ExpressionStatement -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((ExpressionStatement) p).setExpression(newExp);
				}
				if (p instanceof ForStatement) {
					System.out.println("@ForStatement -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((ForStatement) p).setExpression(newExp);
				}
				if (p instanceof IfStatement) {
					System.out.println("@IfStatement -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((IfStatement) p).setExpression(newExp);
				}
				if (p instanceof ReturnStatement) {
					System.out.println("@ReturnStatement -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((ReturnStatement) p).setExpression(newExp);
				}
				if (p instanceof SuperConstructorInvocation) {
					System.out.println("@SuperConstructorInvocation -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((SuperConstructorInvocation) p).setExpression(newExp);
				}
				if (p instanceof SwitchCase) {
					System.out.println("@SwitchCase -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((SwitchCase) p).setExpression(newExp);
				}
				if (p instanceof SwitchStatement) {
					System.out.println("@SwitchStatment -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((SwitchStatement) p).setExpression(newExp);
				}
				if (p instanceof ThrowStatement) {
					System.out.println("@ThrowStatement -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((ThrowStatement) p).setExpression(newExp);
				}
				if (p instanceof WhileStatement) {
					System.out.println("@WhileStatement -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((WhileStatement) p).setExpression(newExp);
				}
				if (p instanceof ArrayAccess) {
					if (((ArrayAccess) p).getIndex() == exp) {
						System.out.println("@ArrayAccess -> " + "Replace var " + name + " with exp " + newExp);
						System.out.println("Parent is " + p);
						((ArrayAccess) p).setIndex(newExp);
					}
					if (((ArrayAccess) p).getArray() == exp) {
						System.out.println("@ArrayAccess -> " + "Replace var " + name + " with exp " + newExp);
						System.out.println("Parent is " + p);
						((ArrayAccess) p).setIndex(newExp);
					}
				}
				if (p instanceof ArrayInitializer) {
					System.out.println("@ArrayInitializer -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					List<Expression> args = ((ArrayInitializer) p).expressions();
					args.set(args.indexOf(exp), newExp);
				}
				if (p instanceof Assignment) {
					if (((Assignment) p).getRightHandSide() == exp) {
						System.out.println("@Assignment -> " + "Replace var " + name + " with exp " + newExp);
						System.out.println("Parent is " + p);
						((Assignment) p).setRightHandSide(newExp);
					}
				}
				if (p instanceof CastExpression) {
					System.out.println("@CastExpression -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((CastExpression) p).setExpression(newExp);
				}
				if (p instanceof ClassInstanceCreation) {
					List<Expression> args = ((ClassInstanceCreation) p).arguments();
					if (args.indexOf(exp) != -1) {
						System.out.println("@ClassInstanceCreation -> " + "Replace var " + name + " with exp " + newExp);
						System.out.println("Parent is " + p);
						args.set(args.indexOf(exp), newExp);
					} else {
						System.out.println("@ClassInstanceCreation -> " + "Replace var " + name + " with exp " + newExp);
						System.out.println("Parent is " + p);
						((ClassInstanceCreation) p).setExpression(newExp);
					}
				}
				if (p instanceof ConditionalExpression) {
					System.out.println("@ClassInstanceCreation -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					if (((ConditionalExpression) p).getExpression() == exp) {
						((ConditionalExpression) p).setExpression(newExp);
					}
					if (((ConditionalExpression) p).getThenExpression() == exp) {
						((ConditionalExpression) p).setThenExpression(newExp);
					}
					if (((ConditionalExpression) p).getElseExpression() == exp) {
						((ConditionalExpression) p).setElseExpression(newExp);
					}
				}
				if (p instanceof FieldAccess) {
					System.out.println("@FileAccess -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((FieldAccess) p).setExpression(newExp);
				}
				if (p instanceof InfixExpression) {
					System.out.println("@InfixExpression -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					if (((InfixExpression) p).getLeftOperand() == exp) {
						((InfixExpression) p).setLeftOperand(newExp);
					}
					if (((InfixExpression) p).getRightOperand() == exp) {
						((InfixExpression) p).setRightOperand(newExp);
					}
				}
				if (p instanceof InstanceofExpression) {
					System.out.println("@InstanceofExpression -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((InstanceofExpression) p).setLeftOperand(newExp);
				}
				if (p instanceof MethodInvocation) {
					System.out.println("@MethodInvocation -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					if (((MethodInvocation) p).getExpression() == exp) {
						((MethodInvocation) p).setExpression(newExp);
					} else {
						List<Expression> args = ((MethodInvocation) p).arguments();
						args.set(args.indexOf(exp), newExp);
					}
				}
				if (p instanceof ParenthesizedExpression) {
					System.out.println("@ParenthesizedExpression -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((ParenthesizedExpression) p).setExpression(newExp);
				}
				if (p instanceof PostfixExpression) {
					System.out.println("@PostfixExpression -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((PostfixExpression) p).setOperand(newExp);
				}
				if (p instanceof PrefixExpression) {
					System.out.println("@PrefixExpression -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					((PrefixExpression) p).setOperand(newExp);
				}
				if (p instanceof SuperMethodInvocation) {
					System.out.println("@SuperMethodInvocation -> " + "Replace var " + name + " with exp " + newExp);
					System.out.println("Parent is " + p);
					List<Expression> args = ((SuperMethodInvocation) p).arguments();
					args.set(args.indexOf(exp), newExp);
				}
			}
		}
		return true;
	}
}
