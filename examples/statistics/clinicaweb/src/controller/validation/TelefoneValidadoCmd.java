package controller.validation;

public class TelefoneValidadoCmd implements ValidarCmd {

	public Object validar(String val) throws Exception {
		Integer.parseInt(val);
		if (val.length()== 10 || val.length()== 9 || val.length()== 11) {
			return val;
		}
		else {
			throw new Exception();
		}
	}
	
	
}
