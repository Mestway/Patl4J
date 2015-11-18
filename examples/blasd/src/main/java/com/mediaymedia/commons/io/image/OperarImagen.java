package com.mediaymedia.commons.io.image;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;

/**
 *
 * User: juan
 * Date: 30-may-2007
 * Time: 18:08:02
 * To change this template use File | Settings | File Templates.
 */
public class OperarImagen {
    private static Log log = LogFactory.getLog(OperarImagen.class);


    public static void escala(File orig, String pathCompletoDefinitivo, TipoLimite tipoLimite) throws IOException {
        File fileDef = new File(pathCompletoDefinitivo);
        OperarImagen.escalador(orig, pathCompletoDefinitivo, tipoLimite);
       log.error("aqui voy");
    }

    private static void escalador(File orig, String pathCompletoDefinitivo, TipoLimite tipoLimite) throws IOException {
        // Get the image from a file.

        Image imagenOriginal = new ImageIcon(orig.getPath()).getImage();
        Proporcion proporcionImagenOriginal = dameProporcion(imagenOriginal);
        // Determine the scale.

        int altoImagen = imagenOriginal.getHeight(null);
        int anchoImagen = imagenOriginal.getWidth(null);


        ImagenDefinitiva imagenDefinitiva = calcularEscala(proporcionImagenOriginal, tipoLimite.getLimitePrincipal(), altoImagen, anchoImagen);
// todo       imagenDefinitiva=calcularEscala(proporcionImagenOriginal, tipoLimite.getLimiteSecundario(), altoImagen, anchoImagen);
        // Determine size of new image. One of them
        // should equal maxDim.

        log.debug("funciona debug");
        log.info("funciona ima escale"+imagenDefinitiva.getScale());

        // Create an image buffer in which to paint on.
        BufferedImage outImage = new BufferedImage(imagenDefinitiva.scaledW, imagenDefinitiva.scaledH,
                BufferedImage.TYPE_INT_RGB);

        // Set the scale.
        AffineTransform tx = new AffineTransform();

        // If the image is smaller than the desired image size,
        // don't bother scaling.
        if (imagenDefinitiva.scale < 1.0d) {
            tx.scale(imagenDefinitiva.scale, imagenDefinitiva.scale);
        }

        // Paint image.
        Graphics2D g2d = outImage.createGraphics();
        g2d.drawImage(imagenOriginal, tx, null);
        g2d.dispose();

        // JPEG-encode the image and write to file.
        OutputStream os = new FileOutputStream(pathCompletoDefinitivo);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(outImage);
        param.setQuality((float) 1, false);
        encoder.setJPEGEncodeParam(param);

        encoder.encode(outImage);

        os.close();

    }

    private static ImagenDefinitiva calcularEscala(Proporcion pro, Limite limite, int altoImagen, int anchoImagen) {

        int maxDim = limite.getMedida();
        double scale;

        if (pro == Proporcion.vertical) {
            log.debug("vertical " + maxDim);
            if (limite.getProporcion().equals(Proporcion.vertical))
                scale = (double) maxDim / (double) altoImagen;
            else
                scale = (double) maxDim / (double) anchoImagen;

        } else if (pro == Proporcion.horizontal) {
            log.debug("horizontal " + maxDim);
            if (limite.getProporcion().equals(Proporcion.horizontal))
                scale = (double) maxDim / (double) anchoImagen;
            else
                scale = (double) maxDim / (double) altoImagen;

        } else if (pro == Proporcion.cuadrada) {
            log.debug("cuadrada" + maxDim);
            scale = (double) maxDim / (double) anchoImagen;
        } else {
            throw new RuntimeException("tipo no observado");
        }
        return new ImagenDefinitiva(scale, anchoImagen, altoImagen);
    }


    public static Proporcion dameProporcion(Image imageOriginal) {
        int ancho = imageOriginal.getWidth(null);
        int alto = imageOriginal.getHeight(null);
        log.debug(alto+"---------------------------------"+ancho);
        if (ancho == alto) return Proporcion.cuadrada;
        else if (ancho > alto) return Proporcion.horizontal;
        else if (alto > ancho) return Proporcion.vertical;
        else
            throw new RuntimeException("tipo no encontrado");

    }
}

class ImagenDefinitiva {
    double scale;
    int scaledW;
    int scaledH;


    public ImagenDefinitiva(double scale, int anchoImagen, int altoImagen) {
        this.scale = scale;
        scaledW = (int) (scale * anchoImagen);
        scaledH = (int) (scale * altoImagen);


        if (scaledW >= anchoImagen && scaledH >= altoImagen) {
            scaledW = anchoImagen;
            scaledH = altoImagen;
        }

    }


    public double getScale() {
        return scale;
    }

    public int getScaledH() {
        return scaledH;
    }

    public int getScaledW() {
        return scaledW;
    }
}
