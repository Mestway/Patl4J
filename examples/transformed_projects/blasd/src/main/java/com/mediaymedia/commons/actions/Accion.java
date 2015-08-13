package com.mediaymedia.commons.actions;

import com.mediaymedia.commons.actions.condiciones.Condicion;
import com.mediaymedia.commons.actions.excepciones.AccionExcepcion;

/**
 *  
 * User: juanitu
 * Date: 22-mar-2007
 * Time: 15:02:50
 * To change this template use File | Settings | File Templates.
 */
public interface Accion {

    Condicion[] getPreCodiciones();

    Condicion[] getPostCodiciones();

    Resultado ejecuta() throws AccionExcepcion;


}
