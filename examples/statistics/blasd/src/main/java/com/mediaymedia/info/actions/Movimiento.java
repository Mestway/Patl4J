package com.mediaymedia.info.actions;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 20:43:07
 * To change this template use File | Settings | File Templates.
 */
public enum Movimiento {

    Avance, Retroceso;

    public static Movimiento dameMovimiento(int origen, int destino) {
        if (origen < destino) return Movimiento.Avance;
        return Movimiento.Retroceso;
    }

}
