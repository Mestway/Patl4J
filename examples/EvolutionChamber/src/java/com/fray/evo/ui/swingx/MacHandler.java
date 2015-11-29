package com.fray.evo.ui.swingx;
/** 
 * Handles various events specific to the Mac OSX environment.
 * @author mike.angstadt
 */
public abstract class MacHandler {
  private boolean aboutOverriden=true;
  /** 
 * Handles the event of the user selecting the "Quit" option from the Mac menu bar.
 * @param applicationEvent the com.apple.eawt.ApplicationEvent object thataccompanies the event
 */
  public void handleQuit(  Object applicationEvent){
  }
  /** 
 * Used for internal purposes. Override                                            {@link handleAbout} instead.
 * @param applicationEvent the com.apple.eawt.ApplicationEvent object thataccompanies the event
 */
  public final void internalHandleAbout(  Object applicationEvent){
    MacHandler genVar3705;
    genVar3705=this;
    genVar3705.handleAbout(applicationEvent);
    if (aboutOverriden) {
      try {
        java.lang.Class genVar3706;
        genVar3706=applicationEvent.getClass();
        java.lang.String genVar3707;
        genVar3707="setHandled";
        java.lang.Class<java.lang.Boolean> genVar3708;
        genVar3708=boolean.class;
        java.lang.reflect.Method genVar3709;
        genVar3709=genVar3706.getMethod(genVar3707,genVar3708);
        boolean genVar3710;
        genVar3710=true;
        genVar3709.invoke(applicationEvent,genVar3710);
      }
 catch (      Exception e) {
      }
    }
 else {
      ;
    }
  }
  /** 
 * Handles the event of the user selecting the "About" option from the Mac menu bar.
 * @param applicationEvent the com.apple.eawt.ApplicationEvent object thataccompanies the event
 */
  public void handleAbout(  Object applicationEvent){
    aboutOverriden=false;
  }
  /** 
 * Handles the event of the user selecting the "Preferences" option from the Mac menu bar.
 * @param applicationEvent the com.apple.eawt.ApplicationEvent object thataccompanies the event
 */
  public void handlePreferences(  Object applicationEvent){
  }
}
