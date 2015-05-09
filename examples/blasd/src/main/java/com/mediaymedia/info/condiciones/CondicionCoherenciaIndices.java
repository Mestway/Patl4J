package com.mediaymedia.info.condiciones;

import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.info.exceptions.InfoCondicionExcepcion;
import com.mediaymedia.info.model.EntidadBase;
import com.mediaymedia.info.model.IndiceSeccion;

import java.util.Set;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 16:19:20
 * To change this template use File | Settings | File Templates.
 */
public class CondicionCoherenciaIndices extends CondicionInformacionAbstract implements Condicion {


    IndiceSeccion indiceSeccionImpl;
    Set entidadesA;


    public CondicionCoherenciaIndices(IndiceSeccion indiceSeccionImpl, Set entidadesA) {
        this.entidadesA = entidadesA;
        this.indiceSeccionImpl = indiceSeccionImpl;
    }

    public void pasa() throws InfoCondicionExcepcion {
    }


}
