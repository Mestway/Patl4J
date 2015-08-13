package com.mediaymedia.test;

import com.mediaymedia.commons.properties.LoaderProperties;

import java.util.Properties;

/**
 *
 * User: juan
 * Date: 13-sep-2007
 * Time: 16:19:29
 * To change this template use File | Settings | File Templates.
 */
public class EinaTest {

    public static Properties loadPropertiesFile(String s) {
        return LoaderProperties.load(s);
    }

    public static String loadProperty(Properties properties, String claveKey) {
        String valor = (String) properties.get(claveKey);
        assert (valor != null) : " no se encuentra la propiedad " + claveKey + " en el fichero de propiedades";
        return valor;
    }
}
