package com.mediaymedia.seam.reflection;

import com.mediaymedia.commons.excepciones.MediaExcepcion;
import com.mediaymedia.commons.strings.ServicioStrings;
import com.mediaymedia.seam.ComponenteBase;
import com.mediaymedia.seam.reflection.errors.ReflectionError;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.lang.reflect.InvocationTargetException;

/**
 * User: juan
 * Date: 24-oct-2007
 * Time: 18:25:48
 */
@Name(ServiceReflection.seamName)
public class ServiceReflectionBean extends ComponenteBase implements ServiceReflection {

    @In(create = true, value = ServicioStrings.seamName)
    ServicioStrings servicioStrings;

    public String invocaGetter(ObjetoConPropiedadPublica g) throws ReflectionError {
        return (String) invocaGetter(g,g.getNombrePropiedad());  //To change body of implemented methods use File | Settings | File Templates.
    }
    public void invocaSetter(ObjetoConPropiedadPublica g, Object valor) throws ReflectionError {
        invocaSetter(g,g.getNombrePropiedad(),valor);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> T creaObjectoPropiedadPublica(Class<T> aClass, Object nombreGrupo) throws ReflectionError {
        try {
            ObjetoConPropiedadPublica t = (ObjetoConPropiedadPublica) aClass.newInstance();
            invocaSetter(t, nombreGrupo);
            return (T) t;
        } catch (InstantiationException e) {
            throw new ReflectionError("error de instanciacion de "+aClass.getSimpleName(),e);
        } catch (IllegalAccessException e) {
            throw new ReflectionError("error de ilegal acceso a metodo "+aClass.getSimpleName(),e);
        }
    }

    enum prefijos {
        get, set, is
    }

    public enum ReflectionMensaje {
        ILEGAL_ACCESO("ilegal acceso al metodo: "),
        METODO_INEXISTENTE("No existe el metodo: "),
        EXCEPCION_INVOCACION("excepcion de invocacion en el metodo: ");

        private final String mensaje;   // in kilograms

        ReflectionMensaje(String m) {
            this.mensaje = m;
        }

        public String toString() {
            return mensaje;    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    public Object invocaGetter(Object o, String nombrePropiedad) throws ReflectionError {

        String nombreMetodoGetter = null;
        try {
            nombreMetodoGetter = prefijos.get.toString() + servicioStrings.conviertePrimeraLetraAMAyuscula(nombrePropiedad);
        } catch (MediaExcepcion mediaExcepcion) {
            throw new ReflectionError(mediaExcepcion.getMessage(), mediaExcepcion);
        }
        try {
            return o.getClass().getMethod(nombreMetodoGetter).invoke(o);
        } catch (IllegalAccessException e) {
            throw new ReflectionError(ReflectionMensaje.ILEGAL_ACCESO + nombreMetodoGetter, e);
        } catch (InvocationTargetException e) {
            throw new ReflectionError(ReflectionMensaje.ILEGAL_ACCESO + nombreMetodoGetter, e);
        } catch (NoSuchMethodException e) {
            throw new ReflectionError(ReflectionMensaje.METODO_INEXISTENTE + nombreMetodoGetter, e);
        }

    }
    public Object invocaSetter(Object o, String nombrePropiedad, Object valor) throws ReflectionError {

        String nombreMetodoSetter = null;
        try {
            nombreMetodoSetter = prefijos.set.toString() + servicioStrings.conviertePrimeraLetraAMAyuscula(nombrePropiedad);
        } catch (MediaExcepcion mediaExcepcion) {
            throw new ReflectionError(mediaExcepcion.getMessage(), mediaExcepcion);
        }
        try {
            return o.getClass().getMethod(nombreMetodoSetter, valor.getClass()).invoke(o,valor);
        } catch (IllegalAccessException e) {
            throw new ReflectionError(ReflectionMensaje.ILEGAL_ACCESO + nombreMetodoSetter, e);
        } catch (InvocationTargetException e) {
            throw new ReflectionError(ReflectionMensaje.ILEGAL_ACCESO + nombreMetodoSetter, e);
        } catch (NoSuchMethodException e) {
            throw new ReflectionError(ReflectionMensaje.METODO_INEXISTENTE + nombreMetodoSetter, e);
        }

    }
}
