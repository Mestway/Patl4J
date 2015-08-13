package com.mediaymedia.commons.io.image;

/**
 *
 * User: juan
 * Date: 30-may-2007
 * Time: 18:07:16
 * To change this template use File | Settings | File Templates.
 */
public enum Proporcion {
    vertical, horizontal, cuadrada;

    /*
    solo acepta vertical o horizontal
     */
    public static Proporcion dameContrario(Proporcion principal) {
        if (principal.equals(vertical)) return horizontal;
        else if (principal.equals(horizontal)) return vertical;
        throw new RuntimeException("esto no puede ser");
    }
}
