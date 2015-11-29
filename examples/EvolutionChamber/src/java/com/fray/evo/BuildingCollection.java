package com.fray.evo;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import com.fray.evo.util.Building;
import com.fray.evo.util.Race;
import com.fray.evo.util.RaceLibraries;
/** 
 * Deperecated, use BuildableCollection instead
 * @author Cyrik
 */
@Deprecated public final class BuildingCollection extends BuildableCollection<Building> implements Serializable {
  private static final long serialVersionUID=7576950287259854976L;
  public BuildingCollection(  Collection<Building> buildings,  Race race){
    this(buildings.size(),race);
  }
  public BuildingCollection(  int size,  Race race){
    super(size,race);
  }
  public HashMap<Building,Integer> toHashMap(){
    com.fray.evo.util.Library<com.fray.evo.util.Building> genVar1254;
    genVar1254=RaceLibraries.getBuildingLibrary(race);
    java.util.HashMap<com.fray.evo.util.Building,java.lang.Integer> genVar1255;
    genVar1255=super.toHashMap(genVar1254);
    return genVar1255;
  }
  @Override public BuildingCollection clone(){
    BuildingCollection result;
    result=new BuildingCollection(arr.length,race);
    int i=0;
    for (; i < arr.length; i++) {
      int genVar1256;
      genVar1256=arr[i];
      result.putById(i,genVar1256);
    }
    return result;
  }
}
