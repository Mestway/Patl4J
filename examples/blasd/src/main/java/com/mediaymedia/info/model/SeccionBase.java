package com.mediaymedia.info.model;

import java.util.Set;

/**
 *
 * User: juanitu
 * Date: 20-mar-2007
 * Time: 13:43:25
 * To change this template use File | Settings | File Templates.
 */
public interface SeccionBase extends EntidadBase {
    Set<InformacionBase> getInformacionesBase();

    void addInformacionBase(InformacionBase informacionBase);

    void addSeccionBase(SeccionBase seccionBase);

    Set<SeccionBase> getSeccionesBase();


}
