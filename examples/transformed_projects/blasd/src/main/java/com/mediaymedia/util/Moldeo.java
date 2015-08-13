package com.mediaymedia.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * User: invitado
 * Date: 10-ene-2005
 * Time: 1:29:55
 * To change this template use File | Settings | File Templates.
 */
abstract public class Moldeo {
    private static Log log = LogFactory.getLog(Moldeo.class);

    public static String formatoEuro3(double moneda) {
        log.debug("" + moneda);
        return formatoEuro(moneda);
    }

    public static String formatoEuro(double moneda) {
        String cadena = moneda + "";
        String[] strings = cadena.split("\\.");
        if (strings.length == 2) {
            String mensaje = strings[1].toString();
            log.debug(mensaje.length() + "-");
            if (mensaje.length() > 2) {
                String s = mensaje.substring(0, 2);
                log.debug(s + "-" + mensaje);
                return strings[0].toString() + "." + s;
            } else if (mensaje.length() < 2)
                return cadena += "0";
        }
        return cadena;
    }

    public static int stringToInt(String valor) {
        if (valor == null) return 0;
        if (valor.equals("")) return 0;
        return Integer.parseInt(valor);
    }

    public static boolean stringToBoolean(String valor) {
        if (valor == null) return false;
        if (valor.equals("1")) return true;
        return false;
    }

    public static double stringToDouble(String valor) {
        if (valor == null) return 0;
        if (valor.equals("")) return 0;
        return Double.parseDouble(valor.replaceAll(",", "."));
    }

    public static String dosDigitos(String valor) {
        return dosDigitos(Integer.parseInt(valor));
    }

    public static String dosDigitos(int valor) {
        if (valor < 10) return "0" + valor;
        else return "" + valor;
    }

    public static boolean intToBoolean(int valor) {
        if (valor == 1) return true;
        return false;
    }

    public static int booleanToInt(boolean valor) {
        if (valor == true) return 1;
        return 0;
    }

    public static String booleanToString(boolean valor) {
        if (valor == true) return "1";
        return "0";
    }

    public static boolean cadenaContieneCadena(String base, String encontrar) {
        String patron = base;
        String modificacion = base.replaceAll(encontrar, "");
        if (patron.equals(modificacion))
            return false;
        else
            return true;

    }

    public static Integer booleantoInteger(boolean activo) {
        return new Integer(booleanToInt(activo));
    }


}
