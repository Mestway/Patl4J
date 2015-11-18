package com.mediaymedia.gdata.model;

import com.google.gdata.data.Entry;
import com.google.gdata.data.TextContent;
import com.google.gdata.data.HtmlTextConstruct;

/**
 *
 * User: juan
 * Date: 31-ago-2007
 * Time: 14:32:24
 * To change this template use File | Settings | File Templates.
 */
public class BlogEntry {

    private String titulo;
    private String contenido;
    private String id;
    private Entry entry;

    public BlogEntry(Entry entry) {
        TextContent content = ((TextContent) entry.getContent());
        HtmlTextConstruct ptc = (HtmlTextConstruct) content.getContent();
        this.contenido = ptc.getHtml();
        this.titulo = entry.getTitle().getPlainText();
        id=entry.getId();
        this.entry=entry;
    }


    public Entry getEntry() {
        return entry;
    }

    public String getId() {
        return id;
    }

    public String getContenido() {
        return contenido;
    }


    public String getTitulo() {
        return titulo;
    }


    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
