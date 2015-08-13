package com.mediaymedia.richfaces;

import com.mediaymedia.commons.colections.ServicioColecciones;
import com.mediaymedia.seam.ComponenteBase;
import com.mediaymedia.seam.reflection.ObjetoConPropiedadPublica;
import com.mediaymedia.seam.reflection.ServiceReflection;
import com.mediaymedia.seam.reflection.errors.ReflectionError;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Name(SugestionBoxBean.seamName)
public class SugestionBoxBean extends ComponenteBase implements SugestionBox {
    public final static String seamName = "suggestionBox";

    @In(value = ServicioColecciones.seamName, create = true)
    ServicioColecciones servicioColecciones;
    @In(value = ServiceReflection.seamName, create = true)
    ServiceReflection serviceReflection;


    public List autocomplete(Object suggest, List<ObjetoConPropiedadPublica> list, String property) {
        log.debug("suggest: " + suggest + ". Valor del property: " + property);

        try {
            List actual = new ArrayList();
            String[] strings = property.split(SugestionBox.characterSeparador);
            String ultimo = ((String) suggest).trim();
            if (ultimo.equals(SugestionBox.characterSugerir)) {
                for (Object g : list) if (!contiene(strings, (ObjetoConPropiedadPublica) g)) actual.add(g);
                return actual;
            } else if (!ultimo.equals("")) {
                List<ObjetoConPropiedadPublica> resultList = servicioColecciones.dameListaConPatronIndiceComun(list, ultimo);
                for (ObjetoConPropiedadPublica g : resultList) if (!contiene(strings, g)) actual.add(g);
                return actual;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    private boolean contiene(String[] list, ObjetoConPropiedadPublica g) throws ReflectionError {
        return servicioColecciones.existe(serviceReflection.invocaGetter(g), list);
    }


    public SeleccionSugestionBox dameSeleccion(String property, List objetosConPropiedadPublica, Class aClass) throws ReflectionError {
        SeleccionSugestionBox seleccionSugestionBox = new SeleccionSugestionBox();
        loadExistentes(seleccionSugestionBox, property, objetosConPropiedadPublica, aClass);
        loadInexistentes(seleccionSugestionBox, property, objetosConPropiedadPublica, aClass);
        return seleccionSugestionBox;
    }

    public void loadInexistentes(SeleccionSugestionBox seleccionSugestionBox, String property, List objetosConPropiedadPublica, Class aClass) throws ReflectionError {
        String[] nombresGrupo = property.split(SugestionBox.characterSeparador);
        for (String nombreGrupo : nombresGrupo) {
            ObjetoConPropiedadPublica grupoExistente = existeEntidad(objetosConPropiedadPublica, nombreGrupo);
            if (grupoExistente == null && nombreGrupo.trim().length() > 0) {
                Object o = serviceReflection.creaObjectoPropiedadPublica(aClass, nombreGrupo);
                addToLista(seleccionSugestionBox.getInexistentes(), (ObjetoConPropiedadPublica) o);
            }
        }
    }

    public void loadExistentes(SeleccionSugestionBox seleccionSugestionBox, String property, List objetosConPropiedadPublica, Class aClass) throws ReflectionError {
        String[] nombresGrupo = property.split(SugestionBox.characterSeparador);
        for (String nombreGrupo : nombresGrupo) {
            ObjetoConPropiedadPublica grupoExistente = existeEntidad(objetosConPropiedadPublica, nombreGrupo);
            if (grupoExistente != null) addToLista(seleccionSugestionBox.getExistentes(), grupoExistente);
        }
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

    private ObjetoConPropiedadPublica existeEntidad(List<ObjetoConPropiedadPublica> resultList, String nombreGrupo) throws ReflectionError {
        for (ObjetoConPropiedadPublica gr : resultList)
            if (((String) serviceReflection.invocaGetter(gr, gr.getNombrePropiedad())).trim().toLowerCase().equals(nombreGrupo.trim().toLowerCase()))
                return gr;
        return null;

    }

    public String dameCadena(List<ObjetoConPropiedadPublica> resultList) throws ReflectionError {
        String cadena = "";
        for (ObjetoConPropiedadPublica gr : resultList) {
            cadena += serviceReflection.invocaGetter(gr, gr.getNombrePropiedad()) + SugestionBox.characterSeparador;
        }
        if (resultList.size() > 0) cadena = cadena.substring(0, cadena.length() - 1);
        return cadena;
    }

    public Object dameOpcion(String seleccion, List<ObjetoConPropiedadPublica> lista, Class aClass) throws ReflectionError {
        if(seleccion.trim().equals("")) return null;
        for(ObjetoConPropiedadPublica o:lista)
            if(serviceReflection.invocaGetter(o).equals(seleccion)) return o;
        return serviceReflection.creaObjectoPropiedadPublica(aClass, seleccion);
    }

}
