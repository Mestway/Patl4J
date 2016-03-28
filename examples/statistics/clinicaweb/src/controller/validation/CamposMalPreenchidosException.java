package controller.validation;

import java.util.List;

public class CamposMalPreenchidosException extends Exception 
{
	
	List<String> camposErrados;

	public CamposMalPreenchidosException(List<String> camposErrados) {
		this.camposErrados = camposErrados;
	}

	public List<String> getCamposErrados() {
		return camposErrados;
	}

}
