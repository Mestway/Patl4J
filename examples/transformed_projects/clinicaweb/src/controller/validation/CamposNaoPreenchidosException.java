package controller.validation;

import java.util.List;

public class CamposNaoPreenchidosException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CamposNaoPreenchidosException(List<String> camposErrados) {
		this.camposErrados = camposErrados;
	}

	List<String> camposErrados;

	public List<String> getCamposErrados() {
		return camposErrados;
	}

}
