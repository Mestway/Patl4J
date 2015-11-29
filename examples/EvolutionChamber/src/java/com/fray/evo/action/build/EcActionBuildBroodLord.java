package com.fray.evo.action.build;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.ZergUnitLibrary;
public final class EcActionBuildBroodLord extends EcActionBuildUnit implements Serializable {
  private static final long serialVersionUID=1L;
  public EcActionBuildBroodLord(){
    super(ZergUnitLibrary.Broodlord);
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    int genVar1942;
    genVar1942=s.getHives();
    int genVar1943;
    genVar1943=0;
    boolean genVar1944;
    genVar1944=genVar1942 == genVar1943;
    if (genVar1944) {
      boolean genVar1945;
      genVar1945=true;
      return genVar1945;
    }
 else {
      ;
    }
    int genVar1946;
    genVar1946=s.getGreaterSpire();
    int genVar1947;
    genVar1947=0;
    boolean genVar1948;
    genVar1948=genVar1946 == genVar1947;
    if (genVar1948) {
      boolean genVar1949;
      genVar1949=true;
      return genVar1949;
    }
 else {
      ;
    }
    int genVar1950;
    genVar1950=2;
    boolean genVar1951;
    genVar1951=s.hasSupply(genVar1950);
    boolean genVar1952;
    genVar1952=!genVar1951;
    if (genVar1952) {
      boolean genVar1953;
      genVar1953=true;
      return genVar1953;
    }
 else {
      ;
    }
    boolean genVar1954;
    genVar1954=super.isInvalid(s);
    return genVar1954;
  }
}
