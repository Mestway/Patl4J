package com.mediaymedia.info.condiciones;

import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.info.exceptions.InfoCondicionExcepcion;
import com.mediaymedia.info.exceptions.InfoExcepcion;
import com.mediaymedia.info.model.EntidadBase;

import java.util.Set;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 16:19:20
 * To change this template use File | Settings | File Templates.
 */
public class CondicionIgualdadSize extends CondicionInformacionAbstract implements Condicion {


    Set<EntidadBase> entidades;

    int sizeEsperado;


    public CondicionIgualdadSize(Set<EntidadBase> entidades, int sizeEsperado) {
        this.entidades = entidades;
        this.sizeEsperado = sizeEsperado;
    }

    public void pasa() throws InfoCondicionExcepcion {
        if (entidades.size() != sizeEsperado)
            throw new InfoCondicionExcepcion(" el numero de entidades es: " + entidades.size() + " el esperado: " + sizeEsperado);


    }

}
