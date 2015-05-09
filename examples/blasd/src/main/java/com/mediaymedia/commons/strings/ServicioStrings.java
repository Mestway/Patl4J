package com.mediaymedia.commons.strings;

import com.mediaymedia.commons.excepciones.MediaExcepcion;

/**
 * User: juan
 * Date: 10-oct-2007
 * Time: 10:48:13
 */
public interface ServicioStrings {
    String seamName = "EinaString";

    String slashLinux = "/";
    String slashWindows = "\\";

    /**
     *
     * @param
     * @return path limpio de /./ o \.\
     * @throws MediaExcepcion
     */
    String limpiarPath(String path) throws MediaExcepcion;

    /**
     *
     * sustituye el path de windows \ con el / en el caso que exista
     * @param s
     * @return 
     */
    String damePathURL(String s);


    /**
     *
     * @param parametro
     * @return
     * @throws MediaExcepcion en el caso de recibir nulo, o cadena de menos de 2 caracteres
     */
     String conviertePrimeraLetraAMAyuscula(String parametro) throws MediaExcepcion;
     String reemplaza(String base, String busqueda, String cambio) ;


    /**
     * compara dos cadenas case insensitive :(conviritendolas a minusculas) para saber si comparten indice
     * @param nombre
     * @param patron
     * @return
     */
    boolean compartenIndice(String nombre, String patron);
}
