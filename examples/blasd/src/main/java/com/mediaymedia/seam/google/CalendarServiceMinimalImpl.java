package com.mediaymedia.seam.google;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;

import javax.ejb.Stateless;

import com.mediaymedia.gdata.service.CalendarioService;
import com.mediaymedia.gdata.service.CalendarioServiceImpl;
import com.mediaymedia.gdata.service.GoogleProperties;
import com.mediaymedia.gdata.model.CalendarioEntry;
import com.mediaymedia.gdata.errors.GoogleProblema;
import com.google.api.services.calendar.model;
import com.google.gdata.util.ServiceException;

import java.util.List;
import java.util.Date;
import java.io.IOException;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 13:15:03
 */
@Name(CalendarServiceMinimal.seamName)
@Stateless
public class CalendarServiceMinimalImpl extends GDataSeam implements CalendarServiceMinimal {
    @In
    GoogleProperties googleProperties;

    CalendarioService calendarioService=new CalendarioServiceImpl();

    public void updateCalendarEntry(Event entry) throws IOException, GoogleProblema, ServiceException { 
        calendarioService.updateCalendarEntry(entry, googleProperties);
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<CalendarioEntry> dameEventosCalendario(Date inicio, Date fin) throws GoogleProblema {
        return calendarioService.dameEventosCalendario(inicio, fin,  googleProperties);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<CalendarioEntry> dameEventosCalendario() throws GoogleProblema {
        return calendarioService.dameEventosCalendario(googleProperties);  //To change body of implemented methods use File | Settings | File Templates.
    }
}
