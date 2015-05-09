package com.mediaymedia.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: juan
 * Date: 28-sep-2007
 * Time: 14:28:36
 */
public class Logger implements org.jboss.seam.log.Log{
      Log log = LogFactory.getLog(Logger.class);

      public boolean isDebugEnabled() {
          return log.isDebugEnabled();  //To change body of implemented methods use File | Settings | File Templates.
      }

      public boolean isErrorEnabled() {
          return log.isErrorEnabled();  //To change body of implemented methods use File | Settings | File Templates.
      }

      public boolean isFatalEnabled() {
          return log.isFatalEnabled();  //To change body of implemented methods use File | Settings | File Templates.
      }

      public boolean isInfoEnabled() {
          return log.isInfoEnabled();  //To change body of implemented methods use File | Settings | File Templates.
      }

      public boolean isTraceEnabled() {
          return log.isTraceEnabled();  //To change body of implemented methods use File | Settings | File Templates.
      }

      public boolean isWarnEnabled() {
          return log.isWarnEnabled();  //To change body of implemented methods use File | Settings | File Templates.
      }

      public void trace(Object object, Object... params) {
          log.trace(object);
          //To change body of implemented methods use File | Settings | File Templates.
      }

      public void trace(Object object, Throwable t, Object... params) {
          log.trace(object,t);
          //To change body of implemented methods use File | Settings | File Templates.
      }

      public void debug(Object object, Object... params) {
          log.debug(object);
          //To change body of implemented methods use File | Settings | File Templates.
      }

      public void debug(Object object, Throwable t, Object... params) {
          log.debug(object,t);
          //To change body of implemented methods use File | Settings | File Templates.
      }

      public void info(Object object, Object... params) {
          log.info(object);
          //To change body of implemented methods use File | Settings | File Templates.
      }

      public void info(Object object, Throwable t, Object... params) {
          log.info(object,t);
          //To change body of implemented methods use File | Settings | File Templates.
      }

      public void warn(Object object, Object... params) {
          log.warn(object);
          //To change body of implemented methods use File | Settings | File Templates.
      }

      public void warn(Object object, Throwable t, Object... params) {
          log.warn(object,t);
          //To change body of implemented methods use File | Settings | File Templates.
      }

      public void error(Object object, Object... params) {
          log.error(object);
          //To change body of implemented methods use File | Settings | File Templates.
      }

      public void error(Object object, Throwable t, Object... params) {
          log.error(object,t);
          //To change body of implemented methods use File | Settings | File Templates.
      }

      public void fatal(Object object, Object... params) {
          log.fatal(object);
          //To change body of implemented methods use File | Settings | File Templates.
      }

      public void fatal(Object object, Throwable t, Object... params) {
          log.fatal(object,t);
          //To change body of implemented methods use File | Settings | File Templates.
      }
  }

