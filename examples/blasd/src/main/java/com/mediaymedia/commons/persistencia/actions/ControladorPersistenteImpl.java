package com.mediaymedia.commons.persistencia.actions;

import com.mediaymedia.commons.persistencia.model.DAO;
import com.mediaymedia.commons.persistencia.excepciones.AccionPersistenciaExcepcion;
import com.mediaymedia.commons.actions.ControladorImpl;

import java.util.List;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:22:33
 * To change this template use File | Settings | File Templates.
 */
public abstract class ControladorPersistenteImpl extends ControladorImpl implements ControladorPersistente {

    protected abstract DAO getDAO();

    protected abstract AccionPersistente getAccion();


    public void persiste() throws AccionPersistenciaExcepcion {
        List<ObjetoPersistente> list = getAccion().getObjetosPersistentes();
        for (ObjetoPersistente op : list)
            if (op.getAccion().equals(ObjetoPersistente.Accion.delete))
                getDAO().delete(op.getObject());
            else if (op.getAccion().equals(ObjetoPersistente.Accion.save))
                getDAO().saveOrUpdate(op.getObject());
            else if (op.getAccion().equals(ObjetoPersistente.Accion.update))
                getDAO().merge(op.getObject());
    }
}
