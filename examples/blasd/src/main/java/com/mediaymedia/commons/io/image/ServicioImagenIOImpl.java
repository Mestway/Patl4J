package com.mediaymedia.commons.io.image;

import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;
import java.awt.image.BufferedImage;
import java.awt.*;

/**
 * User: juan
 * Date: 11-oct-2007
 * Time: 13:06:15
 */
@Name(value = ServicioImagenIO.seamName)
@Stateless
public class ServicioImagenIOImpl implements ServicioImagenIO {


    public void dibujarRectanguloGrafico(BufferedImage resultado, Color back, int posx, int posy, int width, int height) {
        Graphics2D d = resultado.createGraphics();
        d.setBackground(back);
        d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        d.clearRect(posx, posy, width, height);
    }


    public  void pegarImagen(BufferedImage resultado, BufferedImage superponer, int posx, int posy) {
        Graphics2D grPhoto = resultado.createGraphics();
        Object interpolating = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
        Object rendering = RenderingHints.VALUE_RENDER_QUALITY;
        Object colorRendering = RenderingHints.VALUE_COLOR_RENDER_QUALITY;
        Object antialiasing = RenderingHints.VALUE_ANTIALIAS_ON;
        Object dithering = RenderingHints.VALUE_DITHER_ENABLE;
        grPhoto.setRenderingHint(RenderingHints.KEY_INTERPOLATION, interpolating);
        grPhoto.setRenderingHint(RenderingHints.KEY_RENDERING, rendering);
        grPhoto.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, colorRendering);
        grPhoto.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antialiasing);
        grPhoto.setRenderingHint(RenderingHints.KEY_DITHERING, dithering);
        grPhoto.drawImage(superponer, posx, posy, null);

    }



}
