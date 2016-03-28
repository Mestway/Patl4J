package entitites;

import java.util.Date;
import java.util.LinkedList;

import com.google.api.services.calendar.model;
import controller.util.CalendarUtils;
import controller.util.Par;



import entitites.specialists.Especialista;
import entitites.util.Repeticao;
import googlecalendar.GoogleCalendarManagerApp;

/**
 * Guarda todas informações importantes para o
 * evento do Medicamento
 * @author alessandro87
 *
 */
public class Medicamento extends EventoGeral {
	private String idMedicamento;
	private Date dataInicio;
	private Date dataFim;
	private String nome;
	private String dose;
	private String observacoes;

	private LinkedList<Par<Integer,Integer>> horarios;
	private LinkedList<String> idMedicamentoEventohora;
	private Repeticao repeticao;

	private Especialista profissional;
	private Paciente paciente;

	public Medicamento() {
		this.horarios = new LinkedList<Par<Integer,Integer>>();
		this.idMedicamentoEventohora = new LinkedList<String>();
	}
	public Especialista getProfissional() {
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
	public String getIdMedicamento() {
		return idMedicamento;
	}
	public void setIdMedicamento(String idMedicamento) {
		this.idMedicamento = idMedicamento;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDose() {
		return dose;
	}
	public void setDose(String quantidade) {
		this.dose = quantidade;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public Repeticao getRepeticao() {
		return repeticao;
	}
	public void setRepeticao(Repeticao repeticao) {
		this.repeticao = repeticao;
	}
	/**
	 * Metodo utilitario para completar uma string com 0 caso
	 * a mesma so tenha um digito. Util na formatacao HH, MM,
	 * @return
	 */
	private String completeString(String horasFim) {
		String result = horasFim;
		if (horasFim.length() == 1) {result = "0" + result; }
		return result;
	}
	
	public LinkedList<Event> getEventos() {
		LinkedList<Event> result = new LinkedList<Event>();

		String anoDataInicio = String.valueOf(CalendarUtils.getAno(dataInicio));
		String anoDataFim = String.valueOf(CalendarUtils.getAno(dataFim));

		String mesDataInicio = String.valueOf(CalendarUtils.getMes(dataInicio));
		mesDataInicio = completeString(mesDataInicio);
		String mesDataFim = String.valueOf(CalendarUtils.getMes(dataFim));
		mesDataFim = completeString(mesDataFim);
		
		String diaDataInicio = String.valueOf(CalendarUtils.getDia(dataInicio));
		diaDataInicio = completeString(diaDataInicio);
		String diaDataFim = String.valueOf(CalendarUtils.getDia(dataFim));
		diaDataFim = completeString(diaDataFim);
		
		String dataInicioEvento = "DTSTART;VALUE=DATE-TIME:" + anoDataInicio 
		+ mesDataInicio + diaDataInicio;
		String dataFimEvento = "DTEND;VALUE=DATE-TIME:" + anoDataInicio 
		+ mesDataInicio + diaDataInicio;
		String untilEvento =  "UNTIL=" + anoDataFim 
		+ mesDataFim + diaDataFim;

		/** No Google Calendar o maximo de tempo que se pode repetir e diariamente. 
		 * Nao permite, repeticoes feitas em tais horas.*/
		
		String freq = "FREQ=WEEKLY";
		String cabecalho = "RRULE:";
		String byday = "BYDAY=" + repeticao.daysEvent();
		for (Par<Integer, Integer>hora : horarios) {
			Event evento = new Event();

			evento.setSummary("Medicamento: " + getNome()); 
			//**
			////evento.setContent("Medicamento: " + getNome() + "\nQuantidade: " + getDose()  
			////	+ "\nObservacoes: " + getObservacoes()); 
			ajusteHoraTimeZine(hora); // ajusta o timeZone da hora;
			String time = "T" + getHora(hora) + "Z"; /* Z indica horario absoluto. O brazil possui GT = -3; de acordo com gramatica rfc2445 Date-Time */
			
			
			String recurData = dataInicioEvento + time + "\r\n"
			+ dataFimEvento + time + "\r\n" 
			+ cabecalho + freq + ";" + byday + ";" + untilEvento + "\r\n";
			
			
			/*String recurData = "DTSTART;VALUE=DATE-TIME:20090526T150000Z\r\n"
			+ "DTEND;VALUE=DATE-TIME:20090526T150000Z\r\n"
			+ "RRULE:FREQ=WEEKLY;BYDAY=Mo,Tu;UNTIL=20090625\r\n";*/
			
			
			/*DTSTART;VALUE=DATE-TIME:20090525T090000Z
			DTEND;VALUE=DATE-TIME:20090525T090000Z
			RRULE:FREQ=WEEKLY;BYDAY=Mo,Tu;UNTIL=:20090625*/
			
			/**
			 * Setando Recorrencia
			 */
			System.out.println(recurData);
            //**
			evento.setRecurrence(Arrays.asList(recurData.split("\r\n"))); 
			
			result.add(evento);
			
			
			
		}



		return result;
	}
	/**
	 * 
	 * @param hora
	 * @return A String correspondente a hora no formato 
	 * usado pela regra de recorrencia do google, ou seja
	 * HHMMSS
	 */
	private String getHora(Par<Integer, Integer> hora) {
		String horaStr = String.valueOf(hora.getFirst());
		if (horaStr.length() == 1) {
			horaStr = "0" +  horaStr;
		}
		String minStr = String.valueOf(hora.getSecond());
		if (minStr.length() == 1) {
			minStr = "0" +  minStr;
		}
		String seconds ="00";
		String result = horaStr + minStr + seconds;
		
		return result;
	}
	
	/**
	 * 
	 * @return A Hora ajustada de acordo com o timeZone levado pela aplicacao
	 */
	private void ajusteHoraTimeZine (Par<Integer, Integer> hora)  {
		int ajuste = GoogleCalendarManagerApp.ajusteTimeZone;
		hora.setFirst(hora.getFirst() + ajuste);
	}
	
	public void setHorarios(LinkedList<Par<Integer,Integer>> horarios) {
		this.horarios = horarios;
	}
	public LinkedList<String> getIdMedicamentoEventohora() {
		return idMedicamentoEventohora;
	}
	public void setIdMedicamentoEventohora(
			LinkedList<String> idMedicamentoEventohora) {
		this.idMedicamentoEventohora = idMedicamentoEventohora;
	}
	
	public LinkedList<Event> getEventosGoogleDoEvento() {  
		LinkedList<Event> result = new LinkedList<Event>(); 

		String anoDataInicio = String.valueOf(CalendarUtils.getAno(dataInicio));
		String anoDataFim = String.valueOf(CalendarUtils.getAno(dataFim));

		String mesDataInicio = String.valueOf(CalendarUtils.getMes(dataInicio));
		mesDataInicio = completeString(mesDataInicio);
		String mesDataFim = String.valueOf(CalendarUtils.getMes(dataFim));
		mesDataFim = completeString(mesDataFim);
		
		String diaDataInicio = String.valueOf(CalendarUtils.getDia(dataInicio));
		diaDataInicio = completeString(diaDataInicio);
		String diaDataFim = String.valueOf(CalendarUtils.getDia(dataFim));
		diaDataFim = completeString(diaDataFim);
		
		String dataInicioEvento = "DTSTART;VALUE=DATE-TIME:" + anoDataInicio 
		+ mesDataInicio + diaDataInicio;
		String dataFimEvento = "DTEND;VALUE=DATE-TIME:" + anoDataInicio 
		+ mesDataInicio + diaDataInicio;
		String untilEvento =  "UNTIL=" + anoDataFim 
		+ mesDataFim + diaDataFim;

		/** No Google Calendar o maximo de tempo que se pode repetir e diariamente. 
		 * Nao permite, repeticoes feitas em tais horas.*/
		
		String freq = "FREQ=WEEKLY";
		String cabecalho = "RRULE:";
		String byday = "BYDAY=" + repeticao.daysEvent();
		for (Par<Integer, Integer>hora : horarios) {
			Event evento = new Event(); 

			evento.setSummary("Medicamento: " + getNome()); 
            //**
			////evento.setContent("Medicamento: " + getNome() + "\nQuantidade: " + getDose() 
			////	+ "\nObservacoes: " + getObservacoes()); 
			ajusteHoraTimeZine(hora); // ajusta o timeZone da hora;
			String time = "T" + getHora(hora) + "Z"; /* Z indica horario absoluto. O brazil possui GT = -3; de acordo com gramatica rfc2445 Date-Time */
			
			
			String recurData = dataInicioEvento + time + "\r\n"
			+ dataFimEvento + time + "\r\n" 
			+ cabecalho + freq + ";" + byday + ";" + untilEvento + "\r\n";
			
			
			/*String recurData = "DTSTART;VALUE=DATE-TIME:20090526T150000Z\r\n"
			+ "DTEND;VALUE=DATE-TIME:20090526T150000Z\r\n"
			+ "RRULE:FREQ=WEEKLY;BYDAY=Mo,Tu;UNTIL=20090625\r\n";*/
			
			
			/*DTSTART;VALUE=DATE-TIME:20090525T090000Z
			DTEND;VALUE=DATE-TIME:20090525T090000Z
			RRULE:FREQ=WEEKLY;BYDAY=Mo,Tu;UNTIL=:20090625*/
			
			/**
			 * Setando Recorrencia
			 */
			System.out.println(recurData);
			evento.setRecurrence(Arrays.asList(recurData.split("\r\n"))); 
            //**
			result.add(evento);
			
			
			
		}



		return result;
	}

}
