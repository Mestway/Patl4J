package com.mediaymedia.richfaces;

import com.mediaymedia.seam.reflection.ObjetoConPropiedadPublica;

import java.util.List;
import java.util.ArrayList;

/**
 * User: juan
 * Date: 26-oct-2007
 * Time: 14:10:21
 */
public class SeleccionSugestionBox {

    List<ObjetoConPropiedadPublica> existentes=new ArrayList<ObjetoConPropiedadPublica>();
    List<ObjetoConPropiedadPublica> inexistentes=new ArrayList<ObjetoConPropiedadPublica>();


    public List<ObjetoConPropiedadPublica> getExistentes() {
        return existentes;
    }

    public void setExistentes(List<ObjetoConPropiedadPublica> existentes) {
        this.existentes = existentes;
    }

    public List<ObjetoConPropiedadPublica> getInexistentes() {
        return inexistentes;
    }

    public void setInexistentes(List<ObjetoConPropiedadPublica> inexistentes) {
        this.inexistentes = inexistentes;
    }
}
