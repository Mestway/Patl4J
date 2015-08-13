package com.mediaymedia.commons.io.excepciones;

/**
 * User: juan
 * Date: 21-sep-2007
 * Time: 13:55:32
 */
public class FicheroNoSeleccionadoExcepcion extends Exception{
    public FicheroNoSeleccionadoExcepcion() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FicheroNoSeleccionadoExcepcion(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FicheroNoSeleccionadoExcepcion(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FicheroNoSeleccionadoExcepcion(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
