package com.mediaymedia.faces;

import javax.faces.convert.Converter;

/**
 * User: juan
 * Date: 10-oct-2007
 * Time: 21:46:16
 */
public interface FacesServicio {
    String seamName="facesServicio";

    /**
     *
     * @param dir
     * @param paramWebPathRelativo
     * @return
     */
     String dameRealPath(String dir, String paramWebPathRelativo) ;


    /**
     *
      * @param dir
     * @return el path real del dir que existe en aplicacion acabado en java.io.File.separator
     */
    String dameRealPath(String dir) ;


      String getViewId() ;
     Converter getUpperCaseDateTimeConverter()  ;

}
