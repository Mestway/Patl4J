package com.fray.evo.ui.cli;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fray.evo.EcSettings;
import com.fray.evo.EcState;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.Unit;
import com.fray.evo.util.Upgrade;
import com.fray.evo.util.ZergLibrary;
/** 
 * Reads a waypoints file.
 * @author mike.angstadt
 */
public class InputFile {
  /** 
 * Maps unit names from the input file to units in EC.
 */
  private static final Map<String,Buildable[]> buildables=new HashMap<String,Buildable[]>();
static {
    buildables.put("drone",new Buildable[]{ZergLibrary.Drone});
    buildables.put("baneling",new Buildable[]{ZergLibrary.Baneling});
    buildables.put("brood-lord",new Buildable[]{ZergLibrary.Broodlord});
    buildables.put("corruptor",new Buildable[]{ZergLibrary.Corruptor});
    buildables.put("hydralisk",new Buildable[]{ZergLibrary.Hydralisk});
    buildables.put("infestor",new Buildable[]{ZergLibrary.Infestor});
    buildables.put("mutalisk",new Buildable[]{ZergLibrary.Mutalisk});
    buildables.put("overlord",new Buildable[]{ZergLibrary.Overlord});
    buildables.put("overseer",new Buildable[]{ZergLibrary.Overseer});
    buildables.put("queen",new Buildable[]{ZergLibrary.Queen});
    buildables.put("roach",new Buildable[]{ZergLibrary.Roach});
    buildables.put("ultralisk",new Buildable[]{ZergLibrary.Ultralisk});
    buildables.put("zergling",new Buildable[]{ZergLibrary.Zergling});
    buildables.put("baneling-nest",new Buildable[]{ZergLibrary.BanelingNest});
    buildables.put("evolution-chamber",new Buildable[]{ZergLibrary.EvolutionChamber});
    buildables.put("extractor",new Buildable[]{ZergLibrary.Extractor});
    buildables.put("greater-spire",new Buildable[]{ZergLibrary.GreaterSpire});
    buildables.put("hatchery",new Buildable[]{ZergLibrary.Hatchery});
    buildables.put("hive",new Buildable[]{ZergLibrary.Hive});
    buildables.put("hydralisk-den",new Buildable[]{ZergLibrary.HydraliskDen});
    buildables.put("infestation-pit",new Buildable[]{ZergLibrary.InfestationPit});
    buildables.put("lair",new Buildable[]{ZergLibrary.Lair});
    buildables.put("nydus-network",new Buildable[]{ZergLibrary.NydusNetwork});
    buildables.put("nydus-worm",new Buildable[]{ZergLibrary.NydusWorm});
    buildables.put("roach-warren",new Buildable[]{ZergLibrary.RoachWarren});
    buildables.put("spawning-pool",new Buildable[]{ZergLibrary.SpawningPool});
    buildables.put("spine-crawler",new Buildable[]{ZergLibrary.SpineCrawler});
    buildables.put("spire",new Buildable[]{ZergLibrary.Spire});
    buildables.put("spore-crawler",new Buildable[]{ZergLibrary.SporeCrawler});
    buildables.put("ultralisk-cavern",new Buildable[]{ZergLibrary.UltraliskCavern});
    buildables.put("adrenal-glands",new Buildable[]{ZergLibrary.AdrenalGlands});
    buildables.put("burrow",new Buildable[]{ZergLibrary.Burrow});
    buildables.put("carapace",new Buildable[]{ZergLibrary.Armor1,ZergLibrary.Armor2,ZergLibrary.Armor3});
    buildables.put("centrifugal-hooks",new Buildable[]{ZergLibrary.CentrifugalHooks});
    buildables.put("chitinous-plating",new Buildable[]{ZergLibrary.ChitinousPlating});
    buildables.put("flyer-armor",new Buildable[]{ZergLibrary.FlyerArmor1,ZergLibrary.FlyerArmor2,ZergLibrary.FlyerArmor3});
    buildables.put("flyer-attacks",new Buildable[]{ZergLibrary.FlyerAttacks1,ZergLibrary.FlyerAttacks2,ZergLibrary.FlyerAttacks3});
    buildables.put("glial-reconstitution",new Buildable[]{ZergLibrary.GlialReconstitution});
    buildables.put("grooved-spines",new Buildable[]{ZergLibrary.GroovedSpines});
    buildables.put("melee",new Buildable[]{ZergLibrary.Melee1,ZergLibrary.Melee2,ZergLibrary.Melee3});
    buildables.put("metabolic-boost",new Buildable[]{ZergLibrary.MetabolicBoost});
    buildables.put("missile",new Buildable[]{ZergLibrary.Missile1,ZergLibrary.Missile2,ZergLibrary.Missile3});
    buildables.put("neural-parasite",new Buildable[]{ZergLibrary.NeuralParasite});
    buildables.put("pathogen-glands",new Buildable[]{ZergLibrary.PathogenGlands});
    buildables.put("pneumatized-carapace",new Buildable[]{ZergLibrary.PneumatizedCarapace});
    buildables.put("tunneling-claws",new Buildable[]{ZergLibrary.TunnelingClaws});
    buildables.put("ventral-sacs",new Buildable[]{ZergLibrary.VentralSacs});
  }
  private int scoutTiming=0;
  private EcSettings settings;
  private List<EcState> waypoints=new ArrayList<EcState>();
  public InputFile(  File file) throws IOException, UnknownKeywordException {
    this(new FileReader(file));
  }
  public InputFile(  Reader reader) throws IOException, UnknownKeywordException {
    BufferedReader in;
    in=null;
    List<String> unknownKeywords;
    unknownKeywords=new ArrayList<String>();
    try {
      in=new BufferedReader(reader);
      String line;
      EcState curWaypoint;
      curWaypoint=null;
      boolean inSettingsBlock;
      inSettingsBlock=false;
      while ((line=in.readLine()) != null) {
        char genVar3146;
        genVar3146='#';
        int hash;
        hash=line.indexOf(genVar3146);
        int genVar3147;
        genVar3147=0;
        boolean genVar3148;
        genVar3148=hash >= genVar3147;
        if (genVar3148) {
          int genVar3149;
          genVar3149=0;
          line=line.substring(genVar3149,hash);
        }
 else {
          ;
        }
        line=line.trim();
        int genVar3150;
        genVar3150=line.length();
        int genVar3151;
        genVar3151=0;
        boolean genVar3152;
        genVar3152=genVar3150 == genVar3151;
        if (genVar3152) {
          continue;
        }
 else {
          ;
        }
        java.lang.String genVar3153;
        genVar3153="\\s+";
        String[] split;
        split=line.split(genVar3153);
        int genVar3154;
        genVar3154=0;
        String word;
        word=split[genVar3154];
        int genVar3155;
        genVar3155=1;
        boolean genVar3156;
        genVar3156=split.length > genVar3155;
        int genVar3157;
        genVar3157=1;
        String value;
        value=genVar3156 ? split[genVar3157] : null;
        java.lang.String genVar3159;
        genVar3159="scout-timing";
        boolean genVar3160;
        genVar3160=word.equals(genVar3159);
        if (genVar3160) {
          InputFile genVar3161;
          genVar3161=this;
          scoutTiming=genVar3161.parseTime(value);
        }
 else {
          java.lang.String genVar3162;
          genVar3162="settings";
          boolean genVar3163;
          genVar3163=word.equals(genVar3162);
          if (genVar3163) {
            boolean genVar3164;
            genVar3164=settings == null;
            if (genVar3164) {
              settings=new EcSettings();
              settings.avoidMiningGas=false;
              settings.pullThreeWorkersOnly=false;
              settings.pullWorkersFromGas=false;
              settings.useExtractorTrick=false;
              settings.overDrone=false;
              settings.workerParity=false;
            }
 else {
              ;
            }
            inSettingsBlock=true;
          }
 else {
            java.lang.String genVar3165;
            genVar3165="waypoint";
            boolean genVar3166;
            genVar3166=word.equals(genVar3165);
            if (genVar3166) {
              inSettingsBlock=false;
              boolean genVar3167;
              genVar3167=curWaypoint != null;
              if (genVar3167) {
                waypoints.add(curWaypoint);
              }
 else {
                ;
              }
              curWaypoint=EcState.defaultDestination();
              InputFile genVar3168;
              genVar3168=this;
              curWaypoint.targetSeconds=genVar3168.parseTime(value);
            }
 else {
              boolean genVar3169;
              genVar3169=!inSettingsBlock && buildables.containsKey(word);
              if (genVar3169) {
                com.fray.evo.util.Buildable[] genVar3170;
                genVar3170=buildables.get(word);
                int genVar3171;
                genVar3171=0;
                Buildable b;
                b=genVar3170[genVar3171];
                boolean genVar3172;
                genVar3172=b instanceof Unit;
                if (genVar3172) {
                  boolean genVar3173;
                  genVar3173=value == null;
                  int genVar3174;
                  genVar3174=1;
                  int num;
                  num=genVar3173 ? genVar3174 : Integer.parseInt(value);
                  com.fray.evo.util.Unit genVar3176;
                  genVar3176=(Unit)b;
                  curWaypoint.setUnits(genVar3176,num);
                }
 else {
                  boolean genVar3177;
                  genVar3177=b instanceof Building;
                  if (genVar3177) {
                    boolean genVar3178;
                    genVar3178=value == null;
                    int genVar3179;
                    genVar3179=1;
                    int num;
                    num=genVar3178 ? genVar3179 : Integer.parseInt(value);
                    com.fray.evo.util.Building genVar3181;
                    genVar3181=(Building)b;
                    curWaypoint.setBuilding(genVar3181,num);
                  }
 else {
                    boolean genVar3182;
                    genVar3182=b instanceof Upgrade;
                    if (genVar3182) {
                      boolean genVar3183;
                      genVar3183=value == null;
                      int genVar3184;
                      genVar3184=0;
                      int genVar3186;
                      genVar3186=1;
                      int num;
                      num=genVar3183 ? genVar3184 : Integer.parseInt(value) - genVar3186;
                      com.fray.evo.util.Buildable[] genVar3188;
                      genVar3188=buildables.get(word);
                      com.fray.evo.util.Buildable genVar3189;
                      genVar3189=genVar3188[num];
                      com.fray.evo.util.Upgrade genVar3190;
                      genVar3190=(Upgrade)genVar3189;
                      curWaypoint.addUpgrade(genVar3190);
                    }
 else {
                      ;
                    }
                  }
                }
              }
 else {
                if (inSettingsBlock) {
                  InputFile genVar3191;
                  genVar3191=this;
                  boolean validKeyword;
                  validKeyword=genVar3191.processSetting(settings,word,value);
                  boolean genVar3192;
                  genVar3192=!validKeyword;
                  if (genVar3192) {
                    unknownKeywords.add(word);
                  }
 else {
                    ;
                  }
                }
 else {
                  unknownKeywords.add(word);
                }
              }
            }
          }
        }
      }
      boolean genVar3193;
      genVar3193=curWaypoint != null;
      if (genVar3193) {
        waypoints.add(curWaypoint);
      }
 else {
        ;
      }
      boolean genVar3194;
      genVar3194=unknownKeywords.isEmpty();
      boolean genVar3195;
      genVar3195=!genVar3194;
      if (genVar3195) {
        com.fray.evo.ui.cli.UnknownKeywordException genVar3196;
        genVar3196=new UnknownKeywordException(unknownKeywords);
        throw genVar3196;
      }
 else {
        ;
      }
    }
  finally {
      boolean genVar3197;
      genVar3197=in != null;
      if (genVar3197) {
        in.close();
      }
 else {
        ;
      }
    }
  }
  /** 
 * Process an item in the "settings" block.
 * @param settings
 * @param word
 * @param value
 * @return true if the setting keyword exists, false if not
 */
  private boolean processSetting(  EcSettings settings,  String word,  String value){
    java.lang.String genVar3198;
    genVar3198="enforce-worker-parity";
    boolean genVar3199;
    genVar3199=genVar3198.equals(word);
    if (genVar3199) {
      java.lang.String genVar3200;
      genVar3200="until-saturation";
      boolean genVar3201;
      genVar3201=genVar3200.equals(value);
      if (genVar3201) {
        settings.workerParity=true;
        settings.overDrone=false;
      }
 else {
        java.lang.String genVar3202;
        genVar3202="allow-overdroning";
        boolean genVar3203;
        genVar3203=genVar3202.equals(value);
        if (genVar3203) {
          settings.workerParity=false;
          settings.overDrone=true;
        }
 else {
          settings.workerParity=false;
          settings.overDrone=false;
        }
      }
    }
 else {
      java.lang.String genVar3204;
      genVar3204="use-extractor-trick";
      boolean genVar3205;
      genVar3205=genVar3204.equals(word);
      if (genVar3205) {
        settings.useExtractorTrick=true;
      }
 else {
        java.lang.String genVar3206;
        genVar3206="push-pull-workers-gas";
        boolean genVar3207;
        genVar3207=genVar3206.equals(word);
        if (genVar3207) {
          settings.pullWorkersFromGas=true;
        }
 else {
          java.lang.String genVar3208;
          genVar3208="push-pull-in-threes";
          boolean genVar3209;
          genVar3209=genVar3208.equals(word);
          if (genVar3209) {
            settings.pullThreeWorkersOnly=true;
          }
 else {
            java.lang.String genVar3210;
            genVar3210="avoid-unnecessary-extractor";
            boolean genVar3211;
            genVar3211=genVar3210.equals(word);
            if (genVar3211) {
              settings.avoidMiningGas=true;
            }
 else {
              java.lang.String genVar3212;
              genVar3212="max-extractor-trick-supply";
              boolean genVar3213;
              genVar3213=genVar3212.equals(word);
              if (genVar3213) {
                settings.maximumExtractorTrickSupply=Integer.parseInt(value);
              }
 else {
                java.lang.String genVar3214;
                genVar3214="min-pool-supply";
                boolean genVar3215;
                genVar3215=genVar3214.equals(word);
                if (genVar3215) {
                  settings.minimumPoolSupply=Integer.parseInt(value);
                }
 else {
                  java.lang.String genVar3216;
                  genVar3216="min-extractor-supply";
                  boolean genVar3217;
                  genVar3217=genVar3216.equals(word);
                  if (genVar3217) {
                    settings.minimumExtractorSupply=Integer.parseInt(value);
                  }
 else {
                    java.lang.String genVar3218;
                    genVar3218="min-hatchery-supply";
                    boolean genVar3219;
                    genVar3219=genVar3218.equals(word);
                    if (genVar3219) {
                      settings.minimumHatcherySupply=Integer.parseInt(value);
                    }
 else {
                      boolean genVar3220;
                      genVar3220=false;
                      return genVar3220;
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    boolean genVar3221;
    genVar3221=true;
    return genVar3221;
  }
  public EcState getDestination(){
    Comparator<EcState> genVar3222;
    genVar3222=new Comparator<EcState>(){
      @Override public int compare(      EcState arg0,      EcState arg1){
        return arg0.targetSeconds - arg1.targetSeconds;
      }
    }
;
    Collections.sort(waypoints,genVar3222);
    EcState destination;
    int genVar3223;
    genVar3223=waypoints.size();
    int genVar3224;
    genVar3224=0;
    boolean genVar3225;
    genVar3225=genVar3223 > genVar3224;
    if (genVar3225) {
      int genVar3226;
      genVar3226=waypoints.size();
      int genVar3227;
      genVar3227=1;
      int genVar3228;
      genVar3228=genVar3226 - genVar3227;
      destination=waypoints.get(genVar3228);
      int genVar3229;
      genVar3229=0;
      int genVar3230;
      genVar3230=waypoints.size();
      int genVar3231;
      genVar3231=1;
      int genVar3232;
      genVar3232=genVar3230 - genVar3231;
      java.util.List<com.fray.evo.EcState> genVar3233;
      genVar3233=waypoints.subList(genVar3229,genVar3232);
      destination.waypoints=new ArrayList<EcState>(genVar3233);
    }
 else {
      destination=EcState.defaultDestination();
    }
    destination.scoutDrone=scoutTiming;
    boolean genVar3234;
    genVar3234=settings != null;
    if (genVar3234) {
      destination.settings=settings;
    }
 else {
      ;
    }
    return destination;
  }
  /** 
 * Parses time text into number of seconds.
 * @param time the time text (example: "3:02:54")
 * @return the number of seconds
 */
  private static int parseTime(  String time){
    java.lang.String genVar3235;
    genVar3235=":+";
    String[] split;
    split=time.split(genVar3235);
    int hours;
    hours=0;
    int minutes;
    minutes=0;
    int seconds;
    seconds=0;
    int genVar3236;
    genVar3236=3;
    boolean genVar3237;
    genVar3237=split.length >= genVar3236;
    if (genVar3237) {
      int genVar3238;
      genVar3238=0;
      java.lang.String genVar3239;
      genVar3239=split[genVar3238];
      hours=Integer.parseInt(genVar3239);
      int genVar3240;
      genVar3240=1;
      java.lang.String genVar3241;
      genVar3241=split[genVar3240];
      minutes=Integer.parseInt(genVar3241);
      int genVar3242;
      genVar3242=2;
      java.lang.String genVar3243;
      genVar3243=split[genVar3242];
      seconds=Integer.parseInt(genVar3243);
    }
 else {
      int genVar3244;
      genVar3244=2;
      boolean genVar3245;
      genVar3245=split.length == genVar3244;
      if (genVar3245) {
        int genVar3246;
        genVar3246=0;
        java.lang.String genVar3247;
        genVar3247=split[genVar3246];
        minutes=Integer.parseInt(genVar3247);
        int genVar3248;
        genVar3248=1;
        java.lang.String genVar3249;
        genVar3249=split[genVar3248];
        seconds=Integer.parseInt(genVar3249);
      }
 else {
        int genVar3250;
        genVar3250=0;
        java.lang.String genVar3251;
        genVar3251=split[genVar3250];
        seconds=Integer.parseInt(genVar3251);
      }
    }
    int genVar3252;
    genVar3252=3600;
    int genVar3253;
    genVar3253=hours * genVar3252;
    int genVar3254;
    genVar3254=60;
    int genVar3255;
    genVar3255=minutes * genVar3254;
    int genVar3256;
    genVar3256=genVar3253 + genVar3255;
    int genVar3257;
    genVar3257=genVar3256 + seconds;
    return genVar3257;
  }
}
