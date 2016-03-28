package com.mediaymedia.faces;

import org.jboss.seam.annotations.Name;

import javax.ejb.Stateless;
import javax.servlet.ServletContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.component.UIComponent;

import com.mediaymedia.faces.util.FacesUtil;

import java.io.File;
import java.util.Locale;

/**
 * User: juan
 * Date: 10-oct-2007
 * Time: 21:46:19
 */
@Name(FacesServicio.seamName)
@Stateless
public class FacesServicioImpl implements FacesServicio {

    public String dameRealPath(String dir, String paramWebPathRelativo) {
        ServletContext servletContext= FacesUtil.getServletContext(FacesContext.getCurrentInstance());
        String pathRelativo = FacesUtil.getParamValueWebXML(servletContext, paramWebPathRelativo);
        return  servletContext.getRealPath(pathRelativo + dir);
    }

    public String dameRealPath(String dir) {
        String realPath = FacesUtil.getServletContext(FacesContext.getCurrentInstance()).getRealPath(".");
        return realPath+dir+ File.separator;
    }

    public  String getViewId() {
        return FacesContext.getCurrentInstance().getViewRoot().getViewId();
    }


      public Converter getUpperCaseDateTimeConverter()
  {
    return new Converter(){
      private DateTimeConverter dateTimeConverter = new DateTimeConverter();

      public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException
      {
        initDateTimeConverter();
        return dateTimeConverter.getAsObject(context, component, value);
      }

      public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        initDateTimeConverter();
        return dateTimeConverter.getAsString(context, component, value).toUpperCase();
      }

      private void initDateTimeConverter()
      {
        //todo: move locale and string pattern to context parameters
        dateTimeConverter.setPattern("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      }
    };
  }

}
