package com.mediaymedia.info.condiciones;

import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.info.model.EntidadBase;
import com.mediaymedia.info.exceptions.InfoCondicionExcepcion;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 16:19:20
 * To change this template use File | Settings | File Templates.
 */
public class CondicionVerificaOrden extends CondicionInformacionAbstract implements Condicion {


    EntidadBase entidadBase;
    int orden;


    public CondicionVerificaOrden(EntidadBase entidadBase, int orden) {
        this.entidadBase = entidadBase;
        this.orden = orden;
    }

    public void pasa() throws InfoCondicionExcepcion {
        if (entidadBase.getOrden() != orden)
            throw new InfoCondicionExcepcion(lanzaErrorOrden(entidadBase, orden));


    }

}
