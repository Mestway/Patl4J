package entitites;

import java.util.Date;

import entitites.util.TipoUsuario;

public class AdministradorClinico extends Usuario {

	public AdministradorClinico(String login, String senha, String nomeCompleto, Date dataNasc, String cpf,
			String cep, String email, String tel, String cel) {
		super(login, senha, nomeCompleto, dataNasc, cpf, cep, email, tel, cel);
	}

	public String getTipoUsuario() {
		return TipoUsuario.ADMINISTRADOR_CLINICO;
	}
}
