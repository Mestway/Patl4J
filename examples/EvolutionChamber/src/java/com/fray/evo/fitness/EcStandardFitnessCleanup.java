package com.fray.evo.fitness;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fray.evo.EcState;
import com.fray.evo.util.RaceLibraries;
import com.fray.evo.util.Unit;
import com.fray.evo.util.ZergUnitLibrary;
import com.fray.evo.util.Upgrade;
public final class EcStandardFitnessCleanup implements EcFitness {
  public double augmentScore(  EcState current,  EcState destination,  double score,  boolean waypoint){
    int overlordScore;
    int genVar2025;
    genVar2025=current.getOverlords();
    int genVar2026;
    genVar2026=destination.getOverlords();
    boolean genVar2027;
    genVar2027=genVar2025 > genVar2026;
    if (genVar2027) {
      int genVar2028;
      genVar2028=100;
      int genVar2029;
      genVar2029=destination.getOverlords();
      int genVar2030;
      genVar2030=1;
      int genVar2031;
      genVar2031=1;
      int genVar2032;
      genVar2032=destination.supply();
      double genVar2033;
      genVar2033=genVar2032 - destination.supplyUsed;
      double genVar2034;
      genVar2034=Math.max(genVar2031,genVar2033);
      double genVar2035;
      genVar2035=genVar2030 / genVar2034;
      double genVar2036;
      genVar2036=(genVar2035);
      double genVar2037;
      genVar2037=genVar2029 * genVar2036;
      double genVar2038;
      genVar2038=(genVar2037);
      int genVar2039;
      genVar2039=10;
      double genVar2040;
      genVar2040=genVar2038 * genVar2039;
      double genVar2041;
      genVar2041=Math.min(genVar2028,genVar2040);
      overlordScore=(int)genVar2041;
    }
 else {
      int genVar2042;
      genVar2042=100;
      int genVar2043;
      genVar2043=current.getOverlords();
      int genVar2044;
      genVar2044=1;
      int genVar2045;
      genVar2045=1;
      int genVar2046;
      genVar2046=current.supply();
      double genVar2047;
      genVar2047=genVar2046 - current.supplyUsed;
      double genVar2048;
      genVar2048=Math.max(genVar2045,genVar2047);
      double genVar2049;
      genVar2049=genVar2044 / genVar2048;
      double genVar2050;
      genVar2050=(genVar2049);
      double genVar2051;
      genVar2051=genVar2043 * genVar2050;
      double genVar2052;
      genVar2052=(genVar2051);
      int genVar2053;
      genVar2053=10;
      double genVar2054;
      genVar2054=genVar2052 * genVar2053;
      double genVar2055;
      genVar2055=Math.min(genVar2042,genVar2054);
      overlordScore=(int)genVar2055;
    }
    com.fray.evo.util.Library<com.fray.evo.util.Unit> genVar2056;
    genVar2056=RaceLibraries.getUnitLibrary(current.settings.race);
    List<Unit> allUnitsList;
    allUnitsList=genVar2056.getList();
    for (    Unit unit : allUnitsList) {
      boolean genVar2057;
      genVar2057=unit == ZergUnitLibrary.Overlord;
      if (genVar2057) {
        continue;
      }
 else {
        ;
      }
      boolean genVar2058;
      genVar2058=unit == ZergUnitLibrary.Larva;
      if (genVar2058) {
        continue;
      }
 else {
        ;
      }
      EcStandardFitnessCleanup genVar2059;
      genVar2059=this;
      java.lang.String genVar2060;
      genVar2060=unit.getName();
      int currentlyBuiltUnits;
      currentlyBuiltUnits=genVar2059.getUnitByString(genVar2060,current);
      EcStandardFitnessCleanup genVar2061;
      genVar2061=this;
      java.lang.String genVar2062;
      genVar2062=unit.getName();
      int neededUnits;
      neededUnits=genVar2061.getUnitByString(genVar2062,destination);
      EcStandardFitnessCleanup genVar2063;
      genVar2063=this;
      int genVar2064;
      genVar2064=unit.getMinerals();
      int genVar2065;
      genVar2065=unit.getGas();
      int genVar2066;
      genVar2066=genVar2064 + genVar2065;
      score=genVar2063.augmentScore(score,currentlyBuiltUnits,neededUnits,genVar2066);
    }
    EcStandardFitnessCleanup genVar2067;
    genVar2067=this;
    int genVar2068;
    genVar2068=current.getOverlords();
    int genVar2069;
    genVar2069=destination.getOverlords();
    score=genVar2067.augmentScore(score,genVar2068,genVar2069,overlordScore);
    EcStandardFitnessCleanup genVar2070;
    genVar2070=this;
    int genVar2071;
    genVar2071=current.getGasExtractors();
    int genVar2072;
    genVar2072=destination.getGasExtractors();
    int genVar2073;
    genVar2073=25;
    score=genVar2070.augmentScore(score,genVar2071,genVar2072,genVar2073);
    EcStandardFitnessCleanup genVar2074;
    genVar2074=this;
    int genVar2075;
    genVar2075=current.getHatcheries();
    int genVar2076;
    genVar2076=destination.getHatcheries();
    int genVar2077;
    genVar2077=300;
    score=genVar2074.augmentScore(score,genVar2075,genVar2076,genVar2077);
    EcStandardFitnessCleanup genVar2078;
    genVar2078=this;
    int genVar2079;
    genVar2079=current.getLairs();
    int genVar2080;
    genVar2080=destination.getLairs();
    int genVar2081;
    genVar2081=550;
    double genVar2082;
    genVar2082=5.5;
    score=genVar2078.augmentDropoffScore(score,genVar2079,genVar2080,genVar2081,genVar2082,waypoint);
    EcStandardFitnessCleanup genVar2083;
    genVar2083=this;
    int genVar2084;
    genVar2084=current.getHives();
    int genVar2085;
    genVar2085=destination.getHives();
    int genVar2086;
    genVar2086=900;
    int genVar2087;
    genVar2087=9;
    score=genVar2083.augmentDropoffScore(score,genVar2084,genVar2085,genVar2086,genVar2087,waypoint);
    EcStandardFitnessCleanup genVar2088;
    genVar2088=this;
    int genVar2089;
    genVar2089=current.getSpawningPools();
    int genVar2090;
    genVar2090=destination.getSpawningPools();
    int genVar2091;
    genVar2091=200;
    int genVar2092;
    genVar2092=2;
    score=genVar2088.augmentDropoffScore(score,genVar2089,genVar2090,genVar2091,genVar2092,waypoint);
    EcStandardFitnessCleanup genVar2093;
    genVar2093=this;
    int genVar2094;
    genVar2094=current.getRoachWarrens();
    int genVar2095;
    genVar2095=destination.getRoachWarrens();
    int genVar2096;
    genVar2096=150;
    double genVar2097;
    genVar2097=1.5;
    score=genVar2093.augmentDropoffScore(score,genVar2094,genVar2095,genVar2096,genVar2097,waypoint);
    EcStandardFitnessCleanup genVar2098;
    genVar2098=this;
    int genVar2099;
    genVar2099=current.getHydraliskDen();
    int genVar2100;
    genVar2100=destination.getHydraliskDen();
    int genVar2101;
    genVar2101=200;
    int genVar2102;
    genVar2102=2;
    score=genVar2098.augmentDropoffScore(score,genVar2099,genVar2100,genVar2101,genVar2102,waypoint);
    EcStandardFitnessCleanup genVar2103;
    genVar2103=this;
    int genVar2104;
    genVar2104=current.getBanelingNest();
    int genVar2105;
    genVar2105=destination.getBanelingNest();
    int genVar2106;
    genVar2106=150;
    double genVar2107;
    genVar2107=1.5;
    score=genVar2103.augmentDropoffScore(score,genVar2104,genVar2105,genVar2106,genVar2107,waypoint);
    EcStandardFitnessCleanup genVar2108;
    genVar2108=this;
    int genVar2109;
    genVar2109=current.getGreaterSpire();
    int genVar2110;
    genVar2110=destination.getGreaterSpire();
    int genVar2111;
    genVar2111=650;
    double genVar2112;
    genVar2112=6.5;
    score=genVar2108.augmentDropoffScore(score,genVar2109,genVar2110,genVar2111,genVar2112,waypoint);
    EcStandardFitnessCleanup genVar2113;
    genVar2113=this;
    int genVar2114;
    genVar2114=current.getUltraliskCavern();
    int genVar2115;
    genVar2115=destination.getUltraliskCavern();
    int genVar2116;
    genVar2116=350;
    double genVar2117;
    genVar2117=3.5;
    score=genVar2113.augmentDropoffScore(score,genVar2114,genVar2115,genVar2116,genVar2117,waypoint);
    EcStandardFitnessCleanup genVar2118;
    genVar2118=this;
    int genVar2119;
    genVar2119=current.getSpire();
    int genVar2120;
    genVar2120=destination.getSpire();
    int genVar2121;
    genVar2121=400;
    int genVar2122;
    genVar2122=4;
    score=genVar2118.augmentDropoffScore(score,genVar2119,genVar2120,genVar2121,genVar2122,waypoint);
    EcStandardFitnessCleanup genVar2123;
    genVar2123=this;
    int genVar2124;
    genVar2124=current.getInfestationPit();
    int genVar2125;
    genVar2125=destination.getInfestationPit();
    int genVar2126;
    genVar2126=200;
    double genVar2127;
    genVar2127=2.0;
    score=genVar2123.augmentDropoffScore(score,genVar2124,genVar2125,genVar2126,genVar2127,waypoint);
    EcStandardFitnessCleanup genVar2128;
    genVar2128=this;
    int genVar2129;
    genVar2129=current.getEvolutionChambers();
    int genVar2130;
    genVar2130=destination.getEvolutionChambers();
    int genVar2131;
    genVar2131=75;
    double genVar2132;
    genVar2132=0.75;
    score=genVar2128.augmentDropoffScore(score,genVar2129,genVar2130,genVar2131,genVar2132,waypoint);
    EcStandardFitnessCleanup genVar2133;
    genVar2133=this;
    int genVar2134;
    genVar2134=current.getSpineCrawlers();
    int genVar2135;
    genVar2135=destination.getSpineCrawlers();
    int genVar2136;
    genVar2136=100;
    score=genVar2133.augmentScore(score,genVar2134,genVar2135,genVar2136);
    EcStandardFitnessCleanup genVar2137;
    genVar2137=this;
    int genVar2138;
    genVar2138=current.getSporeCrawlers();
    int genVar2139;
    genVar2139=destination.getSporeCrawlers();
    int genVar2140;
    genVar2140=75;
    score=genVar2137.augmentScore(score,genVar2138,genVar2139,genVar2140);
    EcStandardFitnessCleanup genVar2141;
    genVar2141=this;
    int genVar2142;
    genVar2142=current.getNydusNetwork();
    int genVar2143;
    genVar2143=destination.getNydusNetwork();
    int genVar2144;
    genVar2144=350;
    double genVar2145;
    genVar2145=3.00;
    score=genVar2141.augmentDropoffScore(score,genVar2142,genVar2143,genVar2144,genVar2145,waypoint);
    EcStandardFitnessCleanup genVar2146;
    genVar2146=this;
    int genVar2147;
    genVar2147=current.getNydusWorm();
    int genVar2148;
    genVar2148=destination.getNydusWorm();
    int genVar2149;
    genVar2149=200;
    score=genVar2146.augmentScore(score,genVar2147,genVar2148,genVar2149);
    com.fray.evo.util.Library<com.fray.evo.util.Upgrade> genVar2150;
    genVar2150=RaceLibraries.getUpgradeLibrary(current.settings.race);
    List<Upgrade> allUpgradesList;
    allUpgradesList=genVar2150.getList();
    for (    Upgrade upgrade : allUpgradesList) {
      EcStandardFitnessCleanup genVar2151;
      genVar2151=this;
      java.lang.String genVar2152;
      genVar2152=upgrade.getName();
      boolean currentlyBuiltUnits;
      currentlyBuiltUnits=genVar2151.getUpgradeByString(genVar2152,current);
      EcStandardFitnessCleanup genVar2153;
      genVar2153=this;
      java.lang.String genVar2154;
      genVar2154=upgrade.getName();
      boolean neededUnits;
      neededUnits=genVar2153.getUpgradeByString(genVar2154,destination);
      EcStandardFitnessCleanup genVar2155;
      genVar2155=this;
      int genVar2156;
      genVar2156=upgrade.getMinerals();
      int genVar2157;
      genVar2157=upgrade.getGas();
      int genVar2158;
      genVar2158=genVar2156 + genVar2157;
      score=genVar2155.augmentScore(score,currentlyBuiltUnits,neededUnits,genVar2158);
    }
    return score;
  }
  private int getByString(  String name,  EcState state){
    try {
      java.lang.Class genVar2159;
      genVar2159=state.getClass();
      java.lang.reflect.Field genVar2160;
      genVar2160=genVar2159.getField(name);
      int genVar2161;
      genVar2161=genVar2160.getInt(state);
      return genVar2161;
    }
 catch (    IllegalArgumentException ex) {
      java.lang.Class<com.fray.evo.fitness.EcStandardFitnessCleanup> genVar2162;
      genVar2162=EcStandardFitnessCleanup.class;
      java.lang.String genVar2163;
      genVar2163=genVar2162.getName();
      java.util.logging.Logger genVar2164;
      genVar2164=Logger.getLogger(genVar2163);
      genVar2164.log(Level.SEVERE,null,ex);
    }
catch (    IllegalAccessException ex) {
      java.lang.Class<com.fray.evo.fitness.EcStandardFitnessCleanup> genVar2165;
      genVar2165=EcStandardFitnessCleanup.class;
      java.lang.String genVar2166;
      genVar2166=genVar2165.getName();
      java.util.logging.Logger genVar2167;
      genVar2167=Logger.getLogger(genVar2166);
      genVar2167.log(Level.SEVERE,null,ex);
    }
catch (    NoSuchFieldException ex) {
      java.lang.Class<com.fray.evo.fitness.EcStandardFitnessCleanup> genVar2168;
      genVar2168=EcStandardFitnessCleanup.class;
      java.lang.String genVar2169;
      genVar2169=genVar2168.getName();
      java.util.logging.Logger genVar2170;
      genVar2170=Logger.getLogger(genVar2169);
      genVar2170.log(Level.SEVERE,null,ex);
    }
catch (    SecurityException ex) {
      java.lang.Class<com.fray.evo.fitness.EcStandardFitnessCleanup> genVar2171;
      genVar2171=EcStandardFitnessCleanup.class;
      java.lang.String genVar2172;
      genVar2172=genVar2171.getName();
      java.util.logging.Logger genVar2173;
      genVar2173=Logger.getLogger(genVar2172);
      genVar2173.log(Level.SEVERE,null,ex);
    }
    int genVar2174;
    genVar2174=0;
    return genVar2174;
  }
  private boolean getUpgradeByString(  String upgradeName,  EcState state){
    int genVar2175;
    genVar2175=0;
    int genVar2176;
    genVar2176=1;
    java.lang.String genVar2177;
    genVar2177=upgradeName.substring(genVar2175,genVar2176);
    java.lang.String genVar2178;
    genVar2178=genVar2177.toLowerCase();
    int genVar2179;
    genVar2179=1;
    java.lang.String genVar2180;
    genVar2180=upgradeName.substring(genVar2179);
    String fieldName;
    fieldName=genVar2178 + genVar2180;
    java.lang.String genVar2181;
    genVar2181="flyerAttacks1";
    boolean genVar2182;
    genVar2182=fieldName.equals(genVar2181);
    if (genVar2182) {
      fieldName="flyerAttack1";
    }
 else {
      ;
    }
    java.lang.String genVar2183;
    genVar2183="flyerAttacks2";
    boolean genVar2184;
    genVar2184=fieldName.equals(genVar2183);
    if (genVar2184) {
      fieldName="flyerAttack2";
    }
 else {
      ;
    }
    java.lang.String genVar2185;
    genVar2185="flyerAttacks3";
    boolean genVar2186;
    genVar2186=fieldName.equals(genVar2185);
    if (genVar2186) {
      fieldName="flyerAttack3";
    }
 else {
      ;
    }
    try {
      java.lang.Class genVar2187;
      genVar2187=state.getClass();
      java.lang.reflect.Field genVar2188;
      genVar2188=genVar2187.getField(fieldName);
      boolean genVar2189;
      genVar2189=genVar2188.getBoolean(state);
      return genVar2189;
    }
 catch (    IllegalArgumentException ex) {
      java.lang.Class<com.fray.evo.fitness.EcStandardFitnessCleanup> genVar2190;
      genVar2190=EcStandardFitnessCleanup.class;
      java.lang.String genVar2191;
      genVar2191=genVar2190.getName();
      java.util.logging.Logger genVar2192;
      genVar2192=Logger.getLogger(genVar2191);
      genVar2192.log(Level.SEVERE,null,ex);
    }
catch (    IllegalAccessException ex) {
      java.lang.Class<com.fray.evo.fitness.EcStandardFitnessCleanup> genVar2193;
      genVar2193=EcStandardFitnessCleanup.class;
      java.lang.String genVar2194;
      genVar2194=genVar2193.getName();
      java.util.logging.Logger genVar2195;
      genVar2195=Logger.getLogger(genVar2194);
      genVar2195.log(Level.SEVERE,null,ex);
    }
catch (    NoSuchFieldException ex) {
      java.lang.Class<com.fray.evo.fitness.EcStandardFitnessCleanup> genVar2196;
      genVar2196=EcStandardFitnessCleanup.class;
      java.lang.String genVar2197;
      genVar2197=genVar2196.getName();
      java.util.logging.Logger genVar2198;
      genVar2198=Logger.getLogger(genVar2197);
      genVar2198.log(Level.SEVERE,null,ex);
    }
catch (    SecurityException ex) {
      java.lang.Class<com.fray.evo.fitness.EcStandardFitnessCleanup> genVar2199;
      genVar2199=EcStandardFitnessCleanup.class;
      java.lang.String genVar2200;
      genVar2200=genVar2199.getName();
      java.util.logging.Logger genVar2201;
      genVar2201=Logger.getLogger(genVar2200);
      genVar2201.log(Level.SEVERE,null,ex);
    }
    boolean genVar2202;
    genVar2202=false;
    return genVar2202;
  }
  private int getUnitByString(  String unitName,  EcState state){
    java.lang.String genVar2203;
    genVar2203=unitName.toLowerCase();
    java.lang.String genVar2204;
    genVar2204="s";
    String fieldName;
    fieldName=genVar2203 + genVar2204;
    java.lang.String genVar2205;
    genVar2205="roachs";
    boolean genVar2206;
    genVar2206=fieldName.equals(genVar2205);
    if (genVar2206) {
      fieldName="roaches";
    }
 else {
      ;
    }
    EcStandardFitnessCleanup genVar2207;
    genVar2207=this;
    int genVar2208;
    genVar2208=genVar2207.getByString(fieldName,state);
    return genVar2208;
  }
  private double augmentScore(  double score,  boolean candidate,  boolean destination,  int cost){
    EcStandardFitnessCleanup genVar2209;
    genVar2209=this;
    int genVar2210;
    genVar2210=1;
    int genVar2211;
    genVar2211=0;
    int genVar2212;
    genVar2212=candidate ? genVar2210 : genVar2211;
    int genVar2213;
    genVar2213=1;
    int genVar2214;
    genVar2214=0;
    int genVar2215;
    genVar2215=destination ? genVar2213 : genVar2214;
    double genVar2216;
    genVar2216=genVar2209.augmentScore(score,genVar2212,genVar2215,cost);
    return genVar2216;
  }
  private double augmentScore(  double score,  int candidate,  int destination,  double cost){
    EcStandardFitnessCleanup genVar2217;
    genVar2217=this;
    double genVar2218;
    genVar2218=100.0;
    double genVar2219;
    genVar2219=cost / genVar2218;
    boolean genVar2220;
    genVar2220=false;
    double genVar2221;
    genVar2221=genVar2217.augmentScore(score,candidate,destination,cost,genVar2219,genVar2220);
    return genVar2221;
  }
  private double augmentScore(  double score,  int a,  int b,  double mula,  double mulb,  boolean waypoint){
    int genVar2222;
    genVar2222=Math.min(a,b);
    int genVar2223;
    genVar2223=0;
    int genVar2224;
    genVar2224=Math.max(genVar2222,genVar2223);
    score+=genVar2224 * mula;
    boolean genVar2225;
    genVar2225=!waypoint;
    if (genVar2225) {
      int genVar2226;
      genVar2226=a - b;
      int genVar2227;
      genVar2227=0;
      int genVar2228;
      genVar2228=Math.max(genVar2226,genVar2227);
      score+=genVar2228 * mulb;
    }
 else {
      ;
    }
    return score;
  }
  private double augmentDropoffScore(  double score,  int a,  int b,  double mula,  double mulb,  boolean waypoint){
    int genVar2229;
    genVar2229=Math.min(a,b);
    int genVar2230;
    genVar2230=0;
    int genVar2231;
    genVar2231=Math.max(genVar2229,genVar2230);
    score+=genVar2231 * mula;
    boolean genVar2232;
    genVar2232=!waypoint;
    if (genVar2232) {
      int i=0;
      for (; i < Math.max(a - b,0); i++) {
        mulb/=2;
        score+=mulb;
      }
    }
 else {
      ;
    }
    return score;
  }
  private double augmentSlowDropoffScore(  double score,  int a,  int b,  double mula,  double mulb,  boolean waypoint){
    int genVar2233;
    genVar2233=Math.min(a,b);
    int genVar2234;
    genVar2234=0;
    int genVar2235;
    genVar2235=Math.max(genVar2233,genVar2234);
    score+=genVar2235 * mula;
    boolean genVar2236;
    genVar2236=!waypoint;
    if (genVar2236) {
      int i=0;
      for (; i < Math.max(a - b,0); i++) {
        mulb*=.97;
        score+=mulb;
      }
    }
 else {
      ;
    }
    return score;
  }
  @Override public double score(  EcState candidate,  EcState metric){
    double score;
    score=0;
    boolean keepgoing;
    keepgoing=true;
    EcState state;
    state=EcState.defaultDestination();
    for (    EcState s : metric.waypoints) {
      if (keepgoing) {
        state.union(s);
      }
 else {
        ;
      }
      boolean genVar2237;
      genVar2237=s.isSatisfied(candidate);
      boolean genVar2238;
      genVar2238=!genVar2237;
      if (genVar2238) {
        keepgoing=false;
      }
 else {
        ;
      }
    }
    if (keepgoing) {
      state.union(metric);
    }
 else {
      ;
    }
    EcStandardFitnessCleanup genVar2239;
    genVar2239=this;
    boolean genVar2240;
    genVar2240=false;
    score=genVar2239.augmentScore(candidate,state,score,genVar2240);
    boolean genVar2241;
    genVar2241=state.isSatisfied(candidate);
    if (genVar2241) {
      EcStandardFitnessCleanup genVar2242;
      genVar2242=this;
      int genVar2243;
      genVar2243=candidate.getDrones();
      int genVar2244;
      genVar2244=state.getDrones();
      int genVar2245;
      genVar2245=50;
      double genVar2246;
      genVar2246=.58;
      boolean genVar2247;
      genVar2247=false;
      score=genVar2242.augmentScore(score,genVar2243,genVar2244,genVar2245,genVar2246,genVar2247);
      EcStandardFitnessCleanup genVar2248;
      genVar2248=this;
      int genVar2249;
      genVar2249=(int)candidate.minerals;
      int genVar2250;
      genVar2250=(int)state.minerals;
      double genVar2251;
      genVar2251=.011;
      double genVar2252;
      genVar2252=.011;
      boolean genVar2253;
      genVar2253=false;
      score=genVar2248.augmentScore(score,genVar2249,genVar2250,genVar2251,genVar2252,genVar2253);
      EcStandardFitnessCleanup genVar2254;
      genVar2254=this;
      int genVar2255;
      genVar2255=(int)candidate.gas;
      int genVar2256;
      genVar2256=(int)state.gas;
      double genVar2257;
      genVar2257=.015;
      double genVar2258;
      genVar2258=.015;
      boolean genVar2259;
      genVar2259=false;
      score=genVar2254.augmentScore(score,genVar2255,genVar2256,genVar2257,genVar2258,genVar2259);
      int genVar2260;
      genVar2260=0;
      score=Math.max(score,genVar2260);
      candidate.preTimeScore=score;
      double genVar2261;
      genVar2261=(double)candidate.targetSeconds;
      double genVar2262;
      genVar2262=(double)candidate.seconds;
      double genVar2263;
      genVar2263=genVar2261 / genVar2262;
      double genVar2264;
      genVar2264=(genVar2263);
      double genVar2265;
      genVar2265=(double)candidate.targetSeconds;
      double genVar2266;
      genVar2266=(double)candidate.seconds;
      double genVar2267;
      genVar2267=genVar2265 / genVar2266;
      double genVar2268;
      genVar2268=(genVar2267);
      score*=genVar2264 * genVar2268;
      candidate.timeBonus=score - candidate.preTimeScore;
    }
 else {
      double xtraDroneScore;
      xtraDroneScore=.6;
      boolean genVar2269;
      genVar2269=metric.settings.overDrone || metric.settings.workerParity;
      if (genVar2269) {
        xtraDroneScore=2;
      }
 else {
        ;
      }
      EcStandardFitnessCleanup genVar2270;
      genVar2270=this;
      int genVar2271;
      genVar2271=candidate.getDrones();
      int genVar2272;
      genVar2272=state.getDrones();
      int genVar2273;
      genVar2273=50;
      boolean genVar2274;
      genVar2274=false;
      score=genVar2270.augmentScore(score,genVar2271,genVar2272,genVar2273,xtraDroneScore,genVar2274);
      EcStandardFitnessCleanup genVar2275;
      genVar2275=this;
      int genVar2276;
      genVar2276=(int)candidate.minerals;
      int genVar2277;
      genVar2277=(int)state.minerals;
      double genVar2278;
      genVar2278=.001;
      double genVar2279;
      genVar2279=.001;
      boolean genVar2280;
      genVar2280=false;
      score=genVar2275.augmentScore(score,genVar2276,genVar2277,genVar2278,genVar2279,genVar2280);
      EcStandardFitnessCleanup genVar2281;
      genVar2281=this;
      int genVar2282;
      genVar2282=(int)candidate.gas;
      int genVar2283;
      genVar2283=(int)state.gas;
      double genVar2284;
      genVar2284=.0012;
      double genVar2285;
      genVar2285=.0012;
      boolean genVar2286;
      genVar2286=false;
      score=genVar2281.augmentScore(score,genVar2282,genVar2283,genVar2284,genVar2285,genVar2286);
      double genVar2287;
      genVar2287=score - candidate.invalidActions;
      int genVar2288;
      genVar2288=0;
      score=Math.max(genVar2287,genVar2288);
    }
    return score;
  }
}
