package com.mediaymedia.info.servicios;

import com.mediaymedia.commons.persistencia.actions.ObjetoPersistente;
import com.mediaymedia.commons.persistencia.actions.ObjetoPersistenteUpdate;
import com.mediaymedia.commons.persistencia.actions.ObjetoPersistenteDelete;
import com.mediaymedia.info.actions.Movimiento;
import com.mediaymedia.info.actions.SituacionDeEntidad;
import com.mediaymedia.info.model.EntidadBase;
import com.mediaymedia.info.model.IndiceSeccion;
import com.mediaymedia.info.model.IndiceSeccionImpl;
import com.mediaymedia.info.model.SeccionBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Set;

/**
 *
 * User: juanitu
 * Date: 23-mar-2007
 * Time: 12:05:41
 * To change this template use File | Settings | File Templates.
 */
public class ServicioIndicesImpl implements ServicioIndices {
    protected static Log log = LogFactory.getLog(ServicioIndicesImpl.class);


    public void persisteUpdate(List<ObjetoPersistente> objetosPersistentes, EntidadBase entidadBase) {
        objetosPersistentes.add(new ObjetoPersistenteUpdate(entidadBase));


    }

    public void persisteUpdate(List<ObjetoPersistente> objetosPersistentes, Set<EntidadBase> entidadesBase) {
        for (EntidadBase eb : entidadesBase)
            objetosPersistentes.add(new ObjetoPersistenteUpdate(eb));

    }

    public IndiceSeccion creaIndice(SeccionBase seccionDestino, EntidadBase.tipo tipoEntidad) {
        IndiceSeccion indiceSeccion = new IndiceSeccionImpl(seccionDestino, tipoEntidad);
        indiceSeccion.creaIndice();
        return indiceSeccion;
    }

    public void persisteDelete(List<ObjetoPersistente> objetosPersistentes, EntidadBase entidadBase) {
        objetosPersistentes.add(new ObjetoPersistenteDelete(entidadBase));
    }


}
