package goofs;

import junit.framework.TestCase;

public class BaseTestCase extends TestCase {

	protected String username;

	protected String password;

	@Override
	protected void setUp() throws Exception {
		
		setUsername(System.getProperty("username"));
		
		setPassword(System.getProperty("password"));
		
		assertNotNull("You must provide -Dusername=yourusername", getUsername());
		assertNotNull("You must provide -Dpassword=yourpassword", getPassword());
	}



	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}



	protected String getUsername() {
		return username;
	}

	protected void setUsername(String username) {
		this.username = username;
	}

	protected String getPassword() {
		return password;
	}

	protected void setPassword(String password) {
		this.password = password;
	}

}
