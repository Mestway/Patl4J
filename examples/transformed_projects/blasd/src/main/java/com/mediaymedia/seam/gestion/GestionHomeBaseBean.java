package com.mediaymedia.seam.gestion;

import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import com.mediaymedia.seam.pages.PagesServicio;
import com.mediaymedia.faces.FacesServicio;

/**
 * User: juan
 * Date: 20-nov-2007
 * Time: 19:22:12
 */
public abstract class GestionHomeBaseBean extends EntityHome implements GestionHome{
    public final static String seamName="gestionHome";

    @In(value = PagesServicio.seamName, create = true)
    PagesServicio pagesServicio;


    public void create() {
        inicializa();
        super.create();
    }


    protected void seteaEntityClass( Class c) {
        setEntityClass(c);
    }

    protected String getViewId() {
        return facesServicio.getViewId();
    }

    @In(create = true, value = FacesServicio.seamName)
    FacesServicio facesServicio;

    @Logger
    protected Log log;

    public void wire() {
        

    }

    public boolean isWired() {
        return true;
    }

    public Object getDefinedInstance() {
        return isIdDefined() ? getInstance() : null;
    }

    public void clear(){
        setId(null);
        getLog().error("borrando id");

    }


    public String update() {
         super.update();
        return "/gestion/entidad/"+ getView() +".xhtml";
    }

    private String getView() {
        String nameViewId = pagesServicio.getSimpleNameViewId();
        if(nameViewId.contains("Audio")) return nameViewId.replaceAll("Audio","");
        else if(nameViewId.contains("Documento")) return nameViewId.replaceAll("Documento","");
        else if(nameViewId.contains("Fotos")) return nameViewId.replaceAll("Fotos","");
        else return nameViewId;
    }



    public String finalizar() {
        setInstance(null);
        return null;
    }
    @Transactional
    public String remove() {
         super.remove();
        //To change body of overridden methods use File | Settings | File Templates.
        return "/gestion/lista/"+ getView() +".xhtml";
    }


    @Transactional
    public String persist() {
         super.persist();
        return "/gestion/entidad/"+ getView() +".xhtml";

    }

}
