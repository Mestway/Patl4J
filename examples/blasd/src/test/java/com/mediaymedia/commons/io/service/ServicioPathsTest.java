package com.mediaymedia.commons.io.service;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * User: juan
 * Date: 10-oct-2007
 * Time: 12:10:57
 */
public class ServicioPathsTest {

    ServicioPathsImpl servicioPaths;

       @BeforeMethod
       public void iniciar() {
           servicioPaths = new ServicioPathsImpl();

       }

       @AfterMethod
       public void finalizar() {
           servicioPaths = null;
       }



     @DataProvider(name = "simpleName")
    public Object[][] createData5() {
     return new Object[][] {
       { "/hola/adios.jpg", "adios.jpg"},
       { "C:\\hola\\adios.jpg", "adios.jpg"},
     };
    }
    @Test(dataProvider = "simpleName")
    public void pruebaObtenerSimpleName(String nameCompleto, String resultado){
        String resultadoFuncion = servicioPaths.obtenerSimpleName(nameCompleto);
        assert(resultadoFuncion.equals(resultado)):resultadoFuncion+"!= "+resultado;
    }

    @Test
    public void cambiarURL(){
        String pepe="C:\\workspace\\mediaymedia\\resources\\";
        servicioPaths.dameURL(pepe);
    }

}
