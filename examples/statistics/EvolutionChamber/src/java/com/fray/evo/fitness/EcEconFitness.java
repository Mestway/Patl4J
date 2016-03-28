package com.fray.evo.fitness;

import com.fray.evo.EcState;

public final class EcEconFitness implements EcFitness {

	public double augmentScore(EcState current, EcState destination, double score, boolean waypoint)
	{
		EcState c = current;
		
		score = augmentCappedScore(score, c.getDrones(), destination.getDrones(), c.bases()*30, 50, 4, waypoint);
		score = augmentScore(score, c.getZerglings(), destination.getZerglings(), 25, .25, waypoint);
		score = augmentScore(score, c.getBanelings(), destination.getBanelings(), 75, .75, waypoint);
		score = augmentScore(score, c.getRoaches(), destination.getRoaches(), 100, 1.0, waypoint);
		score = augmentScore(score, c.getMutalisks(), destination.getMutalisks(), 200, 2.0, waypoint);
		score = augmentCappedScore(score, c.getQueens(), destination.getQueens(), c.bases()*2, 150, 2.5, waypoint);
		score = augmentScore(score, c.getHydralisks(), destination.getHydralisks(), 150, 1.5, waypoint);
		score = augmentScore(score, c.getInfestors(), destination.getInfestors(), 250, 2.5, waypoint);
		score = augmentScore(score, c.getCorruptors(), destination.getCorruptors(), 250, 2.5, waypoint);
		score = augmentScore(score, c.getUltralisks(), destination.getUltralisks(), 500, 5.0, waypoint);
		score = augmentScore(score, c.getBroodlords(), destination.getBroodlords(), 400, 4.0, waypoint);
		score = augmentCappedScore(score, c.getOverlords(), destination.getOverlords(), 25, 100, 1.8, waypoint);
		score = augmentScore(score, c.getOverseers(), destination.getOverseers(), 250, 2.5, waypoint);

		score = augmentScore(score, c.getGasExtractors(), destination.getGasExtractors(), 25, 1.5, waypoint);
		
		
		score = augmentDropoffScore(score, c.getHatcheries(), destination.getHatcheries(), 300, 4.5, waypoint);
		score = augmentDropoffScore(score, c.getLairs(), destination.getLairs(), 550, 5.5, waypoint);
		score = augmentDropoffScore(score, c.getHives(), destination.getHives(), 900, 9, waypoint);
		score = augmentDropoffScore(score, c.getSpawningPools(), destination.getSpawningPools(), 200, 2, waypoint);
		score = augmentDropoffScore(score, c.getRoachWarrens(), destination.getRoachWarrens(), 150, 1.5, waypoint);
		score = augmentDropoffScore(score, c.getHydraliskDen(), destination.getHydraliskDen(), 200, 2, waypoint);
		score = augmentDropoffScore(score, c.getBanelingNest(), destination.getBanelingNest(), 150, 1.5, waypoint);
		score = augmentDropoffScore(score, c.getGreaterSpire(), destination.getGreaterSpire(), 650, 6.5, waypoint);
		score = augmentDropoffScore(score, c.getUltraliskCavern(), destination.getUltraliskCavern(), 350, 3.5, waypoint);
		score = augmentDropoffScore(score, c.getSpire(), destination.getSpire(), 400, 4, waypoint);
		score = augmentDropoffScore(score, c.getInfestationPit(), destination.getInfestationPit(), 200, 2.0, waypoint);
		score = augmentDropoffScore(score, c.getEvolutionChambers(), destination.getEvolutionChambers(), 75, 0.75, waypoint);
		score = augmentScore(score, c.getSpineCrawlers(), destination.getSpineCrawlers(), 100, 1.00, waypoint);
		score = augmentScore(score, c.getSporeCrawlers(), destination.getSporeCrawlers(), 75, .75, waypoint);
		score = augmentDropoffScore(score, c.getNydusNetwork(), destination.getNydusNetwork(), 350, 3.00, waypoint);
		score = augmentScore(score, c.getNydusWorm(), destination.getNydusWorm(), 200, 2.00, waypoint);

		score = augmentScore(score, c.isMetabolicBoost(), destination.isMetabolicBoost(), 200, 2.0, waypoint);
		score = augmentScore(score, c.isAdrenalGlands(), destination.isAdrenalGlands(), 400, 4.0, waypoint);
		score = augmentScore(score, c.isGlialReconstitution(), destination.isGlialReconstitution(), 200, 2.0, waypoint);
		score = augmentScore(score, c.isTunnelingClaws(), destination.isTunnelingClaws(), 300, 3.0, waypoint);
		score = augmentScore(score, c.isBurrow(), destination.isBurrow(), 200, 2.0, waypoint);
		score = augmentScore(score, c.isPneumatizedCarapace(), destination.isPneumatizedCarapace(), 200, 2.0, waypoint);
		score = augmentScore(score, c.isVentralSacs(), destination.isVentralSacs(), 400, 4.0, waypoint);
		score = augmentScore(score, c.isCentrifugalHooks(), destination.isCentrifugalHooks(), 300, 3.0, waypoint);
		score = augmentScore(score, c.isMelee1(), destination.isMelee1(), 200, 2.0, waypoint);
		score = augmentScore(score, c.isMelee2(), destination.isMelee2(), 300, 3.0, waypoint);
		score = augmentScore(score, c.isMelee3(), destination.isMelee3(), 400, 4.0, waypoint);
		score = augmentScore(score, c.isMissile1(), destination.isMissile1(), 200, 2.0, waypoint);
		score = augmentScore(score, c.isMissile2(), destination.isMissile2(), 300, 3.0, waypoint);
		score = augmentScore(score, c.isMissile3(), destination.isMissile3(), 400, 4.0, waypoint);
		score = augmentScore(score, c.isArmor1(), destination.isArmor1(), 200, 3.0, waypoint);
		score = augmentScore(score, c.isArmor2(), destination.isArmor2(), 300, 3.0, waypoint);
		score = augmentScore(score, c.isArmor3(), destination.isArmor3(), 400, 3.0, waypoint);
		score = augmentScore(score, c.isGroovedSpines(), destination.isGroovedSpines(), 300, 3.0, waypoint);
		score = augmentScore(score, c.isNeuralParasite(), destination.isNeuralParasite(), 300, 3.0, waypoint);
		score = augmentScore(score, c.isPathogenGlands(), destination.isPathogenGlands(), 300, 3.0, waypoint);
		score = augmentScore(score, c.isFlyerAttack1(), destination.isFlyerAttack1(), 200, 2.0, waypoint);
		score = augmentScore(score, c.isFlyerAttack2(), destination.isFlyerAttack2(), 350, 3.5, waypoint);
		score = augmentScore(score, c.isFlyerAttack3(), destination.isFlyerAttack3(), 500, 5.0, waypoint);
		score = augmentScore(score, c.isFlyerArmor1(), destination.isFlyerArmor1(), 300, 3.0, waypoint);
		score = augmentScore(score, c.isFlyerArmor2(), destination.isFlyerArmor2(), 450, 4.5, waypoint);
		score = augmentScore(score, c.isFlyerArmor3(), destination.isFlyerArmor3(), 600, 6.0, waypoint);
		score = augmentScore(score, c.isChitinousPlating(), destination.isChitinousPlating(), 300, 3.0, waypoint);
		return score;
	}

	private double augmentScore(double score, boolean a, boolean b, int mula, double mulb, boolean waypoint)
	{
		return augmentScore(score, a ? 1 : 0, b ? 1 : 0, mula, mulb, waypoint);
	}

	private double augmentCappedScore(double score, int a, int b, int maxa, double mula, double mulb, boolean waypoint)
	{
		int effective_a = Math.min(maxa, a);
		
		score += Math.max(Math.min(effective_a, b), 0) * mula;
		if (!waypoint)
			score += Math.max(effective_a - b, 0) * mulb;
		
		return score;
	}

	private double augmentScore(double score, int a, int b, double mula, double mulb, boolean waypoint)
	{
		score += Math.max(Math.min(a, b), 0) * mula;
		if (!waypoint)
			score += Math.max(a - b, 0) * mulb;
		return score;
	}

	private double augmentDropoffScore(double score, int a, int b, double mula, double mulb, boolean waypoint)
	{
		score += Math.max(Math.min(a, b), 0) * mula;
		if (!waypoint)
			for (int i = 0; i < Math.max(a - b, 0); i++)
			{
				mulb /= 2;
				score += mulb;
			}
		// score += Math.max(a - b, 0) * mulb;
		return score;
	}

	@Override
	public double score(EcState candidate, EcState metric) {
		EcState c = candidate;
		double score = 0;

		boolean keepgoing = true;
		EcState state = EcState.defaultDestination();
		for (EcState s : metric.waypoints)
		{
			if (keepgoing)
				state.union(s);
			if (!s.isSatisfied(c))
				keepgoing = false;
			else
				score += 500.0;
		}
		if (keepgoing)
		{
			state.union(metric);
			score = augmentScore(c, metric, score, false);
		}
		else
			score = augmentScore(c, metric, score, false);

		if (state.isSatisfied(c))
		{
			
			
			score *= 5.0;
			score += Math.min(c.supply() - c.supplyUsed,8);
			
			score += ((double) (120*60) - (double) c.seconds) / 1.7;
			
			score = augmentScore(score, (int) c.minerals, (int) metric.minerals, 0.0011, 0.0011, false);
			score = augmentScore(score, (int) c.gas, (int) metric.gas, .015, .015, false);
			
			
			
			
		}
		else
		{
			score = augmentScore(score, (int) c.minerals, (int) metric.minerals, .00010, .00010, false);
			score = augmentScore(score, (int) c.gas, (int) metric.gas, .005, .005, false);
		}
		// score = Math.max(score - candidate.invalidActions -
		// candidate.actionLength - candidate.waits, 0);
		score = Math.max(score, 0);
		return score;
	}


}
