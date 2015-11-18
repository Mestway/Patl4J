package com.mediaymedia.info.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * User: juanitu
 * Date: 20-mar-2007
 * Time: 16:36:52
 * To change this template use File | Settings | File Templates.
 */
public class EstadoEntidad {
    private SeccionBase seccionBase;
    private IndiceSeccion indiceSeccionImpl;
    private Set<InformacionBase> informaciones;
    private Set<SeccionBase> secciones;

    public EstadoEntidad(SeccionBase seccionBase, IndiceSeccion indiceSeccionImpl) {
        this.indiceSeccionImpl = indiceSeccionImpl;
        this.seccionBase = seccionBase;
        this.informaciones = new HashSet<InformacionBase>(seccionBase.getInformacionesBase());
        this.secciones = new HashSet<SeccionBase>(seccionBase.getSeccionesBase());
    }


    public IndiceSeccion getIndiceEntidad() {
        return indiceSeccionImpl;
    }

    public Set getEntidades() {
        if (indiceSeccionImpl.getTipo().equals(EntidadBase.tipo.informacion)) {
            return informaciones;
        } else {
            return secciones;
        }
    }

    public SeccionBase getSeccionBase() {
        return seccionBase;
    }

}
