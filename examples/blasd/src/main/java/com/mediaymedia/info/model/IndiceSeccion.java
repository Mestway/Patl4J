package com.mediaymedia.info.model;

import java.util.Set;

/**
 *
 * User: juanitu
 * Date: 23-mar-2007
 * Time: 12:29:43
 * To change this template use File | Settings | File Templates.
 */
public interface IndiceSeccion {
    EntidadBase.tipo getTipo();

    Set<EntidadBase> getIniciales();

    Set<EntidadBase> getActualizables();

    SeccionBase getSeccionBase();

    void borra(EntidadBase entidadBase);

    void mueve(EntidadBase entidadBase, int origen, int destino);

    void incluye(EntidadBase entidadBase);

    void creaIndice();

}
