package com.mediaymedia.commons.imagenes;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * User: juan
 * Date: 11-oct-2007
 * Time: 10:14:48
 */
@Embeddable
public class MediaImagenImpl implements Serializable,MediaImagen {

    String imgMini="";
    String imgZoom="";


    public String getImgMini() {
        return imgMini;
    }

    public void setImgMini(String imgMini) {
        this.imgMini = imgMini;
    }

    public String getImgZoom() {
        return imgZoom;
    }

    public void setImgZoom(String imgZoom) {
        this.imgZoom = imgZoom;
    }
}
