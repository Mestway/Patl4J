package com.mediaymedia.info.condiciones;

import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.info.model.EntidadBase;
import com.mediaymedia.info.exceptions.InfoCondicionExcepcion;
import com.mediaymedia.info.exceptions.InfoExcepcion;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 16:19:20
 * To change this template use File | Settings | File Templates.
 */
public class CondicionLimiteMovimiento extends CondicionInformacionAbstract implements Condicion {


    int maximo, destino;


    public CondicionLimiteMovimiento(int destino, int maximo) {
        this.destino = destino;
        this.maximo = maximo;
    }

    public void pasa() throws InfoCondicionExcepcion {

        if (maximo < destino)
            throw new InfoCondicionExcepcion("para el movimiento tiene como maximo: " + maximo
                    + " y el cambio de orden es a:" + destino);

    }

}
