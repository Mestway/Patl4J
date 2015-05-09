package com.mediaymedia.commons.io.excepciones;

/**
 * User: juan
 * Date: 17-sep-2007
 * Time: 20:39:46
 */
public class UploadException extends Exception{

    public UploadException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public UploadException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public UploadException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public UploadException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
