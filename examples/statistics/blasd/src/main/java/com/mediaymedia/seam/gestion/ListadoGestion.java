package com.mediaymedia.seam.gestion;

import com.mediaymedia.seam.model.StatefulBean;

/**
 * User: juan
 * Date: 20-nov-2007
 * Time: 19:58:41
 */
public interface ListadoGestion extends StatefulBean {
    public final static String seamName = "listadoGestion";
    public final static String filtro = "filtroGestion";


    void select();


    Long getFiltroId();

    void setFiltroId(Long filtroId);

    Object getFiltro();


}
