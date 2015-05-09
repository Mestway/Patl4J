package com.mediaymedia.info.actions;

import com.mediaymedia.info.exceptions.InfoExcepcion;
import com.mediaymedia.info.model.EntidadBase;
import com.mediaymedia.info.model.EstadoEntidad;
import com.mediaymedia.info.model.IndiceSeccion;
import com.mediaymedia.info.condiciones.*;
import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.commons.actions.Resultado;
import com.mediaymedia.commons.actions.excepciones.AccionExcepcion;
import com.mediaymedia.commons.persistencia.actions.ObjetoPersistenteUpdate;

/**
 *
 * User: juanitu
 * Date: 20-mar-2007
 * Time: 13:44:28
 * To change this template use File | Settings | File Templates.
 */
public class AccionOrdenDeEntidad extends AccionEntidadBase implements AccionInformacion {
    Integer inicio;
    Integer destino;


    public AccionOrdenDeEntidad(EntidadBase entidad, Integer inicio, Integer destino) {
        super(entidad);
        this.seccionBase = entidad.getSeccionBase();
        this.inicio = inicio;
        this.destino = destino;
    }

    public void grabarEstadoInicial() {
//        estadoEntidad = new EstadoEntidad(getSeccionBase(), grabarEstado(seccionBase, calculaEntidadesAOrdenar(seccionBase), inicio, destino));
    }

    public Integer getDestino() {
        return destino;
    }


    public Integer getInicio() {
        return inicio;
    }


    public Condicion[] getPreCodiciones() {
        return new Condicion[0];
//        {
//                new CondicionVerificaOrden(entidadBase, inicio),
//                new CondicionNoMoverSeccionRaiz(entidadBase),
//                new CondicionLimiteMovimiento(destino, calculaEntidadesAOrdenar(seccionBase).size()),
//        };
    }

    public Condicion[] getPostCodiciones() {
        return new Condicion[0];
//        {
//                new CondicionVerificaOrden(entidadBase, destino),
//                new CondicionNoMoverSeccionRaiz(entidadBase),
//                new CondicionEntidadBaseEnSeccion(entidadBase,estadoEntidad.getSeccionBase()),
//                new CondicionCoherenciaIndices(estadoEntidad.getIndiceEntidad(), calculaEntidadesAOrdenar(seccionBase))
//
//        };
    }

    public Resultado ejecuta() throws AccionExcepcion {
        IndiceSeccion indiceSeccion = servicioIndices.creaIndice(seccionBase, entidadBase.getTipoEntidad());
        indiceSeccion.mueve(entidadBase, inicio, destino);
        servicioIndices.persisteUpdate(getObjetosPersistentes(), indiceSeccion.getActualizables());
        servicioIndices.persisteUpdate(getObjetosPersistentes(), entidadBase);

//        aplicarCambios(getEstadoEntidad().getIndiceEntidad(), getEntidades());
//        entidadBase.setOrden(destino);
//        getObjetosPersistentes().add(new ObjetoPersistenteUpdate(entidadBase));
        return null;
    }

}
