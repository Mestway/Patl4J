package entitites;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import entitites.informations.Informacoes;
import entitites.specialists.Especialista;
import entitites.util.TipoUsuario;

public class Paciente extends Usuario{

	public Paciente(String login,String senha, String nomeCompleto, Date dataNasc, String cpf, String cep,
			String email, String tel,String cel) {
		super(login, senha, nomeCompleto, dataNasc, cpf, cep, email, tel,cel);
		this.medicamentos = new HashSet<Medicamento>();
	}

	private Set<Medicamento> medicamentos;
	private Set<Informacoes> informacoes;
		
	public String getTipoUsuario() {
		return TipoUsuario.PACIENTE;
	}

}
