package com.fray.evo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

import com.fray.evo.action.EcAction;
import com.fray.evo.fitness.EcFitnessType;

public final class EvolutionChamber
{
	private static final Logger logger = Logger.getLogger(EvolutionChamber.class.getName());
	
	/**
	 * The version of EvolutionChamber.
	 */
	public static final String	VERSION = "0026";

	/**
	 * The seed file.
	 */
	private File				SEEDS_EVO				= null;
	
	/**
	 * The backup seed file (in case execution stops while the file is half written).
	 */
	private File				SEEDS_EVO_2				= null;

	/**
	 * Chromosome length refers to the Genetic Algorithm's maximum number of potential actions that can be executed. 
	 */
	private int					CHROMOSOME_LENGTH		= 120;

	/**
	 * Population size refers to the number of chromosomes in the gene pool for a population.
	 */
	private int					POPULATION_SIZE			= 200;
	
	/**
	 * Base mutation rate refers to the rate at which EcGeneticUtil mutations occur.
	 */
	private double				BASE_MUTATION_RATE		= 5;

	/**
	 * Number of threads to run genetic simulators on.
	 */
	private int					NUM_THREADS				= Runtime.getRuntime().availableProcessors();
	
	/**
	 * Maximum allowed number of threads to run genetic simulators on.
	 */
	private int					MAX_NUM_THREADS			= Runtime.getRuntime().availableProcessors() * 4;
	
	/**
	 * The threads that are used for the running simulation.
	 */
	private List<Thread>		threads					= Collections.synchronizedList(new ArrayList<Thread>());
	
	/**
	 * True to kill all running threads, false to let them continue running.
	 */
	private boolean				killThreads				= false;
	
	/**
	 * The best fitness score a simulation generated.
	 */
	private Double				bestScore				= 0.0;
	
	private Integer				stagnationLimit			= 0;
	private Double				waterMark				= 0.0;
	private int					STAGNATION_LIMIT_MIN	= 200;
	
	/**
	 * A list of all previously-run simulations.
	 */
	private List<EcBuildOrder>	history					= new ArrayList<EcBuildOrder>();
	
	/**
	 * The final goal of the current simulation.
	 */
	private EcState				destination				= EcState.defaultDestination();
	
	/**
	 * Allows for the results of a simulation to be outputted in real time.
	 */
	private EcReportable			reportInterface;
	
	/**
	 * The best fitness score for each thread.
	 */
	private Double[]			bestScores;
	
	private Integer[]			evolutionsSinceDiscovery;
	private EcEvolver[]			evolvers;
        private int                             forgottenEvolutions;
	private boolean				firstrun				= true;
	private boolean				newbestscore			= false;
	
	/**
	 * The caluation to use for determining the fitness score of a gene.
	 */
	private EcFitnessType		fitnessType				= EcFitnessType.STANDARD;
	
	/**
	 * the minimum of required actions, determined by analyzing the destination state
	 */
	private List<Class<? extends EcAction>> requiredActions = new ArrayList<Class<? extends EcAction>>();
	
	public EvolutionChamber()
	{
	}
	
	/**
	 * Creates a new object, loading the seeds from the given files.
	 * @param seeds
	 * @param backupSeeds
	 */
	public EvolutionChamber(File seeds, File backupSeeds)
	{
		SEEDS_EVO = seeds;
		SEEDS_EVO_2 = backupSeeds;
		loadSeeds();
	}
	
	public List<EcBuildOrder> getHistory()
	{
		return history;
	}
	
	public int getEvolutionsSinceDiscovery(int thread)
	{
		return evolutionsSinceDiscovery[thread];
	}
	
	/**
	 * Determines if a simulation is currently running.
	 * @return true if a simulation is running, false if not.
	 */
	public boolean isRunning()
	{
		return threads.size() > 0;
	}
	
	public Double[] getBestScores()
	{
		return bestScores;
	}
	
	public int getStagnationLimit()
	{
		return stagnationLimit;
	}
	
	public int getChromosomeLength()
	{
		return CHROMOSOME_LENGTH;
	}
	
	public double getBaseMutationRate()
	{
		return BASE_MUTATION_RATE;
	}
	
	public void setReportInterface(EcReportable reportInterface)
	{
		this.reportInterface = reportInterface;
	}

	public void setSeedFile(File file)
	{
		SEEDS_EVO = file;
	}
	
	public void setBackupSeedFile(File file)
	{
		SEEDS_EVO_2 = file;
	}
	
	/**
	 * Sets the caluation to use for determining the fitness score of a gene.
	 * 
	 * @param fitnessType the fitness type
	 */
	public void setFitnessType(EcFitnessType fitnessType)
	{
		this.fitnessType = fitnessType;
	}
	
	/**
	 * Gets the total number of games played since the simulation started.
	 * @return
	 */
	public long getGamesPlayed()
	{
		long total = 0;
		for (int i = 0; i < evolvers.length; i++)
		{
			EcEvolver evolver = evolvers[i];
			total += evolver.getEvaluations();
		}
		return total+forgottenEvolutions;
	}

	/**
	 * Starts the simulation (asychronously).
	 * @throws InvalidConfigurationException
	 */
	public void go() throws InvalidConfigurationException
	{
		EvolutionChamber genVar1295;
		genVar1295 = this;
		genVar1295.reset();
		EvolutionChamber genVar1296;
		genVar1296 = this;
		EcState s;
		s = genVar1296.importSource();
		EvolutionChamber genVar1297;
		genVar1297 = this;
		EcState d;
		d = genVar1297.getInternalDestination();
		requiredActions = EcRequirementTree.createActionList(d);
		d.settings.fitnessType = fitnessType;
		int genVar1298;
		genVar1298 = d.getEstimatedActions();
		int genVar1299;
		genVar1299 = 70;
		CHROMOSOME_LENGTH = genVar1298 + genVar1299;
		int threadIndex = 0;
		for (; threadIndex < NUM_THREADS; threadIndex++) {
			EvolutionChamber genVar1300;
			genVar1300 = this;
			genVar1300.spawnEvolutionaryChamber(s, d, threadIndex);
		}
	}

	/**
	 * Resets the object so it can run a new simulation.
	 */
	private synchronized void reset()
	{
		killThreads = false;
		firstrun = true;
		haveSavedBefore = false;
		bestScore = 0.0;
		bestScores = new Double[NUM_THREADS];
		evolutionsSinceDiscovery = new Integer[NUM_THREADS];
		evolvers = new EcEvolver[NUM_THREADS];
                forgottenEvolutions = 0;
	}

	/**
	 * Stops the simulation.
	 */
	public void stopAllThreads()
	{
		killThreads = true;
		
		
		System.out.println("length : "+threads.size());
		
		for (Thread t : new ArrayList<Thread>(threads))
			try
			{
				t.join();
			}
			catch (InterruptedException e)
			{
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				logger.severe(sw.toString());
			}
		threads.clear();
	}

	private void spawnEvolutionaryChamber(final EcState source, final EcState destination, final int threadIndex)
			throws InvalidConfigurationException
	{
		reset(threadIndex);

		final EcEvolver myFunc = new EcEvolver(source, destination, requiredActions);
		evolvers[threadIndex] = myFunc;

		final Configuration conf = constructConfiguration(threadIndex, myFunc);

		final Genotype population = Genotype.randomInitialGenotype(conf);

		//Please justify this code.
		//-Lomilar
//		if (!firstrun)
//		{
//			int totalevoSinceDiscoveryOnBest = 0;
//			int numBestThreads = 0;
//			synchronized (bestScores)
//			{
//				for (int i = 0; i < bestScores.length; i++)
//				{
//					if (bestScores[i] >= bestScore)
//					{
//						numBestThreads++;
//						totalevoSinceDiscoveryOnBest += evolutionsSinceDiscovery[i];
//					}
//				}
//			}
//
//			if (!(totalevoSinceDiscoveryOnBest > Math.max(stagnationLimit, STAGNATION_LIMIT_MIN) * numBestThreads)
//					&& numBestThreads < Math.max(Math.ceil(NUM_THREADS / 3), 1))
//			{
//				loadOldBuildOrders(population, conf, myFunc);
//			}
//		}
//		else 
			if (firstrun && threadIndex == 0)
		{
			loadOldBuildOrders(population, conf, myFunc);
		}
		else if (firstrun && threadIndex == NUM_THREADS - 1)
		{
			firstrun = false;
		}

		final Thread thread = new Thread(population);
		conf.getEventManager().addEventListener(GeneticEvent.GENOTYPE_EVOLVED_EVENT, new GeneticEventListener()
		{
			@Override
			public void geneticEventFired(GeneticEvent a_firedEvent)
			{
				Collections.shuffle(conf.getGeneticOperators());
				BASE_MUTATION_RATE += .001;
				if (BASE_MUTATION_RATE >= (double) CHROMOSOME_LENGTH / 2.0 )
					BASE_MUTATION_RATE = 1;
				IChromosome fittestChromosome = population.getFittestChromosome();
				if (killThreads)
				{
					threads.remove(thread);
					thread.interrupt();
				}
				double fitnessValue = fittestChromosome.getFitnessValue();
				if (fitnessValue > bestScores[threadIndex])
				{
					bestScores[threadIndex] = fitnessValue;
					evolutionsSinceDiscovery[threadIndex] = 0;
					BASE_MUTATION_RATE = 1;
					if (reportInterface != null)
						reportInterface.threadScore(threadIndex, getOutput(myFunc, fittestChromosome, fitnessValue));
				}
				else
					evolutionsSinceDiscovery[threadIndex]++;

				int highestevosSinceDiscovery = 0;
				for (int i = 0; i < bestScores.length; i++)
				{
					if (bestScores[i] >= bestScore)
					{
						if (evolutionsSinceDiscovery[i] > highestevosSinceDiscovery)
							highestevosSinceDiscovery = evolutionsSinceDiscovery[i];
					}
				}

				stagnationLimit = Math.max(STAGNATION_LIMIT_MIN, (int) Math.ceil(highestevosSinceDiscovery * (.5)));

				if (fitnessValue < bestScore)
				{
					if (evolutionsSinceDiscovery[threadIndex] > stagnationLimit
							&& fitnessValue < waterMark)
					{
						suicide(source, destination, threadIndex, thread);
					}
					else if (evolutionsSinceDiscovery[threadIndex] > stagnationLimit * 3)
					{
						suicide(source, destination, threadIndex, thread);
					}
				}
				else if (evolutionsSinceDiscovery[threadIndex] > stagnationLimit)
				{
					if (newbestscore)
					{
						waterMark = fitnessValue;
					}

					int totalevoSinceDiscoveryOnBest = 0;
					int numBestThreads = 0;

					for (int i = 0; i < bestScores.length; i++)
					{
						if (bestScores[i] >= bestScore)
						{
							numBestThreads++;
							totalevoSinceDiscoveryOnBest += evolutionsSinceDiscovery[i];
						}
					}

					if (totalevoSinceDiscoveryOnBest > stagnationLimit * 3
							* numBestThreads)
					{
						suicide(source, destination, threadIndex, thread);
					}
				}

				synchronized (bestScore)
				{
					if (fitnessValue > bestScore)
					{
						BASE_MUTATION_RATE = 1;
						bestScore = fitnessValue;
						newbestscore = true;

						String exactBuildOrder = getOutput(myFunc, fittestChromosome, fitnessValue);
						String buildOrder = getBuildOrder(myFunc, fittestChromosome);
						String yabotBuildOrder = getYabotBuildOrder(myFunc, fittestChromosome);

						if (reportInterface != null)
							reportInterface.bestScore(myFunc.evaluateGetBuildOrder(fittestChromosome), bestScore
									.intValue(), exactBuildOrder, buildOrder, yabotBuildOrder);

						if (logger.isLoggable(Level.FINE))
						{
							logger.fine(chromosomeToString(fittestChromosome));
						}
						saveSeeds(fittestChromosome);
					}
				}
			}

		});
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
		threads.add(thread);
	}

	/**
	 * Restarts a thread.
	 * @param source
	 * @param destination
	 * @param threadIndex
	 * @param thread
	 */
	private void suicide(final EcState source, final EcState destination, final int threadIndex,
			final Thread thread)
	{
            forgottenEvolutions += evolvers[threadIndex].getEvaluations();
		// Stagnation. Suicide village and try again.
		logger.fine("Restarting thread " + threadIndex);
		try
		{
			spawnEvolutionaryChamber(source, destination, threadIndex);
		}
		catch (InvalidConfigurationException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
		threads.remove(thread);
		thread.interrupt();
	}

	private void reset(final int threadIndex)
	{
		bestScores[threadIndex] = 0.0;
		evolutionsSinceDiscovery[threadIndex] = 0;
		evolvers[threadIndex] = null;
	}

	/**
	 * Generates a build order string.
	 * @param myFunc
	 * @param fittestChromosome
	 * @param fitnessValue
	 * @return
	 */
	private String getOutput(final EcEvolver myFunc, IChromosome fittestChromosome, double fitnessValue)
	{
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(byteArrayOutputStream);
		if (reportInterface != null)
			myFunc.setLoggingStream(ps);

		displayBuildOrder(myFunc, fittestChromosome);
		ps.println(new Date() + ": " + fitnessValue);
		String results = new String(byteArrayOutputStream.toByteArray());
		return results;
	}

	/**
	 * Generates a build order string.
	 * @param myFunc
	 * @param fittestChromosome
	 * @return
	 */
	public String getBuildOrder(final EcEvolver myFunc, IChromosome fittestChromosome)
	{
		return myFunc.getBuildOrder(fittestChromosome);
	}

	/**
	 * Builds the YABOT representation of a build order.
	 * @param myFunc
	 * @param fittestChromosome
	 * @return
	 */
	public String getYabotBuildOrder(final EcEvolver myFunc, IChromosome fittestChromosome)
	{
		return myFunc.getYabotBuildOrder(fittestChromosome);
	}

	private Configuration constructConfiguration(final int threadIndex, final EcEvolver myFunc)
			throws InvalidConfigurationException
	{
		DefaultConfiguration.reset(threadIndex + " thread.");
		final Configuration conf = new DefaultConfiguration(threadIndex + " thread.", threadIndex + " thread.");
		conf.setFitnessFunction(myFunc);
		conf.addGeneticOperator(EcGeneticUtil.getCleansingOperator(this));
		conf.addGeneticOperator(EcGeneticUtil.getOverlordingOperator(this, requiredActions));
		conf.addGeneticOperator(EcGeneticUtil.getInsertionOperator(this));
		conf.addGeneticOperator(EcGeneticUtil.getDeletionOperator(this));
		conf.addGeneticOperator(EcGeneticUtil.getTwiddleOperator(this));
		conf.addGeneticOperator(EcGeneticUtil.getSwapOperator(this));
		conf.setPopulationSize(POPULATION_SIZE);
		conf.setSelectFromPrevGen(1);
		conf.setPreservFittestIndividual(false);
		conf.setAlwaysCaculateFitness(false);
		conf.setKeepPopulationSizeConstant(false);
		Gene[] initialGenes = importInitialGenes(conf);
		Chromosome c = new Chromosome(conf, initialGenes);
		conf.setSampleChromosome(c);
		return conf;
	}
	
	/**
	 * Converts a {@link IChromosome} to its string representation.
	 * @param chromosome
	 * @return
	 */
	private static String chromosomeToString(IChromosome chromosome)
	{
		int i = 0;
		StringBuilder sb = new StringBuilder();
		for (Gene g : chromosome.getGenes())
		{
			if (i++ == 100)
				break;
			if (((Integer) g.getAllele()).intValue() >= 10)
				sb.append(((char) ((int) 'a' + (Integer) g.getAllele() - 10)));
			else
				sb.append(g.getAllele().toString());
		}
		return sb.toString();
	}

	private static void displayBuildOrder(final EcEvolver myFunc, IChromosome fittestChromosome)
	{
		myFunc.enableLogging(true);
		myFunc.evaluateGetBuildOrder(fittestChromosome);
		myFunc.enableLogging(false);
	}

	private synchronized void loadOldBuildOrders(Genotype population, final Configuration conf, final EcEvolver myFunc)
	{
		loadSeeds();

		int cindex = 0;

		Collections.sort(history, new Comparator<EcBuildOrder>()
		{
			@Override
			public int compare(EcBuildOrder arg0, EcBuildOrder arg1)
			{
				//sort by fitness descending
				double score = 0;
				try
				{
					score = myFunc.getFitnessValue(buildChromosome(conf, arg1))
							- myFunc.getFitnessValue(buildChromosome(conf, arg0));
				}
				catch (InvalidConfigurationException e)
				{
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					logger.severe(sw.toString());
				}
				return (int) score;
			}
		});
		for (EcBuildOrder bo : history)
		{
			try
			{
				Chromosome c = buildChromosome(conf, bo);
				logger.fine(myFunc.getFitnessValue(c) + "");
				population.getPopulation().setChromosome(cindex++, c);
			}
			catch (InvalidConfigurationException e)
			{
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				logger.severe(sw.toString());
			}
		}
	}

	/**
	 * Loads the seeds from the seed file.
	 */
	public void loadSeeds()
	{
		try
		{
			if (SEEDS_EVO != null)
			{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SEEDS_EVO));
				history = (List<EcBuildOrder>) ois.readObject();
				ois.close();
			}
			else
			{
				throw new IOException();
			}
		}
		catch (FileNotFoundException e)
		{
			logger.fine("Seeds file not found.");
		}
		catch (InvalidClassException ex)
		{
			logger.fine("Seeds file is in old format. Starting over. :-(");
		}
		catch (ClassNotFoundException e)
		{
			logger.fine("Seeds file is in old format. Starting over. :-(");
		}
		catch (IOException e)
		{
			try
			{
				if (SEEDS_EVO_2 != null)
				{
					ObjectInputStream ois;
					ois = new ObjectInputStream(new FileInputStream(SEEDS_EVO_2));
					history = (List<EcBuildOrder>) ois.readObject();
					ois.close();
				}
			}
			catch (FileNotFoundException e1)
			{
				logger.fine("Seeds file not found.");
			}
			catch (InvalidClassException e1)
			{
				logger.fine("Seeds 2 file is in old format. Starting over. :-(");
			}
			catch (ClassNotFoundException e1)
			{
				logger.fine("Seeds 2 file is in old format. Starting over. :-(");
			}
			catch (IOException e1)
			{
				StringWriter sw = new StringWriter();
				e1.printStackTrace(new PrintWriter(sw));
				logger.severe(sw.toString());
			}
		}
	}

	private boolean	haveSavedBefore	= false;

	private synchronized void saveSeeds(IChromosome fittestChromosome)
	{
		EcBuildOrder bo = new EcBuildOrder(importDestination());
		try
		{
			bo = EcEvolver.populateBuildOrder(bo, fittestChromosome, requiredActions);
			if (haveSavedBefore)
				history.remove(history.size() - 1);
			haveSavedBefore = true;
			history.add(bo);
		}
		catch (CloneNotSupportedException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}

		saveSeeds();
	}

	/**
	 * Saves the seeds to the seed file.
	 */
	public void saveSeeds()
	{
		try
		{
			if (SEEDS_EVO != null)
			{
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SEEDS_EVO, false));
				oos.writeObject(history);
				oos.close();
			}
			if (SEEDS_EVO_2 != null)
			{
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SEEDS_EVO_2, false));
				oos.writeObject(history);
				oos.close();
			}
		}
		catch (IOException e)
		{
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
		}
	}

	private Gene[] importInitialGenes(Configuration conf)
	{
		ArrayList<Gene> genes = new ArrayList<Gene>();
		for (int i = 0; i < CHROMOSOME_LENGTH; i++)
			try
			{
				IntegerGene g = new IntegerGene(conf, 0, requiredActions.size() - 1);
				g.setAllele(0);
				genes.add(g);
			}
			catch (InvalidConfigurationException e)
			{
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				logger.severe(sw.toString());
			}
		return genes.toArray(new Gene[genes.size()]);
	}

	private EcBuildOrder importSource()
	{
		EcBuildOrder ecBuildOrder;
		ecBuildOrder = new EcBuildOrder();
		EvolutionChamber genVar1423;
		genVar1423 = this;
		com.fray.evo.EcState genVar1424;
		genVar1424 = genVar1423.importDestination();
		ecBuildOrder.targetSeconds = genVar1424.targetSeconds;
		EvolutionChamber genVar1425;
		genVar1425 = this;
		com.fray.evo.EcState genVar1426;
		genVar1426 = genVar1425.importDestination();
		ecBuildOrder.settings = genVar1426.settings;
		EvolutionChamber genVar1427;
		genVar1427 = this;
		com.fray.evo.EcState genVar1428;
		genVar1428 = genVar1427.importDestination();
		ecBuildOrder.scoutDrone = genVar1428.scoutDrone;
		return ecBuildOrder;
	}

	private EcState importDestination()
	{
		try {
			EvolutionChamber genVar1429;
			genVar1429 = this;
			com.fray.evo.EcState genVar1430;
			genVar1430 = genVar1429.getInternalDestination();
			java.lang.Object genVar1431;
			genVar1431 = genVar1430.clone();
			com.fray.evo.EcState genVar1432;
			genVar1432 = (EcState) genVar1431;
			return genVar1432;
		} catch (CloneNotSupportedException e) {
			StringWriter sw;
			sw = new StringWriter();
			java.io.PrintWriter genVar1433;
			genVar1433 = new PrintWriter(sw);
			e.printStackTrace(genVar1433);
			java.lang.String genVar1434;
			genVar1434 = sw.toString();
			logger.severe(genVar1434);
		}
		return null;
	}

	private Chromosome buildChromosome(Configuration conf, EcBuildOrder bo) throws InvalidConfigurationException
	{
		ArrayList<Gene> genes = new ArrayList<Gene>();
		int CC = 0;
		for (EcAction a : bo.actions)
		{
			if (++CC > CHROMOSOME_LENGTH)
				continue;
			IntegerGene g = new IntegerGene(conf, 0, requiredActions.size() - 1);
			Integer allele = EcAction.findAllele(requiredActions, a);
			if (allele == null)
				break;
			g.setAllele(allele);
			genes.add(g);

		}
		while (genes.size() < CHROMOSOME_LENGTH)
		{
			IntegerGene g = new IntegerGene(conf, 0, requiredActions.size() - 1);
			g.setAllele(0);
			genes.add(g);
		}
		Chromosome c = new Chromosome(conf);
		c.setGenes(genes.toArray(new Gene[genes.size()]));
		c.setIsSelectedForNextGeneration(true);
		return c;
	}

	/**
	 * Sets the number of threads the simulation will use.
	 * Defaults to the number of processors the system has.
	 * @param threads the number of threads
	 */
	public void setThreads(int threads)
	{
		int availableProcessors = MAX_NUM_THREADS;
		NUM_THREADS = threads;
		if (NUM_THREADS > availableProcessors || NUM_THREADS < 1)
			NUM_THREADS = availableProcessors;
	}

	/**
	 * Sets the final goal that the simulation must reach.
	 * @param destination the final goal
	 */
	public void setDestination(EcState destination)
	{
		destination.mergedWaypoints = null;
		this.destination = destination;
	}

	public EcState getInternalDestination()
	{
		return destination;
	}
		
	/**
	 * Gets the number of threads the simulation will use.
	 * @return the number of threads
	 */
	public int getThreads()
	{
		return NUM_THREADS;
	}
	
	/**
	 * gets the list of required actions for the current destination
	 * @return
	 */
	public List<Class<? extends EcAction>> getActions(){
		return requiredActions;
	}
}