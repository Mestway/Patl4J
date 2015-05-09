package data.util;

public class UsuarioExistenteException extends Exception {
	
	private String nome;
	public UsuarioExistenteException(String nome) {
		super();
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
