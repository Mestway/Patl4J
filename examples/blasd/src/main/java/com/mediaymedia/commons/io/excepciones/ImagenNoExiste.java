package com.mediaymedia.commons.io.excepciones;

/**
 * User: juan
 * Date: 28-sep-2007
 * Time: 14:15:17
 */
public class ImagenNoExiste extends IOExcepcion{
    public ImagenNoExiste() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ImagenNoExiste(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ImagenNoExiste(String string, Throwable throwable) {
        super(string, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ImagenNoExiste(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
