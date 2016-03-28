package com.fray.evo;

import java.io.Serializable;

import com.fray.evo.fitness.EcEconFitness;
import com.fray.evo.fitness.EcFitness;
import com.fray.evo.fitness.EcFitnessType;
import com.fray.evo.fitness.EcStandardFitness;
import com.fray.evo.util.Race;

public final class EcSettings implements Serializable
{
	private static final long serialVersionUID = -5785538042893439693L;
	public boolean workerParity = false;
	public boolean overDrone = false;
	public boolean useExtractorTrick = true;
	public boolean avoidMiningGas = true;
	public boolean pullWorkersFromGas = true;
	public boolean pullThreeWorkersOnly = false;
	public EcFitnessType fitnessType = EcFitnessType.STANDARD;
	public int maximumExtractorTrickSupply = 200;
	public int minimumPoolSupply = 2;
	public int minimumExtractorSupply = 2;
	public int minimumHatcherySupply = 2;
	public Race race = Race.Zerg;

	public EcFitness getFitnessFunction() {
		switch(fitnessType) {
		case STANDARD:
			return new EcStandardFitness();
		case ECON:
			return new EcEconFitness();
		default:
			return new EcStandardFitness();
		}
		
	}
	
}
