package com.mediaymedia.commons.actions.excepciones;

import com.mediaymedia.commons.excepciones.MediaExcepcion;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:03:12
 * To change this template use File | Settings | File Templates.
 */
public class ControladorExcepcion extends MediaExcepcion {
    public ControladorExcepcion() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ControladorExcepcion(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ControladorExcepcion(String string, Throwable throwable) {
        super(string, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ControladorExcepcion(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
