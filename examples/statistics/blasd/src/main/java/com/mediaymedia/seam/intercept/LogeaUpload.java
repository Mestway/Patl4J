package com.mediaymedia.seam.intercept;

import javax.interceptor.Interceptors;
import java.lang.annotation.*;

/**
 * User: juan
 * Date: 10-oct-2007
 * Time: 10:34:01
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Interceptors(LogeaUploadInterceptor.class)
public @interface LogeaUpload {
}
