package com.fray.evo.action.build;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.ZergLibrary;
import com.fray.evo.util.ZergUnitLibrary;
public final class EcActionBuildQueen extends EcActionBuildUnit implements Serializable {
  private static final long serialVersionUID=1780688280823177517L;
  public EcActionBuildQueen(){
    super(ZergUnitLibrary.Queen);
  }
  @Override protected void postExecute(  final EcBuildOrder s,  final GameLog e){
    super.postExecute(s,e);
    int genVar1704;
    genVar1704=s.larva.size();
    int genVar1705;
    genVar1705=s.hasQueen.size();
    boolean genVar1706;
    genVar1706=genVar1704 > genVar1705;
    if (genVar1706) {
      EcActionBuildQueen genVar1707;
      genVar1707=this;
      genVar1707.spawnLarva(s,e);
    }
 else {
      int genVar1708;
      genVar1708=5;
      RunnableAction genVar1709;
      genVar1709=new RunnableAction(){
        @Override public void run(        GameLog e){
          if (s.larva.size() > s.hasQueen.size())           spawnLarva(s,e);
 else           s.addFutureAction(5,this);
        }
      }
;
      s.addFutureAction(genVar1708,genVar1709);
    }
  }
  private void spawnLarva(  final EcBuildOrder s,  final GameLog e){
    int hatchWithoutQueen;
    hatchWithoutQueen=s.hasQueen.size();
    int genVar1710;
    genVar1710=s.larva.size();
    boolean genVar1711;
    genVar1711=genVar1710 > hatchWithoutQueen;
    if (genVar1711) {
      boolean genVar1712;
      genVar1712=true;
      s.hasQueen.add(genVar1712);
      final int hatchIndex;
      hatchIndex=hatchWithoutQueen;
      int genVar1713;
      genVar1713=40;
      RunnableAction genVar1714;
      genVar1714=new RunnableAction(){
        @Override public void run(        GameLog e){
          if (e.isEnabled() && s.getLarva() < s.bases() * 19)           e.printMessage(s,GameLog.MessageType.Obtained," @" + messages.getString(ZergLibrary.Hatchery.getName()) + " #"+ (hatchIndex + 1)+ " "+ messages.getString(ZergLibrary.Larva.getName())+ " +"+ (Math.min(19,s.getLarva(hatchIndex) + 2) - s.getLarva(hatchIndex)));
          s.setLarva(hatchIndex,Math.min(19,s.getLarva(hatchIndex) + 2));
          s.addFutureAction(1,new RunnableAction(){
            @Override public void run(            GameLog e){
              if (e.isEnabled() && s.getLarva() < s.bases() * 19)               e.printMessage(s,GameLog.MessageType.Obtained," @" + messages.getString(ZergLibrary.Hatchery.getName()) + " #"+ (hatchIndex + 1)+ " "+ messages.getString(ZergLibrary.Larva.getName())+ " +"+ (Math.min(19,s.getLarva(hatchIndex) + 2) - s.getLarva(hatchIndex)));
              s.setLarva(hatchIndex,Math.min(19,s.getLarva(hatchIndex) + 2));
            }
          }
);
          s.addFutureAction(45,this);
          s.larvaProduction.decrement(hatchIndex);
        }
      }
;
      s.addFutureAction(genVar1713,genVar1714);
    }
 else {
      ;
    }
  }
}
