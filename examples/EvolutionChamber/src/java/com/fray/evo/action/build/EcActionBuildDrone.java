package com.fray.evo.action.build;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.Unit;
import com.fray.evo.util.ZergUnitLibrary;
public final class EcActionBuildDrone extends EcActionBuildUnit implements Serializable {
  private static final long serialVersionUID=-9015731889232567803L;
  public EcActionBuildDrone(){
    super(ZergUnitLibrary.Drone);
  }
  @Override protected void postExecute(  final EcBuildOrder s,  final GameLog e){
    com.fray.evo.util.Unit genVar1823;
    genVar1823=(Unit)buildable;
    int genVar1824;
    genVar1824=1;
    s.addUnits(genVar1823,genVar1824);
    s.dronesGoingOnMinerals+=1;
    int genVar1825;
    genVar1825=2;
    RunnableAction genVar1826;
    genVar1826=new RunnableAction(){
      @Override public void run(      GameLog e){
        s.dronesGoingOnMinerals--;
        s.dronesOnMinerals++;
      }
    }
;
    s.addFutureAction(genVar1825,genVar1826);
  }
}
