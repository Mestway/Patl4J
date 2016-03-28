package com.mediaymedia.seam.model;

import org.hibernate.Session;
import org.jboss.seam.annotations.In;

/**
 * User: juan
 * Date: 20-nov-2007
 * Time: 20:00:52
 */
public abstract class BaseBeanDataBase extends StatefulBeanImpl implements StatefulDataBaseBean {





    @In
    protected Session session;



     protected <T> T get ( Class<T> clz, Long id){
         return (T) session.get(clz, id);
     }


    public Session getSession() {
        return session;
    }
}
