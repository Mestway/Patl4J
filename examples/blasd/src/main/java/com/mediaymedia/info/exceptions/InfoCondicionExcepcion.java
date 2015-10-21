package com.mediaymedia.info.exceptions;

import com.mediaymedia.commons.actions.excepciones.CondicionExcepcion;

/**
 *
 * User: juanitu
 * Date: 20-mar-2007
 * Time: 15:34:23
 * To change this template use File | Settings | File Templates.
 */
public class InfoCondicionExcepcion extends CondicionExcepcion {

    public InfoCondicionExcepcion() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public InfoCondicionExcepcion(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public InfoCondicionExcepcion(String string, Throwable throwable) {
        super(string, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public InfoCondicionExcepcion(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
