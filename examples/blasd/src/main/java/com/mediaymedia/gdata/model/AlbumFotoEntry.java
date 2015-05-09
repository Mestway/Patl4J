package com.mediaymedia.gdata.model;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 12:58:26
 */
public class AlbumFotoEntry {

     String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLabel() {
        return getName();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public Object getValue() {
        return getName();    //To change body of overridden methods use File | Settings | File Templates.
    }


    public String toString() {
        return name;    //To change body of overridden methods use File | Settings | File Templates.
    }

}
