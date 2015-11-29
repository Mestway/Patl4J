package com.fray.evo.action.build;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Building;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.GameLog;
public final class EcActionBuildHive extends EcActionBuildBuilding implements Serializable {
  private static final long serialVersionUID=527262025964328976L;
  public EcActionBuildHive(){
    super(ZergBuildingLibrary.Hive);
  }
  @Override protected void preExecute(  EcBuildOrder s){
    com.fray.evo.util.Buildable genVar1827;
    genVar1827=buildable.getConsumes();
    com.fray.evo.util.Building genVar1828;
    genVar1828=(Building)genVar1827;
    com.fray.evo.action.build.EcActionBuildHive genVar1829;
    genVar1829=this;
    s.makeBuildingBusy(genVar1828,genVar1829);
  }
  @Override protected void postExecute(  EcBuildOrder s,  GameLog e){
    com.fray.evo.action.build.EcActionBuildHive genVar1830;
    genVar1830=this;
    s.makeBuildingNotBusy(genVar1830);
    s.removeBuilding(ZergBuildingLibrary.Lair);
    com.fray.evo.util.Building genVar1831;
    genVar1831=(Building)buildable;
    s.addBuilding(genVar1831);
  }
}
