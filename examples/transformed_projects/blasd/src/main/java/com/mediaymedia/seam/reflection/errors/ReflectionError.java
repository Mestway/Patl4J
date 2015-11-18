package com.mediaymedia.seam.reflection.errors;

import com.mediaymedia.commons.excepciones.MediaExcepcion;

/**
 * User: juan
 * Date: 24-oct-2007
 * Time: 18:24:19
 */
public class ReflectionError extends MediaExcepcion {

    public ReflectionError() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ReflectionError(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ReflectionError(String string, Throwable throwable) {
        super(string, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ReflectionError(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
