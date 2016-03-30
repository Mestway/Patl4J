package controller.validation;

import controller.util.Par;





public class HoraValidadoCmd  implements ValidarCmd{

	public Object validar(String val) throws Exception {
		String horas = val.split(":")[0];
		String minutos = val.split(":")[1];

		if (horas.length()>2 || minutos.length()>2 ) {
			throw new Exception();
		}

		int horasInt = Integer.parseInt(horas);
		int minutosInt = Integer.parseInt(minutos);
		
		
		return new Par<Integer, Integer>(horasInt, minutosInt);
	}

}
