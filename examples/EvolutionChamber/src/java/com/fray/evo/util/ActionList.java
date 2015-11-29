package com.fray.evo.util;
import java.util.Comparator;
import java.util.PriorityQueue;
public final class ActionList {
  private int numLeft;
  private PriorityQueue<TWrapper> pq=null;
  public ActionList(){
    numLeft=0;
    int genVar4285;
    genVar4285=11;
    com.fray.evo.util.ActionList.Comparitor genVar4286;
    genVar4286=new Comparitor();
    pq=new PriorityQueue<TWrapper>(genVar4285,genVar4286);
  }
  public void put(  int i,  RunnableAction r){
    com.fray.evo.util.ActionList.TWrapper genVar4287;
    genVar4287=new TWrapper(i,r);
    pq.add(genVar4287);
    numLeft++;
  }
  public RunnableAction get(  int i){
    boolean genVar4288;
    genVar4288=hasFutureActions() && pq.peek().time == i;
    if (genVar4288) {
      numLeft--;
      com.fray.evo.util.ActionList.TWrapper genVar4289;
      genVar4289=pq.poll();
      com.fray.evo.util.RunnableAction genVar4290;
      genVar4290=genVar4289.object;
      return genVar4290;
    }
 else {
      ;
    }
    return null;
  }
  public boolean hasFutureActions(){
    int genVar4291;
    genVar4291=0;
    boolean genVar4292;
    genVar4292=numLeft > genVar4291;
    return genVar4292;
  }
private static class TWrapper {
    public int time;
    public RunnableAction object;
    public TWrapper(    int i,    RunnableAction r){
      com.fray.evo.util.ActionList.TWrapper genVar4293;
      genVar4293=this;
      genVar4293.time=i;
      com.fray.evo.util.ActionList.TWrapper genVar4294;
      genVar4294=this;
      genVar4294.object=r;
    }
  }
public static class Comparitor implements Comparator<TWrapper> {
    @Override public int compare(    TWrapper arg0,    TWrapper arg1){
      int genVar4295;
      genVar4295=arg0.time - arg1.time;
      return genVar4295;
    }
  }
}
