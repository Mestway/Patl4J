package com.mediaymedia.seam.model;

import javax.ejb.Remove;

import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 18:32:21
 */
public abstract class StatefulBeanImpl implements StatefulBean {
    @Logger
    protected Log log;

    @In
    protected FacesMessages facesMessages;

    @Destroy
    @Remove
    public void destroy() {
    }
}
