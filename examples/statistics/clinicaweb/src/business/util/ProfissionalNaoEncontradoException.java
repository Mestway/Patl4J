package business.util;

public class ProfissionalNaoEncontradoException extends Exception{

	public ProfissionalNaoEncontradoException(String cpf) {
		super();
		this.cpf = cpf;
	}

	private String cpf;

	public String getCpf() {
		return cpf;
	}
}
