package com.mediaymedia.seam.reflection;

import javax.persistence.Transient;

/**
 * User: juan
 * Date: 24-oct-2007
 * Time: 21:09:20
 */

/**
 * se utiliza para algunos componentes que muestran una propiedad en capa vista y se accede a dicha propiedad por reflexion
 */
public interface ObjetoConPropiedadPublica {

    @Transient
          String getNombrePropiedad();
}
