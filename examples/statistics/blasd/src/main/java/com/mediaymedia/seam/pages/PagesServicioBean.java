package com.mediaymedia.seam.pages;

import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;

/**
 * User: juan
 * Date: 31-oct-2007
 * Time: 9:54:13
 */
@Stateless
@Name(PagesServicio.seamName)
public class PagesServicioBean implements PagesServicio {
    
    public String getSimpleNameViewId(){
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        try{
            String vv = viewId.substring(viewId.lastIndexOf("/") + 1);
            return vv.substring(0, vv.lastIndexOf("."));
        }catch(Exception e){
            throw new RuntimeException("no es posible obtener el simple name de la vista: "+viewId);
        }
    }
}
