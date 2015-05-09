package com.mediaymedia.info.actions;

import com.mediaymedia.info.exceptions.InfoExcepcion;
import com.mediaymedia.info.model.EntidadBase;
import com.mediaymedia.info.model.EstadoEntidad;
import com.mediaymedia.info.model.SeccionBase;

/**
 *
 * User: juanitu
 * Date: 21-mar-2007
 * Time: 10:43:01
 * To change this template use File | Settings | File Templates.
 */
public interface ProcesoCambio {

    void pasaPostCondiciones() throws InfoExcepcion;

    void pasaPreCondiciones() throws InfoExcepcion;


    void grabarEstadoInicial();

    EntidadBase getEntidadBase();

    SeccionBase getSeccionBase();

    EstadoEntidad getEstadoEntidad();


}
