package com.fray.evo.action.build;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.Unit;
import com.fray.evo.util.ZergUnitLibrary;
public final class EcActionBuildZergling extends EcActionBuildUnit implements Serializable {
  private static final long serialVersionUID=1284065587938103817L;
  public EcActionBuildZergling(){
    super(ZergUnitLibrary.Zergling);
  }
  @Override protected void postExecute(  EcBuildOrder s,  GameLog e){
    com.fray.evo.util.Unit genVar1837;
    genVar1837=(Unit)buildable;
    int genVar1838;
    genVar1838=2;
    s.addUnits(genVar1837,genVar1838);
  }
  @Override protected void obtainOne(  EcBuildOrder s,  GameLog e){
    boolean genVar1839;
    genVar1839=e.isEnabled();
    if (genVar1839) {
      java.lang.String genVar1840;
      genVar1840=" ";
      EcActionBuildZergling genVar1841;
      genVar1841=this;
      java.lang.String genVar1842;
      genVar1842=genVar1841.getName();
      java.lang.String genVar1843;
      genVar1843=messages.getString(genVar1842);
      java.lang.String genVar1844;
      genVar1844="+2";
      java.lang.String genVar1845;
      genVar1845=genVar1840 + genVar1843 + genVar1844;
      e.printMessage(s,GameLog.MessageType.Obtained,genVar1845);
    }
 else {
      ;
    }
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    int genVar1846;
    genVar1846=s.getSpawningPools();
    int genVar1847;
    genVar1847=0;
    boolean genVar1848;
    genVar1848=genVar1846 == genVar1847;
    if (genVar1848) {
      boolean genVar1849;
      genVar1849=true;
      return genVar1849;
    }
 else {
      ;
    }
    boolean genVar1850;
    genVar1850=s.minerals >= 50 && !s.hasSupply(1);
    if (genVar1850) {
      boolean genVar1851;
      genVar1851=true;
      return genVar1851;
    }
 else {
      ;
    }
    boolean genVar1852;
    genVar1852=false;
    return genVar1852;
  }
}
