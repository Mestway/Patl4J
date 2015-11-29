package com.fray.evo.util;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
public final class EcCacheMap<E,T> implements Map<E,T> {
  private static final Logger logger=Logger.getLogger(EcCacheMap.class.getName());
  private static final long serialVersionUID=1L;
  HashMap<E,SoftReference<T>> map=new HashMap<E,SoftReference<T>>();
  long lastCleaned=System.currentTimeMillis();
  public void clear(){
    map.clear();
  }
  private void cull(){
    long current;
    current=System.currentTimeMillis();
    boolean genVar4416;
    genVar4416=current == lastCleaned;
    if (genVar4416) {
      return;
    }
 else {
      ;
    }
    int genVar4417;
    genVar4417=1000;
    int genVar4418;
    genVar4418=60;
    int genVar4419;
    genVar4419=genVar4417 * genVar4418;
    long genVar4420;
    genVar4420=lastCleaned + genVar4419;
    boolean genVar4421;
    genVar4421=current < genVar4420;
    if (genVar4421) {
      return;
    }
 else {
      ;
    }
    java.lang.String genVar4422;
    genVar4422="Cleaning... Before: ";
    int genVar4423;
    genVar4423=map.size();
    java.lang.String genVar4424;
    genVar4424=genVar4422 + genVar4423;
    logger.fine(genVar4424);
    EcCacheMap genVar4425;
    genVar4425=this;
    java.util.Set<E> genVar4426;
    genVar4426=genVar4425.keySet();
    java.util.ArrayList<E> genVar4427;
    genVar4427=new ArrayList<E>(genVar4426);
    for (    E e : genVar4427) {
      EcCacheMap genVar4428;
      genVar4428=this;
      T genVar4429;
      genVar4429=(T)genVar4428.getInner(e);
      boolean genVar4430;
      genVar4430=genVar4429 == null;
      if (genVar4430) {
        EcCacheMap genVar4431;
        genVar4431=this;
        genVar4431.remove(e);
      }
 else {
        ;
      }
    }
    java.lang.String genVar4432;
    genVar4432="Cleaning... After: ";
    int genVar4433;
    genVar4433=map.size();
    java.lang.String genVar4434;
    genVar4434=genVar4432 + genVar4433;
    logger.fine(genVar4434);
    lastCleaned=current;
  }
  public boolean containsKey(  Object arg0){
    java.lang.ref.SoftReference<T> genVar4435;
    genVar4435=map.get(arg0);
    boolean genVar4436;
    genVar4436=genVar4435 != null;
    return genVar4436;
  }
  public boolean containsValue(  Object arg0){
    T genVar4437;
    genVar4437=(T)arg0;
    java.lang.ref.WeakReference<T> genVar4438;
    genVar4438=new WeakReference<T>(genVar4437);
    boolean genVar4439;
    genVar4439=map.containsValue(genVar4438);
    return genVar4439;
  }
  public Set<java.util.Map.Entry<E,T>> entrySet(){
    HashMap<E,T> set;
    set=new HashMap<E,T>();
    java.util.Set<java.util.Map.Entry<E,java.lang.ref.SoftReference<T>>> genVar4440;
    genVar4440=map.entrySet();
    for (    Entry<E,SoftReference<T>> r : genVar4440) {
      java.lang.ref.SoftReference<T> genVar4441;
      genVar4441=r.getValue();
      T value;
      value=genVar4441.get();
      boolean genVar4442;
      genVar4442=value == null;
      if (genVar4442) {
        continue;
      }
 else {
        ;
      }
      E genVar4443;
      genVar4443=r.getKey();
      set.put(genVar4443,value);
    }
    java.util.Set<java.util.Map.Entry<E,T>> genVar4444;
    genVar4444=set.entrySet();
    return genVar4444;
  }
  public T get(  Object arg0){
    EcCacheMap genVar4445;
    genVar4445=this;
    genVar4445.cull();
    EcCacheMap genVar4446;
    genVar4446=this;
    T genVar4447;
    genVar4447=(T)genVar4446.getInner(arg0);
    return genVar4447;
  }
  private T getInner(  Object arg0){
    SoftReference<T> weakReference;
    weakReference=map.get(arg0);
    boolean genVar4448;
    genVar4448=weakReference != null;
    if (genVar4448) {
      T genVar4449;
      genVar4449=weakReference.get();
      return genVar4449;
    }
 else {
      ;
    }
    return null;
  }
  public boolean isEmpty(){
    java.util.Collection<java.lang.ref.SoftReference<T>> genVar4450;
    genVar4450=map.values();
    for (    SoftReference<T> t : genVar4450) {
      T genVar4451;
      genVar4451=t.get();
      boolean genVar4452;
      genVar4452=genVar4451 != null;
      if (genVar4452) {
        boolean genVar4453;
        genVar4453=false;
        return genVar4453;
      }
 else {
        ;
      }
    }
    boolean genVar4454;
    genVar4454=true;
    return genVar4454;
  }
  public Set<E> keySet(){
    java.util.Set<E> genVar4455;
    genVar4455=map.keySet();
    return genVar4455;
  }
  public T put(  E arg0,  T arg1){
    EcCacheMap genVar4456;
    genVar4456=this;
    genVar4456.cull();
    java.lang.ref.SoftReference<T> genVar4457;
    genVar4457=new SoftReference<T>(arg1);
    map.put(arg0,genVar4457);
    return arg1;
  }
  @Override public void putAll(  Map<? extends E,? extends T> arg0){
    Set<?> genVar4458;
    genVar4458=arg0.entrySet();
    for (    Object entry : genVar4458) {
      Map.Entry<? extends E,? extends T> enty=(java.util.Map.Entry<? extends E,? extends T>)entry;
      this.put(enty.getKey(),enty.getValue());
    }
  }
  public T remove(  Object arg0){
    java.lang.ref.SoftReference<T> genVar4462;
    genVar4462=map.remove(arg0);
    T genVar4463;
    genVar4463=genVar4462.get();
    return genVar4463;
  }
  public int size(){
    int i;
    i=0;
    java.util.Collection<java.lang.ref.SoftReference<T>> genVar4464;
    genVar4464=map.values();
    for (    SoftReference<T> t : genVar4464) {
      T genVar4465;
      genVar4465=t.get();
      boolean genVar4466;
      genVar4466=genVar4465 != null;
      if (genVar4466) {
        i++;
      }
 else {
        ;
      }
    }
    return i;
  }
  public Collection<T> values(){
    List<T> results;
    results=new ArrayList<T>();
    java.util.Collection<java.lang.ref.SoftReference<T>> genVar4467;
    genVar4467=map.values();
    for (    SoftReference<T> t : genVar4467) {
      T t2;
      t2=t.get();
      boolean genVar4468;
      genVar4468=t2 != null;
      if (genVar4468) {
        results.add(t2);
      }
 else {
        ;
      }
    }
    return results;
  }
}
