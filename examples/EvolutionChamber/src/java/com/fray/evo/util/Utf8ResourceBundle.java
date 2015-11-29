package com.fray.evo.util;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;
public abstract class Utf8ResourceBundle {
  private static final Logger logger=Logger.getLogger(Utf8ResourceBundle.class.getName());
  public static final ResourceBundle getBundle(  String baseName){
    ResourceBundle bundle;
    bundle=ResourceBundle.getBundle(baseName);
    java.util.ResourceBundle genVar4382;
    genVar4382=createUtf8PropertyResourceBundle(bundle);
    return genVar4382;
  }
  public static final ResourceBundle getBundle(  String baseName,  Locale locale){
    ResourceBundle bundle;
    bundle=ResourceBundle.getBundle(baseName,locale);
    java.util.ResourceBundle genVar4384;
    genVar4384=createUtf8PropertyResourceBundle(bundle);
    return genVar4384;
  }
  public static ResourceBundle getBundle(  String baseName,  Locale locale,  ClassLoader loader){
    ResourceBundle bundle;
    bundle=ResourceBundle.getBundle(baseName,locale);
    java.util.ResourceBundle genVar4386;
    genVar4386=createUtf8PropertyResourceBundle(bundle);
    return genVar4386;
  }
  public static ResourceBundle getBundle(  String baseName,  Locale locale,  ResourceBundle.Control control){
    ResourceBundle bundle;
    bundle=ResourceBundle.getBundle(baseName,locale,control);
    java.util.ResourceBundle genVar4388;
    genVar4388=createUtf8PropertyResourceBundle(bundle);
    return genVar4388;
  }
  private static ResourceBundle createUtf8PropertyResourceBundle(  ResourceBundle bundle){
    boolean genVar4389;
    genVar4389=bundle instanceof PropertyResourceBundle;
    boolean genVar4390;
    genVar4390=(genVar4389);
    boolean genVar4391;
    genVar4391=!genVar4390;
    if (genVar4391) {
      return bundle;
    }
 else {
      ;
    }
    java.util.Locale genVar4392;
    genVar4392=bundle.getLocale();
    String lang;
    lang=genVar4392.getLanguage();
    java.lang.String genVar4393;
    genVar4393="fr";
    boolean genVar4394;
    genVar4394=genVar4393.equals(lang);
    java.lang.String genVar4395;
    genVar4395="es";
    boolean genVar4396;
    genVar4396=genVar4395.equals(lang);
    boolean genVar4397;
    genVar4397=genVar4394 || genVar4396;
    if (genVar4397) {
      return bundle;
    }
 else {
      ;
    }
    java.util.PropertyResourceBundle genVar4398;
    genVar4398=(PropertyResourceBundle)bundle;
    com.fray.evo.util.Utf8ResourceBundle.Utf8PropertyResourceBundle genVar4399;
    genVar4399=new Utf8PropertyResourceBundle(genVar4398);
    return genVar4399;
  }
private static class Utf8PropertyResourceBundle extends ResourceBundle {
    PropertyResourceBundle bundle;
    private Utf8PropertyResourceBundle(    PropertyResourceBundle bundle){
      com.fray.evo.util.Utf8ResourceBundle.Utf8PropertyResourceBundle genVar4400;
      genVar4400=this;
      genVar4400.bundle=bundle;
    }
    public Enumeration<String> getKeys(){
      java.util.Enumeration<java.lang.String> genVar4401;
      genVar4401=bundle.getKeys();
      return genVar4401;
    }
    protected Object handleGetObject(    String key){
      java.lang.Object genVar4402;
      genVar4402=bundle.handleGetObject(key);
      String value;
      value=(String)genVar4402;
      try {
        java.lang.String genVar4403;
        genVar4403="ISO-8859-1";
        byte[] genVar4404;
        genVar4404=value.getBytes(genVar4403);
        java.lang.String genVar4405;
        genVar4405="UTF-8";
        java.lang.String genVar4406;
        genVar4406=new String(genVar4404,genVar4405);
        return genVar4406;
      }
 catch (      UnsupportedEncodingException e) {
        return null;
      }
catch (      NullPointerException n) {
        java.lang.String genVar4407;
        genVar4407="Externalize ";
        java.lang.String genVar4408;
        genVar4408=genVar4407 + key;
        logger.severe(genVar4408);
        return key;
      }
    }
    /** 
 * @return
 * @see java.util.ResourceBundle#getLocale()
 */
    @Override public Locale getLocale(){
      java.util.Locale genVar4409;
      genVar4409=bundle.getLocale();
      return genVar4409;
    }
    /** 
 * @return
 * @see java.util.ResourceBundle#keySet()
 */
    @Override public Set<String> keySet(){
      java.util.Set<java.lang.String> genVar4410;
      genVar4410=bundle.keySet();
      return genVar4410;
    }
    /** 
 * @param aKey
 * @return
 * @see java.util.ResourceBundle#containsKey(java.lang.String)
 */
    @Override public boolean containsKey(    String aKey){
      boolean genVar4411;
      genVar4411=bundle.containsKey(aKey);
      return genVar4411;
    }
  }
}
