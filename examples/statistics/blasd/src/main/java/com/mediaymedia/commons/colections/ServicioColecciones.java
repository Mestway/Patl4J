package com.mediaymedia.commons.colections;

import com.mediaymedia.seam.reflection.ObjetoConPropiedadPublica;
import com.mediaymedia.seam.reflection.errors.ReflectionError;

import java.util.List;

/**
 * User: juan
 * Date: 25-oct-2007
 * Time: 15:02:09
 */
public interface ServicioColecciones {
    String seamName="servicioColecciones";

    boolean existe(String patron, String[] list);


    List<ObjetoConPropiedadPublica> dameListaConPatronIndiceComun(List<ObjetoConPropiedadPublica> resultList, String ultimo) throws ReflectionError;


}
