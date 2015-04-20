package patl4j.matcher;

import org.eclipse.jdt.core.dom.Name;

public class WrappedName {
	String stringName;
	Name name;
	
	public WrappedName(Name name) {
		this.name = name;
		this.stringName = name.getFullyQualifiedName();
	}
	
	public String getStr() {
		return stringName;
	}
	
	public Name getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WrappedName) {
			return this.stringName.equals(((WrappedName)obj).getStr());
		}
		return false;
	}
}
