package com.mediaymedia.util;

//import org.apache.commons.logging.log;
//import org.apache.commons.logging.logfactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Iterator;

/**
 *
 * User: invitado
 * Date: 09-mar-2005
 * Time: 13:25:17
 * To change this template use File | Settings | File Templates.
 */
public abstract class Listas {
    protected static Log log = LogFactory.getLog(Listas.class);

    public static List transformaSetEnLista(Set set) {
        List list = new ArrayList();
        for (Iterator it = set.iterator(); it.hasNext();) {
            list.add(it.next());

        }
        return list;

    }

    public static String separador(List lista, String separa) {
        String resultado = "";
        for (int index = 0; index < lista.size(); index++) {
            if (index == lista.size() - 1) separa = "";
            resultado += lista.get(index) + separa;
        }

        return resultado;
    }

    public static String separador(List lista, String separa, boolean comillas) {
        String resultado = "";
        for (int index = 0; index < lista.size(); index++) {
            if (index == lista.size() - 1) separa = "";
            if (comillas)
                resultado += "'" + lista.get(index) + "'" + separa;
            else
                resultado += lista.get(index) + separa;
        }

        return resultado;
    }


    public static List crearLista(String string, String separaClaveValor) {
        List nueva = new ArrayList();
        if (string != null) {
            String[] primeraSep = string.split(separaClaveValor);

            for (int index = 0; index < primeraSep.length; index++) {
                nueva.add(primeraSep[index]);
            }
        }
        return nueva;
    }

    public static List clonarLista(List lista) {
        List listaNueva = new ArrayList();
        for (int index = 0; index < lista.size(); index++) {
            listaNueva.add(lista.get(index));
        }
        return listaNueva;
    }

    public static String dameSelect(String nombreSelect, List valores, List visibles, String selected, String onChange) {
        String anyos = "";
        String selectedOption = "";
        anyos += "\n<select name='" + nombreSelect + "' " + onChange + ">\n";
        for (int index = 0; index < valores.size(); index++) {
            String valorActual = (String) valores.get(index);
            if (valorActual.equals(selected))
                selectedOption = "selected";
            else
                selectedOption = "";
            anyos += "<option " + selectedOption + " value='" + valorActual + "'>" + visibles.get(index) + "</option>\n";
        }
        anyos += "</select>\n";
        return anyos;

    }

    public static String dameSelect(String nombreSelect, List valores, List visibles, String selected) {
        return dameSelect(nombreSelect, valores, visibles, selected, "");
    }


    public static void sumarLista(List listaBase, List listaAdd) {
        for (int index = 0; index < listaAdd.size(); index++) {
            listaBase.add(listaAdd.get(index));
        }


    }

    public static boolean listaDeStringscontieneString(List lista, String identificativo) {
        log.info("size de array de busqueda " + lista.size() + " identificativo: " + identificativo);
        for (int index = 0; index < lista.size(); index++) {
            String s = (String) lista.get(index);
            log.debug("cadena " + s);
            if (s.equals(identificativo)) {
                log.info("CADENA ENCONTRADA");
                return true;
            }
        }
        log.info("CADENA NO ENCONTRADA");
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

}
