package com.mediaymedia.seam.gestion;

/**
 * User: juan
 * Date: 20-nov-2007
 * Time: 19:59:27
 */
import javax.ejb.Stateful;

import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.web.RequestParameter;

import com.mediaymedia.seam.model.BaseBeanDataBase;


@Stateful
@Name(ListadoGestion.seamName)
public class ListadoGestionBean extends BaseBeanDataBase implements ListadoGestion {

    @Out(value = ListadoGestion.filtro, required = false, scope = ScopeType.CONVERSATION)
    Object filtro;

    @In(create = true)
    Session session;

    @RequestParameter
    Long filtroId;

    @RequestParameter
    String filtroClass;

    @RequestParameter
    String reset;


    public Object getFiltro() {
        return filtro;
    }


    @Begin(nested = true)
    public void select() {

        if (reset != null) {
            filtro = null;
        }
        if (filtroId == null) {
            log.error("filtroId==NULL!!");
        } else if (filtroClass == null) {
            log.error("filtroClass==NULL!!");
        } else {
            log.error("ID:" + filtroId + "Class: " + filtroClass);
            try {
                filtro = session.get(Class.forName(filtroClass), filtroId);

            } catch (Exception e) {
                e.printStackTrace();
                facesMessages.add(e.getMessage());
            }
        }
    }


    public Long getFiltroId() {
        return filtroId;
    }

    public void setFiltroId(Long filtroId) {
        this.filtroId = filtroId;
    }
}
