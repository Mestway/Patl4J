package patl4j.core.transformer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.core.transformer.matcherbinder.MatcherBinder;
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
	 * 		2. re-order some statements
	 * 		3. remove/add/substitute statements	
	 */
	public Statement execute(Block body) {
		this.matching(body);
		return this.transform(body);
	}

	private void matching(Block body) {
		matchers = new MatcherBinder(matchers, rules).bindMatcher(body, matchers);
	}
	
	private Statement transform(Block body) {
		// TODO: A lot of steps to be implemented...
		return body;
	}
	
}
