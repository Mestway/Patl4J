package com.mediaymedia.commons.recursividad;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * User: juanitu
 * Date: 16-mar-2007
 * Time: 19:07:09
 * To change this template use File | Settings | File Templates.
 */
public class EsquemaRecursivo {
    protected static Log log = LogFactory.getLog(EsquemaRecursivo.class);

    EntidadRecursiva raiz;
    List<EntidadRecursiva> entidadesOrdenadas;
    EntidadRecursiva limitacion;

    public EsquemaRecursivo(List<EntidadRecursiva> entidades) {
        raiz = dameRaiz(entidades);
    }


    public void setLimitacion(EntidadRecursiva limitacion) {
        this.limitacion = limitacion;
    }


    protected List<EntidadRecursiva> getEntidadesOrdenadas() {
        return entidadesOrdenadas;
    }

    public List<EntidadRecursiva> generaEntidadesOrdenadas() {
        inicializarLista();
        ordenar();
        return getEntidadesOrdenadas();
    }

    protected void inicializarLista() {
        entidadesOrdenadas = new ArrayList<EntidadRecursiva>();
    }


    public EntidadRecursiva getRaiz() {
        return raiz;
    }

    protected EntidadRecursiva dameRaiz(List<EntidadRecursiva> entidades) {
        for (EntidadRecursiva seccion : entidades) {
            if (seccion.getParent() == null) {
                return seccion;
            }
        }
        throw new RecursividadExcepcion("no existe ninguna entidad cuya entidad parent sea igual a NULL");
    }


    public static String rutar(EntidadRecursiva ordenada) {
        String ruta = "";
        while (parentDiferenteNull(ordenada)) {
            ruta = ordenada.getParent().getNombre() + "/" + ruta;
            ordenada = ordenada.getParent();
        }
        return ruta;
    }


    protected void ordenar() {
        ordenar(raiz);
    }


    protected void ordenar(EntidadRecursiva er) {
        getEntidadesOrdenadas().add(er);
        List<EntidadRecursiva> hijas = er.getChilds();
        for (EntidadRecursiva seccionHija : hijas) {
            if ((limitacion != null && !seccionHija.equals(limitacion)) || limitacion == null)
                ordenar(seccionHija);
        }
    }


    private static boolean parentDiferenteNull(EntidadRecursiva er) {
        return er.getParent() != null;
    }


}
