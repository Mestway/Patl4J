package entitites.specialists;

import java.util.Date;
import java.util.LinkedList;

import entitites.Usuario;

public class Especialista extends Usuario {
	

	public Especialista(String login,String senha, String nomeCompleto, Date dataNasc, String cpf,
			String cep, String email, String tel,String cel, LinkedList<String> servicos, int anoInicio) {
		super(login, senha, nomeCompleto, dataNasc, cpf, cep, email, tel, cel);
		this.servicos = servicos;
		this.anoInicio = anoInicio;
	}

	
	/** Servicos Oferecidos*/
	private LinkedList<String> servicos;
	
	/** Ano de inicio de Carreira*/
	private int  anoInicio;


	public LinkedList<String> getServicos() {
		return servicos;
	}


	
}