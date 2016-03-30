package patl4j.common.ast.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;

import patl4j.common.util.Pair;
import patl4j.common.util.VariableContext;

public class FieldAccessPattern implements RHSPattern {
	
	MetaVariable target;
	String field;
	
	public FieldAccessPattern(MetaVariable target, String field) {
		this.target = target;
		this.field = field;
	}
	
	@Override
	public String toString() {
		return target + "." + field;
	}

	@Override
	public Pair<List<Pair<String, Name>>, Boolean> tryMatch(Expression exp,
			Map<String, String> var2type,
			VariableContext context) {
		
		List<Pair<String, Name>> matchedVarList = new ArrayList<Pair<String, Name>>();
		Boolean matchedSccessful = false;
		
		if (exp instanceof QualifiedName) {
			
//			System.out.println("FieldAccessPattern @ 40 QualifiedName: "+exp);
			
			QualifiedName qn = (QualifiedName) exp;
			if (qn.getQualifier() instanceof SimpleName) {
				
				SimpleName sn = (SimpleName) qn.getQualifier();
				// TODO: add type check on the matching check
//				System.out.println("[TypeInfo from FieldAccessPattern] " + sn.resolveTypeBinding() + " " + var2type.get(target));
				matchedVarList.add(new Pair<String,Name>(this.target.getName(), sn));
				
				// Note that the field won't generate a binding
				if (this.field.equals(qn.getName().getIdentifier())) {
					matchedSccessful = true;
				}
			}
		}
		
		return new Pair<List<Pair<String, Name>>, Boolean>(matchedVarList, matchedSccessful);
	}
	
}
