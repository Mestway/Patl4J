package rulesEngine.manager;

public class AE_ModelId {
	
	private String id;
	private boolean contextManager;
	
	public AE_ModelId(String id) {
		this(id, false);
	}
	
	
	public AE_ModelId(String id, boolean isContextManager) {
		super();
		this.id = id;
		this.contextManager = isContextManager;
	}


	public String getId() {
		return id;
	}


	public boolean isContextManager() {
		return contextManager;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof AE_ModelId))
			return false;
		AE_ModelId ref = (AE_ModelId) obj;
		return ref.getId().equals(id);
	}
	
	@Override
	public String toString() {
		String response = id;
		if(contextManager)
			response += " (context manager)";
		return response;
	}
	
}
