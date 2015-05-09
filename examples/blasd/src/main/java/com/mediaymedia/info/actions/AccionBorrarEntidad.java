package com.mediaymedia.info.actions;

import com.mediaymedia.commons.actions.Resultado;
import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.commons.actions.excepciones.AccionExcepcion;
import com.mediaymedia.commons.persistencia.actions.ObjetoPersistenteDelete;
import com.mediaymedia.info.condiciones.CondicionCoherenciaIndices;
import com.mediaymedia.info.condiciones.CondicionEntidadEsContenidaEnEntidades;
import com.mediaymedia.info.condiciones.CondicionNoBorrarSeccionRaiz;
import com.mediaymedia.info.condiciones.CondicionVerificaOrden;
import com.mediaymedia.info.model.EntidadBase;
import com.mediaymedia.info.model.EstadoEntidad;
import com.mediaymedia.info.model.IndiceSeccion;

/**
 *
 * User: juanitu
 * Date: 21-mar-2007
 * Time: 11:16:24
 * To change this template use File | Settings | File Templates.
 */
public class AccionBorrarEntidad extends AccionEntidadBase implements AccionInformacion {


    public AccionBorrarEntidad(EntidadBase entidadBase) {
        super(entidadBase);
        this.seccionBase = entidadBase.getSeccionBase();
    }


    protected void grabarEstadoInicial() {
//        estadoEntidad = new EstadoEntidad(seccionBase,
//                grabarEstadoIndiceConEntidadAEliminar(calculaEntidadesAOrdenar(seccionBase), entidadBase));
    }

    public Condicion[] getPreCodiciones() {
        return new Condicion[0];
    }

    public Condicion[] getPostCodiciones() {
        return new Condicion[0];
    }

    public Resultado ejecuta() throws AccionExcepcion {
        IndiceSeccion indiceSeccion = servicioIndices.creaIndice(seccionBase, entidadBase.getTipoEntidad());
        indiceSeccion.borra(entidadBase);
        servicioIndices.persisteUpdate(getObjetosPersistentes(), indiceSeccion.getActualizables());
        servicioIndices.persisteDelete(getObjetosPersistentes(), entidadBase);

        return null;

    }


}
