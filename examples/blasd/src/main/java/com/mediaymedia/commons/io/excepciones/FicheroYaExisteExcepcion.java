package com.mediaymedia.commons.io.excepciones;

/**
 * User: juan
 * Date: 21-sep-2007
 * Time: 13:55:32
 */
public class FicheroYaExisteExcepcion extends Exception{
    public FicheroYaExisteExcepcion() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FicheroYaExisteExcepcion(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FicheroYaExisteExcepcion(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FicheroYaExisteExcepcion(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
