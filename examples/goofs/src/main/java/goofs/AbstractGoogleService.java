package goofs;

import com.google.gdata.client.GoogleService;
import com.google.gdata.util.AuthenticationException;

public abstract class AbstractGoogleService implements GoofsService {

	protected GoogleService realService;

	public AbstractGoogleService(String userName, String password)
			throws AuthenticationException {
		realService = new GoogleService(getGoogleServiceName(), APP_NAME);
		realService.setUserCredentials(userName, password);
	}

	protected abstract String getGoogleServiceName();

	public GoogleService getRealService() {
		return realService;
	}

}
