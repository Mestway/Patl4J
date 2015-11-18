package com.mediaymedia.commons.persistencia.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 16:57:07
 * To change this template use File | Settings | File Templates.
 */
public abstract class AccionPersistenteImpl implements AccionPersistente {
    protected static Log log = LogFactory.getLog(AccionPersistenteImpl.class);

    protected List<ObjetoPersistente> persistentes = new ArrayList<ObjetoPersistente>();

    public List<ObjetoPersistente> getObjetosPersistentes() {
        return persistentes;
    }

}
