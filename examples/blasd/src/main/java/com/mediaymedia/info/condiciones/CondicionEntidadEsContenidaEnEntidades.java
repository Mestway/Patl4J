package com.mediaymedia.info.condiciones;

import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.info.actions.AccionEntidadBase;
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
public class CondicionEntidadEsContenidaEnEntidades extends CondicionInformacionAbstract implements Condicion {

    SeccionBase seccionBase;

    EntidadBase entidadBase;
    EntidadBase.tipo tipo;

    public CondicionEntidadEsContenidaEnEntidades(EntidadBase entidadBase, SeccionBase seccionBase, EntidadBase.tipo tipo) {
        this.entidadBase = entidadBase;
        this.seccionBase = seccionBase;
        this.tipo = tipo;
    }

    public void pasa() throws InfoCondicionExcepcion {

        if (seccionBase == null)
            throw new InfoCondicionExcepcion("la seccion es nula");
        if (entidadBase == null)
            throw new InfoCondicionExcepcion("la entidad es nula");
        if (AccionEntidadBase.calculaEntidadesAOrdenar(seccionBase, tipo).contains(entidadBase))
            throw new InfoCondicionExcepcion("la entidad: " + entidadBase.getNombre() + " todav’a permanece en el set de entidades de : " + seccionBase.getNombre());

    }
}
