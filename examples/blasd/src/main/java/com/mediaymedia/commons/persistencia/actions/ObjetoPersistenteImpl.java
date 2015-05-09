package com.mediaymedia.commons.persistencia.actions;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 16:51:12
 * To change this template use File | Settings | File Templates.
 */
public abstract class ObjetoPersistenteImpl implements ObjetoPersistente {
    Accion accion;
    Object object;


    public Accion getAccion() {
        return accion;
    }

    public Object getObject() {
        return object;
    }
}
