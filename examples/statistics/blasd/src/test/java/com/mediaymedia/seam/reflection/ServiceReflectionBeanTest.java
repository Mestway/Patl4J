package com.mediaymedia.seam.reflection;

import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.reset;
import static org.easymock.classextension.EasyMock.verify;
import static org.easymock.classextension.EasyMock.expect;

import java.lang.reflect.InvocationTargetException;

import org.easymock.classextension.EasyMock;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mediaymedia.commons.excepciones.MediaExcepcion;
import com.mediaymedia.commons.strings.ServicioStrings;
import com.mediaymedia.seam.reflection.errors.ReflectionError;
import com.mediaymedia.test.Logger;
import com.mediaymedia.test.TestTimer;

/**
 * User: juan
 * Date: 24-oct-2007
 * Time: 20:33:46
 */
public class ServiceReflectionBeanTest extends TestTimer {
    ServiceReflectionBean reflectionBean;
    ServicioStrings servicioStrings;

    @BeforeMethod
    public void startPrueba() {
        super.startPrueba();
        reflectionBean = new ServiceReflectionBean();
        reflectionBean.setLog(new Logger());
        servicioStrings = createMock(ServicioStrings.class);
        reset(servicioStrings);
        reflectionBean.servicioStrings = servicioStrings;

    }

    @AfterMethod
    public void finishPrueba() {
        super.finishPrueba();
        reflectionBean = null;
    }

    @DataProvider(name = "getter")
    public Object[][] createData5() {
        return new Object[][]{
                {new ObjetoPrueba("hola", "propiedad"), "hola",  "Propiedad", null},
                {new ObjetoPrueba("hola", "propiedad2"), "hola",  "Propiedad2", new ReflectionError(ServiceReflectionBean.ReflectionMensaje.METODO_INEXISTENTE+"getPropiedad2")},

        };
    }


    @Test(dataProvider = "getter")
    public void invocaGetter(ObjetoConPropiedadPublica o, String resultadoEsperado,  String nombreMetodo, ReflectionError r) throws MediaExcepcion {

        try {
            expect(servicioStrings.conviertePrimeraLetraAMAyuscula(o.getNombrePropiedad())).andReturn(nombreMetodo);
            replay(servicioStrings);
            Object resultado = reflectionBean.invocaGetter(o, o.getNombrePropiedad());
            assert (r == null) : " no se esperaba un error!";
            assert (resultado.equals(resultadoEsperado)) : "los resultados no coinciden: " + resultadoEsperado + "!=" + resultado.toString();
            verify(servicioStrings);
        } catch (ReflectionError reflectionError) {
            assert (r != null);
            assert(reflectionError.getMessage().equals(r.getMessage())):reflectionError.getMessage()+"!="+r.getMessage();
        }


    }

    @Test
    public void pruebaSetter() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ObjetoPrueba objetoPrueba = new ObjetoPrueba("hola", "propiedad");
        Object valor="adios";
        objetoPrueba.getClass().getMethod("setPropiedad", valor.getClass()).invoke(objetoPrueba,valor);
        testLog.error(objetoPrueba.getPropiedad());
    }

}

class ObjetoPrueba implements ObjetoConPropiedadPublica {
    String propiedad;
    String nombrePropiedad;


    public ObjetoPrueba(String propiedad, String nombrePropiedad) {
        this.propiedad = propiedad;
        this.nombrePropiedad = nombrePropiedad;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public String getNombrePropiedad() {
        return nombrePropiedad;
    }


    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }
}
