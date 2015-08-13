package com.mediaymedia.seam.intercept;

import com.mediaymedia.commons.imagenes.ServicioImagenes;
import com.mediaymedia.commons.strings.ServicioStrings;
import com.mediaymedia.faces.FacesServicio;
import org.jboss.seam.Component;
import org.jboss.seam.contexts.Contexts;

import javax.interceptor.InvocationContext;

/**
 * User: juan
 * Date: 10-oct-2007
 * Time: 22:05:58
 */
public class SeamBaseInterceptor {

    public String getViewId() {
        return getFacesServicio().getViewId();
    }

    public FacesServicio getFacesServicio() {
        return dameComponenteStateless(FacesServicio.class, FacesServicio.seamName);
    }

    protected ServicioStrings getEinaString() {
        return dameComponenteStateless(ServicioStrings.class, ServicioStrings.seamName);
    }

    protected ServicioImagenes getServicioImagenes() {
        return dameComponenteStateless(ServicioImagenes.class, ServicioImagenes.seamName);
    }

    protected <T> T dameComponenteConversacion(Class<T> claz, String nombre) {
        return (T) Contexts.getConversationContext().get(nombre);
    }
    protected Object dameComponenteConversacion(String nombre) {
        return  Contexts.getConversationContext().get(nombre);
    }
    protected Object dameComponenteEvento(String nombre) {
        return  Contexts.getEventContext().get(nombre);
    }

    protected <T> T dameComponentePagina(Class<T> claz, String nombre) {
        return (T) Contexts.getPageContext().get(nombre);
    }

    protected <T> T dameComponenteEvento(Class<T> claz, String nombre) {
        return (T) Contexts.getEventContext().get(nombre);
    }

    protected <T> T dameComponenteSesion(Class<T> claz, String nombre) {
        return (T) Contexts.getSessionContext().get(nombre);
    }

    protected <T> T dameComponenteStateless(Class<T> claz, String nombre) {
        return (T) Component.getInstance(nombre);


    }

    protected String getMetodName(InvocationContext invocation) {
        return invocation.getMethod().getName();
    }



    protected void logea(String M) {
        System.out.print("INTERCEPTOR::: " + this.getClass().getSimpleName() + "******____ " + M);
    }
}
