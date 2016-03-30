package com.mediaymedia.seam.gestion;

/**
 * User: juan
 * Date: 22-nov-2007
 * Time: 17:01:27
 */
public class FiltroAListado {

    Object atributo;
    Class claseFiltro;


    public FiltroAListado(Object atributo, Class claseFiltro) {
        this.atributo = atributo;
        this.claseFiltro = claseFiltro;

    }


    public Class getClaseFiltro() {
        return claseFiltro;
    }


    public Object getAtributo() {
        return atributo;
    }
}
