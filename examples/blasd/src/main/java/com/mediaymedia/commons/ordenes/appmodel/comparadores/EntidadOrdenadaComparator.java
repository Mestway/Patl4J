package com.mediaymedia.commons.ordenes.appmodel.comparadores;

import com.mediaymedia.commons.ordenes.model.EntidadOrdenada;

import java.util.Comparator;

/**
 *
 * User: juanitu
 * Date: 05-dic-2005
 * Time: 14:27:48
 * To change this template use File | Settings | File Templates.
 */
public class EntidadOrdenadaComparator implements Comparator {
    public int compare(Object a, Object b) {
        EntidadOrdenada eo1 = (EntidadOrdenada) a;
        EntidadOrdenada eo2 = (EntidadOrdenada) b;
        if (eo1.getOrden() > eo2.getOrden())
            return 1;
        else
            return -1;

    }


}
