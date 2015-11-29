package com.fray.evo.util;
import java.util.ArrayList;
import java.util.List;
/** 
 * Creates YABOT build order strings.
 * @author mike.angstadt
 */
public final class EcYabotEncoder {
  /** 
 * Represents a unit, building, or upgrade.
 * @author mike.angstadt
 */
  public static enum Action {  BanelingNest(0,33),   EvolutionChamber(0,34),   Extractor(0,35),   Hatchery(0,36),   HydraliskDen(0,37),   InfestationPit(0,38),   NydusNetwork(0,39),   RoachWarren(0,40),   SpawningPool(0,41),   SpineCrawler(0,42),   GreaterSpire(2,5),   Spire(0,43),   SporeCrawler(0,44),   UltraliskCavern(0,45),   Corruptor(1,27),   Drone(1,28),   Hydralisk(1,29),   Infestor(1,38),   Mutalisk(1,30),   Overlord(1,31),   Queen(1,32),   Roach(1,33),   Ultralisk(1,34),   Zergling(1,035),   Lair(2,3),   Hive(2,4),   BroodLord(2,6),   Baneling(2,7),   Overseer(2,8),   Carapace(3,28),   Melee(3,29),   FlyerAttack(3,31),   FlyerArmor(3,30),   Missile(3,32),   GroovedSpines(3,33),   PneumatizedCarapace(3,34),   GlialReconstitution(3,36),   TunnelingClaws(3,38),   ChitinousPlating(3,40),   AdrenalGlands(3,41),   MetabolicBoost(3,42),   Burrow(3,44),   CentrifugalHooks(3,45),   NeuralParasite(3,49),   PathogenGlands(3,50),   VentralSacs(3,0);   public final int type;
  public final int item;
  private Action(  int type,  int item){
    this.type=type;
    this.item=item;
  }
}
  /** 
 * The name of the build order.
 */
  private String name;
  /** 
 * The name of the person who generated this build order.
 */
  private String author;
  /** 
 * A description of the build order.
 */
  private String description;
  /** 
 * The current YABOT build step.
 */
  private BuildStep curStep=new BuildStep();
  /** 
 * The list of completed YABOT build steps.
 */
  private List<BuildStep> steps=new ArrayList<BuildStep>();
  /** 
 * Constructor.
 * @param name the name of the build order
 * @param author the author of the build order
 * @param description a description for the build order
 */
  public EcYabotEncoder(  String name,  String author,  String description){
    com.fray.evo.util.EcYabotEncoder genVar4627;
    genVar4627=this;
    genVar4627.name=name;
    com.fray.evo.util.EcYabotEncoder genVar4628;
    genVar4628=this;
    genVar4628.author=author;
    com.fray.evo.util.EcYabotEncoder genVar4629;
    genVar4629=this;
    genVar4629.description=description;
  }
  /** 
 * Sets the supply of the current build step.
 * @param supply
 * @return this
 */
  public EcYabotEncoder supply(  int supply){
    curStep.supply=supply;
    com.fray.evo.util.EcYabotEncoder genVar4630;
    genVar4630=this;
    return genVar4630;
  }
  /** 
 * Sets the minerals of the current build step.
 * @param minerals
 * @return this
 */
  public EcYabotEncoder minerals(  int minerals){
    curStep.minerals=minerals;
    com.fray.evo.util.EcYabotEncoder genVar4631;
    genVar4631=this;
    return genVar4631;
  }
  /** 
 * Sets the gas of the current build step.
 * @param gas
 * @return this
 */
  public EcYabotEncoder gas(  int gas){
    curStep.gas=gas;
    com.fray.evo.util.EcYabotEncoder genVar4632;
    genVar4632=this;
    return genVar4632;
  }
  /** 
 * Sets the timestamp of the current build step. Defaults to "0:0".
 * @param timestamp should be in the format "h:m:s". For example, "1:04:32"for one hour, four minutes, thirty-two seconds.
 * @return this
 */
  public EcYabotEncoder timestamp(  String timestamp){
    curStep.timestamp=timestamp;
    com.fray.evo.util.EcYabotEncoder genVar4633;
    genVar4633=this;
    return genVar4633;
  }
  /** 
 * Specifies the unit, building, or upgrade involved in this build step.
 * @param action the unit, building or upgrade
 * @return this
 */
  public EcYabotEncoder action(  Action action){
    curStep.type=action.type;
    curStep.item=action.item;
    com.fray.evo.util.EcYabotEncoder genVar4634;
    genVar4634=this;
    return genVar4634;
  }
  /** 
 * Sets the type number of the current build step. You should use the action() method instead, unless you want to manually set this value yourself.
 * @param type
 * @return this
 */
  public EcYabotEncoder type(  int type){
    curStep.type=type;
    com.fray.evo.util.EcYabotEncoder genVar4635;
    genVar4635=this;
    return genVar4635;
  }
  /** 
 * Sets the item number of the current build step. You should use the action() method instead, unless you want to manually set this value yourself.
 * @param item
 * @return this
 */
  public EcYabotEncoder item(  int item){
    curStep.item=item;
    com.fray.evo.util.EcYabotEncoder genVar4636;
    genVar4636=this;
    return genVar4636;
  }
  /** 
 * Sets whether the current build step is a cancel operation or not. Defaults to false.
 * @param cancel
 * @return this
 */
  public EcYabotEncoder cancel(  boolean cancel){
    curStep.cancel=cancel;
    com.fray.evo.util.EcYabotEncoder genVar4637;
    genVar4637=this;
    return genVar4637;
  }
  /** 
 * Sets the tag of the current build step. Defaults to a single space.
 * @param tag
 * @return this
 */
  public EcYabotEncoder tag(  String tag){
    curStep.tag=tag;
    com.fray.evo.util.EcYabotEncoder genVar4638;
    genVar4638=this;
    return genVar4638;
  }
  /** 
 * Completes the current build step and advances to a new build step.
 * @return this
 */
  public EcYabotEncoder next(){
    steps.add(curStep);
    curStep=new BuildStep();
    com.fray.evo.util.EcYabotEncoder genVar4639;
    genVar4639=this;
    return genVar4639;
  }
  /** 
 * Generates the entire YABOT string. Also resets the encoder so that the same object can be used to generate a new YABOT string.
 * @return the YABOT string
 */
  public String done(){
    EcYabotEncoder genVar4640;
    genVar4640=this;
    String yabot;
    yabot=genVar4640.toString();
    curStep=new BuildStep();
    steps.clear();
    return yabot;
  }
  @Override public String toString(){
    StringBuilder sb;
    sb=new StringBuilder();
    java.lang.String genVar4641;
    genVar4641="1 [i] ";
    java.lang.String genVar4642;
    genVar4642=" | 11 | ";
    java.lang.String genVar4643;
    genVar4643=" | ";
    java.lang.String genVar4644;
    genVar4644=" [/i]";
    java.lang.String genVar4645;
    genVar4645=genVar4641 + name + genVar4642+ author+ genVar4643+ description+ genVar4644;
    sb.append(genVar4645);
    int genVar4646;
    genVar4646=steps.size();
    int genVar4647;
    genVar4647=0;
    boolean genVar4648;
    genVar4648=genVar4646 > genVar4647;
    if (genVar4648) {
      java.lang.String genVar4649;
      genVar4649=" [s] ";
      sb.append(genVar4649);
      int genVar4650;
      genVar4650=0;
      com.fray.evo.util.EcYabotEncoder.BuildStep genVar4651;
      genVar4651=steps.get(genVar4650);
      java.lang.String genVar4652;
      genVar4652=genVar4651.toString();
      sb.append(genVar4652);
      int i=1;
      for (; i < steps.size(); i++) {
        BuildStep entry;
        entry=steps.get(i);
        java.lang.String genVar4653;
        genVar4653=" | ";
        java.lang.String genVar4654;
        genVar4654=entry.toString();
        java.lang.String genVar4655;
        genVar4655=genVar4653 + genVar4654;
        sb.append(genVar4655);
      }
      java.lang.String genVar4656;
      genVar4656=" [/s]";
      sb.append(genVar4656);
    }
 else {
      ;
    }
    java.lang.String genVar4657;
    genVar4657=sb.toString();
    return genVar4657;
  }
  /** 
 * Represents a build order step in a YABOT string.
 * @author mike.angstadt
 */
private static class BuildStep {
    public int supply=0;
    public int minerals=0;
    public int gas=0;
    public String timestamp;
    public int type=0;
    public int item=0;
    public boolean cancel=false;
    public String tag;
    @Override public String toString(){
      StringBuilder sb;
      sb=new StringBuilder();
      sb.append(supply);
      java.lang.String genVar4658;
      genVar4658=" ";
      java.lang.String genVar4659;
      genVar4659=genVar4658 + minerals;
      sb.append(genVar4659);
      java.lang.String genVar4660;
      genVar4660=" ";
      java.lang.String genVar4661;
      genVar4661=genVar4660 + gas;
      sb.append(genVar4661);
      java.lang.String genVar4662;
      genVar4662=" ";
      sb.append(genVar4662);
      boolean genVar4663;
      genVar4663=timestamp == null;
      if (genVar4663) {
        java.lang.String genVar4664;
        genVar4664="0:0";
        sb.append(genVar4664);
      }
 else {
        sb.append(timestamp);
      }
      java.lang.String genVar4665;
      genVar4665=" 1";
      sb.append(genVar4665);
      java.lang.String genVar4666;
      genVar4666=" ";
      java.lang.String genVar4667;
      genVar4667=genVar4666 + type;
      sb.append(genVar4667);
      java.lang.String genVar4668;
      genVar4668=" ";
      java.lang.String genVar4669;
      genVar4669=genVar4668 + item;
      sb.append(genVar4669);
      java.lang.String genVar4670;
      genVar4670=" ";
      java.lang.String genVar4671;
      genVar4671="1";
      java.lang.String genVar4672;
      genVar4672="0";
      java.lang.String genVar4673;
      genVar4673=cancel ? genVar4671 : genVar4672;
      String genVar4674;
      genVar4674=(genVar4673);
      java.lang.String genVar4675;
      genVar4675=genVar4670 + genVar4674;
      sb.append(genVar4675);
      java.lang.String genVar4676;
      genVar4676=" ";
      sb.append(genVar4676);
      boolean genVar4677;
      genVar4677=tag == null;
      if (genVar4677) {
        java.lang.String genVar4678;
        genVar4678=" ";
        sb.append(genVar4678);
      }
 else {
        sb.append(tag);
      }
      java.lang.String genVar4679;
      genVar4679=sb.toString();
      return genVar4679;
    }
  }
}
