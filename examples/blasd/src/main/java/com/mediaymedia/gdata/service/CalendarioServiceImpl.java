package com.mediaymedia.gdata.service;

import com.mediaymedia.gdata.model.CalendarioEntry;
import com.mediaymedia.gdata.errors.GoogleProblema;
import com.google.gdata.data.calendar.CalendarEventFeed;

import com.google.api.services.calendar.model;
import com.google.gdata.data.DateTime;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 12:41:38
 */
public class CalendarioServiceImpl extends GData implements CalendarioService {
    public CalendarService createCalendarService(String usuarioGmail, String clave) throws GoogleProblema {
           CalendarService myServiceCalBis = new CalendarService("exampleCo-exampleApp-3");
           try {
               myServiceCalBis.setUserCredentials(usuarioGmail, clave);
           } catch (AuthenticationException e) {
               throw new GoogleProblema(e.getMessage(), e);
           }
           return myServiceCalBis;

       }

       public CalendarEventFeed createCalendarFeed(CalendarService myServiceCalBis, String url) throws GoogleProblema {
           try {
               return myServiceCalBis.getFeed(new URL(url), CalendarEventFeed.class);
           } catch (IOException e) {
               throw new GoogleProblema(e.getMessage(), e);
           } catch (ServiceException e) {
               throw new GoogleProblema(e.getMessage(), e);
           }

       }

       public CalendarEventFeed createCalendarFeed(CalendarService myServiceCalBis, String url, Date inicio, Date fin ) throws GoogleProblema {
           try {

               URL feedUrl = dameFeedURL( url);
               CalendarQuery myQuery = new CalendarQuery(feedUrl);
               myQuery.setMinimumStartTime(new DateTime(inicio));
               myQuery.setMaximumStartTime(new DateTime(fin));
               return myServiceCalBis.query(myQuery, CalendarEventFeed.class);
           } catch (IOException e) {
               throw new GoogleProblema(e.getMessage(), e);
           } catch (ServiceException e) {
               throw new GoogleProblema(e.getMessage(), e);
           }

       }




    public List<CalendarioEntry> dameEventosCalendario(GoogleProperties googleProperties) throws GoogleProblema {

        List<CalendarioEntry> entradas = new ArrayList<CalendarioEntry>();

        com.google.gdata.client.calendar.CalendarService myServiceCalBis = createCalendarService(googleProperties.getUsuarioGmail(), googleProperties.getClave());
        String url = "http://www.google.com/calendar/feeds/m70o2l3viisdbh7ra2v2utbl0k@group.calendar.google.com/private/full";
        CalendarEventFeed myFeed = createCalendarFeed(myServiceCalBis, url);
        for (Event cita : myFeed.getEntries()) { 
            CalendarioEntry calendarEntry = new CalendarioEntry(cita);
            entradas.add(calendarEntry);
        }
        return entradas;
    }


         //todo refactorizar con el metodo de arriba
    public List<CalendarioEntry> dameEventosCalendario(Date inicio, Date fin, GoogleProperties googleProperties) throws GoogleProblema {
        List<CalendarioEntry> entradas = new ArrayList<CalendarioEntry>();

        CalendarService myServiceCalBis = createCalendarService(googleProperties.getUsuarioGmail(), googleProperties.getClave());
        String url = "http://www.google.com/calendar/feeds/m70o2l3viisdbh7ra2v2utbl0k@group.calendar.google.com/private/full";
        CalendarEventFeed myFeed = createCalendarFeed(myServiceCalBis, url, inicio, fin);
        for (Event cita : myFeed.getEntries()) { 
            CalendarioEntry calendarEntry = new CalendarioEntry(cita);
            entradas.add(calendarEntry);
        }
        return entradas;
    }


  public void updateCalendarEntry(Event entry, GoogleProperties googleProperties) throws IOException, GoogleProblema, ServiceException { 
      CalendarService calendarService = createCalendarService(googleProperties.getUsuarioGmail(), googleProperties.getClave());
      calendarService.update(new URL(entry.getHangoutLink()), entry); 
    }
}
