package com.mediaymedia.commons.actions.condiciones;

import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.info.exceptions.InfoCondicionExcepcion;
import com.mediaymedia.info.condiciones.CondicionInformacionAbstract;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 16:19:20
 * To change this template use File | Settings | File Templates.
 */
public class CondicionIgualdad extends CondicionInformacionAbstract implements Condicion {


    int aInt, bInt;


    public CondicionIgualdad(int a1, int a2) {
        this.bInt = a1;
        this.aInt = a2;
    }

    public void pasa() throws InfoCondicionExcepcion {

        if (aInt != bInt)
            throw new InfoCondicionExcepcion("los indices no se corresponde: " + aInt + "!=" + bInt);

    }

}
