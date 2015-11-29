package com.fray.evo.action;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.ZergUnitLibrary;
public final class EcActionExtractorTrick extends EcAction implements Serializable {
  private static final long serialVersionUID=8713629742335543863L;
  @Override public void execute(  final EcBuildOrder s,  final GameLog e){
    s.minerals-=25;
    int genVar1519;
    genVar1519=1;
    s.removeUnits(ZergUnitLibrary.Drone,genVar1519);
    s.dronesOnMinerals-=1;
    s.supplyUsed-=1;
    s.extractorsBuilding++;
    int genVar1520;
    genVar1520=2;
    RunnableAction genVar1521;
    genVar1521=new RunnableAction(){
      @Override public void run(      GameLog e){
        if (e.isEnabled())         e.printMessage(s,GameLog.MessageType.Obtained," " + messages.getString("finished.extractortrick"));
        s.minerals+=19;
        s.addUnits(ZergUnitLibrary.Drone,1);
        s.dronesOnMinerals+=1;
        s.supplyUsed+=1;
        s.extractorsBuilding--;
      }
    }
;
    s.addFutureAction(genVar1520,genVar1521);
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    boolean genVar1522;
    genVar1522=s.supplyUsed > s.settings.maximumExtractorTrickSupply;
    if (genVar1522) {
      boolean genVar1523;
      genVar1523=true;
      return genVar1523;
    }
 else {
      ;
    }
    int genVar1524;
    genVar1524=s.getGasExtractors();
    int genVar1525;
    genVar1525=genVar1524 + s.extractorsBuilding;
    int genVar1526;
    genVar1526=s.extractors();
    boolean genVar1527;
    genVar1527=genVar1525 >= genVar1526;
    if (genVar1527) {
      boolean genVar1528;
      genVar1528=true;
      return genVar1528;
    }
 else {
      ;
    }
    int genVar1529;
    genVar1529=s.supply();
    int genVar1530;
    genVar1530=1;
    int genVar1531;
    genVar1531=genVar1529 - genVar1530;
    boolean genVar1532;
    genVar1532=s.supplyUsed < genVar1531;
    if (genVar1532) {
      boolean genVar1533;
      genVar1533=true;
      return genVar1533;
    }
 else {
      ;
    }
    boolean genVar1534;
    genVar1534=false;
    return genVar1534;
  }
  @Override public boolean isPossible(  EcBuildOrder s){
    int genVar1535;
    genVar1535=75;
    boolean genVar1536;
    genVar1536=s.minerals < genVar1535;
    if (genVar1536) {
      boolean genVar1537;
      genVar1537=false;
      return genVar1537;
    }
 else {
      ;
    }
    int genVar1538;
    genVar1538=s.getDrones();
    int genVar1539;
    genVar1539=1;
    boolean genVar1540;
    genVar1540=genVar1538 < genVar1539;
    if (genVar1540) {
      boolean genVar1541;
      genVar1541=false;
      return genVar1541;
    }
 else {
      ;
    }
    boolean genVar1542;
    genVar1542=true;
    return genVar1542;
  }
}
