package controller.validation;

/**
 * Valida o numero de caracteres de uma senha
 * @author alessandro87
 *
 */
public class SenhaValidadoCmd implements ValidarCmd{

	public Object validar(String val) throws Exception {
		if (val.length() < 6) {
			throw new Exception();
		}
		return null;
	}

}
