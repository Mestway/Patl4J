package com.mediaymedia.seam.intercept;

import com.mediaymedia.seam.upload.SeamUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.faces.event.ActionEvent;
import java.io.File;
import java.lang.reflect.Method;

public class LogeaUploadInterceptor {
    Log log = LogFactory.getLog(LogeaUploadInterceptor.class);


    @AroundInvoke
    public Object selectFile(InvocationContext invocation) throws Exception {
        String methodName = invocation.getMethod().getName();
        SeamUpload o = (SeamUpload) invocation.getTarget();
        log.info(methodName);
        if (esActionMetod(invocation.getMethod())) {
            log.info(methodName);
            log.info("Nombre Completo de archivo upload: " + o.getFileName());
            log.info("Nombre Simple: " + o.getFileNameSimple());
            log.info("ContentType: " + o.getFileContentType());
            log.info("separatorChar: "+ File.separatorChar);
            log.info("separator: "+File.separator);
            log.info("pathSeparator: "+File.pathSeparator);
            log.info("pathSeparatorChar: "+File.pathSeparatorChar);

        }
        Object result = invocation.proceed();
        return result;


    }

    private boolean esActionMetod(Method method) {
        for(Class claz:method.getParameterTypes())
            if(claz.equals(ActionEvent.class))return true;
        return false;  //To change body of created methods use File | Settings | File Templates.
    }


}
