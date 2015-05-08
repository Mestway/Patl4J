package patl4j.core.transformer.phases;

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

import patl4j.matcher.MatcherSet;
import patl4j.patl.ast.Rule;

public class MatcherBinder {
	
	List<Rule> basicRuleSeq;
	MatcherSet matcherSet;
	
	public MatcherBinder(MatcherSet collection, List<Rule> rules) {
		this.matcherSet = collection;
		this.basicRuleSeq = rules;
	}
	
	// The entry function
	public MatcherSet bindMatcher(Block body, MatcherSet matchers) {
		MatcherSet result = bind(body, matchers);
		return result.clear();
	}
	
	// The default binding method
	private MatcherSet bind(Statement statement, MatcherSet inSet) {
		if (statement instanceof Block) {
			return bind((Block) statement, inSet);
		} else if (statement instanceof ExpressionStatement) {
			return bind((ExpressionStatement) statement, inSet);
		} else if (statement instanceof VariableDeclarationStatement) {
			return bind((VariableDeclarationStatement) statement, inSet);
		} else if (statement instanceof IfStatement) {
			return bind((IfStatement) statement, inSet);
		} else if (statement instanceof WhileStatement) {
			return bind((WhileStatement) statement, inSet);
		} else if (statement instanceof ForStatement) {
			return bind((ForStatement) statement, inSet);
		} else if (statement instanceof EnhancedForStatement) {
			return bind((EnhancedForStatement) statement, inSet);
		} else if (statement instanceof DoStatement) {
			return bind((DoStatement) statement, inSet);
		} else {
			return inSet;
		}	
	}
	
	private MatcherSet bind(Block body, MatcherSet inSet) {
		MatcherSet currentSet = inSet;		
		for (Object i : body.statements()) {
			Statement s = (Statement) i;
			currentSet = bind(s, currentSet);
		}
		return currentSet;
	}
	
	/*
	 * 	Statements used directly for matcher update
	 */
	private MatcherSet bind(ExpressionStatement exp, MatcherSet inSet) {
		return inSet.accept(exp);
	}
	
	private MatcherSet bind(VariableDeclarationStatement vds, MatcherSet inSet) {
		return inSet.accept(vds);
	}
	
	/*
	 * 	If statement: Break and merge
	 */
	private MatcherSet bind(IfStatement ifs, MatcherSet inSet) {
		Statement thenBlock = ifs.getThenStatement();
		Statement elseBlock = ifs.getElseStatement();
		return MatcherSet.merge(
				bind(thenBlock, inSet), 
				bind(elseBlock, inSet));
	}
	
	/*
	 * 	The following several statements are loop statements:
	 * 		we will create a new matcher when we dive into the loop body,
	 *  then clear it and merge it with the original matcher
	 */
	private MatcherSet bind(WhileStatement ws, MatcherSet inSet) {
		Statement body = ws.getBody();
		MatcherSet ms = new MatcherSet(this.basicRuleSeq);
		return MatcherSet.merge(inSet, bind(body, ms).clear());
	}
	
	private MatcherSet bind(ForStatement fs, MatcherSet inSet) {
		Statement body = fs.getBody();
		MatcherSet ms = new MatcherSet(this.basicRuleSeq);
		return MatcherSet.merge(inSet, bind(body, ms).clear());
	}
	
	private MatcherSet bind(DoStatement ds, MatcherSet inSet) {
		Statement body = ds.getBody();
		MatcherSet ms = new MatcherSet(this.basicRuleSeq);
		return MatcherSet.merge(inSet, bind(body, ms).clear());
	}
	
	private MatcherSet bind(EnhancedForStatement efs, MatcherSet inSet) {
		Statement body = efs.getBody();
		MatcherSet ms = new MatcherSet(this.basicRuleSeq);
		return MatcherSet.merge(inSet, bind(body,ms).clear());
	}
	
	/*
	 * 	TODO: Statements that might need to be implemented includes
	 * 		1) SynchronizedStatement
	 * 		2) TryStatement
	 * 		3) TypeDeclarationStatement
	 */
	
}
