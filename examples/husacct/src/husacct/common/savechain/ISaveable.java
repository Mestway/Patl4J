package husacct.common.savechain;

import org.dom4j.Element;

public interface ISaveable {
	Element getWorkspaceData();
	void loadWorkspaceData(Element workspaceData);
}
