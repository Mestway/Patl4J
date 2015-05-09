package rulesEngine.manager;

public class CM_Ids {
	
	private String idUser;
	private String idPreferences;
	
	public CM_Ids(String idUser, String idPreferences) {
		super();
		this.idUser = idUser;
		this.idPreferences = idPreferences;
	}

	public String getIdUser() {
		return idUser;
	}

	public String getIdPreferences() {
		return idPreferences;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof CM_Ids))
			return false;
		return super.equals(idUser.equals(((CM_Ids)obj).idUser));
	}
	

}
