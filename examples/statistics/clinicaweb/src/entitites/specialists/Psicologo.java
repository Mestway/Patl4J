package entitites.specialists;

import java.util.Date;
import java.util.LinkedList;

import entitites.util.TipoUsuario;

public class Psicologo extends Especialista{

	public Psicologo(String login , String senha, String nomeCompleto, Date dataNasc, String cpf,
			String cep, String email, String tel, String cel, LinkedList<String> servicos, int anoInicio) {
		super(login, senha, nomeCompleto, dataNasc, cpf, cep, email, tel, cel, servicos, anoInicio);
	}
	public String getTipoUsuario() {
		return TipoUsuario.PSICOLOGO;
	}
}
