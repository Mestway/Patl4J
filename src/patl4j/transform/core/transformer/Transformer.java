package patl4j.transform.core.transformer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;

import patl4j.common.ast.pattern.Rule;
import patl4j.common.java.analyzer.Analyzer;
import patl4j.common.util.VariableContext;
import patl4j.core.matcher.MatcherSet;
import patl4j.core.shifter.Shifter;
import patl4j.transform.core.transformer.phases.CodeAdapter;
import patl4j.transform.core.transformer.phases.MatcherBinder;

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
	/**
	 * 
	 * @param body the body block of a method
	 * @param analyzer the analyzer for the given file
	 * @return an statement representing the method body
	 */
	public Statement execute(Block body, Analyzer analyzer, String methodName, VariableContext context) {
		
//		System.out.println("[[Current Transformming Method]] " + methodName);
		
		this.matchers = this.matching(body, context);
		
		this.matchers.setMethodName(methodName);
		
		// This is the matcher binded from the method
//		System.out.println("---Matcher printed here (After clear)---");
//		System.out.println(matchers.toString());
		
		// Perform shift operation on the given statements
		Shifter shifter = new Shifter(body, matchers, analyzer);
		
		// Perform adaptation on the statements
		// The body of the program to be adapted is in the shifter
		Statement adaptedBody = this.adapt(this.matchers, shifter);
		
		return adaptedBody;
	}

	private MatcherSet matching(Block body, VariableContext context) {
		return new MatcherBinder(matchers, rules).bindMatcher(body, matchers, context);
	}
	
	private Statement adapt(MatcherSet bindedMatcher, Shifter shifter) {
		return new CodeAdapter(bindedMatcher, shifter).adaptCode();
	}
	
}
