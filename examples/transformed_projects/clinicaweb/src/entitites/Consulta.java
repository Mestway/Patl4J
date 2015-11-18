package entitites;

import java.util.Date;
import java.util.LinkedList;

import com.google.gdata.data.DateTime;

import com.google.api.services.calendar.model;

import controller.util.AplicacaoUtils;
import controller.util.CalendarUtils;
import controller.util.Par;

import entitites.specialists.Especialista;

public class Consulta extends EventoGeral {
	
	public Consulta(Especialista profissional, Paciente paciente, Date data,
			Par<Integer, Integer> hora, int duracao) {
		super();
		this.profissional = profissional;
		this.paciente = paciente;
		this.data = data;
		this.tempo = hora;
		this.duracao = duracao;
	}
	
	/* Id do Banco Local*/
	private String idConsulta;
	
	private Especialista profissional;
	private Paciente paciente;
	private Date data;
	private Par<Integer, Integer> tempo;
	private int duracao;
	
	/** Google ID
	 */
	private String googleIdPaciente = "";
	private String googleIdEspecialista = "";
	
	public String getIdConsulta() {
		return idConsulta;
	}
	public void setIdConsulta(String idConsulta) {
		this.idConsulta = idConsulta;
	}
	public Especialista getEspecialista() {
		return profissional;
	}
	public void setProfissional(Especialista profissional) {
		this.profissional = profissional;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Par<Integer, Integer>  getHora() {
		return tempo;
	}
	public void setHora(Par<Integer, Integer>  hora) {
		this.tempo = hora;
	}
	public int getDuracao() {
		return duracao;
	}
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	/**
	 * Adapter
	 * @return
	 */
	public Event getEvento() {
		Event result = new Event();
		
        result.setSummary("Consulta: " + this.profissional.getLogin());
		result.setSummary("Consulta: " + this.profissional.getLogin());
        ////result.setContent(new PlainTextConstruct("Consulta com: " + this.profissional.getNomeCompleto()));
		
		
		int horasduracao = duracao / 60; 
		int minutosduracao = duracao % 60;		
		int tempoHoras = this.tempo.getFirst() + horasduracao;
		int tempoMinutos = this.tempo.getSecond() + minutosduracao;
		
		String mes = String.valueOf(CalendarUtils.getMes(data));
		if (mes.length() == 1) {mes = "0" + mes; }
		String dia = String.valueOf(CalendarUtils.getDia(data));
		if (dia.length() == 1) {dia = "0" + dia; }
		String ano = String.valueOf(CalendarUtils.getAno(data));
		
		String horas = String.valueOf(tempo.getFirst());
		if (horas.length() == 1) {horas = "0" + horas; }
		String minutos = String.valueOf(tempo.getSecond());
		if (minutos.length() == 1) {minutos = "0" + minutos; }
		
		String startTime = ano + "-" + mes + "-" + dia + "T" + horas + ":" 
		+ minutos + ":00" + AplicacaoUtils.APLICACAO_TIMEZONE;  
		//System.out.println("start " + startTime);
		
		String horasFim = String.valueOf(tempoHoras);
		if (horasFim.length() == 1) {horasFim = "0" + horasFim; }
		String minutosFim = String.valueOf(tempoMinutos);
		if (minutosFim.length() == 1) {minutosFim = "0" + minutosFim; }
		
		/**
		 * segundos eh sempre 00
		 */
		String endTime = ano + "-" + mes + "-" + dia + "T" + horasFim + ":" 
		+ minutosFim + ":00" + AplicacaoUtils.APLICACAO_TIMEZONE;  
		//System.out.println("end " + endTime);
		
		
		//DateTime endTimeDate = DateTime.parseDateTime("2009-05-13T18:00:00-03:00");
		//DateTime startTimeDate = DateTime.parseDateTime("2009-05-13T15:00:00-03:00");
		
        //**
		DateTime startTimeDate = DateTime.parseDateTime(startTime);
		DateTime endTimeDate = DateTime.parseDateTime(endTime);
        EventDateTime start = new EventDateTime();
        start.setDateTime(startTimeDate);
        EventDateTime end = new EventDateTime();
        end.setDateTime(endTimeDate);
        result.setStart(start);
        result.setEnd(end);
		return result;
	}
	public String getGoogleId() {
		return googleIdPaciente;
	}
	public void setGoogleIdPaciente(String googleId) {
		this.googleIdPaciente = googleId;
	}
	public String getGoogleIdEspecialista() {
		return googleIdEspecialista;
	}
	public void setGoogleIdEspecialista(String googleIdEspecialista) {
		this.googleIdEspecialista = googleIdEspecialista;
	}
	@Override
	public LinkedList<Event> getEventosGoogleDoEvento() { 
		
		LinkedList<Event> results = new LinkedList<Event>(); 
		Event result = new Event(); 
		
		result.setSummary("Consulta: " + this.profissional.getLogin()); 
		////result.setContent(new PlainTextConstruct("Consulta com: " + this.profissional.getNomeCompleto())); 
		
		
		int horasduracao = duracao / 60; 
		int minutosduracao = duracao % 60;		
		int tempoHoras = this.tempo.getFirst() + horasduracao;
		int tempoMinutos = this.tempo.getSecond() + minutosduracao;
		
		String mes = String.valueOf(CalendarUtils.getMes(data));
		if (mes.length() == 1) {mes = "0" + mes; }
		String dia = String.valueOf(CalendarUtils.getDia(data));
		if (dia.length() == 1) {dia = "0" + dia; }
		String ano = String.valueOf(CalendarUtils.getAno(data));
		
		String horas = String.valueOf(tempo.getFirst());
		if (horas.length() == 1) {horas = "0" + horas; }
		String minutos = String.valueOf(tempo.getSecond());
		if (minutos.length() == 1) {minutos = "0" + minutos; }
		
		String startTime = ano + "-" + mes + "-" + dia + "T" + horas + ":" 
		+ minutos + ":00" + AplicacaoUtils.APLICACAO_TIMEZONE;  
		//System.out.println("start " + startTime);
		
		String horasFim = String.valueOf(tempoHoras);
		if (horasFim.length() == 1) {horasFim = "0" + horasFim; }
		String minutosFim = String.valueOf(tempoMinutos);
		if (minutosFim.length() == 1) {minutosFim = "0" + minutosFim; }
		
		/**
		 * segundos eh sempre 00
		 */
		String endTime = ano + "-" + mes + "-" + dia + "T" + horasFim + ":" 
		+ minutosFim + ":00" + AplicacaoUtils.APLICACAO_TIMEZONE;  
		//System.out.println("end " + endTime);
		
		
		//DateTime endTimeDate = DateTime.parseDateTime("2009-05-13T18:00:00-03:00");
		//DateTime startTimeDate = DateTime.parseDateTime("2009-05-13T15:00:00-03:00");
		
        //**
		DateTime startTimeDate = DateTime.parseDateTime(startTime); 
		DateTime endTimeDate = DateTime.parseDateTime(endTime);

        EventDateTime start = new EventDateTime();
        start.setDateTime(startTimeDate);
        EventDateTime end = new EventDateTime();
        end.setDateTime(endTimeDate);
        result.setStart(start);
        result.setEnd(end);
		
		results.add(result);
		return results;
	}
	
}
