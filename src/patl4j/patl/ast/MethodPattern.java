package patl4j.patl.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;

import patl4j.util.ErrorManager;
import patl4j.util.Pair;
import patl4j.util.TypeHandler;
import patl4j.util.VariableContext;

public class MethodPattern implements PEPattern {

	MetaVariable target;
	String methodName;
	List<MetaVariable> argList;
	
	public MethodPattern(MetaVariable target, String methodName, List<MetaVariable> argList) {
		this.target = target;
		this.methodName = methodName;
		this.argList = argList;
	}
	
	@Override
	public String toString() {
		String args = "(";
		boolean flag = true;
		for (MetaVariable i : argList) {
			if (!flag)
				args += "," + i.getName();
			else {
				args += i.getName();
				flag = false;
			}
		}
		args += ")";
		return target + "." + methodName + args;
	}

	@Override
	public Pair<List<Pair<String, Name>>, Boolean> tryMatch(Expression exp,
			Map<String, String> var2type,
			VariableContext context) {

		List<Pair<String, Name>> matchedVarList = new ArrayList<Pair<String, Name>>();
		Boolean matchedSuccessful = true;
		
		if (exp instanceof MethodInvocation) {
			MethodInvocation mi = (MethodInvocation) exp;
			
			// Check whether the method name is correct
			if (this.methodName.equals(mi.getName().getIdentifier())) {
				
				// Check the target expression
				if (mi.getExpression() instanceof Name) {
					
					// For debugging purpose, print the type of the pattern and expression
					TypeHandler.printTypeMatchInfo(mi.getExpression(), var2type.get(this.target), "MethodPattern@line62");
					
					// Type check on the target expression
					// TypeHandler.typeMatchCheck(mi.getExpression(), var2type.get(this.target))
					if (context.variableMatchCheck(mi.getExpression(), this.target)) {
						matchedVarList.add(new Pair<String, Name>(this.target.getName(), (Name) mi.getExpression()));
					} else {
						matchedSuccessful = false;
					}
					
					// Check the arguments. (They are supposed to be names)
					if (mi.arguments().size() == this.argList.size()) {
						for (Integer i = 0; i < mi.arguments().size(); i ++) {
							Expression arg = (Expression) mi.arguments().get(i);
							
							if (arg instanceof Name) {	
								// For debugging purpose, print the type of the pattern and expression
								TypeHandler.printTypeMatchInfo(arg, var2type.get(this.argList.get(i)), "MethodPattern@line74");
								
								// Type check on the arguments
								// TypeHandler.typeMatchCheck(arg, var2type.get(this.argList.get(i)))
								if (context.variableMatchCheck(arg, this.argList.get(i))) {
									matchedVarList.add(new Pair<String, Name>(this.argList.get(i).getName(), (Name)arg));
								} else {
									matchedSuccessful = false;
								}
							} else {
								ErrorManager.error("MethodPattern@line87", "Arguments in method not normalized.", "The expression is: " + exp);
								matchedSuccessful = false;
							}
						}
					} else {
						matchedSuccessful = false;
					}
				} else {
					ErrorManager.error("MethodPattern@line95", "method invocation normalizing problem.", "The expression is: " + exp);
					matchedSuccessful = false;
				}
			} else {
				matchedSuccessful = false;
			}
		} else {
			// the structure is not a method invocation.
			matchedSuccessful = false;
		}
		
		return new Pair<List<Pair<String, Name>>, Boolean>(matchedVarList, matchedSuccessful);
	}

}
