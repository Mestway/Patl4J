package com.mediaymedia.faces;

/**
 * User: juan
 * Date: 15-oct-2007
 * Time: 11:19:46
 */
public class ErrorFaces {

    public static String prefijo="#{messages.";
    public static String sufijo="}";

    String error;


    public ErrorFaces(String error) {
        this.error = error;
    }
    public ErrorFaces(Object error) {
        this.error = error.toString();
    }

    public static String dameError(Object o){
      return new ErrorFaces(o).getErrorCompleto();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorFaces that = (ErrorFaces) o;

        if (!getErrorCompleto().equals(that.getErrorCompleto())) return false;

        return true;
    }

    public int hashCode() {
        return getErrorCompleto().hashCode();
    }


    public String getErrorCompleto() {
        return prefijo+error+sufijo;
    }
}
