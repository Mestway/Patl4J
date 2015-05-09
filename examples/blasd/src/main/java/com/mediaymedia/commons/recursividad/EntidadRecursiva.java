package com.mediaymedia.commons.recursividad;

import java.util.List;

/**
 *
 * User: juanitu
 * Date: 16-mar-2007
 * Time: 18:50:12
 * To change this template use File | Settings | File Templates.
 */
public interface EntidadRecursiva {

    EntidadRecursiva getParent();

    List<EntidadRecursiva> getChilds();

    String getNombre();

    Integer getId();
}
