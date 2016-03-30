package patl4j.transform.core.transformer.phases;

import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

import patl4j.common.ast.pattern.Rule;
import patl4j.common.util.VariableContext;
import patl4j.core.matcher.MatcherSet;

public class MatcherBinder {
	
	List<Rule> basicRuleSeq;
	MatcherSet matcherSet;
	
	public MatcherBinder(MatcherSet collection, List<Rule> rules) {
		this.matcherSet = collection;
		this.basicRuleSeq = rules;
	}
	
	// The entry function
	public MatcherSet bindMatcher(Block body, MatcherSet matchers, VariableContext context) {
		MatcherSet result = bind(body, matchers, context);
		return result.clear();
	}
	
	// The default binding method
	private MatcherSet bind(Statement statement, MatcherSet inSet, VariableContext context) {
		if (statement instanceof Block) {
			return bind((Block) statement, inSet, context);
		} else if (statement instanceof ExpressionStatement) {
			return bind((ExpressionStatement) statement, inSet, context);
		} else if (statement instanceof VariableDeclarationStatement) {
			return bind((VariableDeclarationStatement) statement, inSet, context);
		} else if (statement instanceof IfStatement) {
			return bind((IfStatement) statement, inSet, context);
		} else if (statement instanceof WhileStatement) {
			return bind((WhileStatement) statement, inSet, context);
		} else if (statement instanceof ForStatement) {
			return bind((ForStatement) statement, inSet, context);
		} else if (statement instanceof EnhancedForStatement) {
			return bind((EnhancedForStatement) statement, inSet, context);
		} else if (statement instanceof DoStatement) {
			return bind((DoStatement) statement, inSet, context);
		} else {
			return inSet;
		}
	}
	
	private MatcherSet bind(Block body, MatcherSet inSet, VariableContext context) {
		MatcherSet currentSet = inSet;
		for (Object i : body.statements()) {
			Statement s = (Statement) i;
			currentSet = bind(s, currentSet, context);
		}
		return currentSet;
	}
	
	/*
	 * 	Statements used directly for matcher update
	 */
	private MatcherSet bind(ExpressionStatement exp, MatcherSet inSet, VariableContext context) {
		return inSet.accept(exp, context);
	}
	
	private MatcherSet bind(VariableDeclarationStatement vds, MatcherSet inSet, VariableContext context) {
		return inSet.accept(vds, context);
	}
	
	/*
	 * 	If statement: Break and merge
	 */
	private MatcherSet bind(IfStatement ifs, MatcherSet inSet, VariableContext context) {
		Statement thenBlock = ifs.getThenStatement();
		Statement elseBlock = ifs.getElseStatement();
		return MatcherSet.merge(
				bind(thenBlock, inSet, context), 
				bind(elseBlock, inSet, context));
	}
	
	/*
	 * 	The following several statements are loop statements:
	 * 		we will create a new matcher when we dive into the loop body,
	 *  then clear it and merge it with the original matcher
	 */
	private MatcherSet bind(WhileStatement ws, MatcherSet inSet, VariableContext context) {
		Statement body = ws.getBody();
		MatcherSet ms = new MatcherSet(this.basicRuleSeq);
		return MatcherSet.merge(inSet, bind(body, ms, context).clear());
	}
	
	private MatcherSet bind(ForStatement fs, MatcherSet inSet, VariableContext context) {
		Statement body = fs.getBody();
		MatcherSet ms = new MatcherSet(this.basicRuleSeq);
		return MatcherSet.merge(inSet, bind(body, ms, context).clear());
	}
	
	private MatcherSet bind(DoStatement ds, MatcherSet inSet, VariableContext context) {
		Statement body = ds.getBody();
		MatcherSet ms = new MatcherSet(this.basicRuleSeq);
		return MatcherSet.merge(inSet, bind(body, ms, context).clear());
	}
	
	private MatcherSet bind(EnhancedForStatement efs, MatcherSet inSet, VariableContext context) {
		Statement body = efs.getBody();
		MatcherSet ms = new MatcherSet(this.basicRuleSeq);
		return MatcherSet.merge(inSet, bind(body,ms, context).clear());
	}
	
	/*
	 * 	TODO: Statements that might need to be implemented includes
	 * 		1) SynchronizedStatement
	 * 		2) TryStatement
	 * 		3) TypeDeclarationStatement
	 */
	
}
