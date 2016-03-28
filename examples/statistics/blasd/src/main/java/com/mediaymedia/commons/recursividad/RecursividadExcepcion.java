package com.mediaymedia.commons.recursividad;

/**
 *
 * User: juanitu
 * Date: 16-mar-2007
 * Time: 19:20:17
 * To change this template use File | Settings | File Templates.
 */
public class RecursividadExcepcion extends RuntimeException {


    public RecursividadExcepcion() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public RecursividadExcepcion(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public RecursividadExcepcion(String string, Throwable throwable) {
        super(string, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public RecursividadExcepcion(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
