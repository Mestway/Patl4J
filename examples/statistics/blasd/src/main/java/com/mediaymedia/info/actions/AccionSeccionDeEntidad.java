package com.mediaymedia.info.actions;

import com.mediaymedia.info.model.*;
import com.mediaymedia.info.condiciones.CondicionEntidadBaseEnSeccion;
import com.mediaymedia.info.condiciones.CondicionIgualdadSize;
import com.mediaymedia.info.condiciones.CondicionVerificaOrden;
import com.mediaymedia.info.condiciones.CondicionCoherenciaIndices;
import com.mediaymedia.info.servicios.ServicioIndices;
import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.commons.actions.Resultado;
import com.mediaymedia.commons.actions.excepciones.AccionExcepcion;
import com.mediaymedia.commons.persistencia.actions.ObjetoPersistenteUpdate;

public class AccionSeccionDeEntidad extends AccionEntidadBase implements AccionInformacion {
    SeccionBase seccionDestino;

    EstadoEntidad estadoEntidadDestino;

    public AccionSeccionDeEntidad(EntidadBase entidadBase, SeccionBase seccionDestino) {
        super(entidadBase);
        this.seccionDestino = seccionDestino;
        this.seccionBase = entidadBase.getSeccionBase();
//        grabarEstadoInicial();
    }

    protected void grabarEstadoInicial() {

//        IndiceEntidad indiceEntidadorigen = grabarEstadoIndiceConEntidadAEliminar(calculaEntidadesAOrdenar(seccionBase), entidadBase);
//        estadoEntidad = new EstadoEntidad(seccionBase, indiceEntidadorigen);
//        IndiceEntidad indiceEntidadDestino = grabarEstadoInmutables(calculaEntidadesAOrdenar(seccionDestino));
//        estadoEntidadDestino = new EstadoEntidad(seccionDestino, indiceEntidadDestino);
    }


    public SeccionBase getSeccionDestino() {
        return seccionDestino;
    }

    public Condicion[] getPreCodiciones() {
        return new Condicion[0];
    }

    public Condicion[] getPostCodiciones() {
        return new Condicion[0];
    }

    public Resultado ejecuta() throws AccionExcepcion {
        IndiceSeccion indiceSeccionDestino = servicioIndices.creaIndice(seccionDestino, getTipoEntidad());
        IndiceSeccion indiceSeccionOrigen = servicioIndices.creaIndice(seccionBase, getTipoEntidad());

        // primero se elimina y actualizan de su seccion
        indiceSeccionOrigen.borra(entidadBase);
        //se persisten las entidades de la seccion origen cuyo orden era menor que el de la informacion
        servicioIndices.persisteUpdate(getObjetosPersistentes(), indiceSeccionOrigen.getActualizables());

        // segundo se incluye a seccionDestino
        indiceSeccionDestino.incluye(entidadBase);
        //se persiste la entidad base ya que ha cambiado de orden y de seccion
        servicioIndices.persisteUpdate(getObjetosPersistentes(), entidadBase);


        return null;
    }


}
