package com.mediaymedia.commons.io.image;

import java.awt.image.BufferedImage;
import java.awt.*;

/**
 * User: juan
 * Date: 11-oct-2007
 * Time: 13:06:11
 */
public interface ServicioImagenIO {
    String seamName="servicioImagenIO";

     void dibujarRectanguloGrafico(BufferedImage resultado, Color back, int posx, int posy, int width, int height) ;

      void pegarImagen(BufferedImage resultado, BufferedImage superponer, int posx, int posy) ;

}


