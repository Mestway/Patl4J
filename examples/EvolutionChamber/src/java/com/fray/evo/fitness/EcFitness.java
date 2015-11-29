package com.fray.evo.fitness;
import com.fray.evo.EcState;
public interface EcFitness {
  public double score(  EcState candidate,  EcState metric);
}
