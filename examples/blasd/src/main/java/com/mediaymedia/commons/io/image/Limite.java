package com.mediaymedia.commons.io.image;

/**
 *
 * User: juan
 * Date: 30-may-2007
 * Time: 18:08:49
 * To change this template use File | Settings | File Templates.
 */
public class Limite {
    Proporcion proporcion;
    int medida;


    public Limite(Proporcion proporcion, int medida) {
        this.proporcion = proporcion;
        this.medida = medida;
    }


    public Proporcion getProporcion() {
        return proporcion;
    }

    public int getMedida() {
        return medida;
    }
}
