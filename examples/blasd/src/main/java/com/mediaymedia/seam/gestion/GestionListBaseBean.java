package com.mediaymedia.seam.gestion;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityQuery;
import org.jboss.seam.log.Log;

import com.mediaymedia.commons.excepciones.MediaExcepcion;
import com.mediaymedia.commons.strings.ServicioStrings;
import com.mediaymedia.faces.FacesServicio;

/**
 * User: juan
 * Date: 20-nov-2007
 * Time: 19:37:33
 */
public abstract class GestionListBaseBean extends EntityQuery implements GestionList {
    public final static String seamName = "gestionList";
    protected String[] RESTRICCIONES;


    @Logger
    protected Log log;

    @In
    protected FacesMessages facesMessages;
    @In(create = true, value = FacesServicio.seamName)
    FacesServicio facesServicio;
    @In(create = true, value = ServicioStrings.seamName)
    ServicioStrings servicioStrings;


    public void inicializa() throws MediaExcepcion {
        if (getInstance() == null) try {
            setInstance(getClaseListado().newInstance());
        } catch (InstantiationException e) {
            throw new MediaExcepcion("excepcion de instanciacion " + getClaseListado().getSimpleName());
        } catch (IllegalAccessException e) {
            throw new MediaExcepcion("excepcion de instanciacion -ilegal acceso " + getClaseListado().getSimpleName());
        }
        if (getRESTRICCIONES() == null)
            setRESTRICCIONES(new String[]{});
    }


    Object instance;

    @Override
    public Integer getMaxResults() {
        return 10;
    }


    public Object getInstance() {

        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }


    public List<String> getRestrictions() {
        return Arrays.asList(getRESTRICCIONES());
    }


    public String[] getRESTRICCIONES() {
        return RESTRICCIONES;
    }

    public void setRESTRICCIONES(String[] RESTRICCIONES) {
        this.RESTRICCIONES = RESTRICCIONES;
    }


    public Query creaQuery(String s) {
        Query query = getEntityManager().createQuery(s);
        return query;
    }


    public void validate() {
        if (getEjbql() == null) setEjbql("");
        super.validate();
    }

    /**
     * solo mira si es de tipo java.util.List o de Tipo java.util.Set
     *
     * @param aClass
     * @param atributo
     * @return
     * @throws MediaExcepcion
     */
    private boolean esColeccion(Class aClass, Object atributo) throws MediaExcepcion {
        String nombreMetodo = "get" + servicioStrings.conviertePrimeraLetraAMAyuscula(atributo.toString());
        try {
            Class<?> aClass1 = aClass.getMethod(nombreMetodo).getReturnType();
            log.debug("valor returnado: " + aClass1);
            return aClass1.equals(List.class) || aClass1.equals(Set.class);
        } catch (NoSuchMethodException e) {
            throw new MediaExcepcion("no existe el metodo: " + nombreMetodo, e);
        }

    }

    protected void setEjbql(Class aClaz, List<FiltroAListado> listaFiltros) throws MediaExcepcion {
        setEjbql(aClaz);
        if (existeFiltro())
            for (FiltroAListado filtroAListado : listaFiltros) {
                if (filtroAListado.getClaseFiltro().equals(filtro.getClass())) {
                    setEjbql(aClaz, filtroAListado.getAtributo());
                    return;
                }
            }


    }


    protected void setEjbql(Class aClass, Object atributoFiltro) throws MediaExcepcion {
        if (esColeccion(aClass, atributoFiltro))
            this.setEjbql(generaSelect(aClass) + dameFiltroColeccion(atributoFiltro.toString()));
        else
            this.setEjbql(generaSelect(aClass) + dameFiltro(atributoFiltro.toString()));
    }

    protected Class claseListado;


    protected Class getClaseListado() {
        return claseListado;
    }

    protected void setClaseListado(Class claseListado) {
        this.claseListado = claseListado;
    }

    private String generaSelect(Class aClass) {
        setClaseListado(aClass);
        return dameSelect(aClass.getSimpleName());
    }

    protected void setEjbql(Class aClass) {
        this.setEjbql(generaSelect(aClass));
    }

    private String dameSelect(String s) {
        return "select instance from " + s + " instance";
    }

    private String dameFiltroColeccion(String s) {
        if (existeFiltro())
            return " where #{" + ListadoGestion.filtro + "} in elements(instance." + s + ")";
        return "";
    }

    private boolean existeFiltro() {
        boolean b = filtro != null;
        log.error("existe filtro:" + b);
        if (b) log.error(filtro.getClass());
        return b;
    }

    @In(value = ListadoGestion.filtro, required = false, scope = ScopeType.CONVERSATION)
    Object filtro;

    private String dameFiltro(String s) {
        if (existeFiltro()) {
            String s1 = " where instance." + s + "=#{" + ListadoGestion.filtro + "}";
            log.error(s1);
            return s1;
        }
        return "";
    }

    public String getViewId() {
        return facesServicio.getViewId();
    }


}
