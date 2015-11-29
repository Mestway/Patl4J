package com.fray.evo.action.build;
import java.io.Serializable;
import java.util.ArrayList;
import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.Unit;
import com.fray.evo.util.ZergUnitLibrary;
import com.fray.evo.util.Upgrade;
public abstract class EcActionBuildUnit extends EcActionBuild implements Serializable {
  private static final long serialVersionUID=6519230186320700621L;
  public double supply;
  public boolean consumeLarva;
  public EcActionBuildUnit(  Unit unit){
    super(unit);
    com.fray.evo.action.build.EcActionBuildUnit genVar1715;
    genVar1715=this;
    genVar1715.supply=unit.getSupply();
    com.fray.evo.action.build.EcActionBuildUnit genVar1716;
    genVar1716=this;
    com.fray.evo.util.Buildable genVar1717;
    genVar1717=unit.getConsumes();
    boolean genVar1718;
    genVar1718=ZergUnitLibrary.Larva == genVar1717;
    genVar1716.consumeLarva=(genVar1718);
  }
  @Override public void execute(  final EcBuildOrder s,  final GameLog e){
    EcActionBuildUnit genVar1719;
    genVar1719=this;
    s.minerals-=genVar1719.getMinerals();
    EcActionBuildUnit genVar1720;
    genVar1720=this;
    s.gas-=genVar1720.getGas();
    EcActionBuildUnit genVar1721;
    genVar1721=this;
    double genVar1722;
    genVar1722=genVar1721.consumesUnitSupply();
    s.supplyUsed+=supply - genVar1722;
    if (consumeLarva) {
      s.consumeLarva(e);
    }
 else {
      ;
    }
    EcActionBuildUnit genVar1723;
    genVar1723=this;
    genVar1723.preExecute(s);
    EcActionBuildUnit genVar1724;
    genVar1724=this;
    int genVar1725;
    genVar1725=genVar1724.getTime();
    RunnableAction genVar1726;
    genVar1726=new RunnableAction(){
      @Override public void run(      GameLog e){
        obtainOne(s,e);
        postExecute(s,e);
      }
    }
;
    s.addFutureAction(genVar1725,genVar1726);
  }
  private double consumesUnitSupply(){
    EcActionBuildUnit genVar1727;
    genVar1727=this;
    com.fray.evo.util.Buildable genVar1728;
    genVar1728=genVar1727.getConsumes();
    boolean genVar1729;
    genVar1729=genVar1728 != null;
    if (genVar1729) {
      EcActionBuildUnit genVar1730;
      genVar1730=this;
      com.fray.evo.util.Buildable genVar1731;
      genVar1731=genVar1730.getConsumes();
      Unit consumes;
      consumes=(Unit)genVar1731;
      boolean genVar1732;
      genVar1732=consumes == ZergUnitLibrary.Zergling;
      if (genVar1732) {
        double genVar1733;
        genVar1733=0.5;
        return genVar1733;
      }
 else {
        ;
      }
      double genVar1734;
      genVar1734=consumes.getSupply();
      return genVar1734;
    }
 else {
      ;
    }
    int genVar1735;
    genVar1735=0;
    return genVar1735;
  }
  @Override protected final boolean isPossibleResources(  EcBuildOrder s){
    int genVar1736;
    genVar1736=0;
    boolean genVar1737;
    genVar1737=supply > genVar1736;
    if (genVar1737) {
      EcActionBuildUnit genVar1738;
      genVar1738=this;
      double genVar1739;
      genVar1739=genVar1738.consumesUnitSupply();
      double genVar1740;
      genVar1740=supply - genVar1739;
      boolean genVar1741;
      genVar1741=s.hasSupply(genVar1740);
      boolean genVar1742;
      genVar1742=!genVar1741;
      if (genVar1742) {
        boolean genVar1743;
        genVar1743=false;
        return genVar1743;
      }
 else {
        ;
      }
    }
 else {
      ;
    }
    if (consumeLarva) {
      int genVar1744;
      genVar1744=s.getLarva();
      int genVar1745;
      genVar1745=1;
      boolean genVar1746;
      genVar1746=genVar1744 < genVar1745;
      if (genVar1746) {
        boolean genVar1747;
        genVar1747=false;
        return genVar1747;
      }
 else {
        ;
      }
    }
 else {
      ;
    }
    EcActionBuildUnit genVar1748;
    genVar1748=this;
    int genVar1749;
    genVar1749=genVar1748.getMinerals();
    boolean genVar1750;
    genVar1750=s.minerals < genVar1749;
    if (genVar1750) {
      boolean genVar1751;
      genVar1751=false;
      return genVar1751;
    }
 else {
      ;
    }
    EcActionBuildUnit genVar1752;
    genVar1752=this;
    int genVar1753;
    genVar1753=genVar1752.getGas();
    boolean genVar1754;
    genVar1754=s.gas < genVar1753;
    if (genVar1754) {
      boolean genVar1755;
      genVar1755=false;
      return genVar1755;
    }
 else {
      ;
    }
    boolean genVar1756;
    genVar1756=true;
    return genVar1756;
  }
  @Override public boolean isPossible(  EcBuildOrder s){
    EcActionBuildUnit genVar1757;
    genVar1757=this;
    Buildable consumes;
    consumes=genVar1757.getConsumes();
    boolean genVar1758;
    genVar1758=consumes != ZergUnitLibrary.Larva;
    if (genVar1758) {
      boolean genVar1759;
      genVar1759=consumes instanceof Unit && s.getUnitCount((Unit)consumes) < 1;
      if (genVar1759) {
        boolean genVar1760;
        genVar1760=false;
        return genVar1760;
      }
 else {
        ;
      }
    }
 else {
      ;
    }
    EcActionBuildUnit genVar1761;
    genVar1761=this;
    boolean genVar1762;
    genVar1762=genVar1761.isPossibleResources(s);
    return genVar1762;
  }
  protected void postExecute(  EcBuildOrder s,  GameLog e){
    com.fray.evo.util.Unit genVar1763;
    genVar1763=(Unit)buildable;
    Unit genVar1764;
    genVar1764=(genVar1763);
    Building builtFrom;
    builtFrom=genVar1764.getBuiltFrom();
    boolean genVar1765;
    genVar1765=builtFrom != null;
    if (genVar1765) {
      com.fray.evo.action.build.EcActionBuildUnit genVar1766;
      genVar1766=this;
      s.makeBuildingNotBusy(genVar1766);
    }
 else {
      ;
    }
    com.fray.evo.util.Unit genVar1767;
    genVar1767=(Unit)buildable;
    int genVar1768;
    genVar1768=1;
    s.addUnits(genVar1767,genVar1768);
  }
  protected void preExecute(  EcBuildOrder s){
    com.fray.evo.util.Unit genVar1769;
    genVar1769=(Unit)buildable;
    Unit genVar1770;
    genVar1770=(genVar1769);
    Building builtFrom;
    builtFrom=genVar1770.getBuiltFrom();
    boolean genVar1771;
    genVar1771=builtFrom != null;
    if (genVar1771) {
      com.fray.evo.action.build.EcActionBuildUnit genVar1772;
      genVar1772=this;
      s.makeBuildingBusy(builtFrom,genVar1772);
    }
 else {
      ;
    }
    EcActionBuildUnit genVar1773;
    genVar1773=this;
    Buildable consumes;
    consumes=genVar1773.getConsumes();
    boolean genVar1774;
    genVar1774=consumes != null && getConsumes() != ZergUnitLibrary.Larva;
    if (genVar1774) {
      EcActionBuildUnit genVar1775;
      genVar1775=this;
      com.fray.evo.util.Buildable genVar1776;
      genVar1776=genVar1775.getConsumes();
      com.fray.evo.util.Unit genVar1777;
      genVar1777=(Unit)genVar1776;
      int genVar1778;
      genVar1778=1;
      s.removeUnits(genVar1777,genVar1778);
    }
 else {
      ;
    }
  }
  @Override public boolean isInvalid(  EcBuildOrder s){
    com.fray.evo.util.Unit genVar1779;
    genVar1779=(Unit)buildable;
    Unit genVar1780;
    genVar1780=(genVar1779);
    double genVar1781;
    genVar1781=genVar1780.getSupply();
    boolean genVar1782;
    genVar1782=s.hasSupply(genVar1781);
    boolean genVar1783;
    genVar1783=!genVar1782;
    if (genVar1783) {
      boolean genVar1784;
      genVar1784=true;
      return genVar1784;
    }
 else {
      ;
    }
    com.fray.evo.util.Unit genVar1785;
    genVar1785=(Unit)buildable;
    Unit genVar1786;
    genVar1786=(genVar1785);
    ArrayList<Buildable> reqs;
    reqs=genVar1786.getRequirement();
    int i=0;
    for (; i < reqs.size(); i++) {
      Buildable req;
      req=reqs.get(i);
      boolean genVar1787;
      genVar1787=req instanceof Building;
      if (genVar1787) {
        com.fray.evo.util.Building genVar1788;
        genVar1788=(Building)req;
        boolean genVar1789;
        genVar1789=s.isBuilding(genVar1788);
        boolean genVar1790;
        genVar1790=!genVar1789;
        if (genVar1790) {
          boolean genVar1791;
          genVar1791=true;
          return genVar1791;
        }
 else {
          ;
        }
      }
 else {
        ;
      }
      boolean genVar1792;
      genVar1792=req instanceof Unit;
      if (genVar1792) {
        com.fray.evo.util.Unit genVar1793;
        genVar1793=(Unit)req;
        int genVar1794;
        genVar1794=s.getUnitCount(genVar1793);
        int genVar1795;
        genVar1795=1;
        boolean genVar1796;
        genVar1796=genVar1794 < genVar1795;
        if (genVar1796) {
          boolean genVar1797;
          genVar1797=true;
          return genVar1797;
        }
 else {
          ;
        }
      }
 else {
        ;
      }
      boolean genVar1798;
      genVar1798=req instanceof Upgrade;
      if (genVar1798) {
        com.fray.evo.util.Upgrade genVar1799;
        genVar1799=(Upgrade)req;
        boolean genVar1800;
        genVar1800=s.isUpgrade(genVar1799);
        boolean genVar1801;
        genVar1801=!genVar1800;
        if (genVar1801) {
          boolean genVar1802;
          genVar1802=true;
          return genVar1802;
        }
 else {
          ;
        }
      }
 else {
        ;
      }
    }
    com.fray.evo.util.Unit genVar1803;
    genVar1803=(Unit)buildable;
    Unit genVar1804;
    genVar1804=(genVar1803);
    Building builtFrom;
    builtFrom=genVar1804.getBuiltFrom();
    boolean genVar1805;
    genVar1805=builtFrom != null && !s.doesNonBusyExist(((Unit)buildable).getBuiltFrom());
    if (genVar1805) {
      boolean genVar1806;
      genVar1806=true;
      return genVar1806;
    }
 else {
      ;
    }
    boolean genVar1807;
    genVar1807=false;
    return genVar1807;
  }
}
