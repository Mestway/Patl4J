package com.mediaymedia.seam.google;

import com.google.api.services.calendar.model;
import com.google.gdata.util.ServiceException;
import com.mediaymedia.gdata.model.CalendarioEntry;
import com.mediaymedia.gdata.errors.GoogleProblema;

import java.util.Date;
import java.util.List;
import java.io.IOException;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 13:14:42
 */
public interface CalendarServiceMinimal {
    String seamName="calendarServiceMinimal";

    void updateCalendarEntry(Event entry) throws IOException, GoogleProblema, ServiceException; 

    List<CalendarioEntry> dameEventosCalendario(Date inicio, Date fin) throws GoogleProblema;

    List<CalendarioEntry> dameEventosCalendario() throws GoogleProblema;
}
