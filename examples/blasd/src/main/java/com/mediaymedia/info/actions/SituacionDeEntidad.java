package com.mediaymedia.info.actions;

import com.mediaymedia.info.model.EntidadBase;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 20:43:07
 * To change this template use File | Settings | File Templates.
 */
public enum SituacionDeEntidad {

    AntesDeIntervalo, DespuesDeIntervalo, DentroDeIntervalo;


    public static SituacionDeEntidad dameSituacion(EntidadBase entidadBase, int comienzo, int destino) {
        if (estaEntidadAntesDeIntervalo(entidadBase, comienzo, destino))
            return SituacionDeEntidad.AntesDeIntervalo;
        else if (estaEntidadDespuesDeIntervalo(entidadBase, comienzo, destino))
            return SituacionDeEntidad.DespuesDeIntervalo;
        else if (estaEntidadDentroDeIntervalo(entidadBase, comienzo, destino))
            return SituacionDeEntidad.DentroDeIntervalo;
        throw new RuntimeException("opcion no contemplada: orden actual: " + entidadBase.getOrden() + " comienzo: " + comienzo + " destino:" + destino);  //To change body of created methods use File | Settings | File Templates.
    }


    protected static boolean estaEntidadDespuesDeIntervalo(EntidadBase i, int comienzo, int destino) {
        return i.getOrden() > destino && i.getOrden() > comienzo;
    }

    protected static boolean estaEntidadAntesDeIntervalo(EntidadBase i, int comienzo, int destino) {
        return i.getOrden() < comienzo && i.getOrden() < destino;
    }

    protected static boolean estaEntidadDentroDeIntervalo(EntidadBase i, int comienzo, int destino) {
        return (i.getOrden() >= comienzo && i.getOrden() <= destino) || (i.getOrden() <= comienzo && i.getOrden() >= destino);
    }

    protected static String logInicioFin(int comienzo, int destino) {
        return "inicio: " + comienzo + " fin:" + destino;
    }

}
