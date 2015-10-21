package business;


import java.util.LinkedList;

import com.google.api.services.calendar.model;
import com.google.gdata.util.AuthenticationException;

import controller.util.AplicacaoUtils;
import data.IConsultasDAO;
import data.IMedicamentosDAO;
import data.memory.ConsultaMemoryDAO;
import data.memory.MedicamentoMemoryDAO;

import entitites.AdministradorClinico;
import entitites.Consulta;
import entitites.Medicamento;
import entitites.Paciente;
import entitites.Secretario;
import entitites.Usuario;
import entitites.specialists.Especialista;
import googlecalendar.GoogleCalendarUtils;
import googlecalendar.GoogleCalendarManagerApp;

/**
 * Cuida dos serviços de Consultas\Medicamentos
 * bem como a criação de calendários no Google Agenda 
 * e Compartilhamento entre eles.
 * @author alessandro87
 *
 */
public class ServicosAgenda {
	
	private GoogleCalendarManagerApp managerCalendars;
	private IConsultasDAO consultasDAO;
	private IMedicamentosDAO medicamentosDAO;
	
	public ServicosAgenda() throws AuthenticationException {
		consultasDAO = new ConsultaMemoryDAO();
		medicamentosDAO = new MedicamentoMemoryDAO();
		managerCalendars = new GoogleCalendarManagerApp(AplicacaoUtils.APLICACAO_EMAIL
				, AplicacaoUtils.APLICACAO_SENHA, AplicacaoUtils.APLICACAO_LOCAL);
	}
	

	
	/**
	 * Adiciona ao calendario de aplicacao o calendario do paciente.
	 * @param novoUsuario
	 */
	public void cadastrarCalendario(Usuario novoUsuario) {
		String idCalendario = null;
		if( novoUsuario instanceof Especialista) {
			idCalendario = managerCalendars.cadastrarCalendario(
					AplicacaoUtils.APLICACAO_NOME + " " +novoUsuario.getLogin(), GoogleCalendarUtils.COLOR_ESPECIALISTA);
		}
		else if (novoUsuario instanceof Paciente) {
			idCalendario = managerCalendars.cadastrarCalendario(
					AplicacaoUtils.APLICACAO_NOME + " " +novoUsuario.getLogin(), GoogleCalendarUtils.COLOR_PACIENTE);
		}
		else if (novoUsuario instanceof Secretario) {
			idCalendario = managerCalendars.cadastrarCalendario(
					AplicacaoUtils.APLICACAO_NOME + " " +novoUsuario.getLogin(), GoogleCalendarUtils.COLOR_SECRETARIO);
		}
		else if (novoUsuario instanceof AdministradorClinico) {
			idCalendario = managerCalendars.cadastrarCalendario(
					AplicacaoUtils.APLICACAO_NOME + " " +novoUsuario.getLogin(), GoogleCalendarUtils.COLOR_ADMIN);
		}
		
		novoUsuario.setIdGoogleCalendario(idCalendario);
		managerCalendars.compartilharCalendario(idCalendario, novoUsuario.getGmail());
		
	}


	public void marcarConsulta(Consulta consulta) {
		
		consultasDAO.cadastrarConsulta(consulta.getPaciente(), consulta.getEspecialista(), consulta);
		String idPaciente = managerCalendars.cadastrarEvento (consulta.getPaciente().getIdGoogleCalendario(), consulta.getEvento()); 
		String idEspecialista = managerCalendars.cadastrarEvento (consulta.getEspecialista().getIdGoogleCalendario(), consulta.getEvento());
		consulta.setGoogleIdPaciente(idPaciente);
		consulta.setGoogleIdEspecialista(idEspecialista);
	}


	public void marcarMedicamento(Medicamento medic) {
		/*
		 * Banco da Aplicacao
		 */
		medicamentosDAO.cadastrarMedicamento(medic);
		
		/*
		 * Google Calendar
		 */
		LinkedList<Event> eventos = medic.getEventos();
		LinkedList<String> idsMedicamentosEventos = new LinkedList<String>(); /* Armazenar os Ids*/
		
		for (Event evento : eventos) {
			String id = managerCalendars.cadastrarEvento (medic.getPaciente().getIdGoogleCalendario(), evento);
			idsMedicamentosEventos.add(id);
		}
		
		medic.setIdMedicamentoEventohora(idsMedicamentosEventos);
	}

}
