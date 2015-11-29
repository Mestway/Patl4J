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
public final class EvolutionChamber {
  private static final Logger logger=Logger.getLogger(EvolutionChamber.class.getName());
  /** 
 * The version of EvolutionChamber.
 */
  public static final String VERSION="0026";
  /** 
 * The seed file.
 */
  private File SEEDS_EVO=null;
  /** 
 * The backup seed file (in case execution stops while the file is half written).
 */
  private File SEEDS_EVO_2=null;
  /** 
 * Chromosome length refers to the Genetic Algorithm's maximum number of potential actions that can be executed. 
 */
  private int CHROMOSOME_LENGTH=120;
  /** 
 * Population size refers to the number of chromosomes in the gene pool for a population.
 */
  private int POPULATION_SIZE=200;
  /** 
 * Base mutation rate refers to the rate at which EcGeneticUtil mutations occur.
 */
  private double BASE_MUTATION_RATE=5;
  /** 
 * Number of threads to run genetic simulators on.
 */
  private int NUM_THREADS=Runtime.getRuntime().availableProcessors();
  /** 
 * Maximum allowed number of threads to run genetic simulators on.
 */
  private int MAX_NUM_THREADS=Runtime.getRuntime().availableProcessors() * 4;
  /** 
 * The threads that are used for the running simulation.
 */
  private List<Thread> threads=Collections.synchronizedList(new ArrayList<Thread>());
  /** 
 * True to kill all running threads, false to let them continue running.
 */
  private boolean killThreads=false;
  /** 
 * The best fitness score a simulation generated.
 */
  private Double bestScore=0.0;
  private Integer stagnationLimit=0;
  private Double waterMark=0.0;
  private int STAGNATION_LIMIT_MIN=200;
  /** 
 * A list of all previously-run simulations.
 */
  private List<EcBuildOrder> history=new ArrayList<EcBuildOrder>();
  /** 
 * The final goal of the current simulation.
 */
  private EcState destination=EcState.defaultDestination();
  /** 
 * Allows for the results of a simulation to be outputted in real time.
 */
  private EcReportable reportInterface;
  /** 
 * The best fitness score for each thread.
 */
  private Double[] bestScores;
  private Integer[] evolutionsSinceDiscovery;
  private EcEvolver[] evolvers;
  private int forgottenEvolutions;
  private boolean firstrun=true;
  private boolean newbestscore=false;
  /** 
 * The caluation to use for determining the fitness score of a gene.
 */
  private EcFitnessType fitnessType=EcFitnessType.STANDARD;
  /** 
 * the minimum of required actions, determined by analyzing the destination state
 */
  private List<Class<? extends EcAction>> requiredActions=new ArrayList<Class<? extends EcAction>>();
  public EvolutionChamber(){
  }
  /** 
 * Creates a new object, loading the seeds from the given files.
 * @param seeds
 * @param backupSeeds
 */
  public EvolutionChamber(  File seeds,  File backupSeeds){
    SEEDS_EVO=seeds;
    SEEDS_EVO_2=backupSeeds;
    EvolutionChamber genVar1265;
    genVar1265=this;
    genVar1265.loadSeeds();
  }
  public List<EcBuildOrder> getHistory(){
    return history;
  }
  public int getEvolutionsSinceDiscovery(  int thread){
    java.lang.Integer genVar1266;
    genVar1266=evolutionsSinceDiscovery[thread];
    return genVar1266;
  }
  /** 
 * Determines if a simulation is currently running.
 * @return true if a simulation is running, false if not.
 */
  public boolean isRunning(){
    int genVar1267;
    genVar1267=threads.size();
    int genVar1268;
    genVar1268=0;
    boolean genVar1269;
    genVar1269=genVar1267 > genVar1268;
    return genVar1269;
  }
  public Double[] getBestScores(){
    return bestScores;
  }
  public int getStagnationLimit(){
    return stagnationLimit;
  }
  public int getChromosomeLength(){
    return CHROMOSOME_LENGTH;
  }
  public double getBaseMutationRate(){
    return BASE_MUTATION_RATE;
  }
  public void setReportInterface(  EcReportable reportInterface){
    com.fray.evo.EvolutionChamber genVar1270;
    genVar1270=this;
    genVar1270.reportInterface=reportInterface;
  }
  public void setSeedFile(  File file){
    SEEDS_EVO=file;
  }
  public void setBackupSeedFile(  File file){
    SEEDS_EVO_2=file;
  }
  /** 
 * Sets the caluation to use for determining the fitness score of a gene.
 * @param fitnessType the fitness type
 */
  public void setFitnessType(  EcFitnessType fitnessType){
    com.fray.evo.EvolutionChamber genVar1271;
    genVar1271=this;
    genVar1271.fitnessType=fitnessType;
  }
  /** 
 * Gets the total number of games played since the simulation started.
 * @return
 */
  public long getGamesPlayed(){
    long total;
    total=0;
    int i=0;
    for (; i < evolvers.length; i++) {
      EcEvolver evolver;
      evolver=evolvers[i];
      total+=evolver.getEvaluations();
    }
    long genVar1272;
    genVar1272=total + forgottenEvolutions;
    return genVar1272;
  }
  /** 
 * Starts the simulation (asychronously).
 * @throws InvalidConfigurationException
 */
  public void go() throws InvalidConfigurationException {
    EvolutionChamber genVar1273;
    genVar1273=this;
    genVar1273.reset();
    EvolutionChamber genVar1274;
    genVar1274=this;
    EcState s;
    s=genVar1274.importSource();
    EvolutionChamber genVar1275;
    genVar1275=this;
    EcState d;
    d=genVar1275.getInternalDestination();
    requiredActions=EcRequirementTree.createActionList(d);
    d.settings.fitnessType=fitnessType;
    int genVar1276;
    genVar1276=d.getEstimatedActions();
    int genVar1277;
    genVar1277=70;
    CHROMOSOME_LENGTH=genVar1276 + genVar1277;
    int threadIndex=0;
    for (; threadIndex < NUM_THREADS; threadIndex++) {
      EvolutionChamber genVar1278;
      genVar1278=this;
      genVar1278.spawnEvolutionaryChamber(s,d,threadIndex);
    }
  }
  /** 
 * Resets the object so it can run a new simulation.
 */
  private synchronized void reset(){
    killThreads=false;
    firstrun=true;
    haveSavedBefore=false;
    bestScore=0.0;
    bestScores=new Double[NUM_THREADS];
    evolutionsSinceDiscovery=new Integer[NUM_THREADS];
    evolvers=new EcEvolver[NUM_THREADS];
    forgottenEvolutions=0;
  }
  /** 
 * Stops the simulation.
 */
  public void stopAllThreads(){
    killThreads=true;
    java.util.ArrayList<java.lang.Thread> genVar1279;
    genVar1279=new ArrayList<Thread>(threads);
    for (    Thread t : genVar1279)     try {
      t.join();
    }
 catch (    InterruptedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar1280;
      genVar1280=new PrintWriter(sw);
      e.printStackTrace(genVar1280);
      java.lang.String genVar1281;
      genVar1281=sw.toString();
      logger.severe(genVar1281);
    }
    threads.clear();
  }
  private void spawnEvolutionaryChamber(  final EcState source,  final EcState destination,  final int threadIndex) throws InvalidConfigurationException {
    EvolutionChamber genVar1282;
    genVar1282=this;
    genVar1282.reset(threadIndex);
    final EcEvolver myFunc;
    myFunc=new EcEvolver(source,destination,requiredActions);
    evolvers[threadIndex]=myFunc;
    EvolutionChamber genVar1283;
    genVar1283=this;
    final Configuration conf;
    conf=genVar1283.constructConfiguration(threadIndex,myFunc);
    final Genotype population;
    population=Genotype.randomInitialGenotype(conf);
    boolean genVar1284;
    genVar1284=firstrun && threadIndex == 0;
    if (genVar1284) {
      EvolutionChamber genVar1285;
      genVar1285=this;
      genVar1285.loadOldBuildOrders(population,conf,myFunc);
    }
 else {
      boolean genVar1286;
      genVar1286=firstrun && threadIndex == NUM_THREADS - 1;
      if (genVar1286) {
        firstrun=false;
      }
 else {
        ;
      }
    }
    final Thread thread;
    thread=new Thread(population);
    org.jgap.event.IEventManager genVar1287;
    genVar1287=conf.getEventManager();
    GeneticEventListener genVar1288;
    genVar1288=new GeneticEventListener(){
      @Override public void geneticEventFired(      GeneticEvent a_firedEvent){
        Collections.shuffle(conf.getGeneticOperators());
        BASE_MUTATION_RATE+=.001;
        if (BASE_MUTATION_RATE >= (double)CHROMOSOME_LENGTH / 2.0)         BASE_MUTATION_RATE=1;
        IChromosome fittestChromosome=population.getFittestChromosome();
        if (killThreads) {
          threads.remove(thread);
          thread.interrupt();
        }
        double fitnessValue=fittestChromosome.getFitnessValue();
        if (fitnessValue > bestScores[threadIndex]) {
          bestScores[threadIndex]=fitnessValue;
          evolutionsSinceDiscovery[threadIndex]=0;
          BASE_MUTATION_RATE=1;
          if (reportInterface != null)           reportInterface.threadScore(threadIndex,getOutput(myFunc,fittestChromosome,fitnessValue));
        }
 else         evolutionsSinceDiscovery[threadIndex]++;
        int highestevosSinceDiscovery=0;
        for (int i=0; i < bestScores.length; i++) {
          if (bestScores[i] >= bestScore) {
            if (evolutionsSinceDiscovery[i] > highestevosSinceDiscovery)             highestevosSinceDiscovery=evolutionsSinceDiscovery[i];
          }
        }
        stagnationLimit=Math.max(STAGNATION_LIMIT_MIN,(int)Math.ceil(highestevosSinceDiscovery * (.5)));
        if (fitnessValue < bestScore) {
          if (evolutionsSinceDiscovery[threadIndex] > stagnationLimit && fitnessValue < waterMark) {
            suicide(source,destination,threadIndex,thread);
          }
 else           if (evolutionsSinceDiscovery[threadIndex] > stagnationLimit * 3) {
            suicide(source,destination,threadIndex,thread);
          }
        }
 else         if (evolutionsSinceDiscovery[threadIndex] > stagnationLimit) {
          if (newbestscore) {
            waterMark=fitnessValue;
          }
          int totalevoSinceDiscoveryOnBest=0;
          int numBestThreads=0;
          for (int i=0; i < bestScores.length; i++) {
            if (bestScores[i] >= bestScore) {
              numBestThreads++;
              totalevoSinceDiscoveryOnBest+=evolutionsSinceDiscovery[i];
            }
          }
          if (totalevoSinceDiscoveryOnBest > stagnationLimit * 3 * numBestThreads) {
            suicide(source,destination,threadIndex,thread);
          }
        }
synchronized (bestScore) {
          if (fitnessValue > bestScore) {
            BASE_MUTATION_RATE=1;
            bestScore=fitnessValue;
            newbestscore=true;
            String exactBuildOrder=getOutput(myFunc,fittestChromosome,fitnessValue);
            String buildOrder=getBuildOrder(myFunc,fittestChromosome);
            String yabotBuildOrder=getYabotBuildOrder(myFunc,fittestChromosome);
            if (reportInterface != null)             reportInterface.bestScore(myFunc.evaluateGetBuildOrder(fittestChromosome),bestScore.intValue(),exactBuildOrder,buildOrder,yabotBuildOrder);
            if (logger.isLoggable(Level.FINE)) {
              logger.fine(chromosomeToString(fittestChromosome));
            }
            saveSeeds(fittestChromosome);
          }
        }
      }
    }
;
    genVar1287.addEventListener(GeneticEvent.GENOTYPE_EVOLVED_EVENT,genVar1288);
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
  private void suicide(  final EcState source,  final EcState destination,  final int threadIndex,  final Thread thread){
    com.fray.evo.EcEvolver genVar1289;
    genVar1289=evolvers[threadIndex];
    forgottenEvolutions+=genVar1289.getEvaluations();
    java.lang.String genVar1290;
    genVar1290="Restarting thread ";
    java.lang.String genVar1291;
    genVar1291=genVar1290 + threadIndex;
    logger.fine(genVar1291);
    try {
      EvolutionChamber genVar1292;
      genVar1292=this;
      genVar1292.spawnEvolutionaryChamber(source,destination,threadIndex);
    }
 catch (    InvalidConfigurationException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar1293;
      genVar1293=new PrintWriter(sw);
      e.printStackTrace(genVar1293);
      java.lang.String genVar1294;
      genVar1294=sw.toString();
      logger.severe(genVar1294);
    }
    threads.remove(thread);
    thread.interrupt();
  }
  private void reset(  final int threadIndex){
    bestScores[threadIndex]=0.0;
    evolutionsSinceDiscovery[threadIndex]=0;
    evolvers[threadIndex]=null;
  }
  /** 
 * Generates a build order string.
 * @param myFunc
 * @param fittestChromosome
 * @param fitnessValue
 * @return
 */
  private String getOutput(  final EcEvolver myFunc,  IChromosome fittestChromosome,  double fitnessValue){
    ByteArrayOutputStream byteArrayOutputStream;
    byteArrayOutputStream=new ByteArrayOutputStream();
    PrintStream ps;
    ps=new PrintStream(byteArrayOutputStream);
    boolean genVar1295;
    genVar1295=reportInterface != null;
    if (genVar1295) {
      myFunc.setLoggingStream(ps);
    }
 else {
      ;
    }
    EvolutionChamber genVar1296;
    genVar1296=this;
    genVar1296.displayBuildOrder(myFunc,fittestChromosome);
    java.util.Date genVar1297;
    genVar1297=new Date();
    java.lang.String genVar1298;
    genVar1298=": ";
    java.lang.String genVar1299;
    genVar1299=genVar1297 + genVar1298 + fitnessValue;
    ps.println(genVar1299);
    byte[] genVar1300;
    genVar1300=byteArrayOutputStream.toByteArray();
    String results;
    results=new String(genVar1300);
    return results;
  }
  /** 
 * Generates a build order string.
 * @param myFunc
 * @param fittestChromosome
 * @return
 */
  public String getBuildOrder(  final EcEvolver myFunc,  IChromosome fittestChromosome){
    java.lang.String genVar1301;
    genVar1301=myFunc.getBuildOrder(fittestChromosome);
    return genVar1301;
  }
  /** 
 * Builds the YABOT representation of a build order.
 * @param myFunc
 * @param fittestChromosome
 * @return
 */
  public String getYabotBuildOrder(  final EcEvolver myFunc,  IChromosome fittestChromosome){
    java.lang.String genVar1302;
    genVar1302=myFunc.getYabotBuildOrder(fittestChromosome);
    return genVar1302;
  }
  private Configuration constructConfiguration(  final int threadIndex,  final EcEvolver myFunc) throws InvalidConfigurationException {
    java.lang.String genVar1303;
    genVar1303=" thread.";
    java.lang.String genVar1304;
    genVar1304=threadIndex + genVar1303;
    DefaultConfiguration.reset(genVar1304);
    java.lang.String genVar1305;
    genVar1305=" thread.";
    java.lang.String genVar1306;
    genVar1306=threadIndex + genVar1305;
    java.lang.String genVar1307;
    genVar1307=" thread.";
    java.lang.String genVar1308;
    genVar1308=threadIndex + genVar1307;
    Configuration conf;
    conf=new DefaultConfiguration(genVar1306,genVar1308);
    conf.setFitnessFunction(myFunc);
    com.fray.evo.EvolutionChamber genVar1309;
    genVar1309=this;
    org.jgap.GeneticOperator genVar1310;
    genVar1310=EcGeneticUtil.getCleansingOperator(genVar1309);
    conf.addGeneticOperator(genVar1310);
    com.fray.evo.EvolutionChamber genVar1311;
    genVar1311=this;
    org.jgap.GeneticOperator genVar1312;
    genVar1312=EcGeneticUtil.getOverlordingOperator(genVar1311,requiredActions);
    conf.addGeneticOperator(genVar1312);
    com.fray.evo.EvolutionChamber genVar1313;
    genVar1313=this;
    org.jgap.GeneticOperator genVar1314;
    genVar1314=EcGeneticUtil.getInsertionOperator(genVar1313);
    conf.addGeneticOperator(genVar1314);
    com.fray.evo.EvolutionChamber genVar1315;
    genVar1315=this;
    org.jgap.GeneticOperator genVar1316;
    genVar1316=EcGeneticUtil.getDeletionOperator(genVar1315);
    conf.addGeneticOperator(genVar1316);
    com.fray.evo.EvolutionChamber genVar1317;
    genVar1317=this;
    org.jgap.GeneticOperator genVar1318;
    genVar1318=EcGeneticUtil.getTwiddleOperator(genVar1317);
    conf.addGeneticOperator(genVar1318);
    com.fray.evo.EvolutionChamber genVar1319;
    genVar1319=this;
    org.jgap.GeneticOperator genVar1320;
    genVar1320=EcGeneticUtil.getSwapOperator(genVar1319);
    conf.addGeneticOperator(genVar1320);
    conf.setPopulationSize(POPULATION_SIZE);
    int genVar1321;
    genVar1321=1;
    conf.setSelectFromPrevGen(genVar1321);
    boolean genVar1322;
    genVar1322=false;
    conf.setPreservFittestIndividual(genVar1322);
    boolean genVar1323;
    genVar1323=false;
    conf.setAlwaysCaculateFitness(genVar1323);
    boolean genVar1324;
    genVar1324=false;
    conf.setKeepPopulationSizeConstant(genVar1324);
    EvolutionChamber genVar1325;
    genVar1325=this;
    Gene[] initialGenes;
    initialGenes=genVar1325.importInitialGenes(conf);
    Chromosome c;
    c=new Chromosome(conf,initialGenes);
    conf.setSampleChromosome(c);
    return conf;
  }
  /** 
 * Converts a                                                    {@link IChromosome} to its string representation.
 * @param chromosome
 * @return
 */
  private static String chromosomeToString(  IChromosome chromosome){
    int i;
    i=0;
    StringBuilder sb;
    sb=new StringBuilder();
    org.jgap.Gene[] genVar1326;
    genVar1326=chromosome.getGenes();
    for (    Gene g : genVar1326) {
      int genVar1327;
      genVar1327=i++;
      int genVar1328;
      genVar1328=100;
      boolean genVar1329;
      genVar1329=genVar1327 == genVar1328;
      if (genVar1329) {
        break;
      }
 else {
        ;
      }
      java.lang.Object genVar1330;
      genVar1330=g.getAllele();
      java.lang.Integer genVar1331;
      genVar1331=(Integer)genVar1330;
      Integer genVar1332;
      genVar1332=(genVar1331);
      int genVar1333;
      genVar1333=genVar1332.intValue();
      int genVar1334;
      genVar1334=10;
      boolean genVar1335;
      genVar1335=genVar1333 >= genVar1334;
      if (genVar1335) {
        char genVar1336;
        genVar1336='a';
        int genVar1337;
        genVar1337=(int)genVar1336;
        java.lang.Object genVar1338;
        genVar1338=g.getAllele();
        java.lang.Integer genVar1339;
        genVar1339=(Integer)genVar1338;
        int genVar1340;
        genVar1340=genVar1337 + genVar1339;
        int genVar1341;
        genVar1341=10;
        int genVar1342;
        genVar1342=genVar1340 - genVar1341;
        int genVar1343;
        genVar1343=(genVar1342);
        char genVar1344;
        genVar1344=(char)genVar1343;
        char genVar1345;
        genVar1345=(genVar1344);
        sb.append(genVar1345);
      }
 else {
        java.lang.Object genVar1346;
        genVar1346=g.getAllele();
        java.lang.String genVar1347;
        genVar1347=genVar1346.toString();
        sb.append(genVar1347);
      }
    }
    java.lang.String genVar1348;
    genVar1348=sb.toString();
    return genVar1348;
  }
  private static void displayBuildOrder(  final EcEvolver myFunc,  IChromosome fittestChromosome){
    boolean genVar1349;
    genVar1349=true;
    myFunc.enableLogging(genVar1349);
    myFunc.evaluateGetBuildOrder(fittestChromosome);
    boolean genVar1350;
    genVar1350=false;
    myFunc.enableLogging(genVar1350);
  }
  private synchronized void loadOldBuildOrders(  Genotype population,  final Configuration conf,  final EcEvolver myFunc){
    EvolutionChamber genVar1351;
    genVar1351=this;
    genVar1351.loadSeeds();
    int cindex;
    cindex=0;
    Comparator<EcBuildOrder> genVar1352;
    genVar1352=new Comparator<EcBuildOrder>(){
      @Override public int compare(      EcBuildOrder arg0,      EcBuildOrder arg1){
        double score=0;
        try {
          score=myFunc.getFitnessValue(buildChromosome(conf,arg1)) - myFunc.getFitnessValue(buildChromosome(conf,arg0));
        }
 catch (        InvalidConfigurationException e) {
          StringWriter sw=new StringWriter();
          e.printStackTrace(new PrintWriter(sw));
          logger.severe(sw.toString());
        }
        return (int)score;
      }
    }
;
    Collections.sort(history,genVar1352);
    for (    EcBuildOrder bo : history) {
      try {
        EvolutionChamber genVar1353;
        genVar1353=this;
        Chromosome c;
        c=genVar1353.buildChromosome(conf,bo);
        double genVar1354;
        genVar1354=myFunc.getFitnessValue(c);
        java.lang.String genVar1355;
        genVar1355="";
        java.lang.String genVar1356;
        genVar1356=genVar1354 + genVar1355;
        logger.fine(genVar1356);
        org.jgap.Population genVar1357;
        genVar1357=population.getPopulation();
        int genVar1358;
        genVar1358=cindex++;
        genVar1357.setChromosome(genVar1358,c);
      }
 catch (      InvalidConfigurationException e) {
        StringWriter sw;
        sw=new StringWriter();
        java.io.PrintWriter genVar1359;
        genVar1359=new PrintWriter(sw);
        e.printStackTrace(genVar1359);
        java.lang.String genVar1360;
        genVar1360=sw.toString();
        logger.severe(genVar1360);
      }
    }
  }
  /** 
 * Loads the seeds from the seed file.
 */
  public void loadSeeds(){
    try {
      boolean genVar1361;
      genVar1361=SEEDS_EVO != null;
      if (genVar1361) {
        java.io.FileInputStream genVar1362;
        genVar1362=new FileInputStream(SEEDS_EVO);
        ObjectInputStream ois;
        ois=new ObjectInputStream(genVar1362);
        java.lang.Object genVar1363;
        genVar1363=ois.readObject();
        history=(List<EcBuildOrder>)genVar1363;
        ois.close();
      }
 else {
        java.io.IOException genVar1364;
        genVar1364=new IOException();
        throw genVar1364;
      }
    }
 catch (    FileNotFoundException e) {
      java.lang.String genVar1365;
      genVar1365="Seeds file not found.";
      logger.fine(genVar1365);
    }
catch (    InvalidClassException ex) {
      java.lang.String genVar1366;
      genVar1366="Seeds file is in old format. Starting over. :-(";
      logger.fine(genVar1366);
    }
catch (    ClassNotFoundException e) {
      java.lang.String genVar1367;
      genVar1367="Seeds file is in old format. Starting over. :-(";
      logger.fine(genVar1367);
    }
catch (    IOException e) {
      try {
        boolean genVar1368;
        genVar1368=SEEDS_EVO_2 != null;
        if (genVar1368) {
          ObjectInputStream ois;
          java.io.FileInputStream genVar1369;
          genVar1369=new FileInputStream(SEEDS_EVO_2);
          ois=new ObjectInputStream(genVar1369);
          java.lang.Object genVar1370;
          genVar1370=ois.readObject();
          history=(List<EcBuildOrder>)genVar1370;
          ois.close();
        }
 else {
          ;
        }
      }
 catch (      FileNotFoundException e1) {
        java.lang.String genVar1371;
        genVar1371="Seeds file not found.";
        logger.fine(genVar1371);
      }
catch (      InvalidClassException e1) {
        java.lang.String genVar1372;
        genVar1372="Seeds 2 file is in old format. Starting over. :-(";
        logger.fine(genVar1372);
      }
catch (      ClassNotFoundException e1) {
        java.lang.String genVar1373;
        genVar1373="Seeds 2 file is in old format. Starting over. :-(";
        logger.fine(genVar1373);
      }
catch (      IOException e1) {
        StringWriter sw;
        sw=new StringWriter();
        java.io.PrintWriter genVar1374;
        genVar1374=new PrintWriter(sw);
        e1.printStackTrace(genVar1374);
        java.lang.String genVar1375;
        genVar1375=sw.toString();
        logger.severe(genVar1375);
      }
    }
  }
  private boolean haveSavedBefore=false;
  private synchronized void saveSeeds(  IChromosome fittestChromosome){
    EvolutionChamber genVar1376;
    genVar1376=this;
    com.fray.evo.EcState genVar1377;
    genVar1377=genVar1376.importDestination();
    EcBuildOrder bo;
    bo=new EcBuildOrder(genVar1377);
    try {
      bo=EcEvolver.populateBuildOrder(bo,fittestChromosome,requiredActions);
      if (haveSavedBefore) {
        int genVar1378;
        genVar1378=history.size();
        int genVar1379;
        genVar1379=1;
        int genVar1380;
        genVar1380=genVar1378 - genVar1379;
        history.remove(genVar1380);
      }
 else {
        ;
      }
      haveSavedBefore=true;
      history.add(bo);
    }
 catch (    CloneNotSupportedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar1381;
      genVar1381=new PrintWriter(sw);
      e.printStackTrace(genVar1381);
      java.lang.String genVar1382;
      genVar1382=sw.toString();
      logger.severe(genVar1382);
    }
    EvolutionChamber genVar1383;
    genVar1383=this;
    genVar1383.saveSeeds();
  }
  /** 
 * Saves the seeds to the seed file.
 */
  public void saveSeeds(){
    try {
      boolean genVar1384;
      genVar1384=SEEDS_EVO != null;
      if (genVar1384) {
        boolean genVar1385;
        genVar1385=false;
        java.io.FileOutputStream genVar1386;
        genVar1386=new FileOutputStream(SEEDS_EVO,genVar1385);
        ObjectOutputStream oos;
        oos=new ObjectOutputStream(genVar1386);
        oos.writeObject(history);
        oos.close();
      }
 else {
        ;
      }
      boolean genVar1387;
      genVar1387=SEEDS_EVO_2 != null;
      if (genVar1387) {
        boolean genVar1388;
        genVar1388=false;
        java.io.FileOutputStream genVar1389;
        genVar1389=new FileOutputStream(SEEDS_EVO_2,genVar1388);
        ObjectOutputStream oos;
        oos=new ObjectOutputStream(genVar1389);
        oos.writeObject(history);
        oos.close();
      }
 else {
        ;
      }
    }
 catch (    IOException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar1390;
      genVar1390=new PrintWriter(sw);
      e.printStackTrace(genVar1390);
      java.lang.String genVar1391;
      genVar1391=sw.toString();
      logger.severe(genVar1391);
    }
  }
  private Gene[] importInitialGenes(  Configuration conf){
    ArrayList<Gene> genes;
    genes=new ArrayList<Gene>();
    int i=0;
    for (; i < CHROMOSOME_LENGTH; i++) {
      try {
        int genVar1392;
        genVar1392=0;
        int genVar1393;
        genVar1393=requiredActions.size();
        int genVar1394;
        genVar1394=1;
        int genVar1395;
        genVar1395=genVar1393 - genVar1394;
        IntegerGene g;
        g=new IntegerGene(conf,genVar1392,genVar1395);
        int genVar1396;
        genVar1396=0;
        g.setAllele(genVar1396);
        genes.add(g);
      }
 catch (      InvalidConfigurationException e) {
        StringWriter sw;
        sw=new StringWriter();
        java.io.PrintWriter genVar1397;
        genVar1397=new PrintWriter(sw);
        e.printStackTrace(genVar1397);
        java.lang.String genVar1398;
        genVar1398=sw.toString();
        logger.severe(genVar1398);
      }
    }
    org.jgap.Gene[] genVar1399;
    genVar1399=new Gene[genes.size()];
    org.jgap.Gene[] genVar1400;
    genVar1400=genes.toArray(genVar1399);
    return genVar1400;
  }
  private EcBuildOrder importSource(){
    EcBuildOrder ecBuildOrder;
    ecBuildOrder=new EcBuildOrder();
    EvolutionChamber genVar1401;
    genVar1401=this;
    com.fray.evo.EcState genVar1402;
    genVar1402=genVar1401.importDestination();
    ecBuildOrder.targetSeconds=genVar1402.targetSeconds;
    EvolutionChamber genVar1403;
    genVar1403=this;
    com.fray.evo.EcState genVar1404;
    genVar1404=genVar1403.importDestination();
    ecBuildOrder.settings=genVar1404.settings;
    EvolutionChamber genVar1405;
    genVar1405=this;
    com.fray.evo.EcState genVar1406;
    genVar1406=genVar1405.importDestination();
    ecBuildOrder.scoutDrone=genVar1406.scoutDrone;
    return ecBuildOrder;
  }
  private EcState importDestination(){
    try {
      EvolutionChamber genVar1407;
      genVar1407=this;
      com.fray.evo.EcState genVar1408;
      genVar1408=genVar1407.getInternalDestination();
      java.lang.Object genVar1409;
      genVar1409=genVar1408.clone();
      com.fray.evo.EcState genVar1410;
      genVar1410=(EcState)genVar1409;
      return genVar1410;
    }
 catch (    CloneNotSupportedException e) {
      StringWriter sw;
      sw=new StringWriter();
      java.io.PrintWriter genVar1411;
      genVar1411=new PrintWriter(sw);
      e.printStackTrace(genVar1411);
      java.lang.String genVar1412;
      genVar1412=sw.toString();
      logger.severe(genVar1412);
    }
    return null;
  }
  private Chromosome buildChromosome(  Configuration conf,  EcBuildOrder bo) throws InvalidConfigurationException {
    ArrayList<Gene> genes;
    genes=new ArrayList<Gene>();
    int CC;
    CC=0;
    for (    EcAction a : bo.actions) {
      int genVar1413;
      genVar1413=++CC;
      boolean genVar1414;
      genVar1414=genVar1413 > CHROMOSOME_LENGTH;
      if (genVar1414) {
        continue;
      }
 else {
        ;
      }
      int genVar1415;
      genVar1415=0;
      int genVar1416;
      genVar1416=requiredActions.size();
      int genVar1417;
      genVar1417=1;
      int genVar1418;
      genVar1418=genVar1416 - genVar1417;
      IntegerGene g;
      g=new IntegerGene(conf,genVar1415,genVar1418);
      Integer allele;
      allele=EcAction.findAllele(requiredActions,a);
      boolean genVar1419;
      genVar1419=allele == null;
      if (genVar1419) {
        break;
      }
 else {
        ;
      }
      g.setAllele(allele);
      genes.add(g);
    }
    while (genes.size() < CHROMOSOME_LENGTH) {
      int genVar1420;
      genVar1420=0;
      int genVar1421;
      genVar1421=requiredActions.size();
      int genVar1422;
      genVar1422=1;
      int genVar1423;
      genVar1423=genVar1421 - genVar1422;
      IntegerGene g;
      g=new IntegerGene(conf,genVar1420,genVar1423);
      int genVar1424;
      genVar1424=0;
      g.setAllele(genVar1424);
      genes.add(g);
    }
    Chromosome c;
    c=new Chromosome(conf);
    org.jgap.Gene[] genVar1425;
    genVar1425=new Gene[genes.size()];
    org.jgap.Gene[] genVar1426;
    genVar1426=genes.toArray(genVar1425);
    c.setGenes(genVar1426);
    boolean genVar1427;
    genVar1427=true;
    c.setIsSelectedForNextGeneration(genVar1427);
    return c;
  }
  /** 
 * Sets the number of threads the simulation will use. Defaults to the number of processors the system has.
 * @param threads the number of threads
 */
  public void setThreads(  int threads){
    int availableProcessors;
    availableProcessors=MAX_NUM_THREADS;
    NUM_THREADS=threads;
    boolean genVar1428;
    genVar1428=NUM_THREADS > availableProcessors;
    int genVar1429;
    genVar1429=1;
    boolean genVar1430;
    genVar1430=NUM_THREADS < genVar1429;
    boolean genVar1431;
    genVar1431=genVar1428 || genVar1430;
    if (genVar1431) {
      NUM_THREADS=availableProcessors;
    }
 else {
      ;
    }
  }
  /** 
 * Sets the final goal that the simulation must reach.
 * @param destination the final goal
 */
  public void setDestination(  EcState destination){
    destination.mergedWaypoints=null;
    com.fray.evo.EvolutionChamber genVar1432;
    genVar1432=this;
    genVar1432.destination=destination;
  }
  public EcState getInternalDestination(){
    return destination;
  }
  /** 
 * Gets the number of threads the simulation will use.
 * @return the number of threads
 */
  public int getThreads(){
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
