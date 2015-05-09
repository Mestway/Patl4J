package business;

import java.util.LinkedList;

import business.util.LogarException;
import data.IAdministradoresDAO;
import data.ILoginDAO;
import data.IPacientesDAO;
import data.IProfissionaisDAO;
import data.ISecretariosDAO;
import data.IUsuarioDAO;
import data.memory.AdministradoresMemoryDAO;
import data.memory.LoginMemoryDAO;
import data.memory.PacienteMemoryDAO;
import data.memory.ProfissionaisMemoryDAO;
import data.memory.SecretariosMemoryDAO;
import data.memory.UsuarioMemoryDAO;
import data.util.UsuarioExistenteException;
import data.util.UsuarioNaoEncontradoException;
import entitites.Paciente;
import entitites.Usuario;
import entitites.specialists.Especialista;
import entitites.util.TipoUsuario;

/**
 * Classe responsável por cadastrar um paciente
 * @author alessandro87
 *
 */
public class ServicosUsuario {

	IPacientesDAO pacientesDAO;
	ILoginDAO loginsDAO;
	IProfissionaisDAO profissionaisDAO;
	ISecretariosDAO secretariosDAO;
	IAdministradoresDAO administradoresDAO;
	IUsuarioDAO usuariosDAO;

	public ServicosUsuario() {
		profissionaisDAO = new ProfissionaisMemoryDAO();
		pacientesDAO = new PacienteMemoryDAO();
		loginsDAO = new LoginMemoryDAO();
		secretariosDAO = new SecretariosMemoryDAO();
		administradoresDAO = new AdministradoresMemoryDAO();
		usuariosDAO = new UsuarioMemoryDAO();

	}
	
	public void cadastrarUsuario(Usuario novoPaciente) throws UsuarioExistenteException {
		usuariosDAO.cadastrarUsuario(novoPaciente);
		loginsDAO.cadastrarUsuario(novoPaciente);
	}

	/**
	 * Verifica se o usuario esta na tabelas de usuarios cadastrados
	 * @param usuario
	 * @param senha
	 * @return O tipo de usuario(pode ser NAO_CADASTRADO)
	 * @throws LogarException
	 * @throws UsuarioNaoEncontradoException
	 */
	public Usuario logar(String login, String senha) throws LogarException, UsuarioNaoEncontradoException {

		String tipo = loginsDAO.autenticar(login,senha);
		if (tipo.equals(TipoUsuario.NAO_CADASTRADO)) {
			throw new LogarException();
		}
		else {
			return this.getByLogin(login);
		}


	}

	/**
	 * 
	 * @param mail
	 * @return O usuario cujo email é mail
	 */
	public Usuario getByLogin(String mail) throws UsuarioNaoEncontradoException{

			Usuario usuario = usuariosDAO.buscarPeloLogin(mail);


		return usuario;
	}

	public Especialista getProfissionalCpf(String cpfProfissionalLogado) throws UsuarioNaoEncontradoException {
		return (Especialista)usuariosDAO.buscarUsuarioCpf(cpfProfissionalLogado);
	}

	public LinkedList<Especialista> getProfissionais() {
		return profissionaisDAO.getProfissionais();
	}


}
