package rulesEngine.manager;



public class CM_Value{

	private String key;
	private String type;
	private String value;
	
	public CM_Value(String key, String type, String value) {
		super();
		this.key = key;
		this.type = type;
		this.value = value;
	}
	
	public CM_Value(String key, String value) {
		this(key, "xs:string", value);
	}
	
	public String getKey() {
		return key;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	
	
}
