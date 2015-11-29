package com.fray.evo;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Library;
import com.fray.evo.util.Race;
/** 
 * This Class is an alternative to HashMap<T,Integer> and offers better performance than a HashMap, since it uses an int array. It holds an integer for every Buildable <T>.
 * @author dub
 * @param < T > the Buildable type for this collection. For this class to work, only {@link Buildable#getId()} is required to be set correctly
 */
public class BuildableCollection<T extends Buildable> implements Serializable {
  private static final long serialVersionUID=1L;
  protected final int[] arr;
  protected final Race race;
  public BuildableCollection(  Collection<T> buildings,  Race race){
    this(buildings.size(),race);
  }
  public BuildableCollection(  int size,  Race race){
    arr=new int[size];
    this.race=race;
  }
  /** 
 * set the amount of an Buildable
 * @param buildingB
 * @param num
 */
  public void put(  T building,  int num){
    int genVar1241;
    genVar1241=building.getId();
    arr[genVar1241]=num;
  }
  /** 
 * get amount for an Buildable
 * @param building
 * @return
 */
  public int get(  T building){
    int genVar1242;
    genVar1242=building.getId();
    int genVar1243;
    genVar1243=arr[genVar1242];
    return genVar1243;
  }
  /** 
 * check if this Buildable is available in this collection
 * @param building
 * @return
 */
  public boolean containsKey(  T building){
    int genVar1244;
    genVar1244=building.getId();
    int genVar1245;
    genVar1245=arr[genVar1244];
    int genVar1246;
    genVar1246=0;
    boolean genVar1247;
    genVar1247=genVar1245 != genVar1246;
    boolean genVar1248;
    genVar1248=(genVar1247);
    return genVar1248;
  }
  /** 
 * get amount for the Buildable that corresponds to                                                 {@link Buildable#getId()}
 * @param id
 * @return
 */
  public int getById(  int id){
    int genVar1249;
    genVar1249=arr[id];
    return genVar1249;
  }
  /** 
 * @return the list size
 */
  public int getSize(){
    return arr.length;
  }
  /** 
 * calculates the total amount over all Buildables
 * @return
 */
  public int getCount(){
    int result;
    result=0;
    int i=0;
    for (; i < arr.length; i++) {
      result+=arr[i];
    }
    return result;
  }
  /** 
 * returns the content as a HashMap. The Library must match the items and race of this collection
 * @param library
 * @return
 */
  public HashMap<T,Integer> toHashMap(  Library<T> library){
    HashMap<Integer,T> idToItemMap;
    idToItemMap=library.getIdToItemMap();
    HashMap<T,Integer> result;
    result=new HashMap<T,Integer>();
    int i=0;
    for (; i < arr.length; i++) {
      T genVar1250;
      genVar1250=idToItemMap.get(i);
      int genVar1251;
      genVar1251=arr[i];
      result.put(genVar1250,genVar1251);
    }
    return result;
  }
  /** 
 * set an amount by id. The id must correspond to                                                 {@link Buildable#getId()}
 * @param id
 * @param count
 */
  public void putById(  int id,  int count){
    arr[id]=count;
  }
  /** 
 * clone this collection.
 */
  @Override public BuildableCollection<T> clone(){
    BuildableCollection<T> result;
    result=new BuildableCollection<T>(arr.length,race);
    int i=0;
    for (; i < arr.length; i++) {
      int genVar1252;
      genVar1252=arr[i];
      result.putById(i,genVar1252);
    }
    return result;
  }
}
