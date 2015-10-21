package com.mediaymedia.commons.strings;

import com.mediaymedia.commons.excepciones.MediaExcepcion;
import com.mediaymedia.seam.ComponenteBase;
import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;
import java.io.File;

/**
 * User: juan
 * Date: 10-oct-2007
 * Time: 10:48:17
 */
@Name(ServicioStrings.seamName)
@Stateless
public class ServicioStringBean extends ComponenteBase implements ServicioStrings {


    public String limpiarPath(String s) throws MediaExcepcion {
        String limpiarLinux = slashLinux + "." + slashLinux;
        String limpiarWindows = slashWindows + "\\." + slashWindows;
        String encontrarWindows = slashWindows + "." + slashWindows;
        if (s == null) throw new MediaExcepcion("Se ha recibido un path nulo");
        if (s.contains(limpiarLinux))
            return s.replaceAll(limpiarLinux, slashLinux);
        if (s.contains(encontrarWindows)) {
            return reemplaza(s, encontrarWindows, slashWindows);
        }
        return s;
    }

    public String damePathURL(String serverPathFile) {
        try {
            return serverPathFile.replaceAll(File.separator, "/");
        } catch (Exception e) {
            //java.util.regex.PatternSyntaxException
            return serverPathFile.replaceAll(File.separator + File.separator, "/");
        }
    }

    public int dameIndex(String base, String busqueda) {
        return base.indexOf(busqueda);
    }

    public String borra(String base, String busqueda) {
        int i = dameIndex(base, busqueda);
        int length = busqueda.length();
        return base.substring(0, i) + base.substring(i + length);

    }

    public String reemplaza(String base, String busqueda, String cambio) {
        if(dameIndex(base,busqueda)<=0) return base;
        int i = dameIndex(base, busqueda);
        int length = busqueda.length();
        String resultado = base.substring(0, i) + cambio + base.substring(i + length);
        if(dameIndex(resultado,busqueda)>0)
        return reemplaza(resultado, busqueda, cambio);
        return resultado;
    }


    public String conviertePrimeraLetraAMAyuscula(String parametro) throws MediaExcepcion{
        if(parametro==null)throw new MediaExcepcion("se intenta convertir a mayusculas un parametro nulo");
         parametro=parametro.trim();
        if(parametro.length()<2)throw new MediaExcepcion("se intenta convertir a mayusculas un parametro menor de dos caracteres");
        String s = parametro.trim().substring(0, 1);
        String primeraLetra=s.toUpperCase();
        return primeraLetra+parametro.substring(1);
    }

    public boolean compartenIndice(String nombre, String patron) {
        try{
            if(nombre.length()<patron.length())return false;
        String comparar=nombre.substring(0, patron.length());
        log.debug(comparar+"=="+patron);
        return comparar.toLowerCase().equals(patron.toLowerCase());
        }catch(Exception e){
            throw new RuntimeException("nombre: "+nombre+" patron: "+patron,e);
        }
    }

    public int splitea(String valor, String patron) {
        String[] strings = valor.split(patron);
        int i = strings.length;

        log.debug(strings[i-1]);
        return i;
    }
}
