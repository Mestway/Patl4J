package patl4j.java.analyzer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import soot.Body;
import soot.Local;

public class VariableCluster {
	Map fim;
	List variables;
	AliasAnalysis aliasAnalysis;
	
	Integer cnt;
	
	void dfs(Object u) {
		fim.put(u, cnt);
		for (Object oth : variables) {
			if (aliasAnalysis.mayAlias((Local) u, (Local) oth) && fim.get(oth) == null) {
				dfs(oth);
			}
		}
	}
	
	public VariableCluster(Body body) {
		fim = new HashMap();
		variables = new LinkedList<Local>();
		cnt = new Integer(0);
		aliasAnalysis = new AliasAnalysis();
		
		for (Local u: body.getLocals()) {
			variables.add(u);
		}
		
		for (Object u: variables) {
			if (fim.get(u) == null) {
				cnt = new Integer(cnt.intValue() + 1);
				dfs(u);
			}
		}
	}
	
	public Object getIndication(Local u) {
		return fim.get(u);
	}
}
