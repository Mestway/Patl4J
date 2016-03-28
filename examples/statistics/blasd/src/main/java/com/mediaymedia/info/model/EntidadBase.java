package com.mediaymedia.info.model;

import com.mediaymedia.commons.ordenes.model.EntidadOrdenada;

/**
 *
 * User: juanitu
 * Date: 21-mar-2007
 * Time: 11:43:49
 * To change this template use File | Settings | File Templates.
 */
public interface EntidadBase extends EntidadOrdenada {
    enum tipo {
        seccion, informacion
    }

    SeccionBase getSeccionBase();

    void setSeccionBase(SeccionBase seccionBase);

    tipo getTipoEntidad();
}
