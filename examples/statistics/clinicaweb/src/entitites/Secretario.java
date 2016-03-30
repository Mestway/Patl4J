package entitites;

import java.util.Date;

import entitites.util.TipoUsuario;

public class Secretario extends Usuario{

	public Secretario(String login, String nomeCompleto, Date dataNasc, String cpf,
			String cep, String senha, String email, String tel, String cel) {
		super(login, senha, nomeCompleto, dataNasc, cpf, cep, email, tel, cel);
	}

	public String getTipoUsuario() {
		return TipoUsuario.SECRETARIO;
	}
}
