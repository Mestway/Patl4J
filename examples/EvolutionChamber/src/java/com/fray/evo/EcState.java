package com.fray.evo;
import com.fray.evo.util.*;
import com.fray.evo.util.optimization.ArrayListInt;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
public class EcState implements Serializable {
  private static final long serialVersionUID=8913147157413079020L;
  private static final Logger logger=Logger.getLogger(EcState.class.getName());
  public EcSettings settings=new EcSettings();
  protected HashSet<Upgrade> upgrades;
  protected BuildableCollection<Building> buildings;
  protected BuildableCollection<Unit> units;
  public EcState(){
    int genVar238;
    genVar238=0;
    hatcheryTimes.add(genVar238);
    int genVar239;
    genVar239=3;
    larva.add(genVar239);
    int genVar240;
    genVar240=1;
    larvaProduction.add(genVar240);
    com.fray.evo.util.Library<com.fray.evo.util.Unit> genVar241;
    genVar241=RaceLibraries.getUnitLibrary(settings.race);
    java.util.ArrayList<com.fray.evo.util.Unit> genVar242;
    genVar242=genVar241.getList();
    units=new BuildableCollection<Unit>(genVar242,settings.race);
    com.fray.evo.util.Library<com.fray.evo.util.Building> genVar243;
    genVar243=RaceLibraries.getBuildingLibrary(settings.race);
    ArrayList<Building> allBuildingsList;
    allBuildingsList=genVar243.getList();
    buildings=new BuildableCollection<Building>(allBuildingsList,settings.race);
    for (    Building bldg : allBuildingsList) {
      int genVar244;
      genVar244=0;
      buildings.put(bldg,genVar244);
    }
    upgrades=new HashSet<Upgrade>();
    int genVar245;
    genVar245=6;
    units.put(ZergUnitLibrary.Drone,genVar245);
    int genVar246;
    genVar246=1;
    units.put(ZergUnitLibrary.Overlord,genVar246);
    int genVar247;
    genVar247=1;
    buildings.put(ZergBuildingLibrary.Hatchery,genVar247);
  }
  public double preTimeScore=0.0;
  public double timeBonus=0.0;
  public ArrayListInt larva=new ArrayListInt();
  public ArrayList<Boolean> hasQueen=new ArrayList<Boolean>();
  public ArrayListInt larvaProduction=new ArrayListInt();
  public double minerals=50;
  public double gas=0;
  public double supplyUsed=6;
  public int requiredBases=1;
  public int scoutDrone=0;
  public int seconds=0;
  public int targetSeconds=0;
  public int invalidActions=0;
  public double actionLength=0;
  public int waits;
  public int maxOverDrones=50;
  public int overDroneEfficiency=80;
  public ArrayListInt hatcheryTimes=new ArrayListInt();
  public ArrayList<EcState> waypoints=new ArrayList<EcState>();
  public EcState mergedWaypoints=null;
  public double totalMineralsMined=0;
  @Override public Object clone() throws CloneNotSupportedException {
    EcState s;
    s=new EcState();
    EcState genVar248;
    genVar248=this;
    genVar248.assign(s);
    return s;
  }
  protected void assign(  EcState s){
    for (    EcState st : waypoints) {
      try {
        java.lang.Object genVar249;
        genVar249=st.clone();
        com.fray.evo.EcState genVar250;
        genVar250=(EcState)genVar249;
        s.waypoints.add(genVar250);
      }
 catch (      CloneNotSupportedException e) {
        StringWriter sw;
        sw=new StringWriter();
        java.io.PrintWriter genVar251;
        genVar251=new PrintWriter(sw);
        e.printStackTrace(genVar251);
        java.lang.String genVar252;
        genVar252=sw.toString();
        logger.severe(genVar252);
      }
    }
    try {
      boolean genVar253;
      genVar253=mergedWaypoints == null;
      if (genVar253) {
        s.mergedWaypoints=null;
      }
 else {
        java.lang.Object genVar254;
        genVar254=mergedWaypoints.clone();
        s.mergedWaypoints=(EcState)genVar254;
      }
    }
 catch (    CloneNotSupportedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar255;
      genVar255=new PrintWriter(sw);
      e.printStackTrace(genVar255);
      java.lang.String genVar256;
      genVar256=sw.toString();
      logger.severe(genVar256);
    }
    s.settings=settings;
    s.minerals=minerals;
    s.gas=gas;
    s.supplyUsed=supplyUsed;
    s.requiredBases=requiredBases;
    s.scoutDrone=scoutDrone;
    s.buildings=buildings.clone();
    java.lang.Object genVar257;
    genVar257=upgrades.clone();
    s.upgrades=(HashSet<Upgrade>)genVar257;
    s.units=units.clone();
    s.seconds=seconds;
    s.targetSeconds=targetSeconds;
    s.invalidActions=invalidActions;
    s.actionLength=actionLength;
    s.totalMineralsMined=totalMineralsMined;
  }
  public int supply(){
    int genVar258;
    genVar258=units.get(ZergUnitLibrary.Overlord);
    int genVar259;
    genVar259=units.get(ZergUnitLibrary.Overseer);
    int genVar260;
    genVar260=genVar258 + genVar259;
    int genVar261;
    genVar261=(genVar260);
    int genVar262;
    genVar262=8;
    int genVar263;
    genVar263=genVar261 * genVar262;
    int genVar264;
    genVar264=2;
    EcState genVar265;
    genVar265=this;
    int genVar266;
    genVar266=genVar265.bases();
    int genVar267;
    genVar267=genVar264 * genVar266;
    int genVar268;
    genVar268=genVar263 + genVar267;
    int genVar269;
    genVar269=200;
    int genVar270;
    genVar270=Math.min(genVar268,genVar269);
    return genVar270;
  }
  public static EcState defaultDestination(){
    EcState d;
    d=new EcState();
    int genVar271;
    genVar271=0;
    d.setUnits(ZergUnitLibrary.Drone,genVar271);
    int genVar272;
    genVar272=0;
    d.setUnits(ZergUnitLibrary.Overlord,genVar272);
    int genVar273;
    genVar273=0;
    d.setBuilding(ZergBuildingLibrary.Hatchery,genVar273);
    int genVar274;
    genVar274=60;
    int genVar275;
    genVar275=120;
    d.targetSeconds=genVar274 * genVar275;
    return d;
  }
  public double score(  EcState candidate){
    com.fray.evo.fitness.EcFitness genVar276;
    genVar276=settings.getFitnessFunction();
    com.fray.evo.EcState genVar277;
    genVar277=this;
    double genVar278;
    genVar278=genVar276.score(candidate,genVar277);
    return genVar278;
  }
  public void union(  EcState s){
    boolean genVar279;
    genVar279=s.requiredBases > requiredBases;
    if (genVar279) {
      requiredBases=s.requiredBases;
    }
 else {
      ;
    }
    EcState genVar280;
    genVar280=this;
    units=genVar280.unionUnits(units,s.units);
    int genVar281;
    genVar281=buildings.getSize();
    BuildableCollection<Building> temp;
    temp=new BuildableCollection<Building>(genVar281,settings.race);
    int i=0;
    for (; i < buildings.getSize(); i++) {
      int genVar282;
      genVar282=buildings.getById(i);
      int genVar283;
      genVar283=s.buildings.getById(i);
      int genVar284;
      genVar284=Math.max(genVar282,genVar283);
      temp.putById(i,genVar284);
    }
    buildings=temp;
    upgrades.addAll(s.upgrades);
  }
  private BuildableCollection<Unit> unionUnits(  BuildableCollection<Unit> map,  BuildableCollection<Unit> s){
    int genVar285;
    genVar285=units.getSize();
    BuildableCollection<Unit> result;
    result=new BuildableCollection<Unit>(genVar285,settings.race);
    int i=0;
    for (; i < map.getSize(); i++) {
      int genVar286;
      genVar286=map.getById(i);
      int genVar287;
      genVar287=s.getById(i);
      int genVar288;
      genVar288=Math.max(genVar286,genVar287);
      result.putById(i,genVar288);
    }
    return result;
  }
  public boolean isSatisfied(  EcState candidate){
    int genVar289;
    genVar289=waypoints.size();
    int genVar290;
    genVar290=0;
    boolean genVar291;
    genVar291=genVar289 > genVar290;
    if (genVar291) {
      boolean genVar292;
      genVar292=mergedWaypoints == null;
      if (genVar292) {
        EcState genVar293;
        genVar293=this;
        mergedWaypoints=genVar293.getMergedState();
      }
 else {
        ;
      }
      boolean genVar294;
      genVar294=mergedWaypoints.isSatisfied(candidate);
      return genVar294;
    }
 else {
      mergedWaypoints=null;
    }
    int i=0;
    for (; i < units.getSize(); i++) {
      boolean genVar295;
      genVar295=i == 0 && units.race.equals(Race.Zerg);
      if (genVar295) {
        continue;
      }
 else {
        ;
      }
      int genVar296;
      genVar296=candidate.units.getById(i);
      int genVar297;
      genVar297=units.getById(i);
      boolean genVar298;
      genVar298=genVar296 < genVar297;
      if (genVar298) {
        boolean genVar299;
        genVar299=false;
        return genVar299;
      }
 else {
        ;
      }
    }
    int genVar300;
    genVar300=candidate.bases();
    boolean genVar301;
    genVar301=genVar300 < requiredBases;
    if (genVar301) {
      boolean genVar302;
      genVar302=false;
      return genVar302;
    }
 else {
      ;
    }
    i=0;
    for (; i < buildings.getSize(); i++) {
      int genVar303;
      genVar303=candidate.buildings.getById(i);
      int genVar304;
      genVar304=buildings.getById(i);
      boolean genVar305;
      genVar305=genVar303 < genVar304;
      if (genVar305) {
        boolean genVar306;
        genVar306=false;
        return genVar306;
      }
 else {
        ;
      }
    }
    boolean genVar307;
    genVar307=candidate.upgrades.containsAll(upgrades);
    boolean genVar308;
    genVar308=!genVar307;
    if (genVar308) {
      boolean genVar309;
      genVar309=false;
      return genVar309;
    }
 else {
      ;
    }
    boolean genVar310;
    genVar310=candidate.settings.overDrone || candidate.settings.workerParity;
    if (genVar310) {
      EcState genVar311;
      genVar311=this;
      int overDrones;
      overDrones=genVar311.getOverDrones(candidate);
      boolean genVar312;
      genVar312=candidate.settings.overDrone && candidate.units.get(ZergUnitLibrary.Drone) < overDrones;
      if (genVar312) {
        boolean genVar313;
        genVar313=false;
        return genVar313;
      }
 else {
        ;
      }
      if (candidate.settings.workerParity) {
        EcState genVar314;
        genVar314=this;
        int parityDrones;
        parityDrones=genVar314.getParityDrones(candidate);
        int genVar315;
        genVar315=candidate.units.get(ZergUnitLibrary.Drone);
        boolean genVar316;
        genVar316=genVar315 < parityDrones;
        if (genVar316) {
          boolean genVar317;
          genVar317=false;
          return genVar317;
        }
 else {
          ;
        }
      }
 else {
        ;
      }
    }
 else {
      ;
    }
    boolean genVar318;
    genVar318=true;
    return genVar318;
  }
  public int getOverDrones(  EcState s){
    int genVar319;
    genVar319=s.productionTime();
    int genVar320;
    genVar320=17;
    int genVar321;
    genVar321=genVar319 / genVar320;
    int genVar322;
    genVar322=(genVar321);
    int genVar323;
    genVar323=s.usedDrones();
    int genVar324;
    genVar324=genVar322 + genVar323;
    int genVar325;
    genVar325=(genVar324);
    int genVar326;
    genVar326=genVar325 * overDroneEfficiency;
    int genVar327;
    genVar327=100;
    int overDrones;
    overDrones=genVar326 / genVar327;
    int genVar328;
    genVar328=Math.min(overDrones,maxOverDrones);
    return genVar328;
  }
  public int getParityDrones(  EcState s){
    int genVar329;
    genVar329=s.bases();
    int genVar330;
    genVar330=3;
    int genVar331;
    genVar331=Math.min(genVar329,genVar330);
    int genVar332;
    genVar332=16;
    int genVar333;
    genVar333=genVar331 * genVar332;
    int genVar334;
    genVar334=(genVar333);
    int genVar335;
    genVar335=s.getGasExtractors();
    int genVar336;
    genVar336=3;
    int genVar337;
    genVar337=genVar335 * genVar336;
    int genVar338;
    genVar338=(genVar337);
    int genVar339;
    genVar339=genVar334 + genVar338;
    int optimalDrones;
    optimalDrones=Math.min(genVar339,maxOverDrones);
    int genVar340;
    genVar340=s.getOverDrones(s);
    int genVar341;
    genVar341=Math.min(genVar340,optimalDrones);
    return genVar341;
  }
  public int getParityDronesClean(  EcState s){
    int genVar342;
    genVar342=s.bases();
    int genVar343;
    genVar343=3;
    int genVar344;
    genVar344=Math.min(genVar342,genVar343);
    int genVar345;
    genVar345=16;
    int genVar346;
    genVar346=genVar344 * genVar345;
    int genVar347;
    genVar347=(genVar346);
    int genVar348;
    genVar348=s.buildings.get(ZergBuildingLibrary.Extractor);
    int genVar349;
    genVar349=3;
    int genVar350;
    genVar350=genVar348 * genVar349;
    int genVar351;
    genVar351=(genVar350);
    int genVar352;
    genVar352=genVar347 + genVar351;
    int optimalDrones;
    optimalDrones=Math.min(genVar352,maxOverDrones);
    int genVar353;
    genVar353=s.getOverDrones(s);
    int genVar354;
    genVar354=Math.min(genVar353,optimalDrones);
    return genVar354;
  }
  public int bases(){
    int genVar355;
    genVar355=buildings.get(ZergBuildingLibrary.Hatchery);
    int genVar356;
    genVar356=buildings.get(ZergBuildingLibrary.Lair);
    int genVar357;
    genVar357=buildings.get(ZergBuildingLibrary.Hive);
    int genVar358;
    genVar358=genVar355 + genVar356 + genVar357;
    return genVar358;
  }
  public int productionTime(){
    int productionTime;
    productionTime=0;
    int i=0;
    for (; i < Math.min(hatcheryTimes.size(),4); i++) {
      int genVar359;
      genVar359=hatcheryTimes.get(i);
      productionTime+=seconds - genVar359;
    }
    return productionTime;
  }
  public int usedDrones(){
    EcState genVar360;
    genVar360=this;
    int genVar361;
    genVar361=genVar360.getHatcheries();
    int genVar362;
    genVar362=1;
    int genVar363;
    genVar363=genVar361 - genVar362;
    int genVar364;
    genVar364=(genVar363);
    EcState genVar365;
    genVar365=this;
    int genVar366;
    genVar366=genVar365.getLairs();
    EcState genVar367;
    genVar367=this;
    int genVar368;
    genVar368=genVar367.getHives();
    EcState genVar369;
    genVar369=this;
    int genVar370;
    genVar370=genVar369.getSpawningPools();
    EcState genVar371;
    genVar371=this;
    int genVar372;
    genVar372=genVar371.getEvolutionChambers();
    EcState genVar373;
    genVar373=this;
    int genVar374;
    genVar374=genVar373.getRoachWarrens();
    EcState genVar375;
    genVar375=this;
    int genVar376;
    genVar376=genVar375.getHydraliskDen();
    EcState genVar377;
    genVar377=this;
    int genVar378;
    genVar378=genVar377.getBanelingNest();
    EcState genVar379;
    genVar379=this;
    int genVar380;
    genVar380=genVar379.getInfestationPit();
    EcState genVar381;
    genVar381=this;
    int genVar382;
    genVar382=genVar381.getUltraliskCavern();
    EcState genVar383;
    genVar383=this;
    int genVar384;
    genVar384=genVar383.getGasExtractors();
    EcState genVar385;
    genVar385=this;
    int genVar386;
    genVar386=genVar385.getSpire();
    EcState genVar387;
    genVar387=this;
    int genVar388;
    genVar388=genVar387.getSpineCrawlers();
    EcState genVar389;
    genVar389=this;
    int genVar390;
    genVar390=genVar389.getSporeCrawlers();
    EcState genVar391;
    genVar391=this;
    int genVar392;
    genVar392=genVar391.getNydusWorm();
    int genVar393;
    genVar393=genVar364 + genVar366 + genVar368+ genVar370+ genVar372+ genVar374+ genVar376+ genVar378+ genVar380+ genVar382+ genVar384+ genVar386+ genVar388+ genVar390+ genVar392;
    int genVar394;
    genVar394=(genVar393);
    return genVar394;
  }
  public int usedDronesClean(){
    int genVar395;
    genVar395=1;
    int total;
    total=-genVar395;
    com.fray.evo.util.Library<com.fray.evo.util.Building> genVar396;
    genVar396=RaceLibraries.getBuildingLibrary(settings.race);
    java.util.ArrayList<com.fray.evo.util.Building> genVar397;
    genVar397=genVar396.getList();
    for (    Building building : genVar397) {
      com.fray.evo.util.Buildable genVar398;
      genVar398=building.getConsumes();
      boolean genVar399;
      genVar399=genVar398 == ZergUnitLibrary.Drone;
      if (genVar399) {
        total+=buildings.get(building);
      }
 else {
        ;
      }
    }
    return total;
  }
  public int getEstimatedActions(){
    int genVar400;
    genVar400=waypoints.size();
    int genVar401;
    genVar401=0;
    boolean genVar402;
    genVar402=genVar400 > genVar401;
    if (genVar402) {
      boolean genVar403;
      genVar403=mergedWaypoints == null;
      if (genVar403) {
        EcState genVar404;
        genVar404=this;
        mergedWaypoints=genVar404.getMergedState();
      }
 else {
        ;
      }
      int genVar405;
      genVar405=mergedWaypoints.getEstimatedActions();
      return genVar405;
    }
 else {
      ;
    }
    EcState genVar406;
    genVar406=this;
    int genVar407;
    genVar407=genVar406.getLairs();
    EcState genVar408;
    genVar408=this;
    int genVar409;
    genVar409=genVar408.getHives();
    EcState genVar410;
    genVar410=this;
    int genVar411;
    genVar411=genVar410.getSpawningPools();
    EcState genVar412;
    genVar412=this;
    int genVar413;
    genVar413=genVar412.getEvolutionChambers();
    EcState genVar414;
    genVar414=this;
    int genVar415;
    genVar415=genVar414.getRoachWarrens();
    EcState genVar416;
    genVar416=this;
    int genVar417;
    genVar417=genVar416.getHydraliskDen();
    EcState genVar418;
    genVar418=this;
    int genVar419;
    genVar419=genVar418.getBanelingNest();
    EcState genVar420;
    genVar420=this;
    int genVar421;
    genVar421=genVar420.getInfestationPit();
    EcState genVar422;
    genVar422=this;
    int genVar423;
    genVar423=genVar422.getGreaterSpire();
    EcState genVar424;
    genVar424=this;
    int genVar425;
    genVar425=genVar424.getUltraliskCavern();
    EcState genVar426;
    genVar426=this;
    int genVar427;
    genVar427=genVar426.getGasExtractors();
    EcState genVar428;
    genVar428=this;
    int genVar429;
    genVar429=genVar428.getSpire();
    EcState genVar430;
    genVar430=this;
    int genVar431;
    genVar431=genVar430.getSpineCrawlers();
    EcState genVar432;
    genVar432=this;
    int genVar433;
    genVar433=genVar432.getSporeCrawlers();
    EcState genVar434;
    genVar434=this;
    int genVar435;
    genVar435=genVar434.getNydusNetwork();
    EcState genVar436;
    genVar436=this;
    int genVar437;
    genVar437=genVar436.getNydusWorm();
    EcState genVar438;
    genVar438=this;
    int genVar439;
    genVar439=genVar438.getDrones();
    EcState genVar440;
    genVar440=this;
    int genVar441;
    genVar441=genVar440.getOverlords();
    EcState genVar442;
    genVar442=this;
    int genVar443;
    genVar443=genVar442.getOverseers();
    EcState genVar444;
    genVar444=this;
    int genVar445;
    genVar445=genVar444.getZerglings();
    EcState genVar446;
    genVar446=this;
    int genVar447;
    genVar447=genVar446.getBanelings();
    int genVar448;
    genVar448=2;
    int genVar449;
    genVar449=genVar447 * genVar448;
    int genVar450;
    genVar450=requiredBases + genVar407 + genVar409+ genVar411+ genVar413+ genVar415+ genVar417+ genVar419+ genVar421+ genVar423+ genVar425+ genVar427+ genVar429+ genVar431+ genVar433+ genVar435+ genVar437+ genVar439+ genVar441+ genVar443+ genVar445+ genVar449;
    EcState genVar451;
    genVar451=this;
    int genVar452;
    genVar452=genVar451.getRoaches();
    int genVar453;
    genVar453=genVar450 + genVar452;
    EcState genVar454;
    genVar454=this;
    int genVar455;
    genVar455=genVar454.getMutalisks();
    int genVar456;
    genVar456=2;
    int genVar457;
    genVar457=genVar455 * genVar456;
    int genVar458;
    genVar458=genVar453 + genVar457;
    EcState genVar459;
    genVar459=this;
    int genVar460;
    genVar460=genVar459.getInfestors();
    int genVar461;
    genVar461=2;
    int genVar462;
    genVar462=genVar460 * genVar461;
    int genVar463;
    genVar463=genVar458 + genVar462;
    EcState genVar464;
    genVar464=this;
    int genVar465;
    genVar465=genVar464.getQueens();
    int genVar466;
    genVar466=genVar463 + genVar465;
    EcState genVar467;
    genVar467=this;
    int genVar468;
    genVar468=genVar467.getHydralisks();
    int genVar469;
    genVar469=2;
    int genVar470;
    genVar470=genVar468 * genVar469;
    int genVar471;
    genVar471=genVar466 + genVar470;
    EcState genVar472;
    genVar472=this;
    int genVar473;
    genVar473=genVar472.getCorruptors();
    int genVar474;
    genVar474=2;
    int genVar475;
    genVar475=genVar473 * genVar474;
    int genVar476;
    genVar476=genVar471 + genVar475;
    EcState genVar477;
    genVar477=this;
    int genVar478;
    genVar478=genVar477.getUltralisks();
    int genVar479;
    genVar479=2;
    int genVar480;
    genVar480=genVar478 * genVar479;
    int genVar481;
    genVar481=genVar476 + genVar480;
    EcState genVar482;
    genVar482=this;
    int genVar483;
    genVar483=genVar482.getBroodlords();
    int genVar484;
    genVar484=4;
    int genVar485;
    genVar485=genVar483 * genVar484;
    int i;
    i=genVar481 + genVar485;
    EcState genVar486;
    genVar486=this;
    boolean genVar487;
    genVar487=genVar486.isMetabolicBoost();
    if (genVar487) {
      i++;
    }
 else {
      ;
    }
    EcState genVar488;
    genVar488=this;
    boolean genVar489;
    genVar489=genVar488.isAdrenalGlands();
    if (genVar489) {
      i+=3;
    }
 else {
      ;
    }
    EcState genVar490;
    genVar490=this;
    boolean genVar491;
    genVar491=genVar490.isGlialReconstitution();
    if (genVar491) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar492;
    genVar492=this;
    boolean genVar493;
    genVar493=genVar492.isTunnelingClaws();
    if (genVar493) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar494;
    genVar494=this;
    boolean genVar495;
    genVar495=genVar494.isBurrow();
    if (genVar495) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar496;
    genVar496=this;
    boolean genVar497;
    genVar497=genVar496.isPneumatizedCarapace();
    if (genVar497) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar498;
    genVar498=this;
    boolean genVar499;
    genVar499=genVar498.isVentralSacs();
    if (genVar499) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar500;
    genVar500=this;
    boolean genVar501;
    genVar501=genVar500.isCentrifugalHooks();
    if (genVar501) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar502;
    genVar502=this;
    boolean genVar503;
    genVar503=genVar502.isMelee1();
    if (genVar503) {
      i++;
    }
 else {
      ;
    }
    EcState genVar504;
    genVar504=this;
    boolean genVar505;
    genVar505=genVar504.isMelee2();
    if (genVar505) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar506;
    genVar506=this;
    boolean genVar507;
    genVar507=genVar506.isMelee3();
    if (genVar507) {
      i+=3;
    }
 else {
      ;
    }
    EcState genVar508;
    genVar508=this;
    boolean genVar509;
    genVar509=genVar508.isMissile1();
    if (genVar509) {
      i++;
    }
 else {
      ;
    }
    EcState genVar510;
    genVar510=this;
    boolean genVar511;
    genVar511=genVar510.isMissile2();
    if (genVar511) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar512;
    genVar512=this;
    boolean genVar513;
    genVar513=genVar512.isMissile3();
    if (genVar513) {
      i+=3;
    }
 else {
      ;
    }
    EcState genVar514;
    genVar514=this;
    boolean genVar515;
    genVar515=genVar514.isArmor1();
    if (genVar515) {
      i++;
    }
 else {
      ;
    }
    EcState genVar516;
    genVar516=this;
    boolean genVar517;
    genVar517=genVar516.isArmor2();
    if (genVar517) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar518;
    genVar518=this;
    boolean genVar519;
    genVar519=genVar518.isArmor3();
    if (genVar519) {
      i+=3;
    }
 else {
      ;
    }
    EcState genVar520;
    genVar520=this;
    boolean genVar521;
    genVar521=genVar520.isGroovedSpines();
    if (genVar521) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar522;
    genVar522=this;
    boolean genVar523;
    genVar523=genVar522.isNeuralParasite();
    if (genVar523) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar524;
    genVar524=this;
    boolean genVar525;
    genVar525=genVar524.isPathogenGlands();
    if (genVar525) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar526;
    genVar526=this;
    boolean genVar527;
    genVar527=genVar526.isFlyerAttack1();
    if (genVar527) {
      i++;
    }
 else {
      ;
    }
    EcState genVar528;
    genVar528=this;
    boolean genVar529;
    genVar529=genVar528.isFlyerAttack2();
    if (genVar529) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar530;
    genVar530=this;
    boolean genVar531;
    genVar531=genVar530.isFlyerAttack3();
    if (genVar531) {
      i+=3;
    }
 else {
      ;
    }
    EcState genVar532;
    genVar532=this;
    boolean genVar533;
    genVar533=genVar532.isFlyerArmor1();
    if (genVar533) {
      i++;
    }
 else {
      ;
    }
    EcState genVar534;
    genVar534=this;
    boolean genVar535;
    genVar535=genVar534.isFlyerArmor2();
    if (genVar535) {
      i+=2;
    }
 else {
      ;
    }
    EcState genVar536;
    genVar536=this;
    boolean genVar537;
    genVar537=genVar536.isFlyerArmor3();
    if (genVar537) {
      i+=3;
    }
 else {
      ;
    }
    EcState genVar538;
    genVar538=this;
    boolean genVar539;
    genVar539=genVar538.isChitinousPlating();
    if (genVar539) {
      i+=3;
    }
 else {
      ;
    }
    for (    EcState s : waypoints)     i+=s.getEstimatedActions();
    return i;
  }
  public int getEstimatedActionsClean(){
    int genVar540;
    genVar540=waypoints.size();
    int genVar541;
    genVar541=0;
    boolean genVar542;
    genVar542=genVar540 > genVar541;
    if (genVar542) {
      boolean genVar543;
      genVar543=mergedWaypoints == null;
      if (genVar543) {
        EcState genVar544;
        genVar544=this;
        mergedWaypoints=genVar544.getMergedState();
      }
 else {
        ;
      }
      int genVar545;
      genVar545=mergedWaypoints.getEstimatedActions();
      return genVar545;
    }
 else {
      ;
    }
    int i;
    i=requiredBases;
    i+=units.getCount();
    i+=buildings.getCount();
    i+=upgrades.size();
    for (    EcState s : waypoints)     i+=s.getEstimatedActions();
    return i;
  }
  public EcState getMergedState(){
    EcState genVar546;
    genVar546=this;
    EcState state;
    state=genVar546.defaultDestination();
    for (    EcState s : waypoints)     state.union(s);
    com.fray.evo.EcState genVar547;
    genVar547=this;
    state.union(genVar547);
    return state;
  }
  public String timestamp(){
    int genVar548;
    genVar548=60;
    int genVar549;
    genVar549=seconds / genVar548;
    java.lang.String genVar550;
    genVar550=":";
    java.lang.String genVar551;
    genVar551=genVar549 + genVar550;
    int genVar552;
    genVar552=60;
    int genVar553;
    genVar553=seconds % genVar552;
    int genVar554;
    genVar554=10;
    boolean genVar555;
    genVar555=genVar553 < genVar554;
    java.lang.String genVar556;
    genVar556="0";
    java.lang.String genVar557;
    genVar557="";
    java.lang.String genVar558;
    genVar558=genVar555 ? genVar556 : genVar557;
    String genVar559;
    genVar559=(genVar558);
    java.lang.String genVar560;
    genVar560=genVar551 + genVar559;
    int genVar561;
    genVar561=60;
    int genVar562;
    genVar562=seconds % genVar561;
    java.lang.String genVar563;
    genVar563=genVar560 + genVar562;
    return genVar563;
  }
  public String toCompleteString(){
    StringBuilder sb;
    sb=new StringBuilder();
    java.lang.String genVar564;
    genVar564="AtTime";
    java.lang.String genVar565;
    genVar565=messages.getString(genVar564);
    java.lang.StringBuilder genVar566;
    genVar566=sb.append(genVar565);
    java.lang.String genVar567;
    genVar567=": ";
    java.lang.StringBuilder genVar568;
    genVar568=genVar566.append(genVar567);
    EcState genVar569;
    genVar569=this;
    java.lang.String genVar570;
    genVar570=genVar569.timestamp();
    genVar568.append(genVar570);
    java.lang.String genVar571;
    genVar571="\n";
    java.lang.StringBuilder genVar572;
    genVar572=sb.append(genVar571);
    java.lang.String genVar573;
    genVar573="Minerals";
    java.lang.String genVar574;
    genVar574=messages.getString(genVar573);
    java.lang.StringBuilder genVar575;
    genVar575=genVar572.append(genVar574);
    java.lang.String genVar576;
    genVar576=": ";
    java.lang.StringBuilder genVar577;
    genVar577=genVar575.append(genVar576);
    int genVar578;
    genVar578=(int)minerals;
    java.lang.StringBuilder genVar579;
    genVar579=genVar577.append(genVar578);
    java.lang.String genVar580;
    genVar580="\t";
    java.lang.StringBuilder genVar581;
    genVar581=genVar579.append(genVar580);
    java.lang.String genVar582;
    genVar582="Gas";
    java.lang.String genVar583;
    genVar583=messages.getString(genVar582);
    java.lang.StringBuilder genVar584;
    genVar584=genVar581.append(genVar583);
    java.lang.String genVar585;
    genVar585=":      ";
    java.lang.StringBuilder genVar586;
    genVar586=genVar584.append(genVar585);
    int genVar587;
    genVar587=(int)gas;
    java.lang.StringBuilder genVar588;
    genVar588=genVar586.append(genVar587);
    java.lang.String genVar589;
    genVar589="\t";
    java.lang.StringBuilder genVar590;
    genVar590=genVar588.append(genVar589);
    java.lang.String genVar591;
    genVar591="Supply";
    java.lang.String genVar592;
    genVar592=messages.getString(genVar591);
    java.lang.StringBuilder genVar593;
    genVar593=genVar590.append(genVar592);
    java.lang.String genVar594;
    genVar594=":   ";
    java.lang.StringBuilder genVar595;
    genVar595=genVar593.append(genVar594);
    int genVar596;
    genVar596=(int)supplyUsed;
    java.lang.StringBuilder genVar597;
    genVar597=genVar595.append(genVar596);
    java.lang.String genVar598;
    genVar598="/";
    java.lang.StringBuilder genVar599;
    genVar599=genVar597.append(genVar598);
    EcState genVar600;
    genVar600=this;
    int genVar601;
    genVar601=genVar600.supply();
    java.lang.StringBuilder genVar602;
    genVar602=genVar599.append(genVar601);
    java.lang.String genVar603;
    genVar603="\t";
    java.lang.StringBuilder genVar604;
    genVar604=genVar602.append(genVar603);
    java.lang.String genVar605;
    genVar605="Larva";
    java.lang.String genVar606;
    genVar606=messages.getString(genVar605);
    java.lang.StringBuilder genVar607;
    genVar607=genVar604.append(genVar606);
    java.lang.String genVar608;
    genVar608=": ";
    java.lang.StringBuilder genVar609;
    genVar609=genVar607.append(genVar608);
    EcState genVar610;
    genVar610=this;
    int genVar611;
    genVar611=genVar610.getLarva();
    genVar609.append(genVar611);
    EcState genVar612;
    genVar612=this;
    genVar612.appendBuildStuff(sb);
    java.lang.String genVar613;
    genVar613=sb.toString();
    return genVar613;
  }
  public String toUnitsOnlyString(){
    StringBuilder sb;
    sb=new StringBuilder();
    EcState genVar614;
    genVar614=this;
    genVar614.appendBuildStuff(sb);
    java.lang.String genVar615;
    genVar615=sb.toString();
    return genVar615;
  }
  private void appendBuildStuff(  StringBuilder sb){
    EcState genVar616;
    genVar616=this;
    java.lang.String genVar617;
    genVar617=ZergLibrary.Drone.getName();
    EcState genVar618;
    genVar618=this;
    int genVar619;
    genVar619=genVar618.getDrones();
    genVar616.append(sb,genVar617,genVar619);
    EcState genVar620;
    genVar620=this;
    java.lang.String genVar621;
    genVar621=ZergLibrary.Overlord.getName();
    EcState genVar622;
    genVar622=this;
    int genVar623;
    genVar623=genVar622.getOverlords();
    genVar620.append(sb,genVar621,genVar623);
    EcState genVar624;
    genVar624=this;
    java.lang.String genVar625;
    genVar625=ZergLibrary.Overseer.getName();
    EcState genVar626;
    genVar626=this;
    int genVar627;
    genVar627=genVar626.getOverseers();
    genVar624.append(sb,genVar625,genVar627);
    EcState genVar628;
    genVar628=this;
    java.lang.String genVar629;
    genVar629=ZergLibrary.Queen.getName();
    EcState genVar630;
    genVar630=this;
    int genVar631;
    genVar631=genVar630.getQueens();
    genVar628.append(sb,genVar629,genVar631);
    EcState genVar632;
    genVar632=this;
    java.lang.String genVar633;
    genVar633=ZergLibrary.Zergling.getName();
    EcState genVar634;
    genVar634=this;
    int genVar635;
    genVar635=genVar634.getZerglings();
    genVar632.append(sb,genVar633,genVar635);
    EcState genVar636;
    genVar636=this;
    java.lang.String genVar637;
    genVar637=ZergLibrary.Baneling.getName();
    EcState genVar638;
    genVar638=this;
    int genVar639;
    genVar639=genVar638.getBanelings();
    genVar636.append(sb,genVar637,genVar639);
    EcState genVar640;
    genVar640=this;
    java.lang.String genVar641;
    genVar641=ZergLibrary.Roach.getName();
    EcState genVar642;
    genVar642=this;
    int genVar643;
    genVar643=genVar642.getRoaches();
    genVar640.append(sb,genVar641,genVar643);
    EcState genVar644;
    genVar644=this;
    java.lang.String genVar645;
    genVar645=ZergLibrary.Hydralisk.getName();
    EcState genVar646;
    genVar646=this;
    int genVar647;
    genVar647=genVar646.getHydralisks();
    genVar644.append(sb,genVar645,genVar647);
    EcState genVar648;
    genVar648=this;
    java.lang.String genVar649;
    genVar649=ZergLibrary.Infestor.getName();
    EcState genVar650;
    genVar650=this;
    int genVar651;
    genVar651=genVar650.getInfestors();
    genVar648.append(sb,genVar649,genVar651);
    EcState genVar652;
    genVar652=this;
    java.lang.String genVar653;
    genVar653=ZergLibrary.Mutalisk.getName();
    EcState genVar654;
    genVar654=this;
    int genVar655;
    genVar655=genVar654.getMutalisks();
    genVar652.append(sb,genVar653,genVar655);
    EcState genVar656;
    genVar656=this;
    java.lang.String genVar657;
    genVar657=ZergLibrary.Corruptor.getName();
    EcState genVar658;
    genVar658=this;
    int genVar659;
    genVar659=genVar658.getCorruptors();
    genVar656.append(sb,genVar657,genVar659);
    EcState genVar660;
    genVar660=this;
    java.lang.String genVar661;
    genVar661=ZergLibrary.Ultralisk.getName();
    EcState genVar662;
    genVar662=this;
    int genVar663;
    genVar663=genVar662.getUltralisks();
    genVar660.append(sb,genVar661,genVar663);
    EcState genVar664;
    genVar664=this;
    java.lang.String genVar665;
    genVar665=ZergLibrary.Broodlord.getName();
    EcState genVar666;
    genVar666=this;
    int genVar667;
    genVar667=genVar666.getBroodlords();
    genVar664.append(sb,genVar665,genVar667);
    EcState genVar668;
    genVar668=this;
    java.lang.String genVar669;
    genVar669="Total.Minerals.Mined";
    int genVar670;
    genVar670=(int)totalMineralsMined;
    genVar668.append(sb,genVar669,genVar670);
    EcState genVar671;
    genVar671=this;
    int genVar672;
    genVar672=genVar671.bases();
    boolean genVar673;
    genVar673=genVar672 >= requiredBases;
    if (genVar673) {
      EcState genVar674;
      genVar674=this;
      java.lang.String genVar675;
      genVar675="Bases";
      EcState genVar676;
      genVar676=this;
      int genVar677;
      genVar677=genVar676.bases();
      genVar674.append(sb,genVar675,genVar677);
    }
 else {
      EcState genVar678;
      genVar678=this;
      java.lang.String genVar679;
      genVar679="Required.Bases";
      genVar678.append(sb,genVar679,requiredBases);
    }
    EcState genVar680;
    genVar680=this;
    java.lang.String genVar681;
    genVar681=ZergLibrary.Lair.getName();
    EcState genVar682;
    genVar682=this;
    int genVar683;
    genVar683=genVar682.getLairs();
    genVar680.append(sb,genVar681,genVar683);
    EcState genVar684;
    genVar684=this;
    java.lang.String genVar685;
    genVar685=ZergLibrary.Hive.getName();
    EcState genVar686;
    genVar686=this;
    int genVar687;
    genVar687=genVar686.getHives();
    genVar684.append(sb,genVar685,genVar687);
    EcState genVar688;
    genVar688=this;
    java.lang.String genVar689;
    genVar689=ZergLibrary.Extractor.getName();
    EcState genVar690;
    genVar690=this;
    int genVar691;
    genVar691=genVar690.getGasExtractors();
    genVar688.append(sb,genVar689,genVar691);
    EcState genVar692;
    genVar692=this;
    java.lang.String genVar693;
    genVar693=ZergLibrary.SpawningPool.getName();
    EcState genVar694;
    genVar694=this;
    int genVar695;
    genVar695=genVar694.getSpawningPools();
    genVar692.append(sb,genVar693,genVar695);
    EcState genVar696;
    genVar696=this;
    java.lang.String genVar697;
    genVar697=ZergLibrary.BanelingNest.getName();
    EcState genVar698;
    genVar698=this;
    int genVar699;
    genVar699=genVar698.getBanelingNest();
    genVar696.append(sb,genVar697,genVar699);
    EcState genVar700;
    genVar700=this;
    java.lang.String genVar701;
    genVar701=ZergLibrary.RoachWarren.getName();
    EcState genVar702;
    genVar702=this;
    int genVar703;
    genVar703=genVar702.getRoachWarrens();
    genVar700.append(sb,genVar701,genVar703);
    EcState genVar704;
    genVar704=this;
    java.lang.String genVar705;
    genVar705=ZergLibrary.HydraliskDen.getName();
    EcState genVar706;
    genVar706=this;
    int genVar707;
    genVar707=genVar706.getHydraliskDen();
    genVar704.append(sb,genVar705,genVar707);
    EcState genVar708;
    genVar708=this;
    java.lang.String genVar709;
    genVar709=ZergLibrary.InfestationPit.getName();
    EcState genVar710;
    genVar710=this;
    int genVar711;
    genVar711=genVar710.getInfestationPit();
    genVar708.append(sb,genVar709,genVar711);
    EcState genVar712;
    genVar712=this;
    java.lang.String genVar713;
    genVar713=ZergLibrary.Spire.getName();
    EcState genVar714;
    genVar714=this;
    int genVar715;
    genVar715=genVar714.getSpire();
    genVar712.append(sb,genVar713,genVar715);
    EcState genVar716;
    genVar716=this;
    java.lang.String genVar717;
    genVar717=ZergLibrary.UltraliskCavern.getName();
    EcState genVar718;
    genVar718=this;
    int genVar719;
    genVar719=genVar718.getUltraliskCavern();
    genVar716.append(sb,genVar717,genVar719);
    EcState genVar720;
    genVar720=this;
    java.lang.String genVar721;
    genVar721=ZergLibrary.GreaterSpire.getName();
    EcState genVar722;
    genVar722=this;
    int genVar723;
    genVar723=genVar722.getGreaterSpire();
    genVar720.append(sb,genVar721,genVar723);
    EcState genVar724;
    genVar724=this;
    java.lang.String genVar725;
    genVar725=ZergLibrary.EvolutionChamber.getName();
    EcState genVar726;
    genVar726=this;
    int genVar727;
    genVar727=genVar726.getEvolutionChambers();
    genVar724.append(sb,genVar725,genVar727);
    EcState genVar728;
    genVar728=this;
    java.lang.String genVar729;
    genVar729=ZergLibrary.SpineCrawler.getName();
    EcState genVar730;
    genVar730=this;
    int genVar731;
    genVar731=genVar730.getSpineCrawlers();
    genVar728.append(sb,genVar729,genVar731);
    EcState genVar732;
    genVar732=this;
    java.lang.String genVar733;
    genVar733=ZergLibrary.SporeCrawler.getName();
    EcState genVar734;
    genVar734=this;
    int genVar735;
    genVar735=genVar734.getSporeCrawlers();
    genVar732.append(sb,genVar733,genVar735);
    EcState genVar736;
    genVar736=this;
    java.lang.String genVar737;
    genVar737=ZergLibrary.NydusNetwork.getName();
    EcState genVar738;
    genVar738=this;
    int genVar739;
    genVar739=genVar738.getNydusNetwork();
    genVar736.append(sb,genVar737,genVar739);
    EcState genVar740;
    genVar740=this;
    java.lang.String genVar741;
    genVar741=ZergLibrary.NydusWorm.getName();
    EcState genVar742;
    genVar742=this;
    int genVar743;
    genVar743=genVar742.getNydusWorm();
    genVar740.append(sb,genVar741,genVar743);
    EcState genVar744;
    genVar744=this;
    java.lang.String genVar745;
    genVar745=ZergLibrary.Melee1.getName();
    EcState genVar746;
    genVar746=this;
    boolean genVar747;
    genVar747=genVar746.isMelee1();
    genVar744.append(sb,genVar745,genVar747);
    EcState genVar748;
    genVar748=this;
    java.lang.String genVar749;
    genVar749=ZergLibrary.Melee2.getName();
    EcState genVar750;
    genVar750=this;
    boolean genVar751;
    genVar751=genVar750.isMelee2();
    genVar748.append(sb,genVar749,genVar751);
    EcState genVar752;
    genVar752=this;
    java.lang.String genVar753;
    genVar753=ZergLibrary.Melee3.getName();
    EcState genVar754;
    genVar754=this;
    boolean genVar755;
    genVar755=genVar754.isMelee3();
    genVar752.append(sb,genVar753,genVar755);
    EcState genVar756;
    genVar756=this;
    java.lang.String genVar757;
    genVar757=ZergLibrary.Missile1.getName();
    EcState genVar758;
    genVar758=this;
    boolean genVar759;
    genVar759=genVar758.isMissile1();
    genVar756.append(sb,genVar757,genVar759);
    EcState genVar760;
    genVar760=this;
    java.lang.String genVar761;
    genVar761=ZergLibrary.Missile2.getName();
    EcState genVar762;
    genVar762=this;
    boolean genVar763;
    genVar763=genVar762.isMissile2();
    genVar760.append(sb,genVar761,genVar763);
    EcState genVar764;
    genVar764=this;
    java.lang.String genVar765;
    genVar765=ZergLibrary.Missile3.getName();
    EcState genVar766;
    genVar766=this;
    boolean genVar767;
    genVar767=genVar766.isMissile3();
    genVar764.append(sb,genVar765,genVar767);
    EcState genVar768;
    genVar768=this;
    java.lang.String genVar769;
    genVar769=ZergLibrary.Armor1.getName();
    EcState genVar770;
    genVar770=this;
    boolean genVar771;
    genVar771=genVar770.isArmor1();
    genVar768.append(sb,genVar769,genVar771);
    EcState genVar772;
    genVar772=this;
    java.lang.String genVar773;
    genVar773=ZergLibrary.Armor2.getName();
    EcState genVar774;
    genVar774=this;
    boolean genVar775;
    genVar775=genVar774.isArmor2();
    genVar772.append(sb,genVar773,genVar775);
    EcState genVar776;
    genVar776=this;
    java.lang.String genVar777;
    genVar777=ZergLibrary.Armor3.getName();
    EcState genVar778;
    genVar778=this;
    boolean genVar779;
    genVar779=genVar778.isArmor3();
    genVar776.append(sb,genVar777,genVar779);
    EcState genVar780;
    genVar780=this;
    java.lang.String genVar781;
    genVar781=ZergLibrary.FlyerAttacks1.getName();
    EcState genVar782;
    genVar782=this;
    boolean genVar783;
    genVar783=genVar782.isFlyerAttack1();
    genVar780.append(sb,genVar781,genVar783);
    EcState genVar784;
    genVar784=this;
    java.lang.String genVar785;
    genVar785=ZergLibrary.FlyerAttacks2.getName();
    EcState genVar786;
    genVar786=this;
    boolean genVar787;
    genVar787=genVar786.isFlyerAttack2();
    genVar784.append(sb,genVar785,genVar787);
    EcState genVar788;
    genVar788=this;
    java.lang.String genVar789;
    genVar789=ZergLibrary.FlyerAttacks3.getName();
    EcState genVar790;
    genVar790=this;
    boolean genVar791;
    genVar791=genVar790.isFlyerAttack3();
    genVar788.append(sb,genVar789,genVar791);
    EcState genVar792;
    genVar792=this;
    java.lang.String genVar793;
    genVar793=ZergLibrary.FlyerArmor1.getName();
    EcState genVar794;
    genVar794=this;
    boolean genVar795;
    genVar795=genVar794.isFlyerArmor1();
    genVar792.append(sb,genVar793,genVar795);
    EcState genVar796;
    genVar796=this;
    java.lang.String genVar797;
    genVar797=ZergLibrary.FlyerArmor2.getName();
    EcState genVar798;
    genVar798=this;
    boolean genVar799;
    genVar799=genVar798.isFlyerArmor2();
    genVar796.append(sb,genVar797,genVar799);
    EcState genVar800;
    genVar800=this;
    java.lang.String genVar801;
    genVar801=ZergLibrary.FlyerArmor3.getName();
    EcState genVar802;
    genVar802=this;
    boolean genVar803;
    genVar803=genVar802.isFlyerArmor3();
    genVar800.append(sb,genVar801,genVar803);
    EcState genVar804;
    genVar804=this;
    java.lang.String genVar805;
    genVar805=ZergLibrary.MetabolicBoost.getName();
    EcState genVar806;
    genVar806=this;
    boolean genVar807;
    genVar807=genVar806.isMetabolicBoost();
    genVar804.append(sb,genVar805,genVar807);
    EcState genVar808;
    genVar808=this;
    java.lang.String genVar809;
    genVar809=ZergLibrary.AdrenalGlands.getName();
    EcState genVar810;
    genVar810=this;
    boolean genVar811;
    genVar811=genVar810.isAdrenalGlands();
    genVar808.append(sb,genVar809,genVar811);
    EcState genVar812;
    genVar812=this;
    java.lang.String genVar813;
    genVar813=ZergLibrary.GlialReconstitution.getName();
    EcState genVar814;
    genVar814=this;
    boolean genVar815;
    genVar815=genVar814.isGlialReconstitution();
    genVar812.append(sb,genVar813,genVar815);
    EcState genVar816;
    genVar816=this;
    java.lang.String genVar817;
    genVar817=ZergLibrary.TunnelingClaws.getName();
    EcState genVar818;
    genVar818=this;
    boolean genVar819;
    genVar819=genVar818.isTunnelingClaws();
    genVar816.append(sb,genVar817,genVar819);
    EcState genVar820;
    genVar820=this;
    java.lang.String genVar821;
    genVar821=ZergLibrary.Burrow.getName();
    EcState genVar822;
    genVar822=this;
    boolean genVar823;
    genVar823=genVar822.isBurrow();
    genVar820.append(sb,genVar821,genVar823);
    EcState genVar824;
    genVar824=this;
    java.lang.String genVar825;
    genVar825=ZergLibrary.PneumatizedCarapace.getName();
    EcState genVar826;
    genVar826=this;
    boolean genVar827;
    genVar827=genVar826.isPneumatizedCarapace();
    genVar824.append(sb,genVar825,genVar827);
    EcState genVar828;
    genVar828=this;
    java.lang.String genVar829;
    genVar829=ZergLibrary.VentralSacs.getName();
    EcState genVar830;
    genVar830=this;
    boolean genVar831;
    genVar831=genVar830.isVentralSacs();
    genVar828.append(sb,genVar829,genVar831);
    EcState genVar832;
    genVar832=this;
    java.lang.String genVar833;
    genVar833=ZergLibrary.CentrifugalHooks.getName();
    EcState genVar834;
    genVar834=this;
    boolean genVar835;
    genVar835=genVar834.isCentrifugalHooks();
    genVar832.append(sb,genVar833,genVar835);
    EcState genVar836;
    genVar836=this;
    java.lang.String genVar837;
    genVar837=ZergLibrary.GroovedSpines.getName();
    EcState genVar838;
    genVar838=this;
    boolean genVar839;
    genVar839=genVar838.isGroovedSpines();
    genVar836.append(sb,genVar837,genVar839);
    EcState genVar840;
    genVar840=this;
    java.lang.String genVar841;
    genVar841=ZergLibrary.NeuralParasite.getName();
    EcState genVar842;
    genVar842=this;
    boolean genVar843;
    genVar843=genVar842.isNeuralParasite();
    genVar840.append(sb,genVar841,genVar843);
    EcState genVar844;
    genVar844=this;
    java.lang.String genVar845;
    genVar845=ZergLibrary.PathogenGlands.getName();
    EcState genVar846;
    genVar846=this;
    boolean genVar847;
    genVar847=genVar846.isPathogenGlands();
    genVar844.append(sb,genVar845,genVar847);
    EcState genVar848;
    genVar848=this;
    java.lang.String genVar849;
    genVar849=ZergLibrary.ChitinousPlating.getName();
    EcState genVar850;
    genVar850=this;
    boolean genVar851;
    genVar851=genVar850.isChitinousPlating();
    genVar848.append(sb,genVar849,genVar851);
  }
  private void appendBuildStuffClean(  StringBuilder sb){
    EcState genVar852;
    genVar852=this;
    java.lang.String genVar853;
    genVar853="Bases";
    genVar852.append(sb,genVar853,requiredBases);
    for (    Upgrade upgrade : upgrades) {
      EcState genVar854;
      genVar854=this;
      java.lang.String genVar855;
      genVar855=upgrade.getName();
      boolean genVar856;
      genVar856=true;
      genVar854.append(sb,genVar855,genVar856);
    }
  }
  private void append(  StringBuilder sb,  String name,  boolean doit){
    if (doit) {
      java.lang.String genVar857;
      genVar857="\n";
      java.lang.StringBuilder genVar858;
      genVar858=sb.append(genVar857);
      java.lang.String genVar859;
      genVar859=messages.getString(name);
      genVar858.append(genVar859);
    }
 else {
      ;
    }
  }
  private void append(  StringBuilder sb,  String name,  int count){
    int genVar860;
    genVar860=0;
    boolean genVar861;
    genVar861=count > genVar860;
    if (genVar861) {
      java.lang.String genVar862;
      genVar862="\n";
      java.lang.StringBuilder genVar863;
      genVar863=sb.append(genVar862);
      java.lang.String genVar864;
      genVar864=messages.getString(name);
      java.lang.StringBuilder genVar865;
      genVar865=genVar863.append(genVar864);
      java.lang.String genVar866;
      genVar866=": ";
      java.lang.StringBuilder genVar867;
      genVar867=genVar865.append(genVar866);
      genVar867.append(count);
    }
 else {
      ;
    }
  }
  public boolean waypointMissed(  EcBuildOrder candidate){
    boolean genVar868;
    genVar868=waypoints == null;
    if (genVar868) {
      boolean genVar869;
      genVar869=false;
      return genVar869;
    }
 else {
      ;
    }
    int i=0;
    for (; i < waypoints.size(); ++i) {
      EcState s;
      s=waypoints.get(i);
      boolean genVar870;
      genVar870=candidate.seconds == s.targetSeconds;
      if (genVar870) {
        boolean genVar871;
        genVar871=s.isSatisfied(candidate);
        boolean genVar872;
        genVar872=!genVar871;
        if (genVar872) {
          boolean genVar873;
          genVar873=true;
          return genVar873;
        }
 else {
          ;
        }
      }
 else {
        ;
      }
    }
    boolean genVar874;
    genVar874=false;
    return genVar874;
  }
  public int getCurrWaypointIndex(  EcBuildOrder candidate){
    int i=0;
    for (; i < waypoints.size(); ++i) {
      com.fray.evo.EcState genVar875;
      genVar875=waypoints.get(i);
      int genVar876;
      genVar876=genVar875.targetSeconds;
      boolean genVar877;
      genVar877=genVar876 == candidate.seconds;
      if (genVar877) {
        return i;
      }
 else {
        ;
      }
    }
    int genVar878;
    genVar878=1;
    int genVar879;
    genVar879=-genVar878;
    return genVar879;
  }
  public int getWaypointActions(  int index){
    com.fray.evo.EcState genVar880;
    genVar880=waypoints.get(index);
    int genVar881;
    genVar881=genVar880.getEstimatedActions();
    return genVar881;
  }
  public EcState getMergedWaypoints(){
    boolean genVar882;
    genVar882=mergedWaypoints == null;
    if (genVar882) {
      EcState genVar883;
      genVar883=this;
      mergedWaypoints=genVar883.getMergedState();
    }
 else {
      ;
    }
    return mergedWaypoints;
  }
  public HashSet<Upgrade> getUpgrades(){
    return upgrades;
  }
  public void addUpgrade(  Upgrade upgrade){
    upgrades.add(upgrade);
  }
  public void addUnits(  Unit unit,  int number){
    int genVar884;
    genVar884=units.get(unit);
    int genVar885;
    genVar885=genVar884 + number;
    units.put(unit,genVar885);
  }
  public void removeUnits(  Unit unit,  int number){
    int genVar886;
    genVar886=units.get(unit);
    int genVar887;
    genVar887=genVar886 - number;
    units.put(unit,genVar887);
  }
  public void removeUpgrade(  Upgrade upgrade){
    upgrades.remove(upgrade);
  }
  public void setUnits(  Unit unit,  int number){
    units.put(unit,number);
  }
  public void addBuilding(  Building building){
    int genVar888;
    genVar888=buildings.get(building);
    int genVar889;
    genVar889=1;
    int genVar890;
    genVar890=genVar888 + genVar889;
    buildings.put(building,genVar890);
  }
  public int getBuildingCount(  Building building){
    int genVar891;
    genVar891=building.getId();
    int genVar892;
    genVar892=buildings.getById(genVar891);
    return genVar892;
  }
  public boolean isBuilding(  Building building){
    int genVar893;
    genVar893=buildings.get(building);
    int genVar894;
    genVar894=0;
    boolean genVar895;
    genVar895=genVar893 == genVar894;
    if (genVar895) {
      boolean genVar896;
      genVar896=building == ZergBuildingLibrary.Hatchery;
      if (genVar896) {
        EcState genVar897;
        genVar897=this;
        boolean genVar898;
        genVar898=genVar897.isBuilding(ZergBuildingLibrary.Lair);
        return genVar898;
      }
 else {
        boolean genVar899;
        genVar899=building == ZergBuildingLibrary.Lair;
        if (genVar899) {
          EcState genVar900;
          genVar900=this;
          boolean genVar901;
          genVar901=genVar900.isBuilding(ZergBuildingLibrary.Hive);
          return genVar901;
        }
 else {
          boolean genVar902;
          genVar902=building == ZergBuildingLibrary.Spire;
          if (genVar902) {
            EcState genVar903;
            genVar903=this;
            boolean genVar904;
            genVar904=genVar903.isBuilding(ZergBuildingLibrary.GreaterSpire);
            return genVar904;
          }
 else {
            boolean genVar905;
            genVar905=false;
            return genVar905;
          }
        }
      }
    }
 else {
      boolean genVar906;
      genVar906=true;
      return genVar906;
    }
  }
  public int getUnitCount(  Unit unit){
    int genVar907;
    genVar907=unit.getId();
    int genVar908;
    genVar908=units.getById(genVar907);
    return genVar908;
  }
  public boolean isUpgrade(  Upgrade upgrade){
    boolean genVar909;
    genVar909=upgrades.contains(upgrade);
    return genVar909;
  }
  void RequireUnit(  Unit unit){
    boolean genVar910;
    genVar910=unit == ZergUnitLibrary.Zergling;
    if (genVar910) {
      return;
    }
 else {
      ;
    }
    boolean genVar911;
    genVar911=unit == ZergUnitLibrary.Corruptor;
    if (genVar911) {
      return;
    }
 else {
      ;
    }
    boolean genVar912;
    genVar912=unit == ZergUnitLibrary.Overlord;
    if (genVar912) {
      return;
    }
 else {
      ;
    }
    int genVar913;
    genVar913=units.get(unit);
    int genVar914;
    genVar914=1;
    boolean genVar915;
    genVar915=genVar913 < genVar914;
    if (genVar915) {
      int genVar916;
      genVar916=1;
      units.put(unit,genVar916);
    }
 else {
      ;
    }
  }
  public void removeBuilding(  Building building){
    int genVar917;
    genVar917=buildings.get(building);
    int genVar918;
    genVar918=1;
    int genVar919;
    genVar919=genVar917 - genVar918;
    buildings.put(building,genVar919);
  }
  public void requireBuilding(  Building building){
    boolean genVar920;
    genVar920=building == ZergBuildingLibrary.Spire;
    if (genVar920) {
      return;
    }
 else {
      ;
    }
    boolean genVar921;
    genVar921=building == ZergBuildingLibrary.Lair;
    if (genVar921) {
      return;
    }
 else {
      ;
    }
    boolean genVar922;
    genVar922=building == ZergBuildingLibrary.Hatchery;
    if (genVar922) {
      return;
    }
 else {
      ;
    }
    int genVar923;
    genVar923=buildings.get(building);
    int genVar924;
    genVar924=1;
    boolean genVar925;
    genVar925=genVar923 < genVar924;
    if (genVar925) {
      int genVar926;
      genVar926=1;
      buildings.put(building,genVar926);
    }
 else {
      ;
    }
  }
  public HashMap<Building,Integer> getBuildings(){
    Library<Building> allBuildings;
    allBuildings=RaceLibraries.getBuildingLibrary(settings.race);
    java.util.HashMap<com.fray.evo.util.Building,java.lang.Integer> genVar927;
    genVar927=buildings.toHashMap(allBuildings);
    return genVar927;
  }
  public HashMap<Unit,Integer> getUnits(){
    Library<Unit> allUnits;
    allUnits=RaceLibraries.getUnitLibrary(settings.race);
    java.util.HashMap<com.fray.evo.util.Unit,java.lang.Integer> genVar928;
    genVar928=units.toHashMap(allUnits);
    return genVar928;
  }
  public void setBuilding(  Building building,  int number){
    buildings.put(building,number);
  }
  /** 
 * @return the metabolicBoost
 */
  public boolean isMetabolicBoost(){
    boolean genVar929;
    genVar929=upgrades.contains(ZergUpgradeLibrary.MetabolicBoost);
    return genVar929;
  }
  /** 
 * @return the adrenalGlands
 */
  public boolean isAdrenalGlands(){
    boolean genVar930;
    genVar930=upgrades.contains(ZergUpgradeLibrary.AdrenalGlands);
    return genVar930;
  }
  /** 
 * @return the glialReconstitution
 */
  public boolean isGlialReconstitution(){
    boolean genVar931;
    genVar931=upgrades.contains(ZergUpgradeLibrary.GlialReconstitution);
    return genVar931;
  }
  /** 
 * @return the tunnelingClaws
 */
  public boolean isTunnelingClaws(){
    boolean genVar932;
    genVar932=upgrades.contains(ZergUpgradeLibrary.TunnelingClaws);
    return genVar932;
  }
  /** 
 * @return the burrow
 */
  public boolean isBurrow(){
    boolean genVar933;
    genVar933=upgrades.contains(ZergUpgradeLibrary.Burrow);
    return genVar933;
  }
  /** 
 * @return the pneumatizedCarapace
 */
  public boolean isPneumatizedCarapace(){
    boolean genVar934;
    genVar934=upgrades.contains(ZergUpgradeLibrary.PneumatizedCarapace);
    return genVar934;
  }
  /** 
 * @return the ventralSacs
 */
  public boolean isVentralSacs(){
    boolean genVar935;
    genVar935=upgrades.contains(ZergUpgradeLibrary.VentralSacs);
    return genVar935;
  }
  /** 
 * @return the centrifugalHooks
 */
  public boolean isCentrifugalHooks(){
    boolean genVar936;
    genVar936=upgrades.contains(ZergUpgradeLibrary.CentrifugalHooks);
    return genVar936;
  }
  /** 
 * @return the melee1
 */
  public boolean isMelee1(){
    boolean genVar937;
    genVar937=upgrades.contains(ZergUpgradeLibrary.Melee1);
    return genVar937;
  }
  /** 
 * @return the melee2
 */
  public boolean isMelee2(){
    boolean genVar938;
    genVar938=upgrades.contains(ZergUpgradeLibrary.Melee2);
    return genVar938;
  }
  /** 
 * @return the melee3
 */
  public boolean isMelee3(){
    boolean genVar939;
    genVar939=upgrades.contains(ZergUpgradeLibrary.Melee3);
    return genVar939;
  }
  /** 
 * @return the missile1
 */
  public boolean isMissile1(){
    boolean genVar940;
    genVar940=upgrades.contains(ZergUpgradeLibrary.Missile1);
    return genVar940;
  }
  /** 
 * @return the missile2
 */
  public boolean isMissile2(){
    boolean genVar941;
    genVar941=upgrades.contains(ZergUpgradeLibrary.Missile2);
    return genVar941;
  }
  /** 
 * @return the missile3
 */
  public boolean isMissile3(){
    boolean genVar942;
    genVar942=upgrades.contains(ZergUpgradeLibrary.Missile3);
    return genVar942;
  }
  /** 
 * @return the armor1
 */
  public boolean isArmor1(){
    boolean genVar943;
    genVar943=upgrades.contains(ZergUpgradeLibrary.Armor1);
    return genVar943;
  }
  /** 
 * @return the armor2
 */
  public boolean isArmor2(){
    boolean genVar944;
    genVar944=upgrades.contains(ZergUpgradeLibrary.Armor2);
    return genVar944;
  }
  /** 
 * @return the armor3
 */
  public boolean isArmor3(){
    boolean genVar945;
    genVar945=upgrades.contains(ZergUpgradeLibrary.Armor3);
    return genVar945;
  }
  /** 
 * @return the groovedSpines
 */
  public boolean isGroovedSpines(){
    boolean genVar946;
    genVar946=upgrades.contains(ZergUpgradeLibrary.GroovedSpines);
    return genVar946;
  }
  /** 
 * @return the neuralParasite
 */
  public boolean isNeuralParasite(){
    boolean genVar947;
    genVar947=upgrades.contains(ZergUpgradeLibrary.NeuralParasite);
    return genVar947;
  }
  /** 
 * @return the pathogenGlands
 */
  public boolean isPathogenGlands(){
    boolean genVar948;
    genVar948=upgrades.contains(ZergUpgradeLibrary.PathogenGlands);
    return genVar948;
  }
  /** 
 * @return the flyerAttack1
 */
  public boolean isFlyerAttack1(){
    boolean genVar949;
    genVar949=upgrades.contains(ZergUpgradeLibrary.FlyerAttacks1);
    return genVar949;
  }
  /** 
 * @return the flyerAttack2
 */
  public boolean isFlyerAttack2(){
    boolean genVar950;
    genVar950=upgrades.contains(ZergUpgradeLibrary.FlyerAttacks2);
    return genVar950;
  }
  /** 
 * @return the flyerAttack3
 */
  public boolean isFlyerAttack3(){
    boolean genVar951;
    genVar951=upgrades.contains(ZergUpgradeLibrary.FlyerAttacks3);
    return genVar951;
  }
  /** 
 * @return the flyerArmor1
 */
  public boolean isFlyerArmor1(){
    boolean genVar952;
    genVar952=upgrades.contains(ZergUpgradeLibrary.FlyerArmor1);
    return genVar952;
  }
  /** 
 * @return the flyerArmor2
 */
  public boolean isFlyerArmor2(){
    boolean genVar953;
    genVar953=upgrades.contains(ZergUpgradeLibrary.FlyerArmor2);
    return genVar953;
  }
  /** 
 * @return the flyerArmor3
 */
  public boolean isFlyerArmor3(){
    boolean genVar954;
    genVar954=upgrades.contains(ZergUpgradeLibrary.FlyerArmor3);
    return genVar954;
  }
  /** 
 * @return the chitinousPlating
 */
  public boolean isChitinousPlating(){
    boolean genVar955;
    genVar955=upgrades.contains(ZergUpgradeLibrary.ChitinousPlating);
    return genVar955;
  }
  /** 
 * @return the larva
 */
  public int getLarva(){
    int genVar956;
    genVar956=larva.total();
    return genVar956;
  }
  public int getLarva(  int base){
    com.fray.evo.EcState genVar957;
    genVar957=this;
    com.fray.evo.util.optimization.ArrayListInt genVar958;
    genVar958=genVar957.larva;
    int genVar959;
    genVar959=genVar958.get(base);
    return genVar959;
  }
  /** 
 * @param larva the larva to set
 */
  public void setLarva(  int base,  int larva){
    while (this.larva.size() <= base) {
      com.fray.evo.EcState genVar960;
      genVar960=this;
      com.fray.evo.util.optimization.ArrayListInt genVar961;
      genVar961=genVar960.larva;
      int genVar962;
      genVar962=0;
      genVar961.add(genVar962);
    }
    com.fray.evo.EcState genVar963;
    genVar963=this;
    com.fray.evo.util.optimization.ArrayListInt genVar964;
    genVar964=genVar963.larva;
    genVar964.set(base,larva);
  }
  public void incrementLarva(  int base){
    while (this.larva.size() <= base) {
      com.fray.evo.EcState genVar965;
      genVar965=this;
      com.fray.evo.util.optimization.ArrayListInt genVar966;
      genVar966=genVar965.larva;
      int genVar967;
      genVar967=0;
      genVar966.add(genVar967);
    }
    com.fray.evo.EcState genVar968;
    genVar968=this;
    com.fray.evo.util.optimization.ArrayListInt genVar969;
    genVar969=genVar968.larva;
    genVar969.increment(base);
  }
  public void decrementLarva(  int base){
    while (this.larva.size() <= base) {
      com.fray.evo.EcState genVar970;
      genVar970=this;
      com.fray.evo.util.optimization.ArrayListInt genVar971;
      genVar971=genVar970.larva;
      int genVar972;
      genVar972=0;
      genVar971.add(genVar972);
    }
    com.fray.evo.EcState genVar973;
    genVar973=this;
    com.fray.evo.util.optimization.ArrayListInt genVar974;
    genVar974=genVar973.larva;
    genVar974.decrement(base);
  }
  /** 
 * @return the drones
 */
  public int getDrones(){
    int genVar975;
    genVar975=units.get(ZergUnitLibrary.Drone);
    return genVar975;
  }
  /** 
 * @return the overlords
 */
  public int getOverlords(){
    int genVar976;
    genVar976=units.get(ZergUnitLibrary.Overlord);
    return genVar976;
  }
  /** 
 * @return the overseers
 */
  public int getOverseers(){
    int genVar977;
    genVar977=units.get(ZergUnitLibrary.Overseer);
    return genVar977;
  }
  /** 
 * @return the zerglings
 */
  public int getZerglings(){
    int genVar978;
    genVar978=units.get(ZergUnitLibrary.Zergling);
    return genVar978;
  }
  /** 
 * @return the banelings
 */
  public int getBanelings(){
    int genVar979;
    genVar979=units.get(ZergUnitLibrary.Baneling);
    return genVar979;
  }
  /** 
 * @return the roaches
 */
  public int getRoaches(){
    int genVar980;
    genVar980=units.get(ZergUnitLibrary.Roach);
    return genVar980;
  }
  /** 
 * @return the mutalisks
 */
  public int getMutalisks(){
    int genVar981;
    genVar981=units.get(ZergUnitLibrary.Mutalisk);
    return genVar981;
  }
  /** 
 * @return the infestors
 */
  public int getInfestors(){
    int genVar982;
    genVar982=units.get(ZergUnitLibrary.Infestor);
    return genVar982;
  }
  /** 
 * @return the queens
 */
  public int getQueens(){
    int genVar983;
    genVar983=units.get(ZergUnitLibrary.Queen);
    return genVar983;
  }
  /** 
 * @return the hydralisks
 */
  public int getHydralisks(){
    int genVar984;
    genVar984=units.get(ZergUnitLibrary.Hydralisk);
    return genVar984;
  }
  /** 
 * @return the corruptors
 */
  public int getCorruptors(){
    int genVar985;
    genVar985=units.get(ZergUnitLibrary.Corruptor);
    return genVar985;
  }
  /** 
 * @return the ultralisks
 */
  public int getUltralisks(){
    int genVar986;
    genVar986=units.get(ZergUnitLibrary.Ultralisk);
    return genVar986;
  }
  /** 
 * @return the broodlords
 */
  public int getBroodlords(){
    int genVar987;
    genVar987=units.get(ZergUnitLibrary.Broodlord);
    return genVar987;
  }
  /** 
 * @return the hatcheries
 */
  public int getHatcheries(){
    int genVar988;
    genVar988=buildings.get(ZergBuildingLibrary.Hatchery);
    return genVar988;
  }
  /** 
 * @return the lairs
 */
  public int getLairs(){
    int genVar989;
    genVar989=buildings.get(ZergBuildingLibrary.Lair);
    return genVar989;
  }
  /** 
 * @return the hives
 */
  public int getHives(){
    int genVar990;
    genVar990=buildings.get(ZergBuildingLibrary.Hive);
    return genVar990;
  }
  /** 
 * @return the spawningPools
 */
  public int getSpawningPools(){
    int genVar991;
    genVar991=buildings.get(ZergBuildingLibrary.SpawningPool);
    return genVar991;
  }
  /** 
 * @return the evolutionChambers
 */
  public int getEvolutionChambers(){
    int genVar992;
    genVar992=buildings.get(ZergBuildingLibrary.EvolutionChamber);
    return genVar992;
  }
  /** 
 * @return the roachWarrens
 */
  public int getRoachWarrens(){
    int genVar993;
    genVar993=buildings.get(ZergBuildingLibrary.RoachWarren);
    return genVar993;
  }
  /** 
 * @return the hydraliskDen
 */
  public int getHydraliskDen(){
    int genVar994;
    genVar994=buildings.get(ZergBuildingLibrary.HydraliskDen);
    return genVar994;
  }
  /** 
 * @return the banelingNest
 */
  public int getBanelingNest(){
    int genVar995;
    genVar995=buildings.get(ZergBuildingLibrary.BanelingNest);
    return genVar995;
  }
  /** 
 * @return the infestationPit
 */
  public int getInfestationPit(){
    int genVar996;
    genVar996=buildings.get(ZergBuildingLibrary.InfestationPit);
    return genVar996;
  }
  /** 
 * @return the greaterSpire
 */
  public int getGreaterSpire(){
    int genVar997;
    genVar997=buildings.get(ZergBuildingLibrary.GreaterSpire);
    return genVar997;
  }
  /** 
 * @return the ultraliskCavern
 */
  public int getUltraliskCavern(){
    int genVar998;
    genVar998=buildings.get(ZergBuildingLibrary.UltraliskCavern);
    return genVar998;
  }
  /** 
 * @return the gasExtractors
 */
  public int getGasExtractors(){
    int genVar999;
    genVar999=buildings.get(ZergBuildingLibrary.Extractor);
    return genVar999;
  }
  /** 
 * @return the spire
 */
  public int getSpire(){
    int genVar1000;
    genVar1000=buildings.get(ZergBuildingLibrary.Spire);
    return genVar1000;
  }
  /** 
 * @return the spineCrawlers
 */
  public int getSpineCrawlers(){
    int genVar1001;
    genVar1001=buildings.get(ZergBuildingLibrary.SpineCrawler);
    return genVar1001;
  }
  /** 
 * @return the sporeCrawlers
 */
  public int getSporeCrawlers(){
    int genVar1002;
    genVar1002=buildings.get(ZergBuildingLibrary.SporeCrawler);
    return genVar1002;
  }
  /** 
 * @return the nydusNetwork
 */
  public int getNydusNetwork(){
    int genVar1003;
    genVar1003=buildings.get(ZergBuildingLibrary.NydusNetwork);
    return genVar1003;
  }
  /** 
 * @return the nydusWorm
 */
  public int getNydusWorm(){
    int genVar1004;
    genVar1004=buildings.get(ZergBuildingLibrary.NydusWorm);
    return genVar1004;
  }
}
