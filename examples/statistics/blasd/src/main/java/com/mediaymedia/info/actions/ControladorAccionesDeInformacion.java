package com.mediaymedia.info.actions;

import com.mediaymedia.commons.persistencia.actions.ControladorPersistente;
import com.mediaymedia.commons.persistencia.actions.ControladorPersistenteImpl;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:14:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class ControladorAccionesDeInformacion extends ControladorPersistenteImpl implements ControladorPersistente {

    protected abstract AccionInformacion getAccion();


}
