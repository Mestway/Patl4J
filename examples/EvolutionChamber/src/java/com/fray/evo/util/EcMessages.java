package com.fray.evo.util;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
/** 
 * Retrieves string messages from a ResourceBundle properties file for i18n.
 * @author mike.angstadt
 */
public final class EcMessages {
  private final String bundleName;
  private ResourceBundle resourceBundle;
  private Locale locale;
  private static EcMessages messages;
  public static void init(  String bundleName){
    messages=new EcMessages(bundleName);
  }
  public static EcMessages getMessages(){
    return messages;
  }
  ResourceBundle.Control localeCandidateSelector=new ResourceBundle.Control(){
    @Override public List<Locale> getCandidateLocales(    String baseName,    Locale locale){
      boolean genVar4479;
      genVar4479=baseName == null;
      if (genVar4479) {
        java.lang.NullPointerException genVar4480;
        genVar4480=new NullPointerException();
        throw genVar4480;
      }
 else {
        ;
      }
      List<Locale> defaultCandidates;
      defaultCandidates=super.getCandidateLocales(baseName,locale);
      List<Locale> enhancedCandidates;
      enhancedCandidates=new ArrayList<Locale>();
      for (      Locale defaultLocale : defaultCandidates) {
        boolean genVar4481;
        genVar4481=Locale.ROOT.equals(defaultLocale);
        if (genVar4481) {
          enhancedCandidates.add(Locale.ENGLISH);
        }
 else {
          ;
        }
        enhancedCandidates.add(defaultLocale);
      }
      return enhancedCandidates;
    }
  }
;
  /** 
 * Constructor.
 * @param bundleName the base name of the resource bundle, a fully qualifiedclass name
 */
  public EcMessages(  String bundleName){
    this.bundleName=bundleName;
    Locale localeToLoad;
    localeToLoad=Locale.getDefault();
    resourceBundle=Utf8ResourceBundle.getBundle(bundleName,localeToLoad,localeCandidateSelector);
    locale=resourceBundle.getLocale();
    boolean genVar4483;
    genVar4483=locale == null;
    if (genVar4483) {
      locale=Locale.getDefault();
    }
 else {
      ;
    }
  }
  /** 
 * Gets a message.
 * @param key the message key
 * @return the message
 */
  public String getString(  String key){
    java.lang.String genVar4484;
    genVar4484=resourceBundle.getString(key);
    return genVar4484;
  }
  /** 
 * Gets a message that has arguments.
 * @param key the message key
 * @param arguments the arguments to populate the message with
 * @return the formatted message
 */
  public String getString(  String key,  Object... arguments){
    EcMessages genVar4485;
    genVar4485=this;
    java.lang.String genVar4486;
    genVar4486=genVar4485.getString(key);
    java.lang.String genVar4487;
    genVar4487=MessageFormat.format(genVar4486,arguments);
    return genVar4487;
  }
  /** 
 * Changes the Locale.
 * @param locale the new Locale
 */
  public void changeLocale(  Locale locale){
    com.fray.evo.util.EcMessages genVar4488;
    genVar4488=this;
    genVar4488.locale=locale;
    resourceBundle=Utf8ResourceBundle.getBundle(bundleName,locale);
  }
  /** 
 * Gets the current Locale.
 * @return
 */
  public Locale getLocale(){
    return locale;
  }
}
