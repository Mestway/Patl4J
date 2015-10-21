package entitites;

import java.util.Date;
import java.util.LinkedList;

import com.google.api.services.calendar.model;

import controller.util.Par;

/**
 * Classe que representa um evento da clinica;
 * podendo ser: consultas, ingestao de medicamentos, etc
 * @author Alessandro
 *
 */
public abstract class EventoGeral extends ObjPersistente{

	protected LinkedList<Usuario> participantes;
	protected LinkedList<LinkedList<String>> calendariosParticipantes;
	protected Date dataInicio;
	protected Date dataFim;
	private LinkedList<Par<Integer,Integer>> horarios;
	
	public abstract LinkedList<Event> getEventosGoogleDoEvento();
}
