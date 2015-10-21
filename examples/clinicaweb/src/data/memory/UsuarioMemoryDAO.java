package data.memory;

import java.util.Date;
import java.util.LinkedList;

import controller.util.CalendarUtils;
import controller.util.Par;

import data.IUsuarioDAO;
import data.ListaUsuarios;
import data.util.UsuarioExistenteException;
import data.util.UsuarioNaoEncontradoException;
import entitites.AdministradorClinico;
import entitites.Paciente;
import entitites.Usuario;
import entitites.specialists.Especialista;
import entitites.specialists.Proctologista;
import entitites.specialists.Psicologo;
import entitites.util.TipoUsuario;

public class UsuarioMemoryDAO implements IUsuarioDAO {


	public UsuarioMemoryDAO() {
		adicionarUsuariosTemporarios();
	}

	private void adicionarUsuariosTemporarios() {
		String idCalendarioInicio = "http://www.google.com/calendar/feeds/default/calendars/";
		/**
		 * Adicionando Administradores
		 */
		AdministradorClinico alessandro = new AdministradorClinico(
				"alessandro","123456",
				"Alessandro Gurgel",
				CalendarUtils.data(21,11,1969),
				"12345678924",
				"56078455",
				"rolangomaster@gmail.com",
				"32347007", 
				"98714455"
		);
		alessandro.setIdGoogleCalendario(idCalendarioInicio + "ac6on9m36i2vcjvk4t5omdbjno%40group.calendar.google.com");
		ListaUsuarios.getInstance().adicionarUsuarios(alessandro);
		AdministradorClinico tyago = new AdministradorClinico(
				"tyago", "123456",
				"Tyago de Medeiros",
				CalendarUtils.data(4,07,1986),
				"06412339493",
				"59031800",
				"tyagomedeiros@gmail.com",
				"00000000", 
				"00000000"
		);
		tyago.setIdGoogleCalendario(idCalendarioInicio + "sm7ncrh01qpfinjs72role12vg%40group.calendar.google.com");
		
		
		ListaUsuarios.getInstance().adicionarUsuarios(tyago);
		
		AdministradorClinico silvano = new AdministradorClinico(
				"silvano", "123456",
				"Thiago Silvano",
				CalendarUtils.data(24,11,1969),
				"02424242424",
				"56078455",
				"thi.silvano@gmail.com",
				"32347007", 
				"98714455"
		); 
		silvano.setIdGoogleCalendario(idCalendarioInicio + "kl2vftfkuqgvris2un3spumh1k%40group.calendar.google.com");
		ListaUsuarios.getInstance().adicionarUsuarios(silvano);

		/**
		 * Adicionando Dr Freud
		 */
		Date dataNascFreud = CalendarUtils.data(10, 2, 1983);
		LinkedList<String> servicosFreud = new LinkedList<String>();
		servicosFreud.add("Terapia Cognitiva");
		servicosFreud.add("Terapia a base da fé para cegos Esquisofrenicos");
		Psicologo psicologoFreud = new Psicologo("drfreud", "123456","Dr Freud Azevedo", dataNascFreud, "06321458799","56078455"
				, "drfreudcontato@gmail.com", "32347007", "98714455", servicosFreud, 1970);
		psicologoFreud.setIdGoogleCalendario(idCalendarioInicio + "sg86jreebsgjrocd3uko0prri4%40group.calendar.google.com"); 
		ListaUsuarios.getInstance().adicionarUsuarios(psicologoFreud);

		/**
		 * Adicionando Dr Finger
		 */
		Date dataNascFinger = CalendarUtils.data(10, 2, 1983);
		LinkedList<String> servicosFinger = new LinkedList<String>();
		servicosFinger.add("Exame de próstata com diversos níveis de intensidade e sensibilidade");
		servicosFinger.add("Exame da goma");
		Proctologista proctologistaFinger = new Proctologista("drfinger", "123456","Dr Finger Azevedo", dataNascFinger, "06321458799","56078455"
				, "drfingercontato@gmail.com", "32347007", "98714455", servicosFinger, 1970);
		proctologistaFinger.setIdGoogleCalendario(idCalendarioInicio + "8ujc320lsqrlbf1k1kcf3mhh18%40group.calendar.google.com");
		ListaUsuarios.getInstance().adicionarUsuarios(proctologistaFinger);


		/**
		 * Adicionando Pacientes
		 */
		Paciente uira = new Paciente(
				"uira","123456",
				"Uira Kuleska",
				CalendarUtils.data(10, 2, 1975),
				"00000000000",
				"00000000",
				"uira@dimap.ufrn.br",
				"8400000000",
				"8400000000"

		);
		uira.setIdGoogleCalendario(idCalendarioInicio + "nrqt6f6m37hddr6kdpkjjhi8m4%40group.calendar.google.com");
		ListaUsuarios.getInstance().adicionarUsuarios(uira);
		
		
		Paciente paulo = new Paciente(
				"paulo", "123456",
				"Paulo Pires",
				CalendarUtils.data(10, 2, 1975),
				"00000000000",
				"00000000",
				"pires@dimap.ufrn.br",
				"8400000000",
				"8400000000"

		);
		paulo.setIdGoogleCalendario(idCalendarioInicio + "vvnppmujuujklho9tbft0m71g0%40group.calendar.google.com");
		ListaUsuarios.getInstance().adicionarUsuarios(paulo);

	}

	public void cadastrarUsuario(Usuario novoUsuario) throws UsuarioExistenteException {

		for (Usuario usuario : ListaUsuarios.getInstance().usuarios()){
			if (novoUsuario.getLogin().equals(usuario.getLogin())){
				throw new UsuarioExistenteException(novoUsuario.getLogin());
			}
		}
		ListaUsuarios.getInstance().adicionarUsuarios(novoUsuario);
	}

	public Usuario buscarPeloLogin(String login)
	throws UsuarioNaoEncontradoException {
		for (Usuario usuario : ListaUsuarios.getInstance().usuarios()){
			if (usuario.getLogin().equals(login)){
				return usuario;
			}
		}
		throw new UsuarioNaoEncontradoException(login);
	}

	public Usuario buscarUsuarioCpf(String cpf)
	throws UsuarioNaoEncontradoException {
		for (Usuario usuario : ListaUsuarios.getInstance().usuarios()){
			if (usuario.getCpf().equals(cpf)){
				return usuario;
			}
		}
		throw new UsuarioNaoEncontradoException(cpf);
	}



}
