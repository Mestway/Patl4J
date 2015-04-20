package patl4j.patl.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;

import patl4j.util.Pair;

public class FieldAccessPattern implements RHSPattern {
	
	String target;
	String field;
	
	public FieldAccessPattern(String target, String field) {
		this.target = target;
		this.field = field;
	}
	
	@Override
	public String toString() {
		return target + "." + field;
	}

	@Override
	public Pair<List<Pair<String, Name>>, Boolean> tryMatch(Expression exp,
			Map<String, String> var2type) {
		
		List<Pair<String, Name>> matchedVarList = new ArrayList<Pair<String, Name>>();
		Boolean matchedSccessful = false;
		
		if (exp instanceof QualifiedName) {
			QualifiedName qn = (QualifiedName) exp;
			if (qn.getQualifier() instanceof SimpleName) {
				
				SimpleName sn = (SimpleName) qn.getQualifier();
				// TODO: add type check on the matching check
				System.out.println("[TypeInfo from FieldAccessPattern] " + sn.resolveTypeBinding() + " " + var2type.get(target));
				matchedVarList.add(new Pair<String,Name>(this.target, sn));
				
				// Note that the field won't generate a binding
				if (this.field.equals(qn.getName().getIdentifier())) {
					matchedSccessful = true;
				}
			}
		}
		
		return new Pair<List<Pair<String, Name>>, Boolean>(matchedVarList, matchedSccessful);
	}
	
}
