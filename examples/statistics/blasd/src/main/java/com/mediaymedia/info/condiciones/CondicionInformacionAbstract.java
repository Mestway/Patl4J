package com.mediaymedia.info.condiciones;

import com.mediaymedia.info.model.EntidadBase;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 17:18:31
 * To change this template use File | Settings | File Templates.
 */
public class CondicionInformacionAbstract {
    protected String lanzaErrorOrden(EntidadBase entidadBase, int orden) {
        return "el orden de la entidad es: " + entidadBase.getOrden() + " y el esperado: " + orden;
    }

}
