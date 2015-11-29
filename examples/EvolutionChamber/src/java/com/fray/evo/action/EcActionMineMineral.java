package com.fray.evo.action;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
public final class EcActionMineMineral extends EcAction implements Serializable {
  private static final long serialVersionUID=5849476088588414509L;
  @Override public void execute(  final EcBuildOrder s,  final GameLog e){
    if (s.settings.pullThreeWorkersOnly) {
      s.dronesGoingOnMinerals+=3;
      s.dronesOnGas-=3;
    }
 else {
      s.dronesGoingOnMinerals+=1;
      s.dronesOnGas-=1;
    }
    int genVar1584;
    genVar1584=2;
    RunnableAction genVar1585;
    genVar1585=new RunnableAction(){
      @Override public void run(      GameLog e){
        if (s.settings.pullThreeWorkersOnly) {
          if (e.isEnabled())           e.printMessage(s,GameLog.MessageType.Mining," " + messages.getString("3onminerals"));
          s.dronesGoingOnMinerals-=3;
          s.dronesOnMinerals+=3;
        }
 else {
          e.printMessage(s,GameLog.MessageType.Mining," " + messages.getString("1onminerals"));
          s.dronesGoingOnMinerals--;
          s.dronesOnMinerals++;
        }
      }
    }
;
    s.addFutureAction(genVar1584,genVar1585);
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    boolean genVar1586;
    genVar1586=s.dronesOnGas == 0 || s.settings.pullThreeWorkersOnly && s.dronesOnGas < 3;
    boolean genVar1587;
    genVar1587=(genVar1586);
    return genVar1587;
  }
  @Override public boolean isPossible(  EcBuildOrder s){
    EcActionMineMineral genVar1588;
    genVar1588=this;
    boolean genVar1589;
    genVar1589=genVar1588.isInvalid(s);
    boolean genVar1590;
    genVar1590=!genVar1589;
    return genVar1590;
  }
}
