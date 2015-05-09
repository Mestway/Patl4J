package com.mediaymedia.commons.persistencia.model;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 *
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:18:13
 * To change this template use File | Settings | File Templates.
 */
public abstract class DAOImpl implements DAO {
    protected abstract Session getSession();

    public void refresh(Object o) {
        getSession().refresh(o);
    }
    public void lock(Object o) {
        getSession().setReadOnly(o,true);
    }
    public void unlock(Object o) {
        getSession().setReadOnly(o,false);
    }

    public List get(Class c) {
        return getSession().createCriteria(c).list();
    }
    public Object reload(Class c, String identificador, int i) {
        return getSession().createCriteria(c).add(Expression.eq(identificador,i)).uniqueResult();
    }

    public void evict(Object c) {
         getSession().evict(c);
    }

    public void merge(Object o) {
        getSession().merge(o);
    }

    public void merge(List<Object> os) {
        for (Object o : os)
            getSession().merge(o);
    }

    public void saveOrUpdate(Object o) {
        getSession().saveOrUpdate(o);
    }

    public void delete(Object o) {
        getSession().delete(o);
    }

}
