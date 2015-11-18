package com.mediaymedia.commons.persistencia.excepciones;

import com.mediaymedia.commons.actions.excepciones.AccionExcepcion;
import com.mediaymedia.commons.actions.excepciones.ControladorExcepcion;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:09:12
 * To change this template use File | Settings | File Templates.
 */
public class ControladorPersistenciaExcepcion extends AccionPersistenciaExcepcion {
    public ControladorPersistenciaExcepcion() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ControladorPersistenciaExcepcion(String string) {
        super(string);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ControladorPersistenciaExcepcion(String string, Throwable throwable) {
        super(string, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ControladorPersistenciaExcepcion(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
