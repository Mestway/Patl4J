package patl4j.core.transformer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.core.transformer.phases.CodeAdapter;
import patl4j.core.transformer.phases.MatcherBinder;
import patl4j.matcher.MatcherSet;
import patl4j.patl.ast.Rule;

public class Transformer {

	MatcherSet matchers;
	List<Rule> rules = new ArrayList<Rule>();
	
	public Transformer(List<Rule> rule) {
		this.rules = rule;
		this.matchers = new MatcherSet(rule);
	}
	
	/*
	 * 	The key function: 
	 * 		Take in an method/initializer body, return the transformed body,
	 * 	Several steps are need in the process:
	 * 		1. generate matcher based on the block
	 * 		2. re-order some statements (Run in the 'transform' method)
	 * 		3. remove/add/substitute statements	(Run in the 'transform' method)
	 */
	public Statement execute(Block body) {
		this.matchers = this.matching(body);
		
		// This is the matcher binded from the method
		System.out.println("---Matcher printed here (After clear)---");
		System.out.println(matchers.toString());
		
		// Perform shift operation on the given statements
		Block shiftedBody = this.shift(body, this.matchers);
		
		// Perform adaptation on the statements
		Statement adaptedBody = this.adapt(shiftedBody, this.matchers);
		return adaptedBody;
	}

	private MatcherSet matching(Block body) {
		return new MatcherBinder(matchers, rules).bindMatcher(body, matchers);
	}
	
	private Block shift(Block body, MatcherSet matchers) {
		// TODO: shift to be implemented
		return body;
	}
	
	private Statement adapt(Block body, MatcherSet bindedMatcher) {
		return new CodeAdapter(body, bindedMatcher).adaptCode();
	}
	
}
