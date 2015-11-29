package com.fray.evo;
public final class Optimization {
  private static Integer[] integers=new Integer[10000];
  public static Integer inte(  int i){
    int genVar1257;
    genVar1257=0;
    boolean genVar1258;
    genVar1258=i < genVar1257;
    boolean genVar1259;
    genVar1259=i > integers.length;
    boolean genVar1260;
    genVar1260=genVar1258 || genVar1259;
    if (genVar1260) {
      java.lang.Integer genVar1261;
      genVar1261=Integer.valueOf(i);
      return genVar1261;
    }
 else {
      ;
    }
    java.lang.Integer genVar1262;
    genVar1262=integers[i];
    boolean genVar1263;
    genVar1263=genVar1262 == null;
    if (genVar1263) {
      integers[i]=Integer.valueOf(i);
    }
 else {
      ;
    }
    java.lang.Integer genVar1264;
    genVar1264=integers[i];
    return genVar1264;
  }
}
