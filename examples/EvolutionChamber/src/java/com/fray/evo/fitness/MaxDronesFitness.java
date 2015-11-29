package com.fray.evo.fitness;
import com.fray.evo.EcState;
/** 
 * @author Cyrik
 */
public final class MaxDronesFitness implements EcFitness {
  @Override public double score(  EcState candidate,  EcState metric){
    EcStandardFitness fitness;
    fitness=new EcStandardFitness();
    boolean satisfied;
    satisfied=metric.isSatisfied(candidate);
    boolean genVar3039;
    genVar3039=!satisfied;
    boolean genVar3040;
    genVar3040=candidate.seconds > candidate.targetSeconds;
    boolean genVar3041;
    genVar3041=genVar3039 || genVar3040;
    if (genVar3041) {
      double genVar3042;
      genVar3042=fitness.score(candidate,metric);
      return genVar3042;
    }
 else {
      int genVar3043;
      genVar3043=candidate.getDrones();
      int genVar3044;
      genVar3044=4000;
      int genVar3045;
      genVar3045=genVar3043 * genVar3044;
      int genVar3046;
      genVar3046=genVar3045 - candidate.seconds;
      return genVar3046;
    }
  }
}
