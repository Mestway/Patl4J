package controller.validation;

import java.util.Date;

import controller.util.CalendarUtils;

public class DataValidadaCmd implements ValidarCmd{

	public Object validar(String val) throws Exception {
		

	    String DatePattern = "^(?:(31)(\\D)(0?[13578]|1[02])\\2|(29|30)(\\D)(0?[13-9]|1[0-2])\\5|(0?[1-9]|1\\d|2[0-8])(\\D)(0?[1-9]|1[0-2])\\8)((?:1[6-9]|[2-9]\\d)?\\d{2})$|^(29)(\\D)(0?2)\\12((?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:16|[2468][048]|[3579][26])00)$";  
	   	   
		if(val.matches(DatePattern)) {
			String dia = val.split("/")[0];
			String mes = val.split("/")[1];
			String ano = val.split("/")[2];

			

			int diaI =Integer.parseInt(dia);
			int mesI =Integer.parseInt(mes);
			int anoI =Integer.parseInt(ano);
			Date data = CalendarUtils.data(diaI, mesI, anoI);
			return data;
		}
		else{
			throw new Exception();
		}
		
	}

}
