package com.mediaymedia.commons.persistencia.model;

import java.util.List;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:18:08
 * To change this template use File | Settings | File Templates.
 */
public interface DAO {
    void refresh(Object o);

    List get(Class c);

    void merge(Object o);

    void merge(List<Object> o);

    void saveOrUpdate(Object o);

    void delete(Object o);

}
