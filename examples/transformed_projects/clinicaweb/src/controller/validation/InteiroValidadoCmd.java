package controller.validation;

public class InteiroValidadoCmd implements ValidarCmd{

	public Object validar(String val) throws Exception {
		//System.out.println(val);
		return Integer.parseInt(val);
	}

}
