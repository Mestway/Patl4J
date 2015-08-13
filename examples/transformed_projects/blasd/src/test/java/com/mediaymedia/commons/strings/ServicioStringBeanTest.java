package com.mediaymedia.commons.strings;

import com.mediaymedia.commons.excepciones.MediaExcepcion;
import com.mediaymedia.test.TestTimer;
import com.mediaymedia.test.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * User: juan
 * Date: 10-oct-2007
 * Time: 10:48:51
 */
public class ServicioStringBeanTest extends TestTimer {

    ServicioStringBean servicioStringBean;

    @BeforeMethod
    public void iniciar() {
        super.startPrueba();
        servicioStringBean = new ServicioStringBean();
        servicioStringBean.setLog(new Logger());

    }

    @AfterMethod
    public void finalizar() {
        servicioStringBean = null;
        super.finishPrueba();
    }

    @DataProvider(name = "paths")
    public Object[][] createData1() {
        return new Object[][]{
                {null, ""},
                {"C:/hola", "C:/hola"},
                {"/./hola", "/hola"},
                {"C:\\.\\hola", "C:\\hola"},
        };
    }


    /**
     * esta funcion limpia los directorios de linux y windows cuando existen casos de /./ o \.\
     * antes lo hacÌa con:
     * try {
     * return path.replaceAll(File.separator + "\\." + File.separator, File.separator);
     * } catch (Exception e) {
     * return path.replaceAll(File.separator + File.separator + "." + File.separator + File.separator, File.separator + File.separator);
     * <p/>
     * }
     */
    @Test(dataProvider = "paths")
    public void pruebaLimpiarPath(String inicio, String resultado) {
        try {
            String resultadoFuncion = servicioStringBean.limpiarPath(inicio);
            assert (resultadoFuncion.equals(resultado)) : " deberia devolver: " + resultado + ". ha devuelto: " + resultadoFuncion;
            if (inicio == null) assert (false) : " deberia lanzar una excepcion de nulo";
        } catch (MediaExcepcion e) {
            if (inicio != null) assert (false) : "no deberia lanzar una excepcion de nulo";

        }

    }

    @DataProvider(name = "indices")
    public Object[][] createData2() {
        return new Object[][]{
                {"pepejoseluis", "jose", 4},
                {"pepejoseluis", "juan", -1},
                {"C:/pepo", "/", 2},
        };
    }


    @Test(dataProvider = "indices")
    public void pruebaIndex(String base, String busqueda, int index) {
        double resultado = servicioStringBean.dameIndex(base, busqueda);
        assert (resultado == index) : index + "!= " + resultado;
    }

    @DataProvider(name = "borra")
    public Object[][] createData3() {
        return new Object[][]{
                {"pepejoseluis", "jose", "pepeluis"},
                {"C:/hola/./adios", "/./", "C:/holaadios"},
        };
    }

    @Test(dataProvider = "borra")
    public void pruebaBorrar(String base, String busqueda, String resultado) {
        String resultadoFuncion = servicioStringBean.borra(base, busqueda);
        assert (resultadoFuncion.equals(resultado)) : resultadoFuncion + "!= " + resultado;
    }

    @DataProvider(name = "reemplaza")
    public Object[][] createData4() {
        return new Object[][]{
                {"pepejoseluis", "jose", "marco", "pepemarcoluis"},
                {"C:/hola/./adios", "/./", "/", "C:/hola/adios"},
                {"C:\\hola\\adios", "\\", "/", "C:/hola/adios"},
                {"C:\\hola\\.\\adios", "\\.\\", "\\", "C:\\hola\\adios"},
        };
    }

    @Test(dataProvider = "reemplaza")
    public void pruebaReemplaza(String base, String busqueda, String cambio, String resultado) {
        String resultadoFuncion = servicioStringBean.reemplaza(base, busqueda, cambio);
        assert (resultadoFuncion.equals(resultado)) : resultadoFuncion + "!= " + resultado;
    }

    @DataProvider(name = "primeraMayuscula")
    public Object[][] createData5() {
        return new Object[][]{
                {"hola", "Hola", null},
                {"adios", "Adios", null},
                {"ÒAdios", "—Adios", null},
                {" ÒAdios", "—Adios", null},
                {null, null, new MediaExcepcion()},
                {"a", null, new MediaExcepcion()},
                {"aa", "Aa", null},
        };
    }

    @Test(dataProvider = "primeraMayuscula")
    public void primeraMayuscula(String parametro, String resultado, MediaExcepcion m) {
        try {
            String retorno = servicioStringBean.conviertePrimeraLetraAMAyuscula(parametro);
            assert (retorno.equals(resultado)) : " no coinciden: " + retorno + "!=" + resultado;
            if (m != null) assert (false) : " deberia lanzar excepcion con el parametro: " + parametro;
        } catch (MediaExcepcion e) {
            assert (m != null);

        }
    }

    @DataProvider(name = "compartenIndice")
    public Object[][] createDataCompartenIndice() {
        return new Object[][]{
                {"hola", "H", true},
                {"hola", "Ho", true},
                {"hola", "HoL", true},
                {"hola", "HoLa", true},
                {"hola", "Jola", false},
        };
    }

    @Test(dataProvider = "compartenIndice")
    public void compartenIndice(String valor, String patron, boolean resultadoEsperado) {
        boolean resultado = servicioStringBean.compartenIndice(valor, patron);
        assert(resultado==resultadoEsperado):" no coincide esperado: "+resultadoEsperado+ " real "+resultado;

    }
   @DataProvider(name = "splitea")
    public Object[][] createDataSplitea() {
        return new Object[][]{
                {"",1 },
                {"a,b",2 },
        };
    }

    @Test(dataProvider = "splitea")
    public void splitea(String valor, int numero) {
        int numeroDef = servicioStringBean.splitea(valor, ",");
        assert(numeroDef==numero):" no coinciden: "+numero+"!="+numeroDef;
    }

}
