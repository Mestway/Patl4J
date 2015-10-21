package com.mediaymedia.info.model;

import com.mediaymedia.info.actions.Movimiento;
import com.mediaymedia.info.actions.SituacionDeEntidad;

import java.util.HashSet;
import java.util.Set;

public class IndiceSeccionImpl implements IndiceSeccion {

    EntidadBase.tipo tipo;
    SeccionBase seccionBase;

    public IndiceSeccionImpl(SeccionBase seccion, EntidadBase.tipo tipo) {
        this.tipo = tipo;
        seccionBase = seccion;
    }

    Set<EntidadBase> iniciales = new HashSet<EntidadBase>();
    Set<EntidadBase> actualizables = new HashSet<EntidadBase>();


    public Set<EntidadBase> getIniciales() {
        return iniciales;
    }


    public Set<EntidadBase> getActualizables() {
        return actualizables;
    }

    public EntidadBase.tipo getTipo() {
        return tipo;
    }


    public SeccionBase getSeccionBase() {
        return seccionBase;
    }

    public void creaIndice() {
        if (getTipo().equals(EntidadBase.tipo.informacion)) {
            incluirIniciales(seccionBase.getInformacionesBase());
        } else if (getTipo().equals(EntidadBase.tipo.seccion)) {
            incluirIniciales(seccionBase.getSeccionesBase());
        }
    }

    protected void incluirIniciales(Set base) {
        Set<EntidadBase> setI = base;
        for (EntidadBase ib : setI)
            getIniciales().add(ib);
    }


    public void borra(EntidadBase entidadBase) {
        //se elimina de inmutable la entidadBase
        getIniciales().remove(entidadBase);
        if (getTipo().equals(EntidadBase.tipo.informacion))
            entidadBase.getSeccionBase().getInformacionesBase().remove(entidadBase);
        else
            entidadBase.getSeccionBase().getSeccionesBase().remove(entidadBase);

        //se mueven las entidades inmutables a reducibles cuyo orden sea mayor que el de la entidad a borrar
        for (EntidadBase eb : getIniciales())
            if (eb.getOrden() > entidadBase.getOrden()) {
                reduce(eb);
            }

    }

    public void mueve(EntidadBase entidadBase, int comienzo, int destino) {
        Movimiento movimiento = Movimiento.dameMovimiento(comienzo, destino);
        for (EntidadBase i : getIniciales()) {
            SituacionDeEntidad situacion = SituacionDeEntidad.dameSituacion(i, comienzo, destino);
            if (situacion.equals(SituacionDeEntidad.DentroDeIntervalo)) {
                if (movimiento.equals(Movimiento.Avance))
                    reduce(i);
                else if (movimiento.equals(Movimiento.Retroceso))
                    aumenta(i);
                else throw new RuntimeException("movimiento no contemplado en accion: " + movimiento);
            } else {
                // no pasa nada
            }
        }
        entidadBase.setOrden(destino);

    }

    public void incluye(EntidadBase entidadBase) {
        entidadBase.setOrden(getIniciales().size() + 1);
        entidadBase.setSeccionBase(getSeccionBase());
        if (getTipo().equals(EntidadBase.tipo.informacion))
            getSeccionBase().addInformacionBase((InformacionBase) entidadBase);
        else
            getSeccionBase().addSeccionBase((SeccionBase) entidadBase);
    }

    protected void reduce(EntidadBase eb) {
        getActualizables().add(eb);
        eb.reducir();
    }

    protected void aumenta(EntidadBase eb) {
        getActualizables().add(eb);
        eb.aumentar();
    }
}
