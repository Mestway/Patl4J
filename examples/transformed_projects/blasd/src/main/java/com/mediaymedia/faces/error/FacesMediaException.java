package com.mediaymedia.faces.error;

/**
 * User: juan
 * Date: 21-sep-2007
 * Time: 11:22:30
 */
public class FacesMediaException extends RuntimeException{

    public FacesMediaException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FacesMediaException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FacesMediaException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public FacesMediaException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
