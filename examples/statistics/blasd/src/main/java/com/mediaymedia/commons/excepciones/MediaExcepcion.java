package com.mediaymedia.commons.excepciones;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:03:12
 * To change this template use File | Settings | File Templates.
 */
public class MediaExcepcion extends Exception {
    public MediaExcepcion() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public MediaExcepcion(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public MediaExcepcion(String string, Throwable throwable) {
        super(string, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public MediaExcepcion(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
