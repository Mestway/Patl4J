package com.mediaymedia.commons.io.service;

import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;

import com.mediaymedia.commons.strings.ServicioStrings;

/**
 * User: juan
 * Date: 10-oct-2007
 * Time: 12:10:13
 */
@Name(ServicioPaths.seamName)
@Stateless
public class ServicioPathsImpl implements ServicioPaths {

    /**
     * un nombre simple es sin info de directorio!
     * Esto lo he tenido que meter porque daba problemas con el explorer sobre un servidor en linux
     *
     * @return el nombre del archivo sin su directorio ya sea en linux o windows
     * en caso de ser simple se devuelve como tal
     */
    public String obtenerSimpleName(String resultado) {
        if (resultado.contains(ServicioStrings.slashWindows))
            return resultado.substring(resultado.lastIndexOf(ServicioStrings.slashWindows) + 1);
        else if (resultado.contains(ServicioStrings.slashLinux))
            return resultado.substring(resultado.lastIndexOf(ServicioStrings.slashLinux) + 1);

        return resultado;
    }

    public void dameURL(String pepe) {
        System.out.println(pepe.replaceAll("\\", "/"));
    }
}
