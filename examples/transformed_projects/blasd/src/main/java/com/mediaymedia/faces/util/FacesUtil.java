package com.mediaymedia.faces.util;

import com.mediaymedia.faces.error.FacesMediaException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

/**
 * User: juanitu
 * Date: 16-mar-2007
 * Time: 19:29:33
 * To change this template use File | Settings | File Templates.
 */
public abstract class FacesUtil {


    public static List getList(DataModel dataModel) {
        List aux = new ArrayList();
        for (int i = 0; i < dataModel.getRowCount(); i++) {
            dataModel.setRowIndex(i);
            aux.add(dataModel.getRowData());
        }

        return aux;
    }


    public static String getParamValueWebXML(ServletContext servletContext, String name) {
        String initParameter = servletContext.getInitParameter(name);
        if (initParameter == null)
            throw new FacesMediaException("no existe el atributo: " + name + "  declarado en el web.xml");
        return initParameter;

    }


    public static ServletContext getServletContext(FacesContext currentInstance) {
        FacesContext facesContext = currentInstance;
        ExternalContext externalContext = facesContext.getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        return servletContext;

    }
}
