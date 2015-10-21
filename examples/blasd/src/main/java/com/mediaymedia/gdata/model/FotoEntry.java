package com.mediaymedia.gdata.model;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 12:56:44
 */
public class FotoEntry {
     private Long id;
    private String titulo;
    private String url;
    private String zoom;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }


    public Object getValue() {
        return this;    //To change body of overridden methods use File | Settings | File Templates.
    }

    public String getLabel() {
        return getTitulo();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
