package com.fray.evo.action.build;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Building;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.GameLog;
public final class EcActionBuildLair extends EcActionBuildBuilding implements Serializable {
  private static final long serialVersionUID=2953922728901731654L;
  public EcActionBuildLair(){
    super(ZergBuildingLibrary.Lair);
  }
  @Override protected void preExecute(  EcBuildOrder s){
    com.fray.evo.util.Buildable genVar1832;
    genVar1832=buildable.getConsumes();
    com.fray.evo.util.Building genVar1833;
    genVar1833=(Building)genVar1832;
    com.fray.evo.action.build.EcActionBuildLair genVar1834;
    genVar1834=this;
    s.makeBuildingBusy(genVar1833,genVar1834);
  }
  @Override protected void postExecute(  EcBuildOrder s,  GameLog e){
    com.fray.evo.action.build.EcActionBuildLair genVar1835;
    genVar1835=this;
    s.makeBuildingNotBusy(genVar1835);
    s.removeBuilding(ZergBuildingLibrary.Hatchery);
    com.fray.evo.util.Building genVar1836;
    genVar1836=(Building)buildable;
    s.addBuilding(genVar1836);
  }
}
