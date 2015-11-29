package com.fray.evo.action;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
public final class EcActionMineGas extends EcAction implements Serializable {
  private static final long serialVersionUID=7000036942224791220L;
  @Override public void execute(  final EcBuildOrder s,  final GameLog e){
    if (s.settings.pullThreeWorkersOnly) {
      s.dronesGoingOnGas+=3;
      s.dronesOnMinerals-=3;
    }
 else {
      s.dronesGoingOnGas+=1;
      s.dronesOnMinerals-=1;
    }
    int genVar1613;
    genVar1613=2;
    RunnableAction genVar1614;
    genVar1614=new RunnableAction(){
      @Override public void run(      GameLog e){
        if (s.settings.pullThreeWorkersOnly) {
          if (e.isEnabled())           e.printMessage(s,GameLog.MessageType.Mining," " + messages.getString("3ongas"));
          s.dronesGoingOnGas-=3;
          s.dronesOnGas+=3;
        }
 else {
          if (e.isEnabled())           e.printMessage(s,GameLog.MessageType.Mining," " + messages.getString("1ongas"));
          s.dronesGoingOnGas--;
          s.dronesOnGas++;
        }
      }
    }
;
    s.addFutureAction(genVar1613,genVar1614);
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    EcActionMineGas genVar1615;
    genVar1615=this;
    boolean genVar1616;
    genVar1616=genVar1615.isPossible(s);
    boolean genVar1617;
    genVar1617=!genVar1616;
    return genVar1617;
  }
  @Override public boolean isPossible(  EcBuildOrder s){
    int genVar1618;
    genVar1618=s.dronesOnGas + s.dronesGoingOnGas;
    int genVar1619;
    genVar1619=(genVar1618);
    int genVar1620;
    genVar1620=3;
    int genVar1621;
    genVar1621=s.getMineableGasExtractors();
    int genVar1622;
    genVar1622=genVar1620 * genVar1621;
    boolean genVar1623;
    genVar1623=genVar1619 >= genVar1622;
    if (genVar1623) {
      boolean genVar1624;
      genVar1624=false;
      return genVar1624;
    }
 else {
      ;
    }
    boolean genVar1625;
    genVar1625=s.dronesOnMinerals == 0 || s.settings.pullThreeWorkersOnly && s.dronesOnMinerals < 3;
    if (genVar1625) {
      boolean genVar1626;
      genVar1626=false;
      return genVar1626;
    }
 else {
      ;
    }
    boolean genVar1627;
    genVar1627=true;
    return genVar1627;
  }
}
