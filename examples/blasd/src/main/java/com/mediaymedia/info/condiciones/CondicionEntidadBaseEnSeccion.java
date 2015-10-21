package com.mediaymedia.info.condiciones;

import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.info.exceptions.InfoCondicionExcepcion;
import com.mediaymedia.info.model.EntidadBase;
import com.mediaymedia.info.model.SeccionBase;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 16:19:20
 * To change this template use File | Settings | File Templates.
 */
public class CondicionEntidadBaseEnSeccion extends CondicionInformacionAbstract implements Condicion {

    SeccionBase seccionBase;
    EntidadBase entidadBase;


    public CondicionEntidadBaseEnSeccion(EntidadBase entidadBase, SeccionBase seccionBase) {
        this.entidadBase = entidadBase;
        this.seccionBase = seccionBase;
    }

    public void pasa() throws InfoCondicionExcepcion {
//        if (seccionBase == null)
//            throw new InfoCondicionExcepcion("la seccion es nula");
//        if (entidadBase == null)
//            throw new InfoCondicionExcepcion("la informacion es nula");

        if (!entidadBase.getSeccionBase().getId().equals(seccionBase.getId()))
            throw new InfoCondicionExcepcion("la entidad: " + entidadBase.getNombre() + " tiene como seccion base: " +
                    entidadBase.getSeccionBase().getNombre() + " se espera que tenga: " + seccionBase.getNombre());

    }
}
