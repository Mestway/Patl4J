package entitites.util;

public class Repeticao {


	private boolean segunda;
	private boolean terca;
	private boolean quarta;
	private boolean quinta;
	private boolean sexta;
	private boolean sabado;
	private boolean domingo;


	public boolean isSegunda() {
		return segunda;
	}

	public void setSegunda(boolean segunda) {
		this.segunda = segunda;
	}

	public boolean isTerca() {
		return terca;
	}

	public void setTerca(boolean terca) {
		this.terca = terca;
	}

	public boolean isQuarta() {
		return quarta;
	}

	public void setQuarta(boolean quarta) {
		this.quarta = quarta;
	}

	public boolean isQuinta() {
		return quinta;
	}

	public void setQuinta(boolean quinta) {
		this.quinta = quinta;
	}

	public boolean isSexta() {
		return sexta;
	}

	public void setSexta(boolean sexta) {
		this.sexta = sexta;
	}

	public boolean isSabado() {
		return sabado;
	}

	public void setSabado(boolean sabado) {
		this.sabado = sabado;
	}

	public boolean isDomingo() {
		return domingo;
	}

	public void setDomingo(boolean domingo) {
		this.domingo = domingo;
	}


	public String daysEvent() {
		//"SU" / "MO" / "TU" / "WE" / "TH" / "FR" / "SA"
		String result = null;
		if (isDomingo()) {
			if(result == null)
				result = "Su";
			else
				result += ",Su";
		}
		if (isSegunda()){
			if(result == null)
				result = "Mo";
			else
				result += ",Mo";
		}
		if (isTerca()){
			if(result == null)
				result = "Tu";
			else
				result += ",Tu";
		}
		if (isQuarta()){
			if(result == null)
				result = "We";
			else
				result += ",We";
		}
		if (isQuinta()){
			if(result == null)
				result = "Th";
			else
				result += ",Th";
		}
		if (isSexta()){
			if(result == null)
				result = "Fr";
			else
				result += ",Fr";
		}
		if (isSabado()){
			if(result == null)
				result = "Sa";
			else
				result += ",Sa";
		}
		if(result == null)
			result = "";
		return result;
	}
}
