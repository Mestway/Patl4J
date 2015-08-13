package com.mediaymedia.commons.io.excepciones;

import com.mediaymedia.commons.excepciones.MediaExcepcion;

/**
 *
 * User: juan
 * Date: 30-may-2007
 * Time: 15:01:09
 * To change this template use File | Settings | File Templates.
 */
public class IOExcepcion extends MediaExcepcion {


    public IOExcepcion() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public IOExcepcion(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public IOExcepcion(String string, Throwable throwable) {
        super(string, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public IOExcepcion(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
