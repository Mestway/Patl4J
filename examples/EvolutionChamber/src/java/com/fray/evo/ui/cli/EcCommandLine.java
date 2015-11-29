package com.fray.evo.ui.cli;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Locale;
import javax.swing.Timer;
import com.fray.evo.EcReportable;
import com.fray.evo.EcState;
import com.fray.evo.EvolutionChamber;
import com.fray.evo.ui.swingx.EcSwingXMain;
import com.fray.evo.util.EcMessages;
/** 
 * Runs Evolution Chamber from the command line.
 * @author mike.angstadt
 */
public class EcCommandLine {
  public static final EcMessages messages=new EcMessages("com/fray/evo/ui/cli/messages");
  /** 
 * The detailed build order text that was last generated.
 */
  private static String lastDetailed;
  /** 
 * The YABOT build order text that was last generated.
 */
  private static String lastYabot;
  /** 
 * The number of threads to use.
 */
  private static int threads;
  /** 
 * Simulation will terminate when all threads reach this age. -1 to run forever.
 */
  private static int maxAge;
  /** 
 * Simulation will terminate when it has run for this many minutes. -1 to run forever.
 */
  private static int timeLimit;
  /** 
 * True to only output the last build order when the simulation ends.
 */
  private static boolean onlyOutputFinal;
  /** 
 * True to also print the YABOT string.
 */
  private static boolean printYabot;
  /** 
 * The waypoint goals.
 */
  private static InputFile inputFile;
  /** 
 * The time the simulation began.
 */
  private static long startTime;
  /** 
 * True to stop the simulation.
 */
  private static boolean stop=false;
  public static void main(  String args[]) throws Exception {
    getArguments(args);
    final EvolutionChamber ec;
    ec=new EvolutionChamber();
    com.fray.evo.EcState genVar3259;
    genVar3259=inputFile.getDestination();
    ec.setDestination(genVar3259);
    ec.setThreads(threads);
    EcReportable genVar3260;
    genVar3260=new EcReportable(){
      @Override public void threadScore(      int threadIndex,      String output){
      }
      @Override public synchronized void bestScore(      EcState finalState,      int intValue,      String detailedText,      String simpleText,      String yabotText){
        boolean isSatisfied=detailedText.contains(EcSwingXMain.messages.getString("Satisfied"));
        if (isSatisfied) {
          lastDetailed=detailedText;
          lastYabot=yabotText;
          if (!onlyOutputFinal) {
            System.out.println(detailedText);
            if (printYabot) {
              System.out.println(yabotText);
            }
          }
        }
      }
    }
;
    ec.setReportInterface(genVar3260);
    int genVar3261;
    genVar3261=200;
    ActionListener genVar3262;
    genVar3262=new ActionListener(){
      @Override public void actionPerformed(      ActionEvent e){
        if (maxAge > 0) {
          int oldThreads=0;
          for (int i=0; i < ec.getThreads(); i++) {
            int age=ec.getEvolutionsSinceDiscovery(i);
            if (age > maxAge) {
              oldThreads++;
            }
          }
          if (oldThreads == ec.getThreads()) {
            stop=true;
          }
        }
        if (timeLimit > 0) {
          double runningMinutes=(System.currentTimeMillis() - startTime) / 1000.0 / 60.0;
          if (runningMinutes > timeLimit) {
            stop=true;
          }
        }
      }
    }
;
    Timer ageTimer;
    ageTimer=new Timer(genVar3261,genVar3262);
    startTime=System.currentTimeMillis();
    ec.go();
    ageTimer.start();
    while (!stop) {
      int genVar3263;
      genVar3263=200;
      Thread.sleep(genVar3263);
    }
    ageTimer.stop();
    ec.stopAllThreads();
    boolean genVar3264;
    genVar3264=lastDetailed == null;
    if (genVar3264) {
      java.lang.String genVar3265;
      genVar3265="cli.noBuild";
      java.lang.String genVar3266;
      genVar3266=messages.getString(genVar3265);
      System.out.println(genVar3266);
    }
 else {
      if (onlyOutputFinal) {
        System.out.println(lastDetailed);
        if (printYabot) {
          System.out.println(lastYabot);
        }
 else {
          ;
        }
      }
 else {
        ;
      }
    }
  }
  private static void getArguments(  String args[]) throws Exception {
    int index;
    java.lang.String genVar3268;
    genVar3268="-l";
    index=findArg(args,genVar3268);
    int genVar3269;
    genVar3269=0;
    boolean genVar3270;
    genVar3270=index >= genVar3269;
    if (genVar3270) {
      int genVar3271;
      genVar3271=1;
      int genVar3272;
      genVar3272=index + genVar3271;
      java.lang.String genVar3273;
      genVar3273=args[genVar3272];
      Locale lang;
      lang=new Locale(genVar3273);
      EcSwingXMain.messages.changeLocale(lang);
      messages.changeLocale(lang);
    }
 else {
      ;
    }
    int genVar3274;
    genVar3274=0;
    boolean genVar3275;
    genVar3275=args.length == genVar3274;
    java.lang.String genVar3277;
    genVar3277="--help";
    int genVar3278;
    genVar3278=findArg(args,genVar3277);
    int genVar3279;
    genVar3279=0;
    boolean genVar3280;
    genVar3280=genVar3278 >= genVar3279;
    boolean genVar3281;
    genVar3281=genVar3275 || genVar3280;
    if (genVar3281) {
      java.lang.String genVar3282;
      genVar3282="cli.help";
      java.lang.String genVar3283;
      genVar3283=messages.getString(genVar3282,EvolutionChamber.VERSION);
      System.out.println(genVar3283);
      int genVar3284;
      genVar3284=0;
      System.exit(genVar3284);
    }
 else {
      ;
    }
    java.lang.String genVar3286;
    genVar3286="-s";
    int genVar3287;
    genVar3287=findArg(args,genVar3286);
    int genVar3288;
    genVar3288=0;
    boolean genVar3289;
    genVar3289=genVar3287 >= genVar3288;
    if (genVar3289) {
      java.lang.Class<com.fray.evo.ui.cli.EcCommandLine> genVar3290;
      genVar3290=EcCommandLine.class;
      java.lang.String genVar3291;
      genVar3291="sample.txt";
      java.io.InputStream genVar3292;
      genVar3292=genVar3290.getResourceAsStream(genVar3291);
      java.io.InputStreamReader genVar3293;
      genVar3293=new InputStreamReader(genVar3292);
      BufferedReader in;
      in=new BufferedReader(genVar3293);
      String line;
      while ((line=in.readLine()) != null) {
        System.out.println(line);
      }
      in.close();
      int genVar3294;
      genVar3294=0;
      System.exit(genVar3294);
    }
 else {
      ;
    }
    java.lang.String genVar3296;
    genVar3296="-t";
    index=findArg(args,genVar3296);
    int genVar3297;
    genVar3297=0;
    boolean genVar3298;
    genVar3298=index >= genVar3297;
    int genVar3299;
    genVar3299=1;
    int genVar3300;
    genVar3300=index + genVar3299;
    java.lang.String genVar3301;
    genVar3301=args[genVar3300];
    int genVar3302;
    genVar3302=Integer.parseInt(genVar3301);
    java.lang.Runtime genVar3303;
    genVar3303=Runtime.getRuntime();
    int genVar3304;
    genVar3304=genVar3303.availableProcessors();
    threads=genVar3298 ? genVar3302 : genVar3304;
    java.lang.String genVar3306;
    genVar3306="-a";
    index=findArg(args,genVar3306);
    int genVar3307;
    genVar3307=0;
    boolean genVar3308;
    genVar3308=index >= genVar3307;
    int genVar3309;
    genVar3309=1;
    int genVar3310;
    genVar3310=index + genVar3309;
    java.lang.String genVar3311;
    genVar3311=args[genVar3310];
    int genVar3312;
    genVar3312=Integer.parseInt(genVar3311);
    int genVar3313;
    genVar3313=1;
    int genVar3314;
    genVar3314=-genVar3313;
    maxAge=genVar3308 ? genVar3312 : genVar3314;
    java.lang.String genVar3316;
    genVar3316="-i";
    index=findArg(args,genVar3316);
    int genVar3317;
    genVar3317=0;
    boolean genVar3318;
    genVar3318=index >= genVar3317;
    int genVar3319;
    genVar3319=1;
    int genVar3320;
    genVar3320=index + genVar3319;
    java.lang.String genVar3321;
    genVar3321=args[genVar3320];
    int genVar3322;
    genVar3322=Integer.parseInt(genVar3321);
    int genVar3323;
    genVar3323=1;
    int genVar3324;
    genVar3324=-genVar3323;
    timeLimit=genVar3318 ? genVar3322 : genVar3324;
    java.lang.String genVar3326;
    genVar3326="-f";
    index=findArg(args,genVar3326);
    onlyOutputFinal=index >= 0 && (maxAge >= 0 || timeLimit >= 0);
    java.lang.String genVar3328;
    genVar3328="-y";
    index=findArg(args,genVar3328);
    int genVar3329;
    genVar3329=0;
    printYabot=index >= genVar3329;
    int genVar3330;
    genVar3330=1;
    int genVar3331;
    genVar3331=args.length - genVar3330;
    java.lang.String genVar3332;
    genVar3332=args[genVar3331];
    File waypoints;
    waypoints=new File(genVar3332);
    try {
      inputFile=new InputFile(waypoints);
    }
 catch (    UnknownKeywordException e) {
      java.lang.String genVar3333;
      genVar3333="cli.unknownKeywords";
      java.util.List<java.lang.String> genVar3334;
      genVar3334=e.getKeywords();
      java.lang.String genVar3335;
      genVar3335=messages.getString(genVar3333,genVar3334);
      System.out.println(genVar3335);
      int genVar3336;
      genVar3336=1;
      System.exit(genVar3336);
    }
  }
  /** 
 * Gets the position of an argument in the arguments list.
 * @param args the argument list
 * @param arg the argument to search for
 * @return the index of the argument or -1 if not found
 */
  private static int findArg(  String args[],  String arg){
    int i=0;
    for (; i < args.length; i++) {
      java.lang.String genVar3337;
      genVar3337=args[i];
      boolean genVar3338;
      genVar3338=genVar3337.equals(arg);
      if (genVar3338) {
        return i;
      }
 else {
        ;
      }
    }
    int genVar3339;
    genVar3339=1;
    int genVar3340;
    genVar3340=-genVar3339;
    return genVar3340;
  }
}
