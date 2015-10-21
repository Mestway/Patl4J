package com.mediaymedia.commons.io.service;

import com.mediaymedia.commons.io.excepciones.ImagenNoExiste;
import com.mediaymedia.commons.io.image.CropeadoImagen;
import com.mediaymedia.test.Logger;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

/**
 * User: juan
 * Date: 28-sep-2007
 * Time: 13:45:51
 */
public class Prueba {
    String path = "C:\\workspace\\mediaymedia\\resources\\";

    ServicioIOImpl servicioIO;

    String def = "def_";
    String ext1 = "1_";
    String ext2 = "2_";


//    protected Log log = LogFactory.getLog(Prueba.class);

    @BeforeMethod
    public void inicializar() {
        servicioIO = new ServicioIOImpl();
        servicioIO.log = new Logger();
    }

    @DataProvider(name = "listaImagenes")
    public Object[][] createData1() {
        return new Object[][]{
//                {"gatos.jpg", true},
                {"prueba.jpg", true},
                {"prueba2.jpg", true},
                {"gradiente.jpg", true},
                {"NADA.jpg", false},
                {"Invierno.jpg", true},
        };
    }

    class EO {
        int ancho;
        int alto;

        public EO(int ancho, int alto) {
            this.ancho = ancho;
            this.alto = alto;
        }

        public int getAncho() {
            return ancho;
        }

        public int getAlto() {
            return alto;
        }
    }


    @Test
    public void pruebaCropImage() throws ImagenNoExiste {
        String fileName = "gatos.jpg";
        cropear(fileName);
    }

    private void cropear(String fileName) {
        CropeadoImagen cropeadoImagen = new CropeadoImagen(path, fileName, ext1 + fileName, 0, 0, 320, 211);
        servicioIO.cortaImagen(cropeadoImagen);
        CropeadoImagen cropeadoImagen2 = new CropeadoImagen(path, fileName, ext2 + fileName, 320, 0, 158, 211);
        servicioIO.cortaImagen(cropeadoImagen2);
    }

    @Test(dataProvider = "listaImagenes")
    public void pruebaObtenerImagenDirectorio(String nombre, boolean existe) throws IOException {
        Image image;
        try {
            image = servicioIO.dameImagen(path + nombre);
            if (!existe)
                assert (false) : " deberia lanzar una excepcion de tipo:" + ImagenNoExiste.class.getSimpleName();


            Image resizedImage = image.getScaledInstance(image.getWidth(null), image.getHeight(null), Image.SCALE_SMOOTH);

            Image temp = new ImageIcon(resizedImage).getImage();

            if (existe) {
                EO medidaTotalPrimerCuadro = new EO(320, 240);
                EO medidaRectanguloDecorativoPeq = new EO(27, 14);
                Color colorGris = new Color(204, 204, 204);
                Color colorMarron = new Color(153, 153, 102);


                cropear(nombre);

                //imagen 1
                BufferedImage resultadoUNO = new BufferedImage(medidaTotalPrimerCuadro.getAncho(), medidaTotalPrimerCuadro.getAlto(), BufferedImage.TYPE_INT_RGB);
            dibujarRectanguloGrafico(resultadoUNO, colorGris, 0, 0, medidaTotalPrimerCuadro.getAncho(), medidaTotalPrimerCuadro.getAlto());
            dibujarRectanguloGrafico(resultadoUNO, colorMarron, 293, 55, medidaRectanguloDecorativoPeq.getAncho(), medidaRectanguloDecorativoPeq.getAlto());


                pegarImagen(resultadoUNO, ImageIO.read(new File(path + ext1 + nombre)), 0, 69);

                ImageIO.write(resultadoUNO, "jpg", new File(path + ext1 + def + nombre));

                //imagen 2

            BufferedImage resultadoDOS = new BufferedImage(158, 280, BufferedImage.TYPE_INT_RGB);
            dibujarRectanguloGrafico(resultadoDOS, colorGris, 0, 0, 158, medidaTotalPrimerCuadro.getAlto());
            pegarImagen(resultadoDOS, ImageIO.read(new File(path + ext2 +nombre)), 0, 69);
            ImageIO.write(resultadoDOS, "jpg", new File(path + ext2+def + nombre));
//
            new File(path+ext1+nombre).delete();
            new File(path+ext2+nombre).delete();

                //todo borrar los recortes
            }

        } catch (ImagenNoExiste e) {
            if (existe)
                assert (false) : " no deberia lanzar una excepcion de tipo:" + ImagenNoExiste.class.getSimpleName() + " el fichero debe existir: " + path;
        }

    }


    private void dibujarRectanguloGrafico(BufferedImage resultado, Color back, int posx, int posy, int width, int height) {
        Graphics2D d = resultado.createGraphics();
        d.setBackground(back);
        d.clearRect(posx, posy, width, height);
    }

    private void pegarImagen(BufferedImage resultado, Image superponer, int posx, int posy) {
        Graphics d = resultado.createGraphics();

        d.drawImage(superponer, posx, posy, null);
        d.dispose();
        float softenFactor = 0.01f;
        float[] softenArray = {0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        resultado = cOp.filter(resultado, null);
    }









}


