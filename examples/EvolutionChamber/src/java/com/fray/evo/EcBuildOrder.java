package com.fray.evo;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.EcActionMakeBuildable;
import com.fray.evo.util.ActionList;
import com.fray.evo.util.Building;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RaceLibraries;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.ZergLibrary;
public final class EcBuildOrder extends EcState implements Serializable {
  static final long serialVersionUID=1L;
  public int dronesGoingOnMinerals=6;
  public int dronesGoingOnGas=0;
  public int dronesOnMinerals=0;
  public int dronesOnGas=0;
  public boolean droneIsScouting=false;
  public final HashMap<EcAction,Building> actionBusyIn=new HashMap<EcAction,Building>();
  public ArrayList<ArrayList<EcAction>> madeBusyBy;
  private transient ActionList futureAction;
  public ArrayList<EcAction> actions=new ArrayList<EcAction>();
  public EcBuildOrder(){
    super();
    futureAction=new ActionList();
    com.fray.evo.util.Library<com.fray.evo.util.Building> genVar0;
    genVar0=RaceLibraries.getBuildingLibrary(settings.race);
    ArrayList<Building> buildingList;
    buildingList=genVar0.getList();
    int genVar1;
    genVar1=buildingList.size();
    madeBusyBy=new ArrayList<ArrayList<EcAction>>(genVar1);
    int i=0;
    for (; i < buildingList.size(); ++i) {
      java.util.ArrayList<com.fray.evo.action.EcAction> genVar2;
      genVar2=new ArrayList<EcAction>();
      madeBusyBy.add(genVar2);
    }
    EcBuildOrder genVar3;
    genVar3=this;
    int genVar4;
    genVar4=5;
    RunnableAction genVar5;
    genVar5=new RunnableAction(){
      @Override public void run(      GameLog e){
        dronesOnMinerals+=6;
        dronesGoingOnMinerals-=6;
      }
    }
;
    genVar3.addFutureAction(genVar4,genVar5);
  }
  public EcBuildOrder(  EcState importDestination){
    futureAction=new ActionList();
    com.fray.evo.EcBuildOrder genVar6;
    genVar6=this;
    importDestination.assign(genVar6);
  }
  public void tick(  GameLog e){
    int hatchIndex=0;
    for (; hatchIndex < larva.size(); hatchIndex++) {
      EcBuildOrder genVar7;
      genVar7=this;
      int genVar8;
      genVar8=genVar7.getLarva(hatchIndex);
      int genVar9;
      genVar9=3;
      boolean genVar10;
      genVar10=genVar8 < genVar9;
      if (genVar10) {
        int genVar11;
        genVar11=larvaProduction.get(hatchIndex);
        int genVar12;
        genVar12=15;
        boolean genVar13;
        genVar13=genVar11 == genVar12;
        if (genVar13) {
          boolean genVar14;
          genVar14=e.isEnabled();
          if (genVar14) {
            com.fray.evo.EcBuildOrder genVar15;
            genVar15=this;
            java.lang.String genVar16;
            genVar16=" @";
            java.lang.String genVar17;
            genVar17=ZergLibrary.Hatchery.getName();
            java.lang.String genVar18;
            genVar18=messages.getString(genVar17);
            java.lang.String genVar19;
            genVar19=" #";
            int genVar20;
            genVar20=1;
            int genVar21;
            genVar21=hatchIndex + genVar20;
            int genVar22;
            genVar22=(genVar21);
            java.lang.String genVar23;
            genVar23=" ";
            java.lang.String genVar24;
            genVar24=ZergLibrary.Larva.getName();
            java.lang.String genVar25;
            genVar25=messages.getString(genVar24);
            java.lang.String genVar26;
            genVar26=" +1";
            java.lang.String genVar27;
            genVar27=genVar16 + genVar18 + genVar19+ genVar22+ genVar23+ genVar25+ genVar26;
            e.printMessage(genVar15,GameLog.MessageType.Obtained,genVar27);
          }
 else {
            ;
          }
          EcBuildOrder genVar28;
          genVar28=this;
          genVar28.incrementLarva(hatchIndex);
          int genVar29;
          genVar29=0;
          larvaProduction.set(hatchIndex,genVar29);
        }
 else {
          ;
        }
        larvaProduction.increment(hatchIndex);
      }
 else {
        ;
      }
    }
    EcBuildOrder genVar30;
    genVar30=this;
    double mins;
    mins=genVar30.mineMinerals();
    minerals+=mins;
    totalMineralsMined+=mins;
    EcBuildOrder genVar31;
    genVar31=this;
    gas+=genVar31.mineGas();
    boolean genVar32;
    genVar32=!droneIsScouting && scoutDrone != 0 && seconds >= scoutDrone;
    if (genVar32) {
      int genVar33;
      genVar33=0;
      boolean genVar34;
      genVar34=dronesGoingOnMinerals > genVar33;
      if (genVar34) {
        droneIsScouting=true;
        dronesGoingOnMinerals--;
        boolean genVar35;
        genVar35=e.isEnabled();
        if (genVar35) {
          com.fray.evo.EcBuildOrder genVar36;
          genVar36=this;
          java.lang.String genVar37;
          genVar37=" +1 Scouting Drone";
          e.printMessage(genVar36,GameLog.MessageType.Scout,genVar37);
        }
 else {
          ;
        }
      }
 else {
        int genVar38;
        genVar38=0;
        boolean genVar39;
        genVar39=dronesOnMinerals > genVar38;
        if (genVar39) {
          droneIsScouting=true;
          dronesOnMinerals--;
          boolean genVar40;
          genVar40=e.isEnabled();
          if (genVar40) {
            com.fray.evo.EcBuildOrder genVar41;
            genVar41=this;
            java.lang.String genVar42;
            genVar42=" +1 Scouting Drone";
            e.printMessage(genVar41,GameLog.MessageType.Scout,genVar42);
          }
 else {
            ;
          }
        }
 else {
          ;
        }
      }
    }
 else {
      ;
    }
  }
  @Override public EcBuildOrder clone() throws CloneNotSupportedException {
    EcBuildOrder s;
    s=new EcBuildOrder();
    EcBuildOrder genVar43;
    genVar43=this;
    genVar43.assign(s);
    return s;
  }
  private void assign(  final EcBuildOrder s){
    s.dronesGoingOnMinerals=dronesGoingOnMinerals;
    s.dronesGoingOnGas=dronesGoingOnGas;
    s.dronesOnMinerals=dronesOnMinerals;
    s.dronesOnGas=dronesOnGas;
    super.assign(s);
  }
  @Override public String toString(){
    EcBuildOrder genVar44;
    genVar44=this;
    java.lang.String genVar45;
    genVar45=genVar44.toUnitsOnlyString();
    java.lang.String genVar46;
    genVar46="\n";
    java.lang.String genVar47;
    genVar47=" ";
    java.lang.String genVar48;
    genVar48=genVar45.replaceAll(genVar46,genVar47);
    return genVar48;
  }
  public String toShortString(){
    java.lang.String genVar49;
    genVar49="short.time";
    java.lang.String genVar50;
    genVar50=messages.getString(genVar49);
    EcBuildOrder genVar51;
    genVar51=this;
    java.lang.String genVar52;
    genVar52=genVar51.timestamp();
    java.lang.String genVar53;
    genVar53="\t";
    java.lang.String genVar54;
    genVar54="short.minerals";
    java.lang.String genVar55;
    genVar55=messages.getString(genVar54);
    java.lang.String genVar56;
    genVar56=":";
    int genVar57;
    genVar57=(int)minerals;
    java.lang.String genVar58;
    genVar58="\t";
    java.lang.String genVar59;
    genVar59="short.gas";
    java.lang.String genVar60;
    genVar60=messages.getString(genVar59);
    java.lang.String genVar61;
    genVar61=":";
    int genVar62;
    genVar62=(int)gas;
    java.lang.String genVar63;
    genVar63="\t";
    java.lang.String genVar64;
    genVar64="short.larva";
    java.lang.String genVar65;
    genVar65=messages.getString(genVar64);
    java.lang.String genVar66;
    genVar66=":";
    EcBuildOrder genVar67;
    genVar67=this;
    int genVar68;
    genVar68=genVar67.getLarva();
    java.lang.String genVar69;
    genVar69="\t";
    java.lang.String genVar70;
    genVar70="short.supply";
    java.lang.String genVar71;
    genVar71=messages.getString(genVar70);
    java.lang.String genVar72;
    genVar72=":";
    int genVar73;
    genVar73=(int)supplyUsed;
    int genVar74;
    genVar74=(genVar73);
    java.lang.String genVar75;
    genVar75="/";
    EcBuildOrder genVar76;
    genVar76=this;
    int genVar77;
    genVar77=genVar76.supply();
    java.lang.String genVar78;
    genVar78=genVar50 + genVar52 + genVar53+ genVar55+ genVar56+ genVar57+ genVar58+ genVar60+ genVar61+ genVar62+ genVar63+ genVar65+ genVar66+ genVar68+ genVar69+ genVar71+ genVar72+ genVar74+ genVar75+ genVar77;
    String genVar79;
    genVar79=(genVar78);
    return genVar79;
  }
  public ArrayList<EcAction> getActions(){
    return actions;
  }
  public void addAction(  EcAction ecActionBuildDrone){
    actions.add(ecActionBuildDrone);
  }
  public void addFutureAction(  int time,  RunnableAction runnable){
    time=seconds + time;
    futureAction.put(time,runnable);
    actionLength++;
  }
  public RunnableAction getFutureAction(  int time){
    RunnableAction result;
    result=futureAction.get(time);
    return result;
  }
  public boolean nothingGoingToHappen(){
    boolean genVar80;
    genVar80=futureAction.hasFutureActions();
    return genVar80;
  }
  public void consumeLarva(  final GameLog e){
    int highestLarvaHatch;
    highestLarvaHatch=0;
    int highestLarva;
    highestLarva=0;
    int i=0;
    for (; i < larva.size(); i++) {
      int genVar81;
      genVar81=larva.get(i);
      boolean genVar82;
      genVar82=genVar81 > highestLarva;
      if (genVar82) {
        highestLarvaHatch=i;
        highestLarva=larva.get(i);
      }
 else {
        ;
      }
    }
    int finalHighestLarvaHatch;
    finalHighestLarvaHatch=highestLarvaHatch;
    EcBuildOrder genVar83;
    genVar83=this;
    genVar83.decrementLarva(finalHighestLarvaHatch);
  }
  public boolean hasSupply(  double i){
    double genVar84;
    genVar84=supplyUsed + i;
    EcBuildOrder genVar85;
    genVar85=this;
    int genVar86;
    genVar86=genVar85.supply();
    boolean genVar87;
    genVar87=genVar84 <= genVar86;
    if (genVar87) {
      boolean genVar88;
      genVar88=true;
      return genVar88;
    }
 else {
      ;
    }
    boolean genVar89;
    genVar89=false;
    return genVar89;
  }
  public int mineralPatches(){
    EcBuildOrder genVar90;
    genVar90=this;
    int genVar91;
    genVar91=genVar90.bases();
    int genVar92;
    genVar92=8;
    int genVar93;
    genVar93=genVar91 * genVar92;
    return genVar93;
  }
  int[] patches=new int[24];
  public int extractorsBuilding=0;
  public int hatcheriesBuilding=0;
  public int spawningPoolsInUse=0;
  public int roachWarrensInUse=0;
  public int infestationPitInUse=0;
  public int nydusNetworkInUse=0;
  private static double[][] cachedMineralsMined=new double[200][200];
  public double mineMinerals(){
    EcBuildOrder genVar94;
    genVar94=this;
    int mineralPatches;
    mineralPatches=genVar94.mineralPatches();
    int genVar95;
    genVar95=0;
    boolean genVar96;
    genVar96=dronesOnMinerals <= genVar95;
    int genVar97;
    genVar97=0;
    boolean genVar98;
    genVar98=mineralPatches <= genVar97;
    boolean genVar99;
    genVar99=genVar96 || genVar98;
    if (genVar99) {
      int genVar100;
      genVar100=0;
      return genVar100;
    }
 else {
      ;
    }
    int genVar101;
    genVar101=200;
    boolean genVar102;
    genVar102=dronesOnMinerals >= genVar101;
    int genVar103;
    genVar103=200;
    boolean genVar104;
    genVar104=mineralPatches >= genVar103;
    boolean genVar105;
    genVar105=genVar102 || genVar104;
    if (genVar105) {
      EcBuildOrder genVar106;
      genVar106=this;
      double genVar107;
      genVar107=genVar106.mineMineralsImpl();
      return genVar107;
    }
 else {
      ;
    }
    double[] genVar108;
    genVar108=cachedMineralsMined[mineralPatches];
    double genVar109;
    genVar109=genVar108[dronesOnMinerals];
    int genVar110;
    genVar110=0;
    boolean genVar111;
    genVar111=genVar109 == genVar110;
    if (genVar111) {
      double[] genVar112;
      genVar112=cachedMineralsMined[mineralPatches];
      EcBuildOrder genVar113;
      genVar113=this;
      genVar112[dronesOnMinerals]=genVar113.mineMineralsImpl();
    }
 else {
      ;
    }
    double[] genVar114;
    genVar114=cachedMineralsMined[mineralPatches];
    double genVar115;
    genVar115=genVar114[dronesOnMinerals];
    return genVar115;
  }
  /** 
 * Mines minerals on all bases perfectly per one second. okay, we got x patches, and y drones. so the amount of drones per patch should be (y - (y%x))/x the drones%patches represent the amount of patches that have one worker more than the average. also account for the far and near patches here, assuming half of the workes go to far patches and other half onto the near ones. 
 */
  private double mineMineralsImpl(){
    int drones;
    drones=dronesOnMinerals;
    EcBuildOrder genVar116;
    genVar116=this;
    int mineralPatches;
    mineralPatches=genVar116.mineralPatches();
    int genVar117;
    genVar117=0;
    boolean genVar118;
    genVar118=drones == genVar117;
    int genVar119;
    genVar119=0;
    boolean genVar120;
    genVar120=mineralPatches == genVar119;
    boolean genVar121;
    genVar121=genVar118 || genVar120;
    if (genVar121) {
      int genVar122;
      genVar122=0;
      return genVar122;
    }
 else {
      ;
    }
    int droneOverflow;
    droneOverflow=drones % mineralPatches;
    int genVar123;
    genVar123=drones - droneOverflow;
    int genVar124;
    genVar124=(genVar123);
    int avgPatchLoad;
    avgPatchLoad=genVar124 / mineralPatches;
    int genVar125;
    genVar125=3;
    boolean genVar126;
    genVar126=avgPatchLoad >= genVar125;
    if (genVar126) {
      avgPatchLoad=3;
      droneOverflow=0;
    }
 else {
      ;
    }
    boolean genVar127;
    genVar127=mineralPatches > drones;
    boolean genVar128;
    genVar128=(genVar127);
    int genVar129;
    genVar129=0;
    int genVar130;
    genVar130=mineralPatches - droneOverflow;
    int genVar131;
    genVar131=(genVar130);
    int lowerMiningWorkload;
    lowerMiningWorkload=genVar128 ? genVar129 : genVar131;
    int higherMiningWorload;
    higherMiningWorload=droneOverflow;
    float genVar132;
    genVar132=2.0f;
    float genVar133;
    genVar133=lowerMiningWorkload / genVar132;
    int farPatchesLowLoad;
    farPatchesLowLoad=Math.round(genVar133);
    int nearPatchesLowLoad;
    nearPatchesLowLoad=lowerMiningWorkload - farPatchesLowLoad;
    float genVar134;
    genVar134=2.0f;
    float genVar135;
    genVar135=higherMiningWorload / genVar134;
    int nearPatchesHighLoad;
    nearPatchesHighLoad=Math.round(genVar135);
    int farPatchesHighLoad;
    farPatchesHighLoad=higherMiningWorload - nearPatchesHighLoad;
    double mineralsMined;
    mineralsMined=0.0;
switch (avgPatchLoad) {
case 0:
      mineralsMined+=nearPatchesHighLoad * 45.0 / 60.0;
    mineralsMined+=farPatchesHighLoad * 35.0 / 60.0;
  break;
case 1:
mineralsMined+=nearPatchesLowLoad * 45.0 / 60.0;
mineralsMined+=farPatchesLowLoad * 35.0 / 60.0;
mineralsMined+=nearPatchesHighLoad * 90.0 / 60.0;
mineralsMined+=farPatchesHighLoad * 75.0 / 60.0;
break;
case 2:
mineralsMined+=nearPatchesLowLoad * 90.0 / 60.0;
mineralsMined+=farPatchesLowLoad * 75.0 / 60.0;
mineralsMined+=nearPatchesHighLoad * 102.0 / 60.0;
mineralsMined+=farPatchesHighLoad * 100.0 / 60.0;
break;
case 3:
mineralsMined+=nearPatchesLowLoad * 102.0 / 60.0;
mineralsMined+=farPatchesLowLoad * 100.0 / 60.0;
}
return mineralsMined;
}
private static double[][] cachedGasMined=new double[200][200];
public double mineGas(){
EcBuildOrder genVar136;
genVar136=this;
int gasExtra;
gasExtra=genVar136.getGasExtractors();
int genVar137;
genVar137=0;
boolean genVar138;
genVar138=gasExtra == genVar137;
int genVar139;
genVar139=0;
boolean genVar140;
genVar140=dronesOnGas == genVar139;
boolean genVar141;
genVar141=genVar138 || genVar140;
if (genVar141) {
int genVar142;
genVar142=0;
return genVar142;
}
 else {
;
}
int genVar143;
genVar143=200;
boolean genVar144;
genVar144=gasExtra >= genVar143;
int genVar145;
genVar145=200;
boolean genVar146;
genVar146=dronesOnGas >= genVar145;
boolean genVar147;
genVar147=genVar144 || genVar146;
if (genVar147) {
EcBuildOrder genVar148;
genVar148=this;
double genVar149;
genVar149=genVar148.mineGasImpl();
return genVar149;
}
 else {
;
}
double[] genVar150;
genVar150=cachedGasMined[gasExtra];
double genVar151;
genVar151=genVar150[dronesOnGas];
int genVar152;
genVar152=0;
boolean genVar153;
genVar153=genVar151 == genVar152;
if (genVar153) {
double[] genVar154;
genVar154=cachedGasMined[gasExtra];
EcBuildOrder genVar155;
genVar155=this;
genVar154[dronesOnGas]=genVar155.mineGasImpl();
}
 else {
;
}
double[] genVar156;
genVar156=cachedGasMined[gasExtra];
double genVar157;
genVar157=genVar156[dronesOnGas];
return genVar157;
}
private double mineGasImpl(){
int drones;
drones=dronesOnGas;
int[] extractors;
extractors=new int[Math.min(getGasExtractors(),bases() * 2)];
int i=0;
for (; i < extractors.length; i++) {
extractors[i]=0;
}
i=0;
for (; i < extractors.length; i++) {
int genVar158;
genVar158=0;
boolean genVar159;
genVar159=drones > genVar158;
if (genVar159) {
int genVar160;
genVar160=extractors[i];
genVar160++;
drones--;
}
 else {
;
}
}
i=0;
for (; i < extractors.length; i++) {
int genVar161;
genVar161=0;
boolean genVar162;
genVar162=drones > genVar161;
if (genVar162) {
int genVar163;
genVar163=extractors[i];
genVar163++;
drones--;
}
 else {
;
}
}
i=0;
for (; i < extractors.length; i++) {
int genVar164;
genVar164=0;
boolean genVar165;
genVar165=drones > genVar164;
if (genVar165) {
int genVar166;
genVar166=extractors[i];
genVar166++;
drones--;
}
 else {
;
}
}
double gasMined;
gasMined=0.0;
i=0;
for (; i < extractors.length; i++) {
int genVar167;
genVar167=extractors[i];
int genVar168;
genVar168=0;
boolean genVar169;
genVar169=genVar167 == genVar168;
if (genVar169) {
;
}
 else {
int genVar170;
genVar170=extractors[i];
int genVar171;
genVar171=1;
boolean genVar172;
genVar172=genVar170 == genVar171;
if (genVar172) {
double genVar173;
genVar173=38.0;
double genVar174;
genVar174=60.0;
gasMined+=genVar173 / genVar174;
}
 else {
int genVar175;
genVar175=extractors[i];
int genVar176;
genVar176=2;
boolean genVar177;
genVar177=genVar175 == genVar176;
if (genVar177) {
double genVar178;
genVar178=82.0;
double genVar179;
genVar179=60.0;
gasMined+=genVar178 / genVar179;
}
 else {
double genVar180;
genVar180=114.0;
double genVar181;
genVar181=60.0;
gasMined+=genVar180 / genVar181;
}
}
}
}
return gasMined;
}
public String timestampIncremented(int increment){
int incrementedSeconds;
incrementedSeconds=seconds + increment;
int genVar182;
genVar182=60;
int genVar183;
genVar183=incrementedSeconds / genVar182;
java.lang.String genVar184;
genVar184=":";
java.lang.String genVar185;
genVar185=genVar183 + genVar184;
int genVar186;
genVar186=60;
int genVar187;
genVar187=incrementedSeconds % genVar186;
int genVar188;
genVar188=10;
boolean genVar189;
genVar189=genVar187 < genVar188;
java.lang.String genVar190;
genVar190="0";
java.lang.String genVar191;
genVar191="";
java.lang.String genVar192;
genVar192=genVar189 ? genVar190 : genVar191;
String genVar193;
genVar193=(genVar192);
java.lang.String genVar194;
genVar194=genVar185 + genVar193;
int genVar195;
genVar195=60;
int genVar196;
genVar196=incrementedSeconds % genVar195;
java.lang.String genVar197;
genVar197=genVar194 + genVar196;
return genVar197;
}
public int extractors(){
EcBuildOrder genVar198;
genVar198=this;
int genVar199;
genVar199=genVar198.bases();
int genVar200;
genVar200=genVar199 + hatcheriesBuilding;
int genVar201;
genVar201=(genVar200);
int genVar202;
genVar202=2;
int genVar203;
genVar203=genVar201 * genVar202;
return genVar203;
}
public int getMineableGasExtractors(){
EcBuildOrder genVar204;
genVar204=this;
int genVar205;
genVar205=genVar204.bases();
int genVar206;
genVar206=2;
int genVar207;
genVar207=genVar205 * genVar206;
EcBuildOrder genVar208;
genVar208=this;
int genVar209;
genVar209=genVar208.getGasExtractors();
int genVar210;
genVar210=Math.min(genVar207,genVar209);
return genVar210;
}
public void makeBuildingBusy(Building consumes,EcActionMakeBuildable action){
action.makeBusy(madeBusyBy,actionBusyIn,consumes,buildings);
}
public void makeBuildingNotBusy(EcActionMakeBuildable action){
action.makeNotBusy(madeBusyBy,actionBusyIn);
}
public boolean doesNonBusyExist(Building building){
EcBuildOrder genVar211;
genVar211=this;
boolean genVar212;
genVar212=genVar211.doesNonBusyReallyExist(building);
boolean genVar213;
genVar213=!genVar212;
if (genVar213) {
boolean genVar214;
genVar214=building == ZergBuildingLibrary.Hatchery;
if (genVar214) {
EcBuildOrder genVar215;
genVar215=this;
boolean genVar216;
genVar216=genVar215.doesNonBusyExist(ZergBuildingLibrary.Lair);
return genVar216;
}
 else {
boolean genVar217;
genVar217=building == ZergBuildingLibrary.Lair;
if (genVar217) {
EcBuildOrder genVar218;
genVar218=this;
boolean genVar219;
genVar219=genVar218.doesNonBusyExist(ZergBuildingLibrary.Hive);
return genVar219;
}
 else {
boolean genVar220;
genVar220=building == ZergBuildingLibrary.Spire;
if (genVar220) {
EcBuildOrder genVar221;
genVar221=this;
boolean genVar222;
genVar222=genVar221.doesNonBusyExist(ZergBuildingLibrary.GreaterSpire);
return genVar222;
}
 else {
;
}
}
}
boolean genVar223;
genVar223=false;
return genVar223;
}
 else {
boolean genVar224;
genVar224=true;
return genVar224;
}
}
public boolean doesNonBusyReallyExist(Building building){
int genVar225;
genVar225=building.getId();
java.util.ArrayList<com.fray.evo.action.EcAction> genVar226;
genVar226=madeBusyBy.get(genVar225);
int genVar227;
genVar227=genVar226.size();
int genVar228;
genVar228=buildings.get(building);
boolean genVar229;
genVar229=genVar227 < genVar228;
return genVar229;
}
}
