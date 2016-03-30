package com.fray.evo;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

import com.fray.evo.action.EcAction;
import com.fray.evo.action.EcActionExtractorTrick;
import com.fray.evo.action.EcActionMineGas;
import com.fray.evo.action.EcActionMineMineral;
import com.fray.evo.action.EcActionWait;
import com.fray.evo.action.build.EcActionBuildBaneling;
import com.fray.evo.action.build.EcActionBuildBanelingNest;
import com.fray.evo.action.build.EcActionBuildBroodLord;
import com.fray.evo.action.build.EcActionBuildCorruptor;
import com.fray.evo.action.build.EcActionBuildDrone;
import com.fray.evo.action.build.EcActionBuildEvolutionChamber;
import com.fray.evo.action.build.EcActionBuildExtractor;
import com.fray.evo.action.build.EcActionBuildGreaterSpire;
import com.fray.evo.action.build.EcActionBuildHatchery;
import com.fray.evo.action.build.EcActionBuildHive;
import com.fray.evo.action.build.EcActionBuildHydralisk;
import com.fray.evo.action.build.EcActionBuildHydraliskDen;
import com.fray.evo.action.build.EcActionBuildInfestationPit;
import com.fray.evo.action.build.EcActionBuildInfestor;
import com.fray.evo.action.build.EcActionBuildLair;
import com.fray.evo.action.build.EcActionBuildMutalisk;
import com.fray.evo.action.build.EcActionBuildNydusNetwork;
import com.fray.evo.action.build.EcActionBuildOverlord;
import com.fray.evo.action.build.EcActionBuildOverseer;
import com.fray.evo.action.build.EcActionBuildQueen;
import com.fray.evo.action.build.EcActionBuildRoach;
import com.fray.evo.action.build.EcActionBuildRoachWarren;
import com.fray.evo.action.build.EcActionBuildSpawningPool;
import com.fray.evo.action.build.EcActionBuildSpineCrawler;
import com.fray.evo.action.build.EcActionBuildSpire;
import com.fray.evo.action.build.EcActionBuildSporeCrawler;
import com.fray.evo.action.build.EcActionBuildUltralisk;
import com.fray.evo.action.build.EcActionBuildUltraliskCavern;
import com.fray.evo.action.build.EcActionBuildZergling;
import com.fray.evo.action.upgrade.EcActionUpgradeAdrenalGlands;
import com.fray.evo.action.upgrade.EcActionUpgradeBurrow;
import com.fray.evo.action.upgrade.EcActionUpgradeCarapace1;
import com.fray.evo.action.upgrade.EcActionUpgradeCarapace2;
import com.fray.evo.action.upgrade.EcActionUpgradeCarapace3;
import com.fray.evo.action.upgrade.EcActionUpgradeCentrifugalHooks;
import com.fray.evo.action.upgrade.EcActionUpgradeChitinousPlating;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor1;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor2;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor3;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks1;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks2;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks3;
import com.fray.evo.action.upgrade.EcActionUpgradeGlialReconstitution;
import com.fray.evo.action.upgrade.EcActionUpgradeGroovedSpines;
import com.fray.evo.action.upgrade.EcActionUpgradeMelee1;
import com.fray.evo.action.upgrade.EcActionUpgradeMelee2;
import com.fray.evo.action.upgrade.EcActionUpgradeMelee3;
import com.fray.evo.action.upgrade.EcActionUpgradeMetabolicBoost;
import com.fray.evo.action.upgrade.EcActionUpgradeMissile1;
import com.fray.evo.action.upgrade.EcActionUpgradeMissile2;
import com.fray.evo.action.upgrade.EcActionUpgradeMissile3;
import com.fray.evo.action.upgrade.EcActionUpgradeNeuralParasite;
import com.fray.evo.action.upgrade.EcActionUpgradePathogenGlands;
import com.fray.evo.action.upgrade.EcActionUpgradePneumatizedCarapace;
import com.fray.evo.action.upgrade.EcActionUpgradeTunnelingClaws;
import com.fray.evo.action.upgrade.EcActionUpgradeVentralSacs;
import com.fray.evo.util.EcUtil;
import com.fray.evo.util.EcYabotEncoder;
import com.fray.evo.util.GameLog;

public final class EcEvolver extends FitnessFunction
{
	/**
	 * serialVersionUID - Changes to the structure of EcEvolver
	 *   1 - The log attribute was changed to transient
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EcEvolver.class.getName());
	EcState								source;
	private EcState						destination;
	private EcState						mergedDestination;
	private transient GameLog			log;
	private long					evaluations	= 0;
	private final List<Class<? extends EcAction>> actions;
	
	/**
	 * Maps Evolution Chamber classes with the appropriate action class of the YABOT encoder.
	 */
	private static final Map<Class<? extends EcAction>, EcYabotEncoder.Action> yabotMapping;
	static
	{
		Map<Class<? extends EcAction>, EcYabotEncoder.Action> m = new HashMap<Class<? extends EcAction>, EcYabotEncoder.Action>();
	    m.put(EcActionBuildBanelingNest.class, EcYabotEncoder.Action.BanelingNest);
		m.put(EcActionBuildEvolutionChamber.class, EcYabotEncoder.Action.EvolutionChamber);
		m.put(EcActionBuildExtractor.class, EcYabotEncoder.Action.Extractor);
		m.put(EcActionBuildHatchery.class, EcYabotEncoder.Action.Hatchery);
		m.put(EcActionBuildHydraliskDen.class, EcYabotEncoder.Action.HydraliskDen);
		m.put(EcActionBuildInfestationPit.class, EcYabotEncoder.Action.InfestationPit);
		m.put(EcActionBuildNydusNetwork.class, EcYabotEncoder.Action.NydusNetwork);
		m.put(EcActionBuildRoachWarren.class, EcYabotEncoder.Action.RoachWarren);
		m.put(EcActionBuildSpawningPool.class, EcYabotEncoder.Action.SpawningPool);
		m.put(EcActionBuildSpineCrawler.class, EcYabotEncoder.Action.SpineCrawler);
		m.put(EcActionBuildGreaterSpire.class, EcYabotEncoder.Action.GreaterSpire);
		m.put(EcActionBuildSpire.class, EcYabotEncoder.Action.Spire);
		m.put(EcActionBuildSporeCrawler.class, EcYabotEncoder.Action.SporeCrawler);
		m.put(EcActionBuildUltraliskCavern.class, EcYabotEncoder.Action.UltraliskCavern);
		m.put(EcActionBuildCorruptor.class, EcYabotEncoder.Action.Corruptor);
		m.put(EcActionBuildDrone.class, EcYabotEncoder.Action.Drone);
		m.put(EcActionBuildHydralisk.class, EcYabotEncoder.Action.Hydralisk);
		m.put(EcActionBuildInfestor.class, EcYabotEncoder.Action.Infestor);
		m.put(EcActionBuildMutalisk.class, EcYabotEncoder.Action.Mutalisk);
		m.put(EcActionBuildOverlord.class, EcYabotEncoder.Action.Overlord);
		m.put(EcActionBuildQueen.class, EcYabotEncoder.Action.Queen);
		m.put(EcActionBuildRoach.class, EcYabotEncoder.Action.Roach);
		m.put(EcActionBuildUltralisk.class, EcYabotEncoder.Action.Ultralisk);
		m.put(EcActionBuildZergling.class, EcYabotEncoder.Action.Zergling);
		m.put(EcActionBuildLair.class, EcYabotEncoder.Action.Lair);
		m.put(EcActionBuildHive.class, EcYabotEncoder.Action.Hive);
		m.put(EcActionBuildBroodLord.class, EcYabotEncoder.Action.BroodLord);
		m.put(EcActionBuildBaneling.class, EcYabotEncoder.Action.Baneling);
		m.put(EcActionBuildOverseer.class, EcYabotEncoder.Action.Overseer);
		m.put(EcActionUpgradeCarapace1.class, EcYabotEncoder.Action.Carapace);
		m.put(EcActionUpgradeCarapace2.class, EcYabotEncoder.Action.Carapace);
		m.put(EcActionUpgradeCarapace3.class, EcYabotEncoder.Action.Carapace);
		m.put(EcActionUpgradeMelee1.class, EcYabotEncoder.Action.Melee);
		m.put(EcActionUpgradeMelee2.class, EcYabotEncoder.Action.Melee);
		m.put(EcActionUpgradeMelee3.class, EcYabotEncoder.Action.Melee);
		m.put(EcActionUpgradeFlyerAttacks1.class, EcYabotEncoder.Action.FlyerAttack);
		m.put(EcActionUpgradeFlyerAttacks2.class, EcYabotEncoder.Action.FlyerAttack);
		m.put(EcActionUpgradeFlyerAttacks3.class, EcYabotEncoder.Action.FlyerAttack);
		m.put(EcActionUpgradeFlyerArmor1.class, EcYabotEncoder.Action.FlyerArmor);
		m.put(EcActionUpgradeFlyerArmor2.class, EcYabotEncoder.Action.FlyerArmor);
		m.put(EcActionUpgradeFlyerArmor3.class, EcYabotEncoder.Action.FlyerArmor);
		m.put(EcActionUpgradeMissile1.class, EcYabotEncoder.Action.Missile);
		m.put(EcActionUpgradeMissile2.class, EcYabotEncoder.Action.Missile);
		m.put(EcActionUpgradeMissile3.class, EcYabotEncoder.Action.Missile);
		m.put(EcActionUpgradeGroovedSpines.class, EcYabotEncoder.Action.GroovedSpines);
		m.put(EcActionUpgradePneumatizedCarapace.class, EcYabotEncoder.Action.PneumatizedCarapace);
		m.put(EcActionUpgradeGlialReconstitution.class, EcYabotEncoder.Action.GlialReconstitution);
		m.put(EcActionUpgradeTunnelingClaws.class, EcYabotEncoder.Action.TunnelingClaws);
		m.put(EcActionUpgradeChitinousPlating.class, EcYabotEncoder.Action.ChitinousPlating);
		m.put(EcActionUpgradeAdrenalGlands.class, EcYabotEncoder.Action.AdrenalGlands);
		m.put(EcActionUpgradeMetabolicBoost.class, EcYabotEncoder.Action.MetabolicBoost);
		m.put(EcActionUpgradeBurrow.class, EcYabotEncoder.Action.Burrow);
		m.put(EcActionUpgradeCentrifugalHooks.class, EcYabotEncoder.Action.CentrifugalHooks);
		m.put(EcActionUpgradeNeuralParasite.class, EcYabotEncoder.Action.NeuralParasite);
		m.put(EcActionUpgradePathogenGlands.class, EcYabotEncoder.Action.PathogenGlands);
		m.put(EcActionUpgradeVentralSacs.class, EcYabotEncoder.Action.VentralSacs);
		yabotMapping = Collections.unmodifiableMap(m);
	}

	public EcEvolver(EcState source, EcState destination, List<Class<? extends EcAction>> actions)
	{
		this.source = source;
		this.destination = destination;
		this.mergedDestination = destination.getMergedState();
		this.log = new GameLog();
		this.actions = actions;
	}

	protected String getAlleleAsString(IChromosome c)
	{
		StringBuilder sb = new StringBuilder();
		for (Gene g : c.getGenes())
		{
			if (((Integer) g.getAllele()).intValue() >= 10)
				sb.append(((char) ((int) 'a' + (Integer) g.getAllele() - 10)));
			else
				sb.append(g.getAllele().toString());
		}
		return sb.toString();
	}

	@Override
	protected double evaluate(IChromosome chromosome)
	{
		EcBuildOrder s;
		try
		{
			Double score;
			evaluations++;
			s = populateBuildOrder((EcBuildOrder) source, chromosome, actions);
			score = destination.score(doEvaluate(s));
			return score;
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
		return Double.NEGATIVE_INFINITY;
	}

	public EcState evaluateGetBuildOrder(IChromosome chromosome)
	{
		EcBuildOrder s;
		try
		{
			s = populateBuildOrder((EcBuildOrder) source, chromosome, actions);

			return doEvaluate(s);
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
		return null;
	}

	public String getBuildOrder(IChromosome chromosome)
	{
		//this is basically just a copy from the doEvaluate() function adjusted to return a build order string
		EcBuildOrder s;
		try
		{
			s = populateBuildOrder((EcBuildOrder) source, chromosome, actions);
			return doSimpleEvaluate(s);
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
			return "";
		}
	}

	public String doSimpleEvaluate(EcBuildOrder s)
	{
		StringBuilder sb = new StringBuilder();
		
		for (EcAction a : s.getActions())
		{
			if (a.isInvalid(s))
			{
				continue;
			}
			while (!a.canExecute(s, log).can)
			{
				if (s.seconds >= s.targetSeconds || destination.waypointMissed(s))
				{
					return messages.getString("NoFinishedBuildYet");
				}
				
				if (destination.getMergedWaypoints().isSatisfied(s))
				{
					return sb.toString();
				}
			}
			
			if (!(a instanceof EcActionWait) && !(a instanceof EcActionBuildDrone))
			{
				sb.append((int)s.supplyUsed + "  " + a.toBuildOrderString(s) + "\tM:" + (int)s.minerals + "\tG:" + (int)s.gas + "\n");	
			}
			
			a.execute(s, log);
		}

		return messages.getString("RanOutOfThingsToDo");
	}

	public String getYabotBuildOrder(IChromosome chromosome)
	{
		//Yabot build order encoder
		EcBuildOrder s;
		try
		{
			s = populateBuildOrder((EcBuildOrder) source, chromosome, actions);
			
			return doYABOTEvaluate(s);
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
			return "";
		}
	}

	public String doYABOTEvaluate(EcBuildOrder s)
	{
		EcYabotEncoder encoder = new EcYabotEncoder("EC Optimized Build", "EvolutionChamber", "Add description here please.");
		
		ArrayList<String> warnings = new ArrayList<String>();
		
		for (EcAction a : s.getActions())
		{
			if (a.isInvalid(s))
			{
				continue;
			}
			while (!a.canExecute(s, log).can)
			{					
				if (s.seconds >= s.targetSeconds || destination.waypointMissed(s))
				{
					return messages.getString("NoFinishedBuildYet")+"\n"+EcUtil.toString(warnings);
				}
				
				if (destination.getMergedWaypoints().isSatisfied(s))
				{
					String yabot = encoder.done();
					final int max = 770;
					if (yabot.length() > max)
					{
						yabot += "\nBuild was too long. Please trim it by " + (yabot.length() - max) + " characters or try a new build.";
						yabot += "\nThis YABOT string will not work until you fix this!";
					}
					return yabot+"\n"+EcUtil.toString(warnings);
				}
			}
			
			if (!(a instanceof EcActionWait) && !(a instanceof EcActionBuildDrone))
			{
				if(!(a instanceof EcActionExtractorTrick))
				{
					if (a instanceof EcActionMineGas)
					{
						String tag = s.settings.pullThreeWorkersOnly ? "Add_3_drones_to_gas" : "Add_1_drone_to_gas";
						encoder.tag(tag);
					}
					else if (a instanceof EcActionMineMineral)
					{
						String tag = s.settings.pullThreeWorkersOnly ? "Add_3_drones_to_minerals" : "Add_1_drone_to_minerals";
						encoder.tag(tag);
					}
					else
					{
						//get the appropriate YABOT action class
						EcYabotEncoder.Action yabotAction = yabotMapping.get(a.getClass());
						if (yabotAction != null)
						{
							encoder.action(yabotAction);
						}
						else
						{
							warnings.add("YABOT action not found for '" + a.getClass().getName() + "'.");
						}
					}
					
					//enter the rest of the build step info
					encoder.supply((int) s.supplyUsed).minerals((int) s.minerals).gas((int) s.gas).timestamp(s.timestamp()).next();
				}
				else
				{
					// Yabot doesn't support extractor trick so the author suggested telling it to build an extractor and send a cancel string shortly after for the same building
					encoder.supply((int)s.supplyUsed).minerals((int)s.minerals).gas((int)s.gas).timestamp(s.timestamp()).action(EcYabotEncoder.Action.Extractor).tag("Extractor_Trick").next();
					encoder.supply((int)s.supplyUsed).minerals((int)s.minerals).gas((int)s.gas).timestamp(s.timestampIncremented(3)).action(EcYabotEncoder.Action.Extractor).cancel(true).tag("Extractor_Trick").next();
				}
			}
			
			a.execute(s, log);
		}

		return messages.getString("RanOutOfThingsToDo")+"\n"+EcUtil.toString(warnings);
	}
	
	public static EcBuildOrder populateBuildOrder(EcBuildOrder source, IChromosome arg0, List<Class<? extends EcAction>> requiredActions)
			throws CloneNotSupportedException
	{
		EcBuildOrder s;
		s = source.clone();
		for (Gene g1 : arg0.getGenes())
		{
			IntegerGene g = (IntegerGene) g1;
			Integer i = (Integer) g.getAllele();
			try
			{
				s.addAction((EcAction) requiredActions.get(i).newInstance());
			}
			catch (InstantiationException e)
			{
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				logger.severe(sw.toString());
			}
			catch (IllegalAccessException e)
			{
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				logger.severe(sw.toString());
			}
		}
		return s;
	}

	public EcBuildOrder doEvaluate(EcBuildOrder s)
	{
		int i = 0;
		ArrayList<EcAction> actions = s.getActions();
		for (int c = 0; c < actions.size(); ++c)
		{
			EcAction a = actions.get(c);
			i++;
			if (a.isInvalid(s))
			{
				s.invalidActions++;
				continue;
			}
                        EcAction.CanExecuteResult canExecute;
			while (!(canExecute= a.canExecute(s, log)).can)
			{
				if (s.seconds > s.targetSeconds || destination.waypointMissed(s))
				{
					if (s.settings.overDrone && s.getDrones() < s.getOverDrones(s))
						log.printFailure( GameLog.FailReason.OverDrone, mergedDestination, s );
					else
						log.printFailure( GameLog.FailReason.Waypoint, mergedDestination, s );
					return s;
				}
				int waypointIndex = destination.getCurrWaypointIndex(s);
				if (waypointIndex != -1 && destination.getWaypointActions(waypointIndex) > 0)
					log.printWaypoint( waypointIndex, s );
                                if(canExecute.somethingChanged){
				if (destination.getMergedWaypoints().isSatisfied(s))
				{
					log.printSatisfied(i - s.invalidActions, s, mergedDestination);
					return s;
				}}
			}
			
			if (!(a instanceof EcActionWait))
				log.printAction(s, a);

			a.execute(s, log);
		}
		log.printFailure(GameLog.FailReason.OutOfActions, s, null);
		return s;
	}
	
	public void enableLogging (boolean log) {
		this.log.setEnabled(log);
	}
	
	public void setLoggingStream (PrintStream stream) {
		this.log.setPrintStream(stream);
	}

	public EcState getDestination()
	{
		return destination;
	}
	
	public long getEvaluations()
	{
		return evaluations;
	}
}
