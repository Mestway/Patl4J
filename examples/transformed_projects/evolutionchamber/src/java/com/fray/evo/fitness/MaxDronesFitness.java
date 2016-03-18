package com.fray.evo.fitness;

import com.fray.evo.EcState;

public final class MaxDronesFitness implements EcFitness {

    @Override
    public double score(EcState candidate, EcState metric) {
        EcStandardFitness fitness = new EcStandardFitness();
        boolean satisfied = metric.isSatisfied(candidate);
        if(!satisfied || candidate.seconds > candidate.targetSeconds){
        return fitness.score(candidate, metric);
        }
 else{
            return candidate.getDrones()*4000-candidate.seconds;
 }
    }

}
