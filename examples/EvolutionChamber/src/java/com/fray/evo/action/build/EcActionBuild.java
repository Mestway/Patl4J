package com.fray.evo.action.build;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.action.EcActionMakeBuildable;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.GameLog;
public abstract class EcActionBuild extends EcActionMakeBuildable implements Serializable {
  private static final long serialVersionUID=3929615537949650115L;
  protected Buildable buildable;
  public EcActionBuild(  Buildable buildable){
    com.fray.evo.action.build.EcActionBuild genVar1896;
    genVar1896=this;
    genVar1896.buildable=buildable;
  }
  protected boolean isPossibleResources(  EcBuildOrder s){
    EcActionBuild genVar1897;
    genVar1897=this;
    int genVar1898;
    genVar1898=genVar1897.getMinerals();
    boolean genVar1899;
    genVar1899=s.minerals < genVar1898;
    if (genVar1899) {
      boolean genVar1900;
      genVar1900=false;
      return genVar1900;
    }
 else {
      ;
    }
    EcActionBuild genVar1901;
    genVar1901=this;
    int genVar1902;
    genVar1902=genVar1901.getGas();
    boolean genVar1903;
    genVar1903=s.gas < genVar1902;
    if (genVar1903) {
      boolean genVar1904;
      genVar1904=false;
      return genVar1904;
    }
 else {
      ;
    }
    boolean genVar1905;
    genVar1905=true;
    return genVar1905;
  }
  protected void obtainOne(  EcBuildOrder s,  GameLog e){
    boolean genVar1906;
    genVar1906=e.isEnabled();
    if (genVar1906) {
      java.lang.String genVar1907;
      genVar1907=" ";
      EcActionBuild genVar1908;
      genVar1908=this;
      java.lang.String genVar1909;
      genVar1909=genVar1908.getName();
      java.lang.String genVar1910;
      genVar1910=messages.getString(genVar1909);
      java.lang.String genVar1911;
      genVar1911="+1";
      java.lang.String genVar1912;
      genVar1912=genVar1907 + genVar1910 + genVar1911;
      e.printMessage(s,GameLog.MessageType.Obtained,genVar1912);
    }
 else {
      ;
    }
  }
  public int getMinerals(){
    int genVar1913;
    genVar1913=buildable.getMinerals();
    return genVar1913;
  }
  public int getGas(){
    int genVar1914;
    genVar1914=buildable.getGas();
    return genVar1914;
  }
  public int getTime(){
    double genVar1915;
    genVar1915=buildable.getTime();
    int genVar1916;
    genVar1916=(int)genVar1915;
    return genVar1916;
  }
  public String getName(){
    java.lang.String genVar1917;
    genVar1917=buildable.getName();
    return genVar1917;
  }
  public Buildable getConsumes(){
    com.fray.evo.util.Buildable genVar1918;
    genVar1918=buildable.getConsumes();
    return genVar1918;
  }
  @Override public String toString(){
    java.lang.String genVar1919;
    genVar1919=buildable.getName();
    java.lang.String genVar1920;
    genVar1920=".build";
    java.lang.String genVar1921;
    genVar1921=genVar1919 + genVar1920;
    java.lang.String genVar1922;
    genVar1922=messages.getString(genVar1921);
    return genVar1922;
  }
}
