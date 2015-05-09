package googlecalendar;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;

import com.google.api.services.calendar.model;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.calendar.ColorProperty;
import com.google.gdata.data.calendar.HiddenProperty;
import com.google.gdata.data.extensions.Recurrence;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class GoogleCalendarManagerApp {

    //**
	private CalendarService service;
	private String aplicacao;
	private String senhaAplicacao;
	private String local;
	
	public static int ajusteTimeZone = +3; /* ajuste de horario para o brasil
	TODO colocar isso num arquivo de configuracao e carregar desde o inicio*/
	public GoogleCalendarManagerApp(String aplicacao, String senhaAplicacao, String local) throws AuthenticationException {
		this.aplicacao = aplicacao;
		this.senhaAplicacao = senhaAplicacao;
		this.local = local;
        //**
		service = new CalendarService(aplicacao);

		/**Deve logar-se no google e pegar o calendarcredemtials; */
		service.setUserCredentials(this.aplicacao, this.senhaAplicacao);
	}

	/**
	 * Cadastra o calendario
	 * @param string nome do Calendario
	 * @param colorEspecialista codigo da cor
	 * @return id do calendario
	 */
	public String cadastrarCalendario(String title, String color) {

		Calendar calendar = new Calendar();
		calendar.setTitle(title);
		calendar.setHidden(HiddenProperty.FALSE); 
		calendar.setColor(new ColorProperty(color)); 
		calendar.setLocation(local+"/"+local+"/"+local); 

		// Insert the calendar
		URL postUrl;
		CalendarListEntry returnedCalendar = null; 
		try {
			postUrl = new URL(GoogleUtils.URL_CALENDARS_OWNER);
			returnedCalendar = service.insert(postUrl, calendar);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		if (returnedCalendar != null) {
			return returnedCalendar.getId();  
		}
		/** Ocorra alguma falha com o google*/
		System.err.println("Erro no cadastramento do calendario do usuario com o google");
		return GoogleUtils.ERROR_GOOGLE;
	}

	/**
	 * 
	 * @param idCalendario
	 * @return
	 */
	//**public CalendarListEntry retornarCalendario(String idCalendario){
	public CalendarListEntry retornarCalendario(String idCalendario){ 
		CalendarListEntry result = null; 

		URL feedUrl = null;
        //**
		CalendarFeed resultFeed = null;

		try {
			feedUrl = new URL(GoogleUtils.URL_ALLCALENDARS);
			resultFeed = service.getFeed(feedUrl, CalendarFeed.class);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < resultFeed.getEntries().size(); i++) {
			Calendar entry = resultFeed.getEntries().get(i); 
			if (entry.getId().equals(idCalendario)){ 
				return entry;
			}
		}
		System.err.println("Calendario de Id: " + idCalendario + " nao encontrado na aplicacao " + aplicacao );
		return result;
	}

	/**
	 * Compartilha o calendario de id tal com o email passado.
	 * O compartilhado so podera bisoiar.
	 * @param idCalendario
	 * @param gmail
	 */
	public void compartilharCalendario(String idCalendario, String gmail) {

		AclRule entry = new AclRule(); 
        Scope scope = new Scope();
        scope.setType(Scope.Type.USER);
        scope.setValue(gmail);
		entry.setScope(scope);
		entry.setRole("READ"); 

		//CalendarListEntry entrada = this.retornarCalendario
		//(idCalendario);

		try {
			String  url =  idCalendario.replaceAll("/default/calendars", "") + "/acl/full";
			URL aclUrl =
				new URL(url);

			AclRule insertedEntry = service.insert(aclUrl, entry); 
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Cadastrado o Evento evento no calendario idCalendario
	 * @param idCalendario
	 * @param evento
	 * @return O id do evento que foi cadastrado
	 */
	public String cadastrarEvento(String idCalendario, Event evento) { 
		
		evento.setLocation(local+"/"+local+"/"+local); 
		String  strUrl =  idCalendario.replace("/default/calendars", "");
		strUrl +=  "/private/full";
		try {
			URL url = new URL(strUrl);
			Event insertedEntry = service.insert(url, evento);
			return insertedEntry.getId(); 
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		System.err.println("Erro com o Google no cadastramento do evento");
		return null;
	}


}
