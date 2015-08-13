package business;

import java.util.LinkedList;

import com.google.gdata.util.AuthenticationException;

import data.IConsultasDAO;
import data.memory.ConsultaMemoryDAO;
import data.util.UsuarioExistenteException;
import data.util.UsuarioNaoEncontradoException;
import business.util.LogarException;
import business.util.ProfissionalNaoEncontradoException;
import entitites.Consulta;
import entitites.Medicamento;
import entitites.Paciente;
import entitites.Usuario;
import entitites.specialists.Especialista;
import googlecalendar.GoogleCalendarManagerApp;

/**
 * Fachada do Sistema
 * @author alessandro87
 *
 */
public class FachadaManager {

	private static FachadaManager instancia;
	private ServicosUsuario servicosUsuario;
	private ServicosAgenda servicosAgenda;


	private FachadaManager() {

		servicosUsuario = new ServicosUsuario();
		try {
			servicosAgenda = new ServicosAgenda();
		} catch (AuthenticationException e) {
			System.err.println("Não conseguiu logar a agenda da aplicacao no google");
		}
	}

	public static FachadaManager getInstance() {
		if (instancia == null){
			instancia = new FachadaManager();
		}
		return instancia;
	}

	public Usuario getUsuario(String login)
	throws UsuarioNaoEncontradoException{
			return servicosUsuario.getByLogin(login);
	}

	/**
	 * Realiza cadastramento de usuario
	 * @param novoPaciente
	 * @throws UsuarioExistenteException
	 */

	public void cadastrarUsuario(Usuario user) throws UsuarioExistenteException {
		servicosUsuario.cadastrarUsuario(user);
		servicosAgenda.cadastrarCalendario(user);
	}

	/**
	 * Realiza o mecanismo de logagem
	 * @param login
	 * @param password
	 */
	public Usuario logar(String login, String password) throws LogarException, AuthenticationException {
		
		Usuario result;
			try {
				result = servicosUsuario.logar(login,password);
			} catch (UsuarioNaoEncontradoException e) {
				throw new LogarException();
			}
		
		return result;
	}
	
	public LinkedList<Especialista> getProfissionais( ) 
	{
		return servicosUsuario.getProfissionais();
	}

	/**
	 * Marcar uma dada consulta
	 * @param consulta 
	 */
	public void marcarConsulta(Consulta consulta) {
		servicosAgenda.marcarConsulta(consulta);
	}

	public void marcarMedicamento(Medicamento medic) {
		servicosAgenda.marcarMedicamento(medic);
	}


}
