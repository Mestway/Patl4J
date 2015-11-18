package com.mediaymedia.commons.io.service;

import com.mediaymedia.commons.io.excepciones.ImagenNoExiste;
import com.mediaymedia.commons.io.excepciones.FicheroYaExisteExcepcion;
import com.mediaymedia.commons.io.excepciones.IOExcepcion;
import com.mediaymedia.commons.io.image.CropeadoImagen;
import com.mediaymedia.commons.io.image.CropImage;
import com.mediaymedia.seam.ComponentesSeam;

import javax.imageio.ImageIO;
import javax.ejb.Stateless;
import java.awt.*;
import java.io.*;

import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

/**
 * User: juan
 * Date: 28-sep-2007
 * Time: 14:11:27
 */
@Name(ServicioIO.seamName)
@Stateless
public class ServicioIOImpl implements ServicioIO {

    @Logger
    Log log;

    public Image dameImagen(String pathname) throws ImagenNoExiste {
        File archivo= dameFile(pathname);
        log.error(archivo.getAbsolutePath());
        Image image1 = null;
        try {
            image1 = ImageIO.read(archivo);
        } catch (IOException e) {
            throw new ImagenNoExiste(pathname,e);
        }
        if(image1.getWidth(null)<=0)
            throw new ImagenNoExiste("el ancho de la imagen es negativo!");
        return image1;
    }

    public void cortaImagen(CropeadoImagen cropeadoImagen) {
            CropImage cropImage=new CropImage(cropeadoImagen.getPath(), cropeadoImagen.getPath());
            cropImage.cropImage(cropeadoImagen.getNameIni(), cropeadoImagen.getNameDef(), cropeadoImagen.getX(), cropeadoImagen.getY(), cropeadoImagen.getWidth(), cropeadoImagen.getHeight());


    }

    public File creaArchivoNuevo(String s) throws FicheroYaExisteExcepcion, IOExcepcion {
        File file = dameFile(s);
        if (file.exists()) throw new FicheroYaExisteExcepcion(file.getName());
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new IOExcepcion(s);
        }
        log.error("*********************************__________________________________________*************");
        log.error(file.getAbsolutePath());
        log.error(file.getName());
        return file;
    }

    private File dameFile(String path) {
        return new File(path);
    }

    public void borrar(String path){
        dameFile(path).delete();
    }

    public void writeData(File file, byte[] fileData) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileData);
        OutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
        int data;
        while ((data = byteArrayInputStream.read()) != -1) {
            outputStream.write(data);
        }

        fileInputStream.close();
        outputStream.close();
    }
}
