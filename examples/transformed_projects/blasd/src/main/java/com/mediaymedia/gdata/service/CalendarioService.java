package com.mediaymedia.gdata.service;

import com.mediaymedia.gdata.model.CalendarioEntry;
import com.mediaymedia.gdata.errors.GoogleProblema;
import com.google.api.services.calendar.model;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.util.ServiceException;
import com.google.gdata.client.calendar.CalendarService;

import java.util.List;
import java.util.Date;
import java.io.IOException;

/**
 *
 * User: juan
 * Date: 13-sep-2007
 * Time: 16:43:37
 * To change this template use File | Settings | File Templates.
 */
public interface CalendarioService {
    CalendarService createCalendarService(String usuarioGmail, String clave) throws GoogleProblema;

    CalendarEventFeed createCalendarFeed(CalendarService myServiceCalBis, String url) throws GoogleProblema;
     CalendarEventFeed createCalendarFeed(CalendarService myServiceCalBis, String url,Date inicio, Date fin) throws GoogleProblema ;


    List<CalendarioEntry> dameEventosCalendario(GoogleProperties googleProperties) throws GoogleProblema;
    List<CalendarioEntry> dameEventosCalendario(Date inicio, Date fin, GoogleProperties googleProperties) throws GoogleProblema;
    void updateCalendarEntry(Event entry, GoogleProperties googleProperties) throws IOException, GoogleProblema, ServiceException; 
    
}
