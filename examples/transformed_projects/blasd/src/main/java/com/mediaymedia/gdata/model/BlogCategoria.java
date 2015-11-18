package com.mediaymedia.gdata.model;

/**
 * User: juan
 * Date: 14-oct-2007
 * Time: 18:29:15
 */
public class BlogCategoria {

    /**
     * el term tiene forma de URL es decir
     * si hay un espacio en blanco se debe sustituir por su correspondiente %20 ...
     * @param term
     */
    public BlogCategoria(String term) {
        this.term = term;
    }

    String term;

    public String getTerm() {
        return term;

    }


    public void setTerm(String term) {
        this.term = term;
    }
}
