package com.fray.evo.action.build;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Building;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.GameLog;
public final class EcActionBuildGreaterSpire extends EcActionBuildBuilding implements Serializable {
  private static final long serialVersionUID=-3977353805525588023L;
  public EcActionBuildGreaterSpire(){
    super(ZergBuildingLibrary.GreaterSpire);
  }
  @Override protected void preExecute(  EcBuildOrder s){
    com.fray.evo.util.Buildable genVar1699;
    genVar1699=buildable.getConsumes();
    com.fray.evo.util.Building genVar1700;
    genVar1700=(Building)genVar1699;
    com.fray.evo.action.build.EcActionBuildGreaterSpire genVar1701;
    genVar1701=this;
    s.makeBuildingBusy(genVar1700,genVar1701);
  }
  @Override protected void postExecute(  EcBuildOrder s,  GameLog e){
    com.fray.evo.action.build.EcActionBuildGreaterSpire genVar1702;
    genVar1702=this;
    s.makeBuildingNotBusy(genVar1702);
    com.fray.evo.util.Building genVar1703;
    genVar1703=(Building)buildable;
    s.addBuilding(genVar1703);
  }
}
