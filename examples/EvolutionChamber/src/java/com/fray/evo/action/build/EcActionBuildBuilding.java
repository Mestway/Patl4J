package com.fray.evo.action.build;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.Unit;
import com.fray.evo.util.ZergUnitLibrary;
import com.fray.evo.util.Upgrade;
import java.util.ArrayList;
public abstract class EcActionBuildBuilding extends EcActionBuild implements Serializable {
  private static final long serialVersionUID=6761289110026332078L;
  public EcActionBuildBuilding(  Building building){
    super(building);
  }
  @Override public void execute(  final EcBuildOrder s,  final GameLog e){
    EcActionBuildBuilding genVar1853;
    genVar1853=this;
    s.minerals-=genVar1853.getMinerals();
    EcActionBuildBuilding genVar1854;
    genVar1854=this;
    s.gas-=genVar1854.getGas();
    EcActionBuildBuilding genVar1855;
    genVar1855=this;
    com.fray.evo.util.Buildable genVar1856;
    genVar1856=genVar1855.getConsumes();
    boolean genVar1857;
    genVar1857=genVar1856 == ZergUnitLibrary.Drone;
    if (genVar1857) {
      int genVar1858;
      genVar1858=1;
      s.removeUnits(ZergUnitLibrary.Drone,genVar1858);
      s.dronesOnMinerals-=1;
      s.supplyUsed-=1;
    }
 else {
      ;
    }
    EcActionBuildBuilding genVar1859;
    genVar1859=this;
    genVar1859.preExecute(s);
    EcActionBuildBuilding genVar1860;
    genVar1860=this;
    int genVar1861;
    genVar1861=genVar1860.getTime();
    RunnableAction genVar1862;
    genVar1862=new RunnableAction(){
      @Override public void run(      GameLog e){
        obtainOne(s,e);
        postExecute(s,e);
      }
    }
;
    s.addFutureAction(genVar1861,genVar1862);
  }
  protected void preExecute(  EcBuildOrder s){
  }
  @Override public boolean isPossible(  EcBuildOrder s){
    EcActionBuildBuilding genVar1863;
    genVar1863=this;
    com.fray.evo.util.Buildable genVar1864;
    genVar1864=genVar1863.getConsumes();
    boolean genVar1865;
    genVar1865=genVar1864 == ZergUnitLibrary.Drone;
    if (genVar1865) {
      int genVar1866;
      genVar1866=s.getDrones();
      int genVar1867;
      genVar1867=1;
      boolean genVar1868;
      genVar1868=genVar1866 < genVar1867;
      if (genVar1868) {
        boolean genVar1869;
        genVar1869=false;
        return genVar1869;
      }
 else {
        ;
      }
    }
 else {
      EcActionBuildBuilding genVar1870;
      genVar1870=this;
      com.fray.evo.util.Buildable genVar1871;
      genVar1871=genVar1870.getConsumes();
      boolean genVar1872;
      genVar1872=genVar1871 instanceof Building;
      if (genVar1872) {
        com.fray.evo.util.Buildable genVar1873;
        genVar1873=buildable.getConsumes();
        com.fray.evo.util.Building genVar1874;
        genVar1874=(Building)genVar1873;
        boolean genVar1875;
        genVar1875=s.doesNonBusyReallyExist(genVar1874);
        boolean genVar1876;
        genVar1876=!genVar1875;
        if (genVar1876) {
          boolean genVar1877;
          genVar1877=false;
          return genVar1877;
        }
 else {
          ;
        }
      }
 else {
        ;
      }
    }
    EcActionBuildBuilding genVar1878;
    genVar1878=this;
    boolean genVar1879;
    genVar1879=genVar1878.isPossibleResources(s);
    return genVar1879;
  }
  protected void postExecute(  EcBuildOrder s,  GameLog e){
    com.fray.evo.util.Building genVar1880;
    genVar1880=(Building)buildable;
    s.addBuilding(genVar1880);
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    ArrayList<Buildable> requirements;
    requirements=buildable.getRequirement();
    int i=0;
    for (; i < requirements.size(); i++) {
      Buildable requirement;
      requirement=requirements.get(i);
      boolean genVar1881;
      genVar1881=requirement instanceof Building && !s.isBuilding((Building)requirement);
      if (genVar1881) {
        boolean genVar1882;
        genVar1882=true;
        return genVar1882;
      }
 else {
        boolean genVar1883;
        genVar1883=requirement instanceof Unit && s.getUnitCount((Unit)requirement) == 0;
        if (genVar1883) {
          boolean genVar1884;
          genVar1884=true;
          return genVar1884;
        }
 else {
          boolean genVar1885;
          genVar1885=requirement instanceof Upgrade && !s.isUpgrade((Upgrade)requirement);
          if (genVar1885) {
            boolean genVar1886;
            genVar1886=true;
            return genVar1886;
          }
 else {
            ;
          }
        }
      }
    }
    Buildable consumes;
    consumes=buildable.getConsumes();
    boolean genVar1887;
    genVar1887=consumes instanceof Building && s.getBuildingCount((Building)consumes) == 0;
    if (genVar1887) {
      boolean genVar1888;
      genVar1888=true;
      return genVar1888;
    }
 else {
      boolean genVar1889;
      genVar1889=consumes instanceof Unit && s.getUnitCount((Unit)consumes) == 0;
      if (genVar1889) {
        boolean genVar1890;
        genVar1890=true;
        return genVar1890;
      }
 else {
        ;
      }
    }
    boolean genVar1891;
    genVar1891=false;
    return genVar1891;
  }
}
