package data.util;

public class UsuarioNaoEncontradoException extends Exception {

	private String name;
	
	public UsuarioNaoEncontradoException(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
