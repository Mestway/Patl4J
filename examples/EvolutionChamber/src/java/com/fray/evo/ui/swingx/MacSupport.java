package com.fray.evo.ui.swingx;
import java.awt.Image;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessControlException;
import javax.swing.ImageIcon;
/** 
 * This class contains code which helps to better integrate the application into the Mac OSX environment.
 * @see http://developer.apple.com/mac/library/documentation/Java/Reference/1.5.0/appledoc/api/index.html
 * @author mike.angstadt
 */
public class MacSupport {
  /** 
 * Runs initialization code specific to Mac OSX.
 * @param title the title of the application
 * @param enablePreferences true to enable the "Preferences" menu option,false to disable it. If enabled, you must override MacHandler.handlePreferences() in the handler parameter
 * @param dockImage the classpath to the image that will appear in the dockand when alt-tabbed. Null for no image.
 * @param handler handles the various Mac events
 */
  public static void init(  String title,  boolean enablePreferences,  String dockImage,  final MacHandler handler){
    try {
      java.lang.String genVar4122;
      genVar4122="apple.laf.useScreenMenuBar";
      java.lang.String genVar4123;
      genVar4123="true";
      System.setProperty(genVar4122,genVar4123);
      java.lang.String genVar4124;
      genVar4124="com.apple.mrj.application.apple.menu.about.name";
      System.setProperty(genVar4124,title);
      java.lang.String genVar4125;
      genVar4125="com.apple.eawt.ApplicationListener";
      Class<?> applicationListenerInterface;
      applicationListenerInterface=Class.forName(genVar4125);
      java.lang.Class<com.fray.evo.ui.swingx.MacSupport> genVar4126;
      genVar4126=MacSupport.class;
      java.lang.ClassLoader genVar4127;
      genVar4127=genVar4126.getClassLoader();
      Class<?>[] genVar4128;
      genVar4128=new Class<?>[]{applicationListenerInterface};
      InvocationHandler genVar4129;
      genVar4129=new InvocationHandler(){
        @Override public Object invoke(        Object proxy,        Method method,        Object[] arguments) throws Throwable {
          String methodName=method.getName();
          Object applicationEvent=arguments[0];
          if (methodName.equals("handleQuit")) {
            handler.handleQuit(applicationEvent);
          }
 else           if (methodName.equals("handleAbout")) {
            handler.internalHandleAbout(applicationEvent);
          }
 else           if (methodName.equals("handlePreferences")) {
            handler.handlePreferences(applicationEvent);
          }
          return null;
        }
      }
;
      Object applicationListenerInstance;
      applicationListenerInstance=Proxy.newProxyInstance(genVar4127,genVar4128,genVar4129);
      java.lang.String genVar4130;
      genVar4130="com.apple.eawt.Application";
      Class<?> applicationClass;
      applicationClass=Class.forName(genVar4130);
      java.lang.String genVar4131;
      genVar4131="getApplication";
      Method getApplicationMethod;
      getApplicationMethod=applicationClass.getMethod(genVar4131);
      Object applicationInstance;
      applicationInstance=getApplicationMethod.invoke(null);
      if (enablePreferences) {
        java.lang.String genVar4132;
        genVar4132="setEnabledPreferencesMenu";
        java.lang.Class<java.lang.Boolean> genVar4133;
        genVar4133=boolean.class;
        Method setEnabledPreferencesMenuMethod;
        setEnabledPreferencesMenuMethod=applicationClass.getMethod(genVar4132,genVar4133);
        setEnabledPreferencesMenuMethod.invoke(applicationInstance,enablePreferences);
      }
 else {
        ;
      }
      java.lang.String genVar4134;
      genVar4134="addApplicationListener";
      Method addApplicationListenerMethod;
      addApplicationListenerMethod=applicationClass.getMethod(genVar4134,applicationListenerInterface);
      addApplicationListenerMethod.invoke(applicationInstance,applicationListenerInstance);
      boolean genVar4135;
      genVar4135=dockImage != null;
      if (genVar4135) {
        java.lang.String genVar4136;
        genVar4136="setDockIconImage";
        java.lang.Class<java.awt.Image> genVar4137;
        genVar4137=Image.class;
        Method setDockIconImageMethod;
        setDockIconImageMethod=applicationClass.getMethod(genVar4136,genVar4137);
        java.lang.Class<com.fray.evo.ui.swingx.MacSupport> genVar4138;
        genVar4138=MacSupport.class;
        java.net.URL genVar4139;
        genVar4139=genVar4138.getResource(dockImage);
        ImageIcon icon;
        icon=new ImageIcon(genVar4139);
        java.awt.Image genVar4140;
        genVar4140=icon.getImage();
        setDockIconImageMethod.invoke(applicationInstance,genVar4140);
      }
 else {
        ;
      }
    }
 catch (    AccessControlException e) {
    }
catch (    Throwable e) {
    }
  }
  /** 
 * Determines whether the application is running on a Mac.
 * @return true if the application is running on a Mac, false if not
 */
  public static boolean isMac(){
    java.lang.String genVar4141;
    genVar4141="os.name";
    java.lang.String genVar4142;
    genVar4142=System.getProperty(genVar4141);
    String osName;
    osName=genVar4142.toLowerCase();
    java.lang.String genVar4143;
    genVar4143="mac os x";
    boolean genVar4144;
    genVar4144=osName.startsWith(genVar4143);
    return genVar4144;
  }
  /** 
 * Runs initialization code specific to Mac OSX only if the application is running on a Mac.
 * @param title the title of the application
 * @param enablePreferences true to enable the "Preferences" menu option,false to disable it. If enabled, you must override MacHandler.handlePreferences() in the handler parameter
 * @param dockImage the classpath to the image that will appear in the dockand when alt-tabbed. Null for no image.
 * @param handler handles the various Mac events
 */
  public static void initIfMac(  String title,  boolean enablePreferences,  String dockImage,  MacHandler handler){
    boolean genVar4146;
    genVar4146=isMac();
    if (genVar4146) {
      init(title,enablePreferences,dockImage,handler);
    }
 else {
      ;
    }
  }
}
