package com.fray.evo.util;
import java.io.Serializable;
import java.util.ArrayList;
/** 
 * Provides the basic information a item like a Unit,Upgrade or Building requires. Items that do not consume anything, the consumes may be set to null.  If the consumable is not null, the consumed costs and time are considered for the full-values (FullCost,FullGas,FullTime)
 * @author Cyrik, bdurrer
 */
public abstract class Buildable implements Serializable {
  private static final long serialVersionUID=1L;
  protected final String name;
  protected final int minerals;
  protected final int gas;
  protected final double time;
  protected final Buildable consumes;
  protected final ArrayList<Buildable> requirements;
  protected final int id;
  /** 
 * @param name
 * @param minerals
 * @param gas
 * @param time
 * @param consumes
 * @param requierments
 * @param id the ID. Is currently only unique for its sort (Unit/Upgrade/Building)
 */
  public Buildable(  String name,  int minerals,  int gas,  double time,  Buildable consumes,  ArrayList<Buildable> requirements,  int id){
    this.name=name;
    this.minerals=minerals;
    this.gas=gas;
    this.time=time;
    this.consumes=consumes;
    this.requirements=requirements;
    this.id=id;
  }
  public int getId(){
    return id;
  }
  public int getMinerals(){
    return minerals;
  }
  public int getGas(){
    return gas;
  }
  public double getTime(){
    return time;
  }
  /** 
 * The i18n property name of the unit/upgrade/building.
 * @return
 */
  public String getName(){
    return name;
  }
  public int getFullMinerals(){
    boolean genVar4348;
    genVar4348=consumes == null;
    if (genVar4348) {
      return minerals;
    }
 else {
      int genVar4349;
      genVar4349=consumes.getFullMinerals();
      int genVar4350;
      genVar4350=genVar4349 + minerals;
      return genVar4350;
    }
  }
  public int getFullGas(){
    boolean genVar4351;
    genVar4351=consumes == null;
    if (genVar4351) {
      return gas;
    }
 else {
      int genVar4352;
      genVar4352=consumes.getFullGas();
      int genVar4353;
      genVar4353=genVar4352 + gas;
      return genVar4353;
    }
  }
  public double getFullTime(){
    boolean genVar4354;
    genVar4354=consumes == null;
    if (genVar4354) {
      return time;
    }
 else {
      double genVar4355;
      genVar4355=consumes.getFullTime();
      double genVar4356;
      genVar4356=genVar4355 + time;
      return genVar4356;
    }
  }
  public Buildable getConsumes(){
    return consumes;
  }
  public ArrayList<Buildable> getRequirement(){
    return requirements;
  }
  /** 
 * @return the hashCode built out of the item's name
 */
  public int hashCode(){
    int genVar4357;
    genVar4357=name.hashCode();
    return genVar4357;
  }
  /** 
 * A Buildable A is the same as B if 				<br/> a) it is of the same subclass (runtime class)	<br/> b) it has the same name
 * @param obj object to compare
 * @return true if both are considered as equals
 */
  public boolean equals(  Object obj){
    boolean genVar4358;
    genVar4358=obj == null;
    if (genVar4358) {
      boolean genVar4359;
      genVar4359=false;
      return genVar4359;
    }
 else {
      ;
    }
    Buildable genVar4360;
    genVar4360=this;
    java.lang.Class genVar4361;
    genVar4361=genVar4360.getClass();
    java.lang.Class genVar4362;
    genVar4362=obj.getClass();
    boolean genVar4363;
    genVar4363=genVar4361 != genVar4362;
    if (genVar4363) {
      boolean genVar4364;
      genVar4364=false;
      return genVar4364;
    }
 else {
      ;
    }
    Buildable other;
    other=(Buildable)obj;
    com.fray.evo.util.Buildable genVar4365;
    genVar4365=this;
    java.lang.String genVar4366;
    genVar4366=genVar4365.name;
    boolean genVar4367;
    genVar4367=genVar4366 == null;
    boolean genVar4368;
    genVar4368=(genVar4367);
    boolean genVar4369;
    genVar4369=other.name != null;
    boolean genVar4370;
    genVar4370=(genVar4369);
    com.fray.evo.util.Buildable genVar4371;
    genVar4371=this;
    java.lang.String genVar4372;
    genVar4372=genVar4371.name;
    boolean genVar4373;
    genVar4373=genVar4372.equals(other.name);
    boolean genVar4374;
    genVar4374=!genVar4373;
    boolean genVar4375;
    genVar4375=genVar4368 ? genVar4370 : genVar4374;
    if (genVar4375) {
      boolean genVar4376;
      genVar4376=false;
      return genVar4376;
    }
 else {
      ;
    }
    boolean genVar4377;
    genVar4377=true;
    return genVar4377;
  }
}
