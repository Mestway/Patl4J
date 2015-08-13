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
public class CondicionNoMoverSeccionRaiz extends CondicionInformacionAbstract implements Condicion {

    EntidadBase entidadBase;


    public CondicionNoMoverSeccionRaiz(EntidadBase entidadBase) {
        this.entidadBase = entidadBase;
    }

    public void pasa() throws InfoCondicionExcepcion {
        if (entidadBase == null)
            throw new InfoCondicionExcepcion("la entidad es nula ");
        if (entidadBase.getSeccionBase() == null)
            throw new InfoCondicionExcepcion("Est‡ intentando mover la seccion raiz " +
                    "y eso no est‡ permitido ");


    }
}
