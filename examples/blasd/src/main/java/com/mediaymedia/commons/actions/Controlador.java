package com.mediaymedia.commons.actions;

import com.mediaymedia.commons.actions.excepciones.ControladorExcepcion;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:02:56
 * To change this template use File | Settings | File Templates.
 */
public interface Controlador {
    
    void pasaPrecondiciones() throws ControladorExcepcion;

    void pasaPostcondiciones() throws ControladorExcepcion;

    Resultado ejecuta() throws ControladorExcepcion;
}
