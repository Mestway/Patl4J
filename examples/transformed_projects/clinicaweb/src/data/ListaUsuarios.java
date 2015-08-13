package data;

import java.util.LinkedList;

import entitites.Usuario;
import entitites.specialists.Psicologo;

public class ListaUsuarios {

	private LinkedList<Usuario> usuariosCadastrados;
	
	private static ListaUsuarios instance;
	
	private ListaUsuarios(){
		usuariosCadastrados = new LinkedList<Usuario>();
	}
	
	public static ListaUsuarios getInstance() {
		if (instance == null) {
			instance = new ListaUsuarios();
		}
		return instance;
	}

	public void adicionarUsuarios(Usuario usuario) {
		usuariosCadastrados.add(usuario);
	}

	public LinkedList<Usuario> usuarios() {
		return usuariosCadastrados;
	}
}

