package com.fray.evo;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import com.fray.evo.util.Race;
import com.fray.evo.util.RaceLibraries;
import com.fray.evo.util.Unit;
/** 
 * Deperecated, use BuildableCollection instead
 * @author Cyrik
 */
@Deprecated public final class UnitCollection extends BuildableCollection<Unit> implements Serializable {
  private static final long serialVersionUID=4544549226705001596L;
  public UnitCollection(  Collection<Unit> units,  Race race){
    this(units.size(),race);
  }
  public UnitCollection(  int size,  Race race){
    super(size,race);
  }
  public HashMap<Unit,Integer> toHashMap(){
    com.fray.evo.util.Library<com.fray.evo.util.Unit> genVar1434;
    genVar1434=RaceLibraries.getUnitLibrary(race);
    java.util.HashMap<com.fray.evo.util.Unit,java.lang.Integer> genVar1435;
    genVar1435=super.toHashMap(genVar1434);
    return genVar1435;
  }
  @Override public UnitCollection clone(){
    UnitCollection result;
    result=new UnitCollection(arr.length,race);
    int i=0;
    for (; i < arr.length; i++) {
      int genVar1436;
      genVar1436=arr[i];
      result.putById(i,genVar1436);
    }
    return result;
  }
}
