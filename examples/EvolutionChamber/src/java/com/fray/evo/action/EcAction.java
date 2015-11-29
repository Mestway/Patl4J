package com.fray.evo.action;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;
import java.util.List;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcState;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
public abstract class EcAction implements Serializable {
  private static final long serialVersionUID=-2128362561021916042L;
public static class CanExecuteResult {
    public boolean can;
    public boolean somethingChanged;
    public CanExecuteResult(    boolean can,    boolean somethingChanged){
      com.fray.evo.action.EcAction.CanExecuteResult genVar1543;
      genVar1543=this;
      genVar1543.can=can;
      com.fray.evo.action.EcAction.CanExecuteResult genVar1544;
      genVar1544=this;
      genVar1544.somethingChanged=somethingChanged;
    }
  }
  public abstract void execute(  EcBuildOrder s,  GameLog e);
  @Override public String toString(){
    EcAction genVar1545;
    genVar1545=this;
    java.lang.Class genVar1546;
    genVar1546=genVar1545.getClass();
    java.lang.String genVar1547;
    genVar1547=genVar1546.getSimpleName();
    java.lang.String genVar1548;
    genVar1548="EcAction";
    java.lang.String genVar1549;
    genVar1549="";
    java.lang.String genVar1550;
    genVar1550=genVar1547.replace(genVar1548,genVar1549);
    java.lang.String genVar1551;
    genVar1551=messages.getString(genVar1550);
    return genVar1551;
  }
  public String toBuildOrderString(  EcState state){
    EcAction genVar1552;
    genVar1552=this;
    java.lang.Class genVar1553;
    genVar1553=genVar1552.getClass();
    java.lang.String genVar1554;
    genVar1554=genVar1553.getSimpleName();
    java.lang.String genVar1555;
    genVar1555="EcAction";
    java.lang.String genVar1556;
    genVar1556="";
    java.lang.String genVar1557;
    genVar1557=genVar1554.replace(genVar1555,genVar1556);
    java.lang.String genVar1558;
    genVar1558="Build";
    java.lang.String genVar1559;
    genVar1559="";
    java.lang.String genVar1560;
    genVar1560=genVar1557.replace(genVar1558,genVar1559);
    java.lang.String genVar1561;
    genVar1561="Upgrade";
    java.lang.String genVar1562;
    genVar1562="";
    String result;
    result=genVar1560.replace(genVar1561,genVar1562);
    if (state.settings.pullThreeWorkersOnly) {
      java.lang.String genVar1563;
      genVar1563="MineGas";
      java.lang.String genVar1564;
      genVar1564="+3 Drones on gas";
      java.lang.String genVar1565;
      genVar1565=result.replace(genVar1563,genVar1564);
      java.lang.String genVar1566;
      genVar1566="MineMineral";
      java.lang.String genVar1567;
      genVar1567="+3 Drones on minerals";
      result=genVar1565.replace(genVar1566,genVar1567);
    }
 else {
      java.lang.String genVar1568;
      genVar1568="MineGas";
      java.lang.String genVar1569;
      genVar1569="+1 Drone on gas";
      java.lang.String genVar1570;
      genVar1570=result.replace(genVar1568,genVar1569);
      java.lang.String genVar1571;
      genVar1571="MineMineral";
      java.lang.String genVar1572;
      genVar1572="+1 Drone on minerals";
      result=genVar1570.replace(genVar1571,genVar1572);
    }
    return result;
  }
  public CanExecuteResult canExecute(  EcBuildOrder s,  GameLog e){
    EcAction genVar1573;
    genVar1573=this;
    boolean genVar1574;
    genVar1574=genVar1573.isPossible(s);
    if (genVar1574) {
      boolean genVar1575;
      genVar1575=true;
      boolean genVar1576;
      genVar1576=false;
      com.fray.evo.action.EcAction.CanExecuteResult genVar1577;
      genVar1577=new CanExecuteResult(genVar1575,genVar1576);
      return genVar1577;
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
      changed=true;
    }
    s.tick(e);
    boolean genVar1578;
    genVar1578=false;
    com.fray.evo.action.EcAction.CanExecuteResult genVar1579;
    genVar1579=new CanExecuteResult(genVar1578,changed);
    return genVar1579;
  }
  public boolean isInvalid(  EcBuildOrder s){
    boolean genVar1580;
    genVar1580=false;
    return genVar1580;
  }
  public abstract boolean isPossible(  EcBuildOrder s);
  public static Integer findAllele(  List<Class<? extends EcAction>> actionList,  EcAction a){
    int actionIndex;
    actionIndex=actionList.indexOf(a);
    int genVar1581;
    genVar1581=0;
    boolean genVar1582;
    genVar1582=actionIndex < genVar1581;
    if (genVar1582) {
      return null;
    }
 else {
      java.lang.Integer genVar1583;
      genVar1583=Integer.valueOf(actionIndex);
      return genVar1583;
    }
  }
}
