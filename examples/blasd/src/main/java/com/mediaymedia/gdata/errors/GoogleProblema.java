package com.mediaymedia.gdata.errors;

/**
 *
 * User: juan
 * Date: 17-ago-2007
 * Time: 14:36:14
 * To change this template use File | Settings | File Templates.
 */
public class GoogleProblema extends Exception{


    public GoogleProblema() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public GoogleProblema(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public GoogleProblema(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public GoogleProblema(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
