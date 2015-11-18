package com.mediaymedia.gdata.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.util.ServiceException;
import com.mediaymedia.gdata.GDataBaseTest;
import com.mediaymedia.gdata.model.CalendarioEntry;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 12:34:29
 */
public class CalendarServicetImplTest extends GDataBaseTest {

    @Test
    public void calendando() throws ServiceException, IOException {
        //CALENDAR

        com.google.gdata.client.calendar.CalendarService myServiceCal = new CalendarService("exampleCo-exampleApp-1");
        myServiceCal.setUserCredentials(usuarioGmail, clave);

// Send the request and print the response
        URL feedUrl = null;
        feedUrl = new URL("http://www.google.com/calendar/feeds/default/allcalendars/full");
        CalendarFeed resultFeed = null;
        resultFeed = myServiceCal.getFeed(feedUrl, CalendarFeed.class);
        System.out.println("Calendars you own:");
        System.out.println();
        recorrer(resultFeed);
    }

    public void calendandoZanfona() throws ServiceException, IOException {
        recorrerCalendario("http://www.google.com/calendar/feeds/2p0ueadbmorqefrsmc5leg154o%40group.calendar.google.com/private/full");
    }

    private void recorrer(CalendarFeed resultFeed) throws IOException, ServiceException {
        String feedUri = "";
        for (int i = 0; i < resultFeed.getEntries().size(); i++) {
            CalendarEntry entry = resultFeed.getEntries().get(i);
            System.out.println("\t" + entry.getTitle().getPlainText() + "////////////////////////////////");
            System.out.println(entry.getId());
            feedUri = (new String("http://www.google.com/calendar/feeds/" +
                    entry.getId().split("/")[entry.getId().split("/").length - 1] + "/private/full"));
            recorrerCalendario(feedUri);
        }


    }

    private void recorrerCalendario(String url) throws IOException, ServiceException {
        List<CalendarioEntry> entradas = new ArrayList<CalendarioEntry>();
        CalendarService myServiceCalBis = new CalendarService("exampleCo-exampleApp-3");
        myServiceCalBis.setUserCredentials(usuarioGmail, clave);

        CalendarEventFeed myFeed = myServiceCalBis.getFeed(new URL(url), CalendarEventFeed.class);
        for (CalendarEventEntry cita : myFeed.getEntries()) {
            CalendarioEntry calendarEntry = new CalendarioEntry(cita);
            entradas.add(calendarEntry);
            System.out.println(calendarEntry.getTitulo() + calendarEntry.getContenido() + calendarEntry.getLugar() + calendarEntry.getStartTime());


        }
    }


//    public void pruebaWords() throws ServiceException, IOException {
//        com.google.gdata.client.docs.DocsService service = new DocsService("Document List Demo");
//        service.setUserCredentials(usuarioGmail, clave);
//        showAllDocs(service);
//    }
//
//    protected void showAllDocs(DocsService service) throws IOException, ServiceException {
//        DocumentListFeed feed = service.getFeed(new URL("http://docs.google.com/feeds/documents/private/full"),
//                DocumentListFeed.class);
//
//        for (DocumentListEntry entry : feed.getEntries()) {
//            printDocumentEntry(entry);
//        }
//    }
//
//    protected void printDocumentEntry(DocumentListEntry doc) {
//        String shortId = doc.getId().substring(doc.getId().lastIndexOf('/') + 1);
//        System.out.println(" -- Document(" + shortId + "/" + doc.getTitle().getPlainText()
//                + ")");
//    }
}
