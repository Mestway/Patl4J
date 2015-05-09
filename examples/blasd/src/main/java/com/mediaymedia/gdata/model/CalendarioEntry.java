package com.mediaymedia.gdata.model;

import com.google.api.services.calendar.model;
import com.google.gdata.data.DateTime;

import java.util.Date;
import java.util.Calendar;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 12:35:13
 */
public class CalendarioEntry {
     Date startTime;
    Date endTime;
    String titulo;
    String contenido;
    String lugar;
    Event entry;
    String id;

    public CalendarioEntry(Event entry) {
        this.id=entry.getId();
        this.entry=entry;
        titulo=entry.getSummary();
        contenido = obteinHtml(entry);
        System.out.println(titulo);
        //// if(entry.getTimes().size()>0){ 
        EventDateTime v = new EventDateTime();
        v = entry.getStart();
        startTime= generaDate(v.getDate());
        v = entry.getEnd();
        endTime= generaDate(v.getDate());
        //// }

        //// if(entry.getLocations().size()>0) 
        lugar=entry.getLocation();
        /*
            auto x = entry.getLocations();
            int n = x.size();
            if (n > 0) {
                lugar = x.get(0).getValueString();
            }
            */

    }


    public Event getEntry() { 
        return entry;
    }

    private Date generaDate(DateTime start) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(start.getValue());
        return calendar.getTime();
    }

    private String obteinHtml(Event entry) { 
        //// TextContent content = ((TextContent) entry.getContent()); 
        //// TextConstruct ptc = (TextConstruct) content.getContent(); 
        //// return  ptc.getPlainText(); 
        return null;
    }


    public String getContenido() {
        return contenido;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getTitulo() {
        return titulo;
    }


    public String getLugar() {
        return lugar;
    }


    public String getId() {
        return id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
