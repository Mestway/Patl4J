package com.mediaymedia.commons.ordenes.model;

/**
 *
 * User: juanitu
 * Date: 19-feb-2007
 * Time: 16:17:28
 * To change this template use File | Settings | File Templates.
 */
public interface EntidadOrdenada {
    enum operacion {
        aumentar, reducir
    }

    Integer getOrden();

    void aumentar();

    void reducir();

    void setOrden(Integer orden);

    String getNombre();

    Integer getId();
}
