package com.mediaymedia.commons.persistencia.actions;

import com.mediaymedia.commons.actions.Controlador;
import com.mediaymedia.commons.persistencia.excepciones.AccionPersistenciaExcepcion;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:08:41
 * To change this template use File | Settings | File Templates.
 */
public interface ControladorPersistente extends Controlador {

    void persiste() throws AccionPersistenciaExcepcion;

}
