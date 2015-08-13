package com.mediaymedia.commons.actions;

import com.mediaymedia.commons.actions.excepciones.ControladorExcepcion;
import com.mediaymedia.commons.actions.excepciones.CondicionExcepcion;
import com.mediaymedia.commons.actions.excepciones.AccionExcepcion;
import com.mediaymedia.commons.actions.condiciones.Condicion;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 16:10:49
 * To change this template use File | Settings | File Templates.
 */
public abstract class ControladorImpl implements Controlador {
    protected abstract Accion getAccion();

    public void pasaPrecondiciones() throws ControladorExcepcion {
        for (Condicion p : getAccion().getPreCodiciones()) {
            try {
                p.pasa();
            } catch (CondicionExcepcion condicionExcepcion) {
                throw new ControladorExcepcion(condicionExcepcion.getMessage());
            }
        }
    }

    public void pasaPostcondiciones() throws ControladorExcepcion {
        for (Condicion p : getAccion().getPostCodiciones()) {
            try {
                p.pasa();
            } catch (CondicionExcepcion condicionExcepcion) {
                throw new ControladorExcepcion(condicionExcepcion.getMessage());
            }
        }
    }

    public Resultado ejecuta() throws ControladorExcepcion {
        try {
            return getAccion().ejecuta();
        } catch (AccionExcepcion accionExcepcion) {
            throw new ControladorExcepcion(accionExcepcion.getMessage());
        }

    }


}
