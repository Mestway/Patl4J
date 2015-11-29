package com.fray.evo.action.upgrade;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.action.EcActionMakeBuildable;
import com.fray.evo.util.*;
import java.util.ArrayList;
public abstract class EcActionUpgrade extends EcActionMakeBuildable implements Serializable {
  private static final long serialVersionUID=-7775300504786760312L;
  protected Upgrade upgrade;
  public EcActionUpgrade(){
    EcActionUpgrade genVar1975;
    genVar1975=this;
    genVar1975.init();
  }
  @Override public void execute(  final EcBuildOrder s,  final GameLog e){
    EcActionUpgrade genVar1976;
    genVar1976=this;
    s.minerals-=genVar1976.getMinerals();
    EcActionUpgrade genVar1977;
    genVar1977=this;
    s.gas-=genVar1977.getGas();
    com.fray.evo.util.Building genVar1978;
    genVar1978=upgrade.getBuiltIn();
    com.fray.evo.action.upgrade.EcActionUpgrade genVar1979;
    genVar1979=this;
    s.makeBuildingBusy(genVar1978,genVar1979);
    EcActionUpgrade genVar1980;
    genVar1980=this;
    int genVar1981;
    genVar1981=genVar1980.getTime();
    RunnableAction genVar1982;
    genVar1982=new RunnableAction(){
      @Override public void run(      GameLog e){
        if (e.isEnabled())         e.printMessage(s,GameLog.MessageType.Evolved,messages.getString(getName()));
        afterTime(s,e);
      }
    }
;
    s.addFutureAction(genVar1981,genVar1982);
  }
  @Override public boolean isPossible(  EcBuildOrder s){
    EcActionUpgrade genVar1983;
    genVar1983=this;
    int genVar1984;
    genVar1984=genVar1983.getMinerals();
    boolean genVar1985;
    genVar1985=s.minerals < genVar1984;
    if (genVar1985) {
      boolean genVar1986;
      genVar1986=false;
      return genVar1986;
    }
 else {
      ;
    }
    EcActionUpgrade genVar1987;
    genVar1987=this;
    int genVar1988;
    genVar1988=genVar1987.getGas();
    boolean genVar1989;
    genVar1989=s.gas < genVar1988;
    if (genVar1989) {
      boolean genVar1990;
      genVar1990=false;
      return genVar1990;
    }
 else {
      ;
    }
    com.fray.evo.util.Building genVar1991;
    genVar1991=upgrade.getBuiltIn();
    boolean genVar1992;
    genVar1992=s.doesNonBusyExist(genVar1991);
    return genVar1992;
  }
  public abstract void init();
  protected void init(  Upgrade upgrade){
    com.fray.evo.action.upgrade.EcActionUpgrade genVar1993;
    genVar1993=this;
    genVar1993.upgrade=upgrade;
  }
  public void afterTime(  EcBuildOrder s,  GameLog e){
    com.fray.evo.action.upgrade.EcActionUpgrade genVar1994;
    genVar1994=this;
    s.makeBuildingNotBusy(genVar1994);
    EcActionUpgrade genVar1995;
    genVar1995=this;
    genVar1995.superAfterTime(s,e);
  }
  protected void superAfterTime(  EcBuildOrder s,  GameLog e){
    s.addUpgrade(upgrade);
  }
  public int getMinerals(){
    int genVar1996;
    genVar1996=upgrade.getMinerals();
    return genVar1996;
  }
  public int getGas(){
    int genVar1997;
    genVar1997=upgrade.getGas();
    return genVar1997;
  }
  public int getTime(){
    double genVar1998;
    genVar1998=upgrade.getTime();
    int genVar1999;
    genVar1999=(int)genVar1998;
    return genVar1999;
  }
  public String getName(){
    java.lang.String genVar2000;
    genVar2000=upgrade.getName();
    return genVar2000;
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    ArrayList<Buildable> requirements;
    requirements=upgrade.getRequirement();
    int i=0;
    for (; i < requirements.size(); i++) {
      Buildable requirement;
      requirement=requirements.get(i);
      boolean genVar2001;
      genVar2001=requirement instanceof Building && !s.isBuilding((Building)requirement);
      if (genVar2001) {
        boolean genVar2002;
        genVar2002=true;
        return genVar2002;
      }
 else {
        boolean genVar2003;
        genVar2003=requirement instanceof Unit && s.getUnitCount((Unit)requirement) == 0;
        if (genVar2003) {
          boolean genVar2004;
          genVar2004=true;
          return genVar2004;
        }
 else {
          boolean genVar2005;
          genVar2005=requirement instanceof Upgrade && !s.isUpgrade((Upgrade)requirement);
          if (genVar2005) {
            boolean genVar2006;
            genVar2006=true;
            return genVar2006;
          }
 else {
            ;
          }
        }
      }
    }
    com.fray.evo.util.Building genVar2007;
    genVar2007=upgrade.getBuiltIn();
    boolean genVar2008;
    genVar2008=s.isBuilding(genVar2007);
    boolean genVar2009;
    genVar2009=!genVar2008;
    if (genVar2009) {
      boolean genVar2010;
      genVar2010=true;
      return genVar2010;
    }
 else {
      ;
    }
    boolean genVar2011;
    genVar2011=s.isUpgrade(upgrade);
    if (genVar2011) {
      boolean genVar2012;
      genVar2012=true;
      return genVar2012;
    }
 else {
      ;
    }
    boolean genVar2013;
    genVar2013=false;
    return genVar2013;
  }
  @Override public String toString(){
    java.lang.String genVar2014;
    genVar2014=upgrade.getName();
    java.lang.String genVar2015;
    genVar2015=".upgrade";
    java.lang.String genVar2016;
    genVar2016=genVar2014 + genVar2015;
    java.lang.String genVar2017;
    genVar2017=messages.getString(genVar2016);
    return genVar2017;
  }
}
