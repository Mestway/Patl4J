package com.mediaymedia.commons.imagenes;

import com.mediaymedia.commons.upload.Tamanyo;
import com.mediaymedia.commons.strings.ServicioStrings;

import java.io.File;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;

import javax.ejb.Stateless;

/**
 * User: juan
 * Date: 11-oct-2007
 * Time: 10:19:28
 */
@Name(value = ServicioImagenes.seamName)
@Stateless
public class ServicioImagenesImpl implements ServicioImagenes {

    @In(value = ServicioStrings.seamName,create = true)
    ServicioStrings einaString;

    public void seteaMediaImagen(String simleName, String methodName, MediaImagen zanfonaImagen, String path)  {
        if (methodName.contains(Tamanyo.Miniatura.toString())) {
            zanfonaImagen.setImgMini(einaString.damePathURL(path + File.separator + dameSimpleName(Tamanyo.Miniatura, simleName)));
        } else if (methodName.contains(Tamanyo.Zoom.toString()))
            zanfonaImagen.setImgZoom(einaString.damePathURL(path + File.separator + dameSimpleName(Tamanyo.Zoom, simleName)));

    }


    private String dameSimpleName(Tamanyo tam, String simpleName) {
        return tam.toString() + "_" + simpleName;
    }




}
