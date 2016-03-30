package com.mediaymedia.commons.io.image;

/**
 * User: juan
 * Date: 28-sep-2007
 * Time: 18:26:36
 */
public class CropeadoImagen {

    String path;
    String nameIni;
    String nameDef;
    int x;
    int y;
    int width;
    int height;


    public CropeadoImagen(String path, String nameIni, String nameDef, int x, int y, int width, int height) {
        this.path = path;
        this.nameIni = nameIni;
        this.nameDef = nameDef;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getPath() {
        return path;
    }

    public String getNameIni() {
        return nameIni;
    }

    public String getNameDef() {
        return nameDef;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
