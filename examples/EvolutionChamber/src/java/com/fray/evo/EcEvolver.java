package com.fray.evo;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.EcActionExtractorTrick;
import com.fray.evo.action.EcActionMineGas;
import com.fray.evo.action.EcActionMineMineral;
import com.fray.evo.action.EcActionWait;
import com.fray.evo.action.build.EcActionBuildBaneling;
import com.fray.evo.action.build.EcActionBuildBanelingNest;
import com.fray.evo.action.build.EcActionBuildBroodLord;
import com.fray.evo.action.build.EcActionBuildCorruptor;
import com.fray.evo.action.build.EcActionBuildDrone;
import com.fray.evo.action.build.EcActionBuildEvolutionChamber;
import com.fray.evo.action.build.EcActionBuildExtractor;
import com.fray.evo.action.build.EcActionBuildGreaterSpire;
import com.fray.evo.action.build.EcActionBuildHatchery;
import com.fray.evo.action.build.EcActionBuildHive;
import com.fray.evo.action.build.EcActionBuildHydralisk;
import com.fray.evo.action.build.EcActionBuildHydraliskDen;
import com.fray.evo.action.build.EcActionBuildInfestationPit;
import com.fray.evo.action.build.EcActionBuildInfestor;
import com.fray.evo.action.build.EcActionBuildLair;
import com.fray.evo.action.build.EcActionBuildMutalisk;
import com.fray.evo.action.build.EcActionBuildNydusNetwork;
import com.fray.evo.action.build.EcActionBuildOverlord;
import com.fray.evo.action.build.EcActionBuildOverseer;
import com.fray.evo.action.build.EcActionBuildQueen;
import com.fray.evo.action.build.EcActionBuildRoach;
import com.fray.evo.action.build.EcActionBuildRoachWarren;
import com.fray.evo.action.build.EcActionBuildSpawningPool;
import com.fray.evo.action.build.EcActionBuildSpineCrawler;
import com.fray.evo.action.build.EcActionBuildSpire;
import com.fray.evo.action.build.EcActionBuildSporeCrawler;
import com.fray.evo.action.build.EcActionBuildUltralisk;
import com.fray.evo.action.build.EcActionBuildUltraliskCavern;
import com.fray.evo.action.build.EcActionBuildZergling;
import com.fray.evo.action.upgrade.EcActionUpgradeAdrenalGlands;
import com.fray.evo.action.upgrade.EcActionUpgradeBurrow;
import com.fray.evo.action.upgrade.EcActionUpgradeCarapace1;
import com.fray.evo.action.upgrade.EcActionUpgradeCarapace2;
import com.fray.evo.action.upgrade.EcActionUpgradeCarapace3;
import com.fray.evo.action.upgrade.EcActionUpgradeCentrifugalHooks;
import com.fray.evo.action.upgrade.EcActionUpgradeChitinousPlating;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor1;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor2;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor3;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks1;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks2;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks3;
import com.fray.evo.action.upgrade.EcActionUpgradeGlialReconstitution;
import com.fray.evo.action.upgrade.EcActionUpgradeGroovedSpines;
import com.fray.evo.action.upgrade.EcActionUpgradeMelee1;
import com.fray.evo.action.upgrade.EcActionUpgradeMelee2;
import com.fray.evo.action.upgrade.EcActionUpgradeMelee3;
import com.fray.evo.action.upgrade.EcActionUpgradeMetabolicBoost;
import com.fray.evo.action.upgrade.EcActionUpgradeMissile1;
import com.fray.evo.action.upgrade.EcActionUpgradeMissile2;
import com.fray.evo.action.upgrade.EcActionUpgradeMissile3;
import com.fray.evo.action.upgrade.EcActionUpgradeNeuralParasite;
import com.fray.evo.action.upgrade.EcActionUpgradePathogenGlands;
import com.fray.evo.action.upgrade.EcActionUpgradePneumatizedCarapace;
import com.fray.evo.action.upgrade.EcActionUpgradeTunnelingClaws;
import com.fray.evo.action.upgrade.EcActionUpgradeVentralSacs;
import com.fray.evo.util.EcUtil;
import com.fray.evo.util.EcYabotEncoder;
import com.fray.evo.util.GameLog;
public final class EcEvolver extends FitnessFunction {
  /** 
 * serialVersionUID - Changes to the structure of EcEvolver 1 - The log attribute was changed to transient
 */
  private static final long serialVersionUID=1L;
  private static final Logger logger=Logger.getLogger(EcEvolver.class.getName());
  EcState source;
  private EcState destination;
  private EcState mergedDestination;
  private transient GameLog log;
  private long evaluations=0;
  private final List<Class<? extends EcAction>> actions;
  /** 
 * Maps Evolution Chamber classes with the appropriate action class of the YABOT encoder.
 */
  private static final Map<Class<? extends EcAction>,EcYabotEncoder.Action> yabotMapping;
static {
    Map<Class<? extends EcAction>,EcYabotEncoder.Action> m;
    m=new HashMap<Class<? extends EcAction>,EcYabotEncoder.Action>();
    java.lang.Class<com.fray.evo.action.build.EcActionBuildBanelingNest> genVar1005;
    genVar1005=EcActionBuildBanelingNest.class;
    m.put(genVar1005,EcYabotEncoder.Action.BanelingNest);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildEvolutionChamber> genVar1006;
    genVar1006=EcActionBuildEvolutionChamber.class;
    m.put(genVar1006,EcYabotEncoder.Action.EvolutionChamber);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildExtractor> genVar1007;
    genVar1007=EcActionBuildExtractor.class;
    m.put(genVar1007,EcYabotEncoder.Action.Extractor);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildHatchery> genVar1008;
    genVar1008=EcActionBuildHatchery.class;
    m.put(genVar1008,EcYabotEncoder.Action.Hatchery);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildHydraliskDen> genVar1009;
    genVar1009=EcActionBuildHydraliskDen.class;
    m.put(genVar1009,EcYabotEncoder.Action.HydraliskDen);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildInfestationPit> genVar1010;
    genVar1010=EcActionBuildInfestationPit.class;
    m.put(genVar1010,EcYabotEncoder.Action.InfestationPit);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildNydusNetwork> genVar1011;
    genVar1011=EcActionBuildNydusNetwork.class;
    m.put(genVar1011,EcYabotEncoder.Action.NydusNetwork);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildRoachWarren> genVar1012;
    genVar1012=EcActionBuildRoachWarren.class;
    m.put(genVar1012,EcYabotEncoder.Action.RoachWarren);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildSpawningPool> genVar1013;
    genVar1013=EcActionBuildSpawningPool.class;
    m.put(genVar1013,EcYabotEncoder.Action.SpawningPool);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildSpineCrawler> genVar1014;
    genVar1014=EcActionBuildSpineCrawler.class;
    m.put(genVar1014,EcYabotEncoder.Action.SpineCrawler);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildGreaterSpire> genVar1015;
    genVar1015=EcActionBuildGreaterSpire.class;
    m.put(genVar1015,EcYabotEncoder.Action.GreaterSpire);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildSpire> genVar1016;
    genVar1016=EcActionBuildSpire.class;
    m.put(genVar1016,EcYabotEncoder.Action.Spire);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildSporeCrawler> genVar1017;
    genVar1017=EcActionBuildSporeCrawler.class;
    m.put(genVar1017,EcYabotEncoder.Action.SporeCrawler);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildUltraliskCavern> genVar1018;
    genVar1018=EcActionBuildUltraliskCavern.class;
    m.put(genVar1018,EcYabotEncoder.Action.UltraliskCavern);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildCorruptor> genVar1019;
    genVar1019=EcActionBuildCorruptor.class;
    m.put(genVar1019,EcYabotEncoder.Action.Corruptor);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildDrone> genVar1020;
    genVar1020=EcActionBuildDrone.class;
    m.put(genVar1020,EcYabotEncoder.Action.Drone);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildHydralisk> genVar1021;
    genVar1021=EcActionBuildHydralisk.class;
    m.put(genVar1021,EcYabotEncoder.Action.Hydralisk);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildInfestor> genVar1022;
    genVar1022=EcActionBuildInfestor.class;
    m.put(genVar1022,EcYabotEncoder.Action.Infestor);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildMutalisk> genVar1023;
    genVar1023=EcActionBuildMutalisk.class;
    m.put(genVar1023,EcYabotEncoder.Action.Mutalisk);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildOverlord> genVar1024;
    genVar1024=EcActionBuildOverlord.class;
    m.put(genVar1024,EcYabotEncoder.Action.Overlord);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildQueen> genVar1025;
    genVar1025=EcActionBuildQueen.class;
    m.put(genVar1025,EcYabotEncoder.Action.Queen);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildRoach> genVar1026;
    genVar1026=EcActionBuildRoach.class;
    m.put(genVar1026,EcYabotEncoder.Action.Roach);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildUltralisk> genVar1027;
    genVar1027=EcActionBuildUltralisk.class;
    m.put(genVar1027,EcYabotEncoder.Action.Ultralisk);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildZergling> genVar1028;
    genVar1028=EcActionBuildZergling.class;
    m.put(genVar1028,EcYabotEncoder.Action.Zergling);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildLair> genVar1029;
    genVar1029=EcActionBuildLair.class;
    m.put(genVar1029,EcYabotEncoder.Action.Lair);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildHive> genVar1030;
    genVar1030=EcActionBuildHive.class;
    m.put(genVar1030,EcYabotEncoder.Action.Hive);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildBroodLord> genVar1031;
    genVar1031=EcActionBuildBroodLord.class;
    m.put(genVar1031,EcYabotEncoder.Action.BroodLord);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildBaneling> genVar1032;
    genVar1032=EcActionBuildBaneling.class;
    m.put(genVar1032,EcYabotEncoder.Action.Baneling);
    java.lang.Class<com.fray.evo.action.build.EcActionBuildOverseer> genVar1033;
    genVar1033=EcActionBuildOverseer.class;
    m.put(genVar1033,EcYabotEncoder.Action.Overseer);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeCarapace1> genVar1034;
    genVar1034=EcActionUpgradeCarapace1.class;
    m.put(genVar1034,EcYabotEncoder.Action.Carapace);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeCarapace2> genVar1035;
    genVar1035=EcActionUpgradeCarapace2.class;
    m.put(genVar1035,EcYabotEncoder.Action.Carapace);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeCarapace3> genVar1036;
    genVar1036=EcActionUpgradeCarapace3.class;
    m.put(genVar1036,EcYabotEncoder.Action.Carapace);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeMelee1> genVar1037;
    genVar1037=EcActionUpgradeMelee1.class;
    m.put(genVar1037,EcYabotEncoder.Action.Melee);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeMelee2> genVar1038;
    genVar1038=EcActionUpgradeMelee2.class;
    m.put(genVar1038,EcYabotEncoder.Action.Melee);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeMelee3> genVar1039;
    genVar1039=EcActionUpgradeMelee3.class;
    m.put(genVar1039,EcYabotEncoder.Action.Melee);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks1> genVar1040;
    genVar1040=EcActionUpgradeFlyerAttacks1.class;
    m.put(genVar1040,EcYabotEncoder.Action.FlyerAttack);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks2> genVar1041;
    genVar1041=EcActionUpgradeFlyerAttacks2.class;
    m.put(genVar1041,EcYabotEncoder.Action.FlyerAttack);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks3> genVar1042;
    genVar1042=EcActionUpgradeFlyerAttacks3.class;
    m.put(genVar1042,EcYabotEncoder.Action.FlyerAttack);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor1> genVar1043;
    genVar1043=EcActionUpgradeFlyerArmor1.class;
    m.put(genVar1043,EcYabotEncoder.Action.FlyerArmor);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor2> genVar1044;
    genVar1044=EcActionUpgradeFlyerArmor2.class;
    m.put(genVar1044,EcYabotEncoder.Action.FlyerArmor);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor3> genVar1045;
    genVar1045=EcActionUpgradeFlyerArmor3.class;
    m.put(genVar1045,EcYabotEncoder.Action.FlyerArmor);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeMissile1> genVar1046;
    genVar1046=EcActionUpgradeMissile1.class;
    m.put(genVar1046,EcYabotEncoder.Action.Missile);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeMissile2> genVar1047;
    genVar1047=EcActionUpgradeMissile2.class;
    m.put(genVar1047,EcYabotEncoder.Action.Missile);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeMissile3> genVar1048;
    genVar1048=EcActionUpgradeMissile3.class;
    m.put(genVar1048,EcYabotEncoder.Action.Missile);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeGroovedSpines> genVar1049;
    genVar1049=EcActionUpgradeGroovedSpines.class;
    m.put(genVar1049,EcYabotEncoder.Action.GroovedSpines);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradePneumatizedCarapace> genVar1050;
    genVar1050=EcActionUpgradePneumatizedCarapace.class;
    m.put(genVar1050,EcYabotEncoder.Action.PneumatizedCarapace);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeGlialReconstitution> genVar1051;
    genVar1051=EcActionUpgradeGlialReconstitution.class;
    m.put(genVar1051,EcYabotEncoder.Action.GlialReconstitution);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeTunnelingClaws> genVar1052;
    genVar1052=EcActionUpgradeTunnelingClaws.class;
    m.put(genVar1052,EcYabotEncoder.Action.TunnelingClaws);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeChitinousPlating> genVar1053;
    genVar1053=EcActionUpgradeChitinousPlating.class;
    m.put(genVar1053,EcYabotEncoder.Action.ChitinousPlating);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeAdrenalGlands> genVar1054;
    genVar1054=EcActionUpgradeAdrenalGlands.class;
    m.put(genVar1054,EcYabotEncoder.Action.AdrenalGlands);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeMetabolicBoost> genVar1055;
    genVar1055=EcActionUpgradeMetabolicBoost.class;
    m.put(genVar1055,EcYabotEncoder.Action.MetabolicBoost);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeBurrow> genVar1056;
    genVar1056=EcActionUpgradeBurrow.class;
    m.put(genVar1056,EcYabotEncoder.Action.Burrow);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeCentrifugalHooks> genVar1057;
    genVar1057=EcActionUpgradeCentrifugalHooks.class;
    m.put(genVar1057,EcYabotEncoder.Action.CentrifugalHooks);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeNeuralParasite> genVar1058;
    genVar1058=EcActionUpgradeNeuralParasite.class;
    m.put(genVar1058,EcYabotEncoder.Action.NeuralParasite);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradePathogenGlands> genVar1059;
    genVar1059=EcActionUpgradePathogenGlands.class;
    m.put(genVar1059,EcYabotEncoder.Action.PathogenGlands);
    java.lang.Class<com.fray.evo.action.upgrade.EcActionUpgradeVentralSacs> genVar1060;
    genVar1060=EcActionUpgradeVentralSacs.class;
    m.put(genVar1060,EcYabotEncoder.Action.VentralSacs);
    yabotMapping=Collections.unmodifiableMap(m);
  }
  public EcEvolver(  EcState source,  EcState destination,  List<Class<? extends EcAction>> actions){
    com.fray.evo.EcEvolver genVar1061;
    genVar1061=this;
    genVar1061.source=source;
    com.fray.evo.EcEvolver genVar1062;
    genVar1062=this;
    genVar1062.destination=destination;
    com.fray.evo.EcEvolver genVar1063;
    genVar1063=this;
    genVar1063.mergedDestination=destination.getMergedState();
    com.fray.evo.EcEvolver genVar1064;
    genVar1064=this;
    genVar1064.log=new GameLog();
    this.actions=actions;
  }
  protected String getAlleleAsString(  IChromosome c){
    StringBuilder sb;
    sb=new StringBuilder();
    org.jgap.Gene[] genVar1066;
    genVar1066=c.getGenes();
    for (    Gene g : genVar1066) {
      java.lang.Object genVar1067;
      genVar1067=g.getAllele();
      java.lang.Integer genVar1068;
      genVar1068=(Integer)genVar1067;
      Integer genVar1069;
      genVar1069=(genVar1068);
      int genVar1070;
      genVar1070=genVar1069.intValue();
      int genVar1071;
      genVar1071=10;
      boolean genVar1072;
      genVar1072=genVar1070 >= genVar1071;
      if (genVar1072) {
        char genVar1073;
        genVar1073='a';
        int genVar1074;
        genVar1074=(int)genVar1073;
        java.lang.Object genVar1075;
        genVar1075=g.getAllele();
        java.lang.Integer genVar1076;
        genVar1076=(Integer)genVar1075;
        int genVar1077;
        genVar1077=genVar1074 + genVar1076;
        int genVar1078;
        genVar1078=10;
        int genVar1079;
        genVar1079=genVar1077 - genVar1078;
        int genVar1080;
        genVar1080=(genVar1079);
        char genVar1081;
        genVar1081=(char)genVar1080;
        char genVar1082;
        genVar1082=(genVar1081);
        sb.append(genVar1082);
      }
 else {
        java.lang.Object genVar1083;
        genVar1083=g.getAllele();
        java.lang.String genVar1084;
        genVar1084=genVar1083.toString();
        sb.append(genVar1084);
      }
    }
    java.lang.String genVar1085;
    genVar1085=sb.toString();
    return genVar1085;
  }
  @Override protected double evaluate(  IChromosome chromosome){
    EcBuildOrder s;
    try {
      Double score;
      evaluations++;
      EcEvolver genVar1086;
      genVar1086=this;
      com.fray.evo.EcBuildOrder genVar1087;
      genVar1087=(EcBuildOrder)source;
      s=genVar1086.populateBuildOrder(genVar1087,chromosome,actions);
      EcEvolver genVar1088;
      genVar1088=this;
      com.fray.evo.EcBuildOrder genVar1089;
      genVar1089=genVar1088.doEvaluate(s);
      score=destination.score(genVar1089);
      return score;
    }
 catch (    CloneNotSupportedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar1090;
      genVar1090=new PrintWriter(sw);
      e.printStackTrace(genVar1090);
      java.lang.String genVar1091;
      genVar1091=sw.toString();
      logger.severe(genVar1091);
    }
    return Double.NEGATIVE_INFINITY;
  }
  public EcState evaluateGetBuildOrder(  IChromosome chromosome){
    EcBuildOrder s;
    try {
      EcEvolver genVar1092;
      genVar1092=this;
      com.fray.evo.EcBuildOrder genVar1093;
      genVar1093=(EcBuildOrder)source;
      s=genVar1092.populateBuildOrder(genVar1093,chromosome,actions);
      EcEvolver genVar1094;
      genVar1094=this;
      com.fray.evo.EcBuildOrder genVar1095;
      genVar1095=genVar1094.doEvaluate(s);
      return genVar1095;
    }
 catch (    CloneNotSupportedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar1096;
      genVar1096=new PrintWriter(sw);
      e.printStackTrace(genVar1096);
      java.lang.String genVar1097;
      genVar1097=sw.toString();
      logger.severe(genVar1097);
    }
    return null;
  }
  public String getBuildOrder(  IChromosome chromosome){
    EcBuildOrder s;
    try {
      EcEvolver genVar1098;
      genVar1098=this;
      com.fray.evo.EcBuildOrder genVar1099;
      genVar1099=(EcBuildOrder)source;
      s=genVar1098.populateBuildOrder(genVar1099,chromosome,actions);
      EcEvolver genVar1100;
      genVar1100=this;
      java.lang.String genVar1101;
      genVar1101=genVar1100.doSimpleEvaluate(s);
      return genVar1101;
    }
 catch (    CloneNotSupportedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar1102;
      genVar1102=new PrintWriter(sw);
      e.printStackTrace(genVar1102);
      java.lang.String genVar1103;
      genVar1103=sw.toString();
      logger.severe(genVar1103);
      java.lang.String genVar1104;
      genVar1104="";
      return genVar1104;
    }
  }
  public String doSimpleEvaluate(  EcBuildOrder s){
    StringBuilder sb;
    sb=new StringBuilder();
    java.util.ArrayList<com.fray.evo.action.EcAction> genVar1105;
    genVar1105=s.getActions();
    for (    EcAction a : genVar1105) {
      boolean genVar1106;
      genVar1106=a.isInvalid(s);
      if (genVar1106) {
        continue;
      }
 else {
        ;
      }
      while (!a.canExecute(s,log).can) {
        boolean genVar1107;
        genVar1107=s.seconds >= s.targetSeconds;
        boolean genVar1108;
        genVar1108=destination.waypointMissed(s);
        boolean genVar1109;
        genVar1109=genVar1107 || genVar1108;
        if (genVar1109) {
          java.lang.String genVar1110;
          genVar1110="NoFinishedBuildYet";
          java.lang.String genVar1111;
          genVar1111=messages.getString(genVar1110);
          return genVar1111;
        }
 else {
          ;
        }
        com.fray.evo.EcState genVar1112;
        genVar1112=destination.getMergedWaypoints();
        boolean genVar1113;
        genVar1113=genVar1112.isSatisfied(s);
        if (genVar1113) {
          java.lang.String genVar1114;
          genVar1114=sb.toString();
          return genVar1114;
        }
 else {
          ;
        }
      }
      boolean genVar1115;
      genVar1115=!(a instanceof EcActionWait) && !(a instanceof EcActionBuildDrone);
      if (genVar1115) {
        int genVar1116;
        genVar1116=(int)s.supplyUsed;
        java.lang.String genVar1117;
        genVar1117="  ";
        java.lang.String genVar1118;
        genVar1118=a.toBuildOrderString(s);
        java.lang.String genVar1119;
        genVar1119="\tM:";
        int genVar1120;
        genVar1120=(int)s.minerals;
        java.lang.String genVar1121;
        genVar1121="\tG:";
        int genVar1122;
        genVar1122=(int)s.gas;
        java.lang.String genVar1123;
        genVar1123="\n";
        java.lang.String genVar1124;
        genVar1124=genVar1116 + genVar1117 + genVar1118+ genVar1119+ genVar1120+ genVar1121+ genVar1122+ genVar1123;
        sb.append(genVar1124);
      }
 else {
        ;
      }
      a.execute(s,log);
    }
    java.lang.String genVar1125;
    genVar1125="RanOutOfThingsToDo";
    java.lang.String genVar1126;
    genVar1126=messages.getString(genVar1125);
    return genVar1126;
  }
  public String getYabotBuildOrder(  IChromosome chromosome){
    EcBuildOrder s;
    try {
      EcEvolver genVar1127;
      genVar1127=this;
      com.fray.evo.EcBuildOrder genVar1128;
      genVar1128=(EcBuildOrder)source;
      s=genVar1127.populateBuildOrder(genVar1128,chromosome,actions);
      EcEvolver genVar1129;
      genVar1129=this;
      java.lang.String genVar1130;
      genVar1130=genVar1129.doYABOTEvaluate(s);
      return genVar1130;
    }
 catch (    CloneNotSupportedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar1131;
      genVar1131=new PrintWriter(sw);
      e.printStackTrace(genVar1131);
      java.lang.String genVar1132;
      genVar1132=sw.toString();
      logger.severe(genVar1132);
      java.lang.String genVar1133;
      genVar1133="";
      return genVar1133;
    }
  }
  public String doYABOTEvaluate(  EcBuildOrder s){
    java.lang.String genVar1134;
    genVar1134="EC Optimized Build";
    java.lang.String genVar1135;
    genVar1135="EvolutionChamber";
    java.lang.String genVar1136;
    genVar1136="Add description here please.";
    EcYabotEncoder encoder;
    encoder=new EcYabotEncoder(genVar1134,genVar1135,genVar1136);
    ArrayList<String> warnings;
    warnings=new ArrayList<String>();
    java.util.ArrayList<com.fray.evo.action.EcAction> genVar1137;
    genVar1137=s.getActions();
    for (    EcAction a : genVar1137) {
      boolean genVar1138;
      genVar1138=a.isInvalid(s);
      if (genVar1138) {
        continue;
      }
 else {
        ;
      }
      while (!a.canExecute(s,log).can) {
        boolean genVar1139;
        genVar1139=s.seconds >= s.targetSeconds;
        boolean genVar1140;
        genVar1140=destination.waypointMissed(s);
        boolean genVar1141;
        genVar1141=genVar1139 || genVar1140;
        if (genVar1141) {
          java.lang.String genVar1142;
          genVar1142="NoFinishedBuildYet";
          java.lang.String genVar1143;
          genVar1143=messages.getString(genVar1142);
          java.lang.String genVar1144;
          genVar1144="\n";
          java.lang.String genVar1145;
          genVar1145=EcUtil.toString(warnings);
          java.lang.String genVar1146;
          genVar1146=genVar1143 + genVar1144 + genVar1145;
          return genVar1146;
        }
 else {
          ;
        }
        com.fray.evo.EcState genVar1147;
        genVar1147=destination.getMergedWaypoints();
        boolean genVar1148;
        genVar1148=genVar1147.isSatisfied(s);
        if (genVar1148) {
          String yabot;
          yabot=encoder.done();
          int max;
          max=770;
          int genVar1149;
          genVar1149=yabot.length();
          boolean genVar1150;
          genVar1150=genVar1149 > max;
          if (genVar1150) {
            java.lang.String genVar1151;
            genVar1151="\nBuild was too long. Please trim it by ";
            int genVar1152;
            genVar1152=yabot.length();
            int genVar1153;
            genVar1153=genVar1152 - max;
            int genVar1154;
            genVar1154=(genVar1153);
            java.lang.String genVar1155;
            genVar1155=" characters or try a new build.";
            yabot+=genVar1151 + genVar1154 + genVar1155;
            yabot+="\nThis YABOT string will not work until you fix this!";
          }
 else {
            ;
          }
          java.lang.String genVar1156;
          genVar1156="\n";
          java.lang.String genVar1157;
          genVar1157=EcUtil.toString(warnings);
          java.lang.String genVar1158;
          genVar1158=yabot + genVar1156 + genVar1157;
          return genVar1158;
        }
 else {
          ;
        }
      }
      boolean genVar1159;
      genVar1159=!(a instanceof EcActionWait) && !(a instanceof EcActionBuildDrone);
      if (genVar1159) {
        boolean genVar1160;
        genVar1160=a instanceof EcActionExtractorTrick;
        boolean genVar1161;
        genVar1161=(genVar1160);
        boolean genVar1162;
        genVar1162=!genVar1161;
        if (genVar1162) {
          boolean genVar1163;
          genVar1163=a instanceof EcActionMineGas;
          if (genVar1163) {
            java.lang.String genVar1164;
            genVar1164="Add_3_drones_to_gas";
            java.lang.String genVar1165;
            genVar1165="Add_1_drone_to_gas";
            String tag;
            tag=s.settings.pullThreeWorkersOnly ? genVar1164 : genVar1165;
            encoder.tag(tag);
          }
 else {
            boolean genVar1166;
            genVar1166=a instanceof EcActionMineMineral;
            if (genVar1166) {
              java.lang.String genVar1167;
              genVar1167="Add_3_drones_to_minerals";
              java.lang.String genVar1168;
              genVar1168="Add_1_drone_to_minerals";
              String tag;
              tag=s.settings.pullThreeWorkersOnly ? genVar1167 : genVar1168;
              encoder.tag(tag);
            }
 else {
              java.lang.Class genVar1169;
              genVar1169=a.getClass();
              EcYabotEncoder.Action yabotAction;
              yabotAction=yabotMapping.get(genVar1169);
              boolean genVar1170;
              genVar1170=yabotAction != null;
              if (genVar1170) {
                encoder.action(yabotAction);
              }
 else {
                java.lang.String genVar1171;
                genVar1171="YABOT action not found for '";
                java.lang.Class genVar1172;
                genVar1172=a.getClass();
                java.lang.String genVar1173;
                genVar1173=genVar1172.getName();
                java.lang.String genVar1174;
                genVar1174="'.";
                java.lang.String genVar1175;
                genVar1175=genVar1171 + genVar1173 + genVar1174;
                warnings.add(genVar1175);
              }
            }
          }
          int genVar1176;
          genVar1176=(int)s.supplyUsed;
          com.fray.evo.util.EcYabotEncoder genVar1177;
          genVar1177=encoder.supply(genVar1176);
          int genVar1178;
          genVar1178=(int)s.minerals;
          com.fray.evo.util.EcYabotEncoder genVar1179;
          genVar1179=genVar1177.minerals(genVar1178);
          int genVar1180;
          genVar1180=(int)s.gas;
          com.fray.evo.util.EcYabotEncoder genVar1181;
          genVar1181=genVar1179.gas(genVar1180);
          java.lang.String genVar1182;
          genVar1182=s.timestamp();
          com.fray.evo.util.EcYabotEncoder genVar1183;
          genVar1183=genVar1181.timestamp(genVar1182);
          genVar1183.next();
        }
 else {
          int genVar1184;
          genVar1184=(int)s.supplyUsed;
          com.fray.evo.util.EcYabotEncoder genVar1185;
          genVar1185=encoder.supply(genVar1184);
          int genVar1186;
          genVar1186=(int)s.minerals;
          com.fray.evo.util.EcYabotEncoder genVar1187;
          genVar1187=genVar1185.minerals(genVar1186);
          int genVar1188;
          genVar1188=(int)s.gas;
          com.fray.evo.util.EcYabotEncoder genVar1189;
          genVar1189=genVar1187.gas(genVar1188);
          java.lang.String genVar1190;
          genVar1190=s.timestamp();
          com.fray.evo.util.EcYabotEncoder genVar1191;
          genVar1191=genVar1189.timestamp(genVar1190);
          com.fray.evo.util.EcYabotEncoder genVar1192;
          genVar1192=genVar1191.action(EcYabotEncoder.Action.Extractor);
          java.lang.String genVar1193;
          genVar1193="Extractor_Trick";
          com.fray.evo.util.EcYabotEncoder genVar1194;
          genVar1194=genVar1192.tag(genVar1193);
          genVar1194.next();
          int genVar1195;
          genVar1195=(int)s.supplyUsed;
          com.fray.evo.util.EcYabotEncoder genVar1196;
          genVar1196=encoder.supply(genVar1195);
          int genVar1197;
          genVar1197=(int)s.minerals;
          com.fray.evo.util.EcYabotEncoder genVar1198;
          genVar1198=genVar1196.minerals(genVar1197);
          int genVar1199;
          genVar1199=(int)s.gas;
          com.fray.evo.util.EcYabotEncoder genVar1200;
          genVar1200=genVar1198.gas(genVar1199);
          int genVar1201;
          genVar1201=3;
          java.lang.String genVar1202;
          genVar1202=s.timestampIncremented(genVar1201);
          com.fray.evo.util.EcYabotEncoder genVar1203;
          genVar1203=genVar1200.timestamp(genVar1202);
          com.fray.evo.util.EcYabotEncoder genVar1204;
          genVar1204=genVar1203.action(EcYabotEncoder.Action.Extractor);
          boolean genVar1205;
          genVar1205=true;
          com.fray.evo.util.EcYabotEncoder genVar1206;
          genVar1206=genVar1204.cancel(genVar1205);
          java.lang.String genVar1207;
          genVar1207="Extractor_Trick";
          com.fray.evo.util.EcYabotEncoder genVar1208;
          genVar1208=genVar1206.tag(genVar1207);
          genVar1208.next();
        }
      }
 else {
        ;
      }
      a.execute(s,log);
    }
    java.lang.String genVar1209;
    genVar1209="RanOutOfThingsToDo";
    java.lang.String genVar1210;
    genVar1210=messages.getString(genVar1209);
    java.lang.String genVar1211;
    genVar1211="\n";
    java.lang.String genVar1212;
    genVar1212=EcUtil.toString(warnings);
    java.lang.String genVar1213;
    genVar1213=genVar1210 + genVar1211 + genVar1212;
    return genVar1213;
  }
  public static EcBuildOrder populateBuildOrder(  EcBuildOrder source,  IChromosome arg0,  List<Class<? extends EcAction>> requiredActions) throws CloneNotSupportedException {
    EcBuildOrder s;
    s=source.clone();
    org.jgap.Gene[] genVar1214;
    genVar1214=arg0.getGenes();
    for (    Gene g1 : genVar1214) {
      IntegerGene g;
      g=(IntegerGene)g1;
      java.lang.Object genVar1215;
      genVar1215=g.getAllele();
      Integer i;
      i=(Integer)genVar1215;
      try {
        java.lang.Class genVar1216;
        genVar1216=requiredActions.get(i);
        EcAction genVar1217;
        genVar1217=(EcAction)genVar1216.newInstance();
        com.fray.evo.action.EcAction genVar1218;
        genVar1218=(EcAction)genVar1217;
        s.addAction(genVar1218);
      }
 catch (      InstantiationException e) {
        StringWriter sw;
        sw=new StringWriter();
        java.io.PrintWriter genVar1219;
        genVar1219=new PrintWriter(sw);
        e.printStackTrace(genVar1219);
        java.lang.String genVar1220;
        genVar1220=sw.toString();
        logger.severe(genVar1220);
      }
catch (      IllegalAccessException e) {
        StringWriter sw;
        sw=new StringWriter();
        java.io.PrintWriter genVar1221;
        genVar1221=new PrintWriter(sw);
        e.printStackTrace(genVar1221);
        java.lang.String genVar1222;
        genVar1222=sw.toString();
        logger.severe(genVar1222);
      }
    }
    return s;
  }
  public EcBuildOrder doEvaluate(  EcBuildOrder s){
    int i;
    i=0;
    ArrayList<EcAction> actions;
    actions=s.getActions();
    int c=0;
    for (; c < actions.size(); ++c) {
      EcAction a;
      a=actions.get(c);
      i++;
      boolean genVar1223;
      genVar1223=a.isInvalid(s);
      if (genVar1223) {
        s.invalidActions++;
        continue;
      }
 else {
        ;
      }
      EcAction.CanExecuteResult canExecute;
      while (!(canExecute=a.canExecute(s,log)).can) {
        boolean genVar1224;
        genVar1224=s.seconds > s.targetSeconds;
        boolean genVar1225;
        genVar1225=destination.waypointMissed(s);
        boolean genVar1226;
        genVar1226=genVar1224 || genVar1225;
        if (genVar1226) {
          boolean genVar1227;
          genVar1227=s.settings.overDrone && s.getDrones() < s.getOverDrones(s);
          if (genVar1227) {
            log.printFailure(GameLog.FailReason.OverDrone,mergedDestination,s);
          }
 else {
            log.printFailure(GameLog.FailReason.Waypoint,mergedDestination,s);
          }
          return s;
        }
 else {
          ;
        }
        int waypointIndex;
        waypointIndex=destination.getCurrWaypointIndex(s);
        boolean genVar1228;
        genVar1228=waypointIndex != -1 && destination.getWaypointActions(waypointIndex) > 0;
        if (genVar1228) {
          log.printWaypoint(waypointIndex,s);
        }
 else {
          ;
        }
        if (canExecute.somethingChanged) {
          com.fray.evo.EcState genVar1229;
          genVar1229=destination.getMergedWaypoints();
          boolean genVar1230;
          genVar1230=genVar1229.isSatisfied(s);
          if (genVar1230) {
            int genVar1231;
            genVar1231=i - s.invalidActions;
            log.printSatisfied(genVar1231,s,mergedDestination);
            return s;
          }
 else {
            ;
          }
        }
 else {
          ;
        }
      }
      boolean genVar1232;
      genVar1232=a instanceof EcActionWait;
      boolean genVar1233;
      genVar1233=(genVar1232);
      boolean genVar1234;
      genVar1234=!genVar1233;
      if (genVar1234) {
        log.printAction(s,a);
      }
 else {
        ;
      }
      a.execute(s,log);
    }
    log.printFailure(GameLog.FailReason.OutOfActions,s,null);
    return s;
  }
  public void enableLogging(  boolean log){
    com.fray.evo.EcEvolver genVar1235;
    genVar1235=this;
    com.fray.evo.util.GameLog genVar1236;
    genVar1236=genVar1235.log;
    genVar1236.setEnabled(log);
  }
  public void setLoggingStream(  PrintStream stream){
    com.fray.evo.EcEvolver genVar1237;
    genVar1237=this;
    com.fray.evo.util.GameLog genVar1238;
    genVar1238=genVar1237.log;
    genVar1238.setPrintStream(stream);
  }
  public EcState getDestination(){
    return destination;
  }
  public long getEvaluations(){
    return evaluations;
  }
}
