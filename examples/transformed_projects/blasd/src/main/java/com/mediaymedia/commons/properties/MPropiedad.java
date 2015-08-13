package com.mediaymedia.commons.properties;

/**
 * User: juan
 * Date: 01-oct-2007
 * Time: 12:37:03
 */
public class MPropiedad {

    String key;
    String value;


    public MPropiedad(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }


    public String getValue() {
        return value;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
