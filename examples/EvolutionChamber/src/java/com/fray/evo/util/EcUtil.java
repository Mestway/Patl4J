package com.fray.evo.util;
import java.util.ArrayList;
public final class EcUtil {
  public static String toString(  ArrayList<String> strings){
    StringBuilder sb;
    sb=new StringBuilder();
    int i=0;
    for (; i < strings.size(); ++i) {
      java.lang.String genVar4412;
      genVar4412=strings.get(i);
      sb.append(genVar4412);
      char genVar4413;
      genVar4413='\n';
      sb.append(genVar4413);
    }
    java.lang.String genVar4414;
    genVar4414=sb.toString();
    return genVar4414;
  }
}
