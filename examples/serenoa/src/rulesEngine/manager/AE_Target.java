package rulesEngine.manager;

import java.util.List;

import org.jdom2.Document;

public class AE_Target {
	private AE_ModelId modelId;
	private Document model;
	private List<?> targets;
	
	public AE_Target(String modelId, Document model, List<?> targets) {
		super();
		this.modelId = new AE_ModelId(modelId);
		this.model = model;
		this.targets = targets;
	}

	public Document getModel() {
		return model;
	}
	
	public List<?> getTargets() {
		return targets;
	}

	public AE_ModelId getModelId() {
		return modelId;
	}	
	
	
}
