package com.mediaymedia.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * User: invitado
 * Date: 16-mar-2005
 * Time: 15:19:09
 * To change this template use File | Settings | File Templates.
 */
public abstract class Cadena {


    public static boolean contieneCadena(String base, String encontrar) {
        String patron = base;
        String modificacion = base.replaceAll(encontrar, "");
        if (patron.equals(modificacion))
            return false;
        else
            return true;
    }

    public static boolean ultimoCaracter(String s, String s1) {


        String l = s.substring(s.length() - 1);
        if (l.equals(s1)) return true;
        return false;

    }

    public static String formatear(String contenido) {
        if (contenido != null)
            return contenido.replaceAll("\n", "<br>");
        else
            return "";
    }

}
