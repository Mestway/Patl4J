package com.mediaymedia.seam.reflection;

import com.mediaymedia.seam.reflection.errors.ReflectionError;

/**
 * User: juan
 * Date: 24-oct-2007
 * Time: 18:25:43
 */
public interface ServiceReflection {
     String seamName="serviceReflection";

    Object invocaGetter(Object o, String nombrePropiedad) throws ReflectionError;

    String invocaGetter(ObjetoConPropiedadPublica g) throws ReflectionError;

     <T> T creaObjectoPropiedadPublica(Class<T> aClass, Object valor) throws ReflectionError;
}
