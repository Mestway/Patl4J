package com.fray.evo.action;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
public final class EcActionWait extends EcAction implements Serializable {
  private static final long serialVersionUID=-2361841555630773088L;
  boolean go=false;
  @Override public void execute(  EcBuildOrder s,  GameLog e){
    s.waits+=1;
  }
  @Override public EcAction.CanExecuteResult canExecute(  EcBuildOrder s,  GameLog e){
    EcActionWait genVar1688;
    genVar1688=this;
    boolean genVar1689;
    genVar1689=genVar1688.isPossible(s);
    if (genVar1689) {
      boolean genVar1690;
      genVar1690=true;
      boolean genVar1691;
      genVar1691=false;
      com.fray.evo.action.EcAction.CanExecuteResult genVar1692;
      genVar1692=new CanExecuteResult(genVar1690,genVar1691);
      return genVar1692;
    }
 else {
      ;
    }
    s.seconds+=1;
    RunnableAction futureAction;
    boolean changed;
    changed=false;
    while ((futureAction=s.getFutureAction(s.seconds)) != null) {
      futureAction.run(e);
      go=true;
      changed=true;
    }
    s.tick(e);
    boolean genVar1693;
    genVar1693=true;
    com.fray.evo.action.EcAction.CanExecuteResult genVar1694;
    genVar1694=new CanExecuteResult(genVar1693,changed);
    return genVar1694;
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    boolean genVar1695;
    genVar1695=s.nothingGoingToHappen();
    if (genVar1695) {
      boolean genVar1696;
      genVar1696=true;
      return genVar1696;
    }
 else {
      ;
    }
    boolean genVar1697;
    genVar1697=super.isInvalid(s);
    return genVar1697;
  }
  @Override public boolean isPossible(  EcBuildOrder s){
    return go;
  }
}
