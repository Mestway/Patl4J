package com.mediaymedia.info.actions;

import com.mediaymedia.commons.persistencia.actions.AccionPersistenteImpl;
import com.mediaymedia.commons.persistencia.actions.ObjetoPersistenteUpdate;
import com.mediaymedia.commons.persistencia.actions.ObjetoPersistente;
import com.mediaymedia.info.exceptions.InfoExcepcion;
import com.mediaymedia.info.model.*;
import com.mediaymedia.info.servicios.ServicioIndices;
import com.mediaymedia.info.servicios.ServicioIndicesImpl;

import java.util.Set;
import java.util.List;

/**
 *
 * User: juanitu
 * Date: 20-mar-2007
 * Time: 15:39:27
 * To change this template use File | Settings | File Templates.
 */
public abstract class AccionEntidadBase extends AccionPersistenteImpl {


    EntidadBase entidadBase;
    EstadoEntidad estadoEntidad;
    SeccionBase seccionBase;
    EntidadBase.tipo tipoEntidad;

    protected ServicioIndices servicioIndices = new ServicioIndicesImpl();


    protected void setObjetosPersistentes(List<ObjetoPersistente> persistentes) {
        this.persistentes = persistentes;

    }


    public AccionEntidadBase(EntidadBase entidadBase) {
        this.entidadBase = entidadBase;
        calculaTipoEntidadAOrdenar();
    }


    public SeccionBase getSeccionBase() {
        return seccionBase;
    }

    public EntidadBase getEntidadBase() {
        return entidadBase;
    }

    public EstadoEntidad getEstadoEntidad() {
        return estadoEntidad;
    }


    private EntidadBase.tipo calculaTipoEntidadAOrdenar() {
        if (entidadBase instanceof SeccionBase) {
            tipoEntidad = EntidadBase.tipo.seccion;
        } else if (entidadBase instanceof InformacionBase) {
            tipoEntidad = EntidadBase.tipo.informacion;
        } else {
            throw new RuntimeException("de que tipo es esta entidad: " + entidadBase.getClass());
        }
        return tipoEntidad;

    }


    protected Set<EntidadBase> calculaEntidadesAOrdenar(SeccionBase entidadBase) {
        return calculaEntidadesAOrdenar(entidadBase, tipoEntidad);
    }

    public static Set<EntidadBase> calculaEntidadesAOrdenar(SeccionBase entidadBase, EntidadBase.tipo tipoEntidad) {
        Set entidades;
        if (tipoEntidad.equals(EntidadBase.tipo.seccion))
            entidades = entidadBase.getSeccionesBase();
        else
            entidades = entidadBase.getInformacionesBase();
        return entidades;
    }


    public Set<EntidadBase> getEntidades() {
        return calculaEntidadesAOrdenar(entidadBase.getSeccionBase());
    }


    protected InfoExcepcion lanzaErrorOrden(EntidadBase informacionBase, int ordenEsperado) {
        return new InfoExcepcion("la entidad: " + informacionBase.getNombre() + " se espera que tenga un orden=" + ordenEsperado + " el actual es: " + informacionBase.getOrden());
    }


    public EntidadBase.tipo getTipoEntidad() {
        return tipoEntidad;
    }
}
