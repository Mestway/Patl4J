package goofs;

import com.google.gdata.util.AuthenticationException;


public interface GoofsService {
	
	public static final String APP_NAME = "goofs-goofs-1";
	
	public void acquireSessionTokens(String username, String password) throws AuthenticationException;

}
