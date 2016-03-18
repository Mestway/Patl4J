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


public class InputFile {

	private static final Map<String, Buildable[]> buildables = new HashMap<String, Buildable[]>();
	static {
		buildables.put("drone", new Buildable[] { ZergLibrary.Drone });
		buildables.put("baneling", new Buildable[] { ZergLibrary.Baneling });
		buildables.put("brood-lord", new Buildable[] { ZergLibrary.Broodlord });
		buildables.put("corruptor", new Buildable[] { ZergLibrary.Corruptor });
		buildables.put("hydralisk", new Buildable[] { ZergLibrary.Hydralisk });
		buildables.put("infestor", new Buildable[] { ZergLibrary.Infestor });
		buildables.put("mutalisk", new Buildable[] { ZergLibrary.Mutalisk });
		buildables.put("overlord", new Buildable[] { ZergLibrary.Overlord });
		buildables.put("overseer", new Buildable[] { ZergLibrary.Overseer });
		buildables.put("queen", new Buildable[] { ZergLibrary.Queen });
		buildables.put("roach", new Buildable[] { ZergLibrary.Roach });
		buildables.put("ultralisk", new Buildable[] { ZergLibrary.Ultralisk });
		buildables.put("zergling", new Buildable[] { ZergLibrary.Zergling });

		buildables.put("baneling-nest", new Buildable[] { ZergLibrary.BanelingNest });
		buildables.put("evolution-chamber", new Buildable[] { ZergLibrary.EvolutionChamber });
		buildables.put("extractor", new Buildable[] { ZergLibrary.Extractor });
		buildables.put("greater-spire", new Buildable[] { ZergLibrary.GreaterSpire });
		buildables.put("hatchery", new Buildable[] { ZergLibrary.Hatchery });
		buildables.put("hive", new Buildable[] { ZergLibrary.Hive });
		buildables.put("hydralisk-den", new Buildable[] { ZergLibrary.HydraliskDen });
		buildables.put("infestation-pit", new Buildable[] { ZergLibrary.InfestationPit });
		buildables.put("lair", new Buildable[] { ZergLibrary.Lair });
		buildables.put("nydus-network", new Buildable[] { ZergLibrary.NydusNetwork });
		buildables.put("nydus-worm", new Buildable[] { ZergLibrary.NydusWorm });
		buildables.put("roach-warren", new Buildable[] { ZergLibrary.RoachWarren });
		buildables.put("spawning-pool", new Buildable[] { ZergLibrary.SpawningPool });
		buildables.put("spine-crawler", new Buildable[] { ZergLibrary.SpineCrawler });
		buildables.put("spire", new Buildable[] { ZergLibrary.Spire });
		buildables.put("spore-crawler", new Buildable[] { ZergLibrary.SporeCrawler });
		buildables.put("ultralisk-cavern", new Buildable[] { ZergLibrary.UltraliskCavern });

		buildables.put("adrenal-glands", new Buildable[] { ZergLibrary.AdrenalGlands });
		buildables.put("burrow", new Buildable[] { ZergLibrary.Burrow });
		buildables.put("carapace", new Buildable[] { ZergLibrary.Armor1, ZergLibrary.Armor2, ZergLibrary.Armor3 });
		buildables.put("centrifugal-hooks", new Buildable[] { ZergLibrary.CentrifugalHooks });
		buildables.put("chitinous-plating", new Buildable[] { ZergLibrary.ChitinousPlating });
		buildables.put("flyer-armor", new Buildable[] { ZergLibrary.FlyerArmor1, ZergLibrary.FlyerArmor2, ZergLibrary.FlyerArmor3 });
		buildables.put("flyer-attacks", new Buildable[] { ZergLibrary.FlyerAttacks1, ZergLibrary.FlyerAttacks2, ZergLibrary.FlyerAttacks3 });
		buildables.put("glial-reconstitution", new Buildable[] { ZergLibrary.GlialReconstitution });
		buildables.put("grooved-spines", new Buildable[] { ZergLibrary.GroovedSpines });
		buildables.put("melee", new Buildable[] { ZergLibrary.Melee1, ZergLibrary.Melee2, ZergLibrary.Melee3 });
		buildables.put("metabolic-boost", new Buildable[] { ZergLibrary.MetabolicBoost });
		buildables.put("missile", new Buildable[] { ZergLibrary.Missile1, ZergLibrary.Missile2, ZergLibrary.Missile3 });
		buildables.put("neural-parasite", new Buildable[] { ZergLibrary.NeuralParasite });
		buildables.put("pathogen-glands", new Buildable[] { ZergLibrary.PathogenGlands });
		buildables.put("pneumatized-carapace", new Buildable[] { ZergLibrary.PneumatizedCarapace });
		buildables.put("tunneling-claws", new Buildable[] { ZergLibrary.TunnelingClaws });
		buildables.put("ventral-sacs", new Buildable[] { ZergLibrary.VentralSacs });
	}

	private int scoutTiming = 0;
	private EcSettings settings;
	private List<EcState> waypoints = new ArrayList<EcState>();

	public InputFile(File file) throws IOException, UnknownKeywordException {
		this(new FileReader(file));
	}

	public InputFile(Reader reader) throws IOException, UnknownKeywordException {
		BufferedReader in = null;
		List<String> unknownKeywords = new ArrayList<String>();
		try {
			in = new BufferedReader(reader);
			String line;
			EcState curWaypoint = null;
			boolean inSettingsBlock = false;
			while ((line = in.readLine()) != null) {
				int hash = line.indexOf('#');
				if (hash >= 0){
					line = line.substring(0, hash);
				}
				
				line = line.trim();
				if (line.length() == 0) {
					continue;
				}

				String split[] = line.split("\\s+");
				String word = split[0];
				String value = split.length > 1 ? split[1] : null;
				if (word.equals("scout-timing")) {
					scoutTiming = parseTime(value);
				} else if (word.equals("settings")) {
					if (settings == null){
						settings = new EcSettings();
						
						settings.avoidMiningGas = false;
						settings.pullThreeWorkersOnly = false;
						settings.pullWorkersFromGas = false;
						settings.useExtractorTrick = false;
						settings.overDrone = false;
						settings.workerParity = false;
					}
					inSettingsBlock = true;
				} else if (word.equals("waypoint")) {
					inSettingsBlock = false;
					if (curWaypoint != null) {
						waypoints.add(curWaypoint);
					}
					curWaypoint = EcState.defaultDestination();
					curWaypoint.targetSeconds = parseTime(value);
				} else if (!inSettingsBlock && buildables.containsKey(word)) {
					Buildable b = buildables.get(word)[0];
					if (b instanceof Unit) {
						int num = value == null ? 1 : Integer.parseInt(value);
						curWaypoint.setUnits((Unit) b, num);
					} else if (b instanceof Building) {
						int num = value == null ? 1 : Integer.parseInt(value);
						curWaypoint.setBuilding((Building) b, num);
					} else if (b instanceof Upgrade) {
						int num = value == null ? 0 : Integer.parseInt(value) - 1;
						curWaypoint.addUpgrade((Upgrade) buildables.get(word)[num]);
					}
				} else if (inSettingsBlock) {
					boolean validKeyword = processSetting(settings, word, value);
					if (!validKeyword){
						unknownKeywords.add(word);
					}
				} else {
					unknownKeywords.add(word);
				}
			}
			if (curWaypoint != null){
				waypoints.add(curWaypoint);
			}
			if (!unknownKeywords.isEmpty()){
				throw new UnknownKeywordException(unknownKeywords);
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}
	
	private boolean processSetting(EcSettings settings, String word, String value){
		if ("enforce-worker-parity".equals(word)){
			if ("until-saturation".equals(value)){
				settings.workerParity = true;
				settings.overDrone = false;
			} else if ("allow-overdroning".equals(value)){
				settings.workerParity = false;
				settings.overDrone = true;
			} else {
				settings.workerParity = false;
				settings.overDrone = false;
			}
		} else if ("use-extractor-trick".equals(word)){
			settings.useExtractorTrick = true;
		} else if ("push-pull-workers-gas".equals(word)){
			settings.pullWorkersFromGas = true;
		} else if ("push-pull-in-threes".equals(word)){
			settings.pullThreeWorkersOnly = true;
		} else if ("avoid-unnecessary-extractor".equals(word)){
			settings.avoidMiningGas = true;
		} else if ("max-extractor-trick-supply".equals(word)){
			settings.maximumExtractorTrickSupply = Integer.parseInt(value);
		} else if ("min-pool-supply".equals(word)){
			settings.minimumPoolSupply = Integer.parseInt(value);
		} else if ("min-extractor-supply".equals(word)){
			settings.minimumExtractorSupply = Integer.parseInt(value);
		} else if ("min-hatchery-supply".equals(word)){
			settings.minimumHatcherySupply = Integer.parseInt(value);
		} else {
			return false;
		}
		return true;
	}
	
	public EcState getDestination(){
		Collections.sort(waypoints, new Comparator<EcState>(){
			@Override
			public int compare(EcState arg0, EcState arg1) {
				return arg0.targetSeconds - arg1.targetSeconds;
			}
		});
		
		EcState destination;
		if (waypoints.size() > 0){
			destination = waypoints.get(waypoints.size()-1);
			destination.waypoints = new ArrayList<EcState>(waypoints.subList(0, waypoints.size()-1));
		} else {
			destination = EcState.defaultDestination();
		}
		destination.scoutDrone = scoutTiming;
		if (settings != null){
			destination.settings = settings;
		}
		
		return destination;
	}

	private static int parseTime(String time) {
		
		System.out.println("time: "+time);
		
		String[] split = time.split(":+");
		int hours = 0, minutes = 0, seconds = 0;
		if (split.length >= 3) {
			hours = Integer.parseInt(split[0]);
			minutes = Integer.parseInt(split[1]);
			seconds = Integer.parseInt(split[2]);
		} else if (split.length == 2) {
			minutes = Integer.parseInt(split[0]);
			seconds = Integer.parseInt(split[1]);
		} else {
			seconds = Integer.parseInt(split[0]);
		}

		return hours * 3600 + minutes * 60 + seconds;
	}
}
