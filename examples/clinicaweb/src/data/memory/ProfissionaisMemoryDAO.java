package data.memory;

import java.util.LinkedList;

import data.IProfissionaisDAO;
import data.ListaUsuarios;

import entitites.Usuario;
import entitites.specialists.Especialista;


public class ProfissionaisMemoryDAO implements IProfissionaisDAO {

	public ProfissionaisMemoryDAO() {
	}

	public LinkedList<Especialista> getProfissionais() {
		LinkedList<Especialista> profissionais = new LinkedList<Especialista>();
		for (Usuario user : ListaUsuarios.getInstance().usuarios()) {
			if (user instanceof Especialista) {
				profissionais.add((Especialista) user);
			}
		}
		return profissionais;
	}

	

}
