package com.mediaymedia.commons.actions.condiciones;

import com.mediaymedia.commons.actions.excepciones.CondicionExcepcion;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:06:14
 * To change this template use File | Settings | File Templates.
 */
public interface Condicion {
    void pasa() throws CondicionExcepcion;
}
