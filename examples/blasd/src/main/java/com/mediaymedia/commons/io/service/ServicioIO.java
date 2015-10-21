package com.mediaymedia.commons.io.service;

import com.mediaymedia.commons.io.excepciones.ImagenNoExiste;
import com.mediaymedia.commons.io.excepciones.FicheroYaExisteExcepcion;
import com.mediaymedia.commons.io.excepciones.IOExcepcion;
import com.mediaymedia.commons.io.image.CropeadoImagen;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * User: juan
 * Date: 28-sep-2007
 * Time: 14:11:23
 */
public interface ServicioIO {
    String seamName="servicioIO";
    Image dameImagen(String pathname) throws ImagenNoExiste;

    void cortaImagen(CropeadoImagen cropeadoImagen);

    /**
     *
     * @param s nombre de archivo con directorio incluido
     * @return
     */
    File creaArchivoNuevo(String s) throws FicheroYaExisteExcepcion,  IOExcepcion;

    /**
     *
     * @param file  archivo existente pero vacio que se va a reescribir
     * @param fileData
     * @throws IOException
     */
    void writeData(File file,byte[] fileData) throws IOException;

     void borrar(String path);



}
