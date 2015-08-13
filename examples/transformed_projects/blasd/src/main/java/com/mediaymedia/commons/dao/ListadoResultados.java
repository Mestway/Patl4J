package com.mediaymedia.commons.dao;



/**
 *
 * User: juan
 * Date: 05-jul-2007
 * Time: 12:11:30
 * To change this template use File | Settings | File Templates.
 */
public interface ListadoResultados {
    void nextPage();

    void prevPage();

    boolean isLastPage();

    boolean isFirstPage();

    void resetPaginacion();
}
