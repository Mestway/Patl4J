package com.mediaymedia.commons.persistencia.actions;

import com.mediaymedia.commons.actions.Accion;

import java.util.List;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:04:45
 * To change this template use File | Settings | File Templates.
 */
public interface AccionPersistente extends Accion {

    List<ObjetoPersistente> getObjetosPersistentes();
}
