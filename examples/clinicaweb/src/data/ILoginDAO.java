package data;


import data.util.UsuarioExistenteException;

import entitites.Usuario;

/**
 * Interface que conterá usuários logados e 
 * os tipos do mesmos
 * @author alessandro87
 *
 */
public interface ILoginDAO {

	public String autenticar(String login, String senha);

	public void cadastrarUsuario(Usuario novoPaciente) throws UsuarioExistenteException;
}
