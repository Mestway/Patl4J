package controller.validation;

import googlecalendar.GoogleUtils;

public class GmailValidadoCmd implements ValidarCmd {

	public Object validar(String val) throws Exception {
		if (!val.endsWith(GoogleUtils.GMAIL_ACCOUNT)){
			throw new Exception();
		}
		return val;
	}

}
