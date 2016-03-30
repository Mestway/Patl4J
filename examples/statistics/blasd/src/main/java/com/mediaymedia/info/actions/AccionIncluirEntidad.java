package com.mediaymedia.info.actions;

import com.mediaymedia.info.model.*;
import com.mediaymedia.info.condiciones.CondicionVerificaOrden;
import com.mediaymedia.info.condiciones.CondicionCoherenciaIndices;
import com.mediaymedia.commons.actions.Resultado;
import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.commons.actions.excepciones.AccionExcepcion;
import com.mediaymedia.commons.persistencia.actions.ObjetoPersistenteSave;

/**
 *
 * User: juanitu
 * Date: 21-mar-2007
 * Time: 10:59:05
 * To change this template use File | Settings | File Templates.
 */
public class AccionIncluirEntidad extends AccionEntidadBase implements AccionInformacion {


    public AccionIncluirEntidad(EntidadBase entidadBase, SeccionBase seccion) {
        super(entidadBase);
        this.seccionBase = seccion;
    }


    public Condicion[] getPreCodiciones() {
        return new Condicion[0];
    }

    public Condicion[] getPostCodiciones() {
        return new Condicion[0];
    }

    public Resultado ejecuta() throws AccionExcepcion {
        IndiceSeccion indiceSeccion = servicioIndices.creaIndice(seccionBase, entidadBase.getTipoEntidad());
        indiceSeccion.incluye(entidadBase);
//        servicioIndices.persisteUpdate(getObjetosPersistentes(), entidadBase);
        return null;
    }
}
