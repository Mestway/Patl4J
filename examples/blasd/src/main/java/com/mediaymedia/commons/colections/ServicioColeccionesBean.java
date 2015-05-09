package com.mediaymedia.commons.colections;

import com.mediaymedia.seam.ComponenteBase;
import com.mediaymedia.seam.reflection.ObjetoConPropiedadPublica;
import com.mediaymedia.seam.reflection.ServiceReflection;
import com.mediaymedia.seam.reflection.errors.ReflectionError;
import com.mediaymedia.commons.strings.ServicioStrings;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;

import javax.ejb.Stateless;
import java.util.List;
import java.util.ArrayList;

/**
 * User: juan
 * Date: 25-oct-2007
 * Time: 15:02:41
 */
@Name(ServicioColecciones.seamName)
@Stateless
public class ServicioColeccionesBean extends ComponenteBase implements ServicioColecciones {
    @In(value = ServiceReflection.seamName, create = true)
    ServiceReflection serviceReflection;

    @In(value = ServicioStrings.seamName, create = true)
    ServicioStrings servicioStrings;
   
    public boolean existe(String s, String[] list) {
        for (String dentro : list) {
            log.debug(dentro + "==" + s);
            if (dentro.trim().equals(s.trim())) return true;
        }
        return false;
    }
    public List<ObjetoConPropiedadPublica> dameListaConPatronIndiceComun(List<ObjetoConPropiedadPublica> resultList, String patron) throws ReflectionError {
        List<ObjetoConPropiedadPublica> listado = new ArrayList<ObjetoConPropiedadPublica>();
        for (ObjetoConPropiedadPublica g : resultList)
            if (servicioStrings.compartenIndice((String) serviceReflection.invocaGetter(g, g.getNombrePropiedad()), patron))
                addToLista(listado, g);

        return listado;
    }
    private void addToLista(List<ObjetoConPropiedadPublica> definitivos, ObjetoConPropiedadPublica grupoExistente) throws ReflectionError {
        boolean existe = false;
        String externo = (String) serviceReflection.invocaGetter(grupoExistente, grupoExistente.getNombrePropiedad());
        for (ObjetoConPropiedadPublica ob : definitivos) {
            String interno = (String) serviceReflection.invocaGetter(ob, ob.getNombrePropiedad());
            if (interno.toLowerCase().trim().equals(externo.toLowerCase().trim())) existe = true;
        }
        if (!existe) definitivos.add(grupoExistente);

    }
       
}
