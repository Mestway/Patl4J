package com.mediaymedia.seam.gestion;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

import com.mediaymedia.richfaces.SeleccionSugestionBox;
import com.mediaymedia.richfaces.SugestionBox;
import com.mediaymedia.richfaces.SugestionBoxBean;
import com.mediaymedia.seam.reflection.ObjetoConPropiedadPublica;
import com.mediaymedia.seam.reflection.ServiceReflection;
import com.mediaymedia.seam.reflection.errors.ReflectionError;

/**
 * User: juan
 * Date: 15-nov-2007
 * Time: 14:45:26
 */
public abstract class HomeConSugestion implements HSugestion{

    @In(value = SugestionBoxBean.seamName, create = true)
    protected SugestionBox sugestionBox;

    @In(value = GestionHomeBaseBean.seamName, create = true)
    EntityHome entityHome;

    @Logger
    protected Log log;

    @In
    protected FacesMessages facesMessages;

    @In(value = ServiceReflection.seamName, create = true)
    ServiceReflection serviceReflection;

    @In
    protected Session session;


    public void wire() {
    }

    public boolean isWired() {
        return true;
    }

    public Object getDefinedInstance() {
        return entityHome.isIdDefined() ? getInstance() : null;
    }

    public Object getInstance() {
        return entityHome.getInstance();
    }


    protected Log getLog() {
        return log;
    }


    protected List getValor(SeleccionSugestionBox box) throws ReflectionError {
        List def = new ArrayList();
        def.addAll(box.getExistentes());
        for (Object e : box.getInexistentes())
            session.save(e);
        def.addAll(box.getInexistentes());
        return def;
    }


    protected String dameValor(List lista) {
        if (lista == null || lista.size() == 0) return "";
        try {
            return sugestionBox.dameCadena(lista);
        } catch (ReflectionError reflectionError) {
            reflectionError.printStackTrace();
            facesMessages.add(reflectionError.getMessage());

        }
        return null;
    }
    protected String dameValor(ObjetoConPropiedadPublica object) {
        if (object == null ) return "";
        try {
            return serviceReflection.invocaGetter(object);
        } catch (ReflectionError reflectionError) {
            reflectionError.printStackTrace();
            facesMessages.add(reflectionError.getMessage());

        }
        return null;
    }

    @Transactional
    public String remove() {
        return entityHome.remove();
    }

    public boolean isManaged() {
        return entityHome.isManaged();
    }


    @Transactional
    public String update() {
        persistirPropiedades();
        return entityHome.update();
    }

    @Transactional
    public String persist() {
        persistirPropiedades();
        return entityHome.persist();
    }

    protected List<ObjetoConPropiedadPublica> casteaObjectoPublico(List list) {
        return list;
    }

    protected void setInstance(Object o){
        entityHome.setInstance(o);
    }

}
