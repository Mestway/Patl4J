package com.mediaymedia.seam.upload;

import com.mediaymedia.commons.upload.FileUpload;
import com.mediaymedia.commons.upload.Tamanyo;
import com.mediaymedia.commons.properties.UploadProperties;
import org.jboss.seam.log.Log;

import javax.faces.event.ActionEvent;

/**
 *
 * User: juan
 * Date: 11-sep-2007
 * Time: 13:23:00
 * To change this template use File | Settings | File Templates.
 */
public interface SeamUpload extends FileUpload {


    UploadProperties getProperties();

    Log getLog();


    void subirAudio(ActionEvent event);
    void subirMiniatura(ActionEvent event);

    void subirZoom(ActionEvent event);


    void destroy();


}
