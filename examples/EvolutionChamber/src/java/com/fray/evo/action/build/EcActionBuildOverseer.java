package com.fray.evo.action.build;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.ZergUnitLibrary;
public final class EcActionBuildOverseer extends EcActionBuildUnit implements Serializable {
  private static final long serialVersionUID=-5927839271180382939L;
  public EcActionBuildOverseer(){
    super(ZergUnitLibrary.Overseer);
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    boolean genVar1893;
    genVar1893=s.getLairs() == 0 && s.getHives() == 0;
    if (genVar1893) {
      boolean genVar1894;
      genVar1894=true;
      return genVar1894;
    }
 else {
      ;
    }
    boolean genVar1895;
    genVar1895=false;
    return genVar1895;
  }
}
