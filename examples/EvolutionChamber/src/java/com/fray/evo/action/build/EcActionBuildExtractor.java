package com.fray.evo.action.build;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Building;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.GameLog;
public final class EcActionBuildExtractor extends EcActionBuildBuilding implements Serializable {
  private static final long serialVersionUID=-4936056567243927906L;
  public EcActionBuildExtractor(){
    super(ZergBuildingLibrary.Extractor);
  }
  @Override protected void preExecute(  EcBuildOrder s){
    s.extractorsBuilding++;
  }
  @Override protected void postExecute(  EcBuildOrder s,  GameLog e){
    int genVar1808;
    genVar1808=0;
    boolean genVar1809;
    genVar1809=s.extractorsBuilding == genVar1808;
    if (genVar1809) {
      java.lang.String genVar1810;
      genVar1810="wtf?";
      java.lang.RuntimeException genVar1811;
      genVar1811=new RuntimeException(genVar1810);
      throw genVar1811;
    }
 else {
      ;
    }
    com.fray.evo.util.Building genVar1812;
    genVar1812=(Building)buildable;
    s.addBuilding(genVar1812);
    boolean genVar1813;
    genVar1813=false;
    boolean genVar1814;
    genVar1814=s.settings.pullWorkersFromGas == genVar1813;
    if (genVar1814) {
      s.dronesOnMinerals-=3;
      s.dronesOnGas+=3;
    }
 else {
      ;
    }
    s.extractorsBuilding--;
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    int genVar1815;
    genVar1815=s.getGasExtractors();
    int genVar1816;
    genVar1816=genVar1815 + s.extractorsBuilding;
    int genVar1817;
    genVar1817=s.extractors();
    boolean genVar1818;
    genVar1818=genVar1816 >= genVar1817;
    if (genVar1818) {
      boolean genVar1819;
      genVar1819=true;
      return genVar1819;
    }
 else {
      ;
    }
    boolean genVar1820;
    genVar1820=s.supplyUsed < s.settings.minimumExtractorSupply;
    if (genVar1820) {
      boolean genVar1821;
      genVar1821=true;
      return genVar1821;
    }
 else {
      ;
    }
    boolean genVar1822;
    genVar1822=super.isPossible(s);
    return genVar1822;
  }
}
