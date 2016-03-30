package data;

import data.util.UsuarioExistenteException;
import data.util.UsuarioNaoEncontradoException;
import entitites.Usuario;
import entitites.specialists.Especialista;
/**
 * Interface responsável por acessar os dados
 * dos usuários cadastrados
 * @author alessandro87
 *
 */
public interface IUsuarioDAO {

	public void cadastrarUsuario(Usuario novoUsuario) throws UsuarioExistenteException;

	public Usuario buscarPeloLogin(String login)throws UsuarioNaoEncontradoException;

	public Usuario buscarUsuarioCpf(String cpfProfissionalLogado) throws UsuarioNaoEncontradoException;
}
