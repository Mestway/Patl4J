package com.mediaymedia.richfaces;

import com.mediaymedia.seam.reflection.ObjetoConPropiedadPublica;
import com.mediaymedia.seam.reflection.errors.ReflectionError;

import java.util.List;

/**
 * User: juan
 * Date: 25-oct-2007
 * Time: 14:46:09
 */
public interface SugestionBox {
     String characterSeparador=",";
     String characterSugerir="?";

    /**
     * @param suggest  es el último parametro de property
     * @param list
     * @param property
     * @return
     */
    List autocomplete(Object suggest, List<ObjetoConPropiedadPublica> list, String property) ;


    SeleccionSugestionBox dameSeleccion(String property, List objetosConPropiedadPublica, Class claz) throws ReflectionError;
    String dameCadena(List<ObjetoConPropiedadPublica> resultList) throws ReflectionError;

    Object dameOpcion(String propertyPrincipales, List<ObjetoConPropiedadPublica> aplicacionesPrincipalesList, Class aClass) throws ReflectionError;
}
