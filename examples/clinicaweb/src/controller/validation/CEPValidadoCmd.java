package controller.validation;

public class CEPValidadoCmd implements ValidarCmd{

	public Object validar(String val) throws Exception {
		Integer.parseInt(val);
		if (val.length() == 8) {
			return val;
		}
		else {
			throw new Exception();
		}
	}

}
