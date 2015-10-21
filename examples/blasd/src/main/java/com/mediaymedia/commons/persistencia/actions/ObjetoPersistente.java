package com.mediaymedia.commons.persistencia.actions;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 16:49:16
 * To change this template use File | Settings | File Templates.
 */
public interface ObjetoPersistente {
    enum Accion {
        save, update, delete
    }

    Accion getAccion();

    Object getObject();

}
