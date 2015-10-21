package com.mediaymedia.info.exceptions;

import com.mediaymedia.commons.persistencia.excepciones.AccionPersistenciaExcepcion;

/**
 *
 * User: juanitu
 * Date: 20-mar-2007
 * Time: 15:34:23
 * To change this template use File | Settings | File Templates.
 */
public class InfoExcepcion extends AccionPersistenciaExcepcion {

    public InfoExcepcion() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public InfoExcepcion(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public InfoExcepcion(String string, Throwable throwable) {
        super(string, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public InfoExcepcion(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
