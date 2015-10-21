package com.mediaymedia.info.servicios;

import com.mediaymedia.commons.persistencia.actions.ObjetoPersistente;
import com.mediaymedia.info.model.EntidadBase;
import com.mediaymedia.info.model.IndiceSeccion;
import com.mediaymedia.info.model.SeccionBase;

import java.util.List;
import java.util.Set;

/**
 *
 * User: juanitu
 * Date: 23-mar-2007
 * Time: 12:24:44
 * To change this template use File | Settings | File Templates.
 */
public interface ServicioIndices {


    void persisteUpdate(List<ObjetoPersistente> objetosPersistentes, EntidadBase entidadBase);

    void persisteUpdate(List<ObjetoPersistente> objetosPersistentes, Set<EntidadBase> entidadBase);

    IndiceSeccion creaIndice(SeccionBase seccionDestino, EntidadBase.tipo tipoEntidad);

    void persisteDelete(List<ObjetoPersistente> objetosPersistentes, EntidadBase entidadBase);
}
