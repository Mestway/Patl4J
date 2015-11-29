package com.fray.evo.ui.swingx;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;
import com.fray.evo.EvolutionChamber;
/** 
 * Stores and retrieves the user's personal settings.
 * @author mike.angstadt
 */
public class UserSettings {
  private static final Logger logger=Logger.getLogger(UserSettings.class.getName());
  /** 
 * The settings.
 */
  private Properties properties;
  /** 
 * The properties file that the settings are stored in.
 */
  private File file;
  /** 
 * Loads a user settings file at the specified location.
 * @param file the location of the settings file
 */
  public UserSettings(  File file){
    properties=new Properties();
    com.fray.evo.ui.swingx.UserSettings genVar3341;
    genVar3341=this;
    genVar3341.file=file;
    boolean genVar3342;
    genVar3342=file.exists();
    if (genVar3342) {
      try {
        FileInputStream inStream;
        inStream=new FileInputStream(file);
        properties.load(inStream);
        inStream.close();
      }
 catch (      IOException e) {
        java.lang.String genVar3343;
        genVar3343="Cannot read user settings.";
        logger.severe(genVar3343);
      }
    }
 else {
      ;
    }
    UserSettings genVar3344;
    genVar3344=this;
    java.lang.String genVar3345;
    genVar3345=genVar3344.getVersion();
    boolean genVar3346;
    genVar3346=genVar3345 == null;
    if (genVar3346) {
      UserSettings genVar3347;
      genVar3347=this;
      java.lang.String genVar3348;
      genVar3348="version";
      genVar3347.setPropertyAndSave(genVar3348,EvolutionChamber.VERSION);
    }
 else {
      ;
    }
  }
  /** 
 * Gets the user's preferred Locale.
 * @return
 */
  public Locale getLocale(){
    java.lang.String genVar3349;
    genVar3349="locale";
    String localeStr;
    localeStr=properties.getProperty(genVar3349);
    boolean genVar3350;
    genVar3350=localeStr != null;
    if (genVar3350) {
      char genVar3351;
      genVar3351='_';
      int pos;
      pos=localeStr.indexOf(genVar3351);
      int genVar3352;
      genVar3352=0;
      boolean genVar3353;
      genVar3353=pos >= genVar3352;
      if (genVar3353) {
        int genVar3354;
        genVar3354=0;
        java.lang.String genVar3355;
        genVar3355=localeStr.substring(genVar3354,pos);
        int genVar3356;
        genVar3356=1;
        int genVar3357;
        genVar3357=pos + genVar3356;
        java.lang.String genVar3358;
        genVar3358=localeStr.substring(genVar3357);
        java.util.Locale genVar3359;
        genVar3359=new Locale(genVar3355,genVar3358);
        return genVar3359;
      }
 else {
        int genVar3360;
        genVar3360=localeStr.length();
        int genVar3361;
        genVar3361=0;
        boolean genVar3362;
        genVar3362=genVar3360 > genVar3361;
        if (genVar3362) {
          java.util.Locale genVar3363;
          genVar3363=new Locale(localeStr);
          return genVar3363;
        }
 else {
          ;
        }
      }
    }
 else {
      ;
    }
    return null;
  }
  /** 
 * Sets the user's preferred Locale.
 * @param locale
 */
  public void setLocale(  Locale locale){
    String localeStr;
    localeStr=locale.getLanguage();
    boolean genVar3364;
    genVar3364=locale.getCountry() != null && !locale.getCountry().isEmpty();
    if (genVar3364) {
      java.lang.String genVar3365;
      genVar3365="_";
      java.lang.String genVar3366;
      genVar3366=locale.getCountry();
      localeStr+=genVar3365 + genVar3366;
    }
 else {
      ;
    }
    UserSettings genVar3367;
    genVar3367=this;
    java.lang.String genVar3368;
    genVar3368="locale";
    genVar3367.setPropertyAndSave(genVar3368,localeStr);
  }
  /** 
 * retrieves a property as integer
 * @param propertyName the property key
 * @return the integer value or null if it is not set or could not be parsed
 */
  private Integer getAsInteger(  String propertyName){
    String stringVal;
    stringVal=properties.getProperty(propertyName);
    try {
      Integer integerVal;
      integerVal=Integer.valueOf(stringVal);
      return integerVal;
    }
 catch (    NumberFormatException e) {
      java.lang.String genVar3369;
      genVar3369="The value of the UserSetting '";
      java.lang.String genVar3370;
      genVar3370="' could not be parsed to a integer";
      java.lang.String genVar3371;
      genVar3371=genVar3369 + propertyName + genVar3370;
      logger.warning(genVar3371);
    }
    return null;
  }
  /** 
 * Gets the preferred window extension state (normal, maximized)
 * @return
 */
  public Integer getWindowExtensionState(){
    UserSettings genVar3372;
    genVar3372=this;
    java.lang.String genVar3373;
    genVar3373="gui.windowExtendedState";
    java.lang.Integer genVar3374;
    genVar3374=genVar3372.getAsInteger(genVar3373);
    return genVar3374;
  }
  /** 
 * Gets the preferred window extension state (normal, maximized)
 * @return
 */
  public Dimension getWindowSize(){
    UserSettings genVar3375;
    genVar3375=this;
    java.lang.String genVar3376;
    genVar3376="gui.windowHeight";
    Integer height;
    height=genVar3375.getAsInteger(genVar3376);
    UserSettings genVar3377;
    genVar3377=this;
    java.lang.String genVar3378;
    genVar3378="gui.windowWidth";
    Integer width;
    width=genVar3377.getAsInteger(genVar3378);
    boolean genVar3379;
    genVar3379=height != null && width != null;
    if (genVar3379) {
      int genVar3380;
      genVar3380=width.intValue();
      int genVar3381;
      genVar3381=height.intValue();
      java.awt.Dimension genVar3382;
      genVar3382=new Dimension(genVar3380,genVar3381);
      return genVar3382;
    }
 else {
      ;
    }
    return null;
  }
  /** 
 * save the window dimensions
 * @param dimension
 */
  public void setWindowSize(  Dimension dimension){
    boolean genVar3383;
    genVar3383=dimension != null;
    if (genVar3383) {
      java.lang.String genVar3384;
      genVar3384="gui.windowHeight";
      java.lang.String genVar3385;
      genVar3385=Integer.toString(dimension.height);
      properties.setProperty(genVar3384,genVar3385);
      java.lang.String genVar3386;
      genVar3386="gui.windowWidth";
      java.lang.String genVar3387;
      genVar3387=Integer.toString(dimension.width);
      properties.setProperty(genVar3386,genVar3387);
      UserSettings genVar3388;
      genVar3388=this;
      genVar3388.save();
    }
 else {
      ;
    }
  }
  /** 
 * save the preferred window state (normal,maximized)
 * @param state
 */
  public void setWindowExtensionState(  int state){
    UserSettings genVar3389;
    genVar3389=this;
    java.lang.String genVar3390;
    genVar3390="gui.windowExtendedState";
    java.lang.String genVar3391;
    genVar3391=Integer.toString(state);
    genVar3389.setPropertyAndSave(genVar3390,genVar3391);
  }
  /** 
 * Gets the version of the file.
 * @return
 */
  public String getVersion(){
    java.lang.String genVar3392;
    genVar3392="version";
    java.lang.String genVar3393;
    genVar3393=properties.getProperty(genVar3392);
    return genVar3393;
  }
  /** 
 * Sets a property and saves the file.
 * @param key
 * @param value
 */
  private void setPropertyAndSave(  String key,  String value){
    properties.setProperty(key,value);
    UserSettings genVar3394;
    genVar3394=this;
    genVar3394.save();
  }
  /** 
 * Writes the settings to disk.
 */
  public void save(){
    try {
      FileOutputStream outStream;
      outStream=new FileOutputStream(file);
      java.lang.String genVar3395;
      genVar3395="EvolutionChamber user settings file";
      properties.store(outStream,genVar3395);
      outStream.close();
    }
 catch (    IOException e) {
      java.lang.String genVar3396;
      genVar3396="Cannot save user settings.";
      logger.severe(genVar3396);
    }
  }
}
