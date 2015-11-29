package com.fray.evo.action.build;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.ZergUnitLibrary;
public final class EcActionBuildHatchery extends EcActionBuildBuilding implements Serializable {
  private static final long serialVersionUID=265935963741996160L;
  public EcActionBuildHatchery(){
    super(ZergBuildingLibrary.Hatchery);
  }
  @Override protected void preExecute(  EcBuildOrder s){
    s.hatcheriesBuilding+=1;
    EcActionBuildHatchery genVar1923;
    genVar1923=this;
    int genVar1924;
    genVar1924=genVar1923.getTime();
    double genVar1925;
    genVar1925=ZergBuildingLibrary.Extractor.getTime();
    double genVar1926;
    genVar1926=genVar1924 - genVar1925;
    double genVar1927;
    genVar1927=(genVar1926);
    int genVar1928;
    genVar1928=(int)genVar1927;
    RunnableAction genVar1929;
    genVar1929=new RunnableAction(){
      @Override public void run(      GameLog e){
      }
    }
;
    s.addFutureAction(genVar1928,genVar1929);
    EcActionBuildHatchery genVar1930;
    genVar1930=this;
    int genVar1931;
    genVar1931=genVar1930.getTime();
    double genVar1932;
    genVar1932=ZergUnitLibrary.Queen.getTime();
    double genVar1933;
    genVar1933=genVar1931 - genVar1932;
    double genVar1934;
    genVar1934=(genVar1933);
    int genVar1935;
    genVar1935=(int)genVar1934;
    RunnableAction genVar1936;
    genVar1936=new RunnableAction(){
      @Override public void run(      GameLog e){
      }
    }
;
    s.addFutureAction(genVar1935,genVar1936);
  }
  @Override protected void postExecute(  EcBuildOrder s,  GameLog e){
    s.addBuilding(ZergBuildingLibrary.Hatchery);
    s.hatcheriesBuilding-=1;
    s.hatcheryTimes.add(s.seconds);
    int genVar1937;
    genVar1937=1;
    s.larva.add(genVar1937);
    int genVar1938;
    genVar1938=1;
    s.larvaProduction.add(genVar1938);
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    boolean genVar1939;
    genVar1939=s.supplyUsed < s.settings.minimumHatcherySupply;
    if (genVar1939) {
      boolean genVar1940;
      genVar1940=true;
      return genVar1940;
    }
 else {
      ;
    }
    boolean genVar1941;
    genVar1941=super.isPossible(s);
    return genVar1941;
  }
}
