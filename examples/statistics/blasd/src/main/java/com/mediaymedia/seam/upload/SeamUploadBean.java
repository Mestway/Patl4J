package com.mediaymedia.seam.upload;

import java.io.File;
import java.io.IOException;

import javax.ejb.Remove;

import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import com.mediaymedia.commons.io.excepciones.FicheroNoSeleccionadoExcepcion;
import com.mediaymedia.commons.io.excepciones.FicheroYaExisteExcepcion;
import com.mediaymedia.commons.io.excepciones.IOExcepcion;
import com.mediaymedia.commons.io.image.OperarImagen;
import com.mediaymedia.commons.io.image.TipoLimite;
import com.mediaymedia.commons.io.service.ServicioIO;
import com.mediaymedia.commons.io.service.ServicioPaths;
import com.mediaymedia.commons.upload.FileUploadAction;
import com.mediaymedia.commons.upload.Tamanyo;

/**
 * User: juan
 * Date: 11-sep-2007
 * Time: 13:23:09
 * To change this template use File | Settings | File Templates.
 */
public abstract class SeamUploadBean extends FileUploadAction implements SeamUpload {


    @Logger
    protected Log log;
    @In
    protected FacesMessages facesMessages;

    @In(value = ServicioPaths.seamName, create = true)
    protected ServicioPaths servicioPaths;
    @In(value = ServicioIO.seamName, create = true)
    ServicioIO servicioIO;


    protected void uploadFile(String path) throws FicheroYaExisteExcepcion, FicheroNoSeleccionadoExcepcion, IOExcepcion, IOException {

        if ((getFileData() != null) && (getFileData().length > 0)) {
            log.error("Last uploaded file " + getFileData().length + " bytes." + getFileName());
//			FileUtils.readFileToByteArray( item.getFile() );

            if (getFileNameSimple() == null) setFileNameSimple(servicioPaths.obtenerSimpleName(getFileName()));

            String nameDefinitivo = path + File.separator + getFileNameSimple();

            File file = servicioIO.creaArchivoNuevo(nameDefinitivo);


            servicioIO.writeData(file, getFileData());


        } else {
            throw new FicheroNoSeleccionadoExcepcion();
        }

    }


    protected void uploadImagen(String path, Tamanyo tamanyo, TipoLimite limite) throws FicheroYaExisteExcepcion, FicheroNoSeleccionadoExcepcion, IOException, IOExcepcion {
        if (!getFileContentType().contains("jpg") && !getFileContentType().contains("jpeg")) {
            setFileUploadMsg("el archivo no es de tipo jpg");
        }

        if ((getFileData() != null) && (getFileData().length > 0)) {
            setFileUploadMsg("Last uploaded file " + getFileData().length + " bytes.");
            if (getFileNameSimple() == null) setFileNameSimple(servicioPaths.obtenerSimpleName(getFileName()));

            String name = path + File.separator + tamanyo.toString() + "_" + getFileNameSimple();
            getLog().error("---------------------------- > " + name);
            File file = servicioIO.creaArchivoNuevo(name);
            servicioIO.writeData(file, getFileData());

            OperarImagen.escala(file, name, limite);


        } else {
            throw new FicheroNoSeleccionadoExcepcion();
        }

    }


    public Log getLog() {
        return log;
    }

    @Destroy
    @Remove
    public void destroy() {
    }


}
