package com.mediaymedia.seam;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

/**
 * User: juan
 * Date: 24-oct-2007
 * Time: 20:50:47
 */
public class ComponenteBase {
    @Logger
    protected Log log;
    @In
    protected FacesMessages facesMessages;


    public void setLog(Log log) {
        this.log = log;
    }

    public void setFacesMessages(FacesMessages facesMessages) {
        this.facesMessages = facesMessages;
    }
}
