package com.mediaymedia.seam.pages;

import org.testng.annotations.Test;
import com.mediaymedia.test.TestTimer;

/**
 * User: juan
 * Date: 31-oct-2007
 * Time: 9:56:23
 */
public class PagesServicioBeanTest extends TestTimer {

    @Test
    public void convierte(){
        String  valor="/gestion/entidad/cabecera.xhtml";
        String vv = valor.substring(valor.lastIndexOf("/") + 1);
        testLog.error(vv.substring(0, vv.lastIndexOf(".")));

    }
}
