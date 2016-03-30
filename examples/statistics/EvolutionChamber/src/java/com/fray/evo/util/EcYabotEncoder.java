package com.fray.evo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates YABOT build order strings.
 * 
 * @author mike.angstadt
 */
public final class EcYabotEncoder {
	/**
	 * Represents a unit, building, or upgrade.
	 * 
	 * @author mike.angstadt
	 */
	public static enum Action {
		//TODO find the correct data for Ventral Sacs
		BanelingNest(0, 33), EvolutionChamber(0, 34), Extractor(0, 35), Hatchery(0, 36), HydraliskDen(0, 37), InfestationPit(0, 38), NydusNetwork(0, 39), RoachWarren(0, 40), SpawningPool(0, 41), SpineCrawler(0, 42), GreaterSpire(2, 5), Spire(0, 43), SporeCrawler(0, 44), UltraliskCavern(0, 45), Corruptor(1, 27), Drone(1, 28), Hydralisk(1, 29), Infestor(1, 38), Mutalisk(1, 30), Overlord(1, 31), Queen(1, 32), Roach(1, 33), Ultralisk(1, 34), Zergling(1, 035), Lair(2, 3), Hive(2, 4), BroodLord(2, 6), Baneling(2, 7), Overseer(2, 8), Carapace(3, 28), Melee(3, 29), FlyerAttack(3, 31), FlyerArmor(3, 30), Missile(3, 32), GroovedSpines(3, 33), PneumatizedCarapace(3, 34), GlialReconstitution(3, 36), TunnelingClaws(3, 38), ChitinousPlating(3, 40), AdrenalGlands(3, 41), MetabolicBoost(3, 42), Burrow(3, 44), CentrifugalHooks(3, 45), NeuralParasite(3, 49), PathogenGlands(3, 50), VentralSacs(3, 0);

		public final int type;
		public final int item;

		private Action(int type, int item) {
			this.type = type;
			this.item = item;
		}
	};

	/**
	 * The name of the build order.
	 */
	private String name;

	/**
	 * The name of the person who generated this build order.
	 */
	private String author;

	/**
	 * A description of the build order.
	 */
	private String description;

	/**
	 * The current YABOT build step.
	 */
	private BuildStep curStep = new BuildStep();

	/**
	 * The list of completed YABOT build steps.
	 */
	private List<BuildStep> steps = new ArrayList<BuildStep>();

	/**
	 * Constructor.
	 * 
	 * @param name the name of the build order
	 * @param author the author of the build order
	 * @param description a description for the build order
	 */
	public EcYabotEncoder(String name, String author, String description) {
		this.name = name;
		this.author = author;
		this.description = description;
	}

	/**
	 * Sets the supply of the current build step.
	 * 
	 * @param supply
	 * @return this
	 */
	public EcYabotEncoder supply(int supply) {
		curStep.supply = supply;
		return this;
	}

	/**
	 * Sets the minerals of the current build step.
	 * 
	 * @param minerals
	 * @return this
	 */
	public EcYabotEncoder minerals(int minerals) {
		curStep.minerals = minerals;
		return this;
	}

	/**
	 * Sets the gas of the current build step.
	 * 
	 * @param gas
	 * @return this
	 */
	public EcYabotEncoder gas(int gas) {
		curStep.gas = gas;
		return this;
	}

	/**
	 * Sets the timestamp of the current build step. Defaults to "0:0".
	 * 
	 * @param timestamp should be in the format "h:m:s". For example, "1:04:32"
	 * for one hour, four minutes, thirty-two seconds.
	 * @return this
	 */
	public EcYabotEncoder timestamp(String timestamp) {
		curStep.timestamp = timestamp;
		return this;
	}

	/**
	 * Specifies the unit, building, or upgrade involved in this build step.
	 * 
	 * @param action the unit, building or upgrade
	 * @return this
	 */
	public EcYabotEncoder action(Action action) {
		curStep.type = action.type;
		curStep.item = action.item;
		return this;
	}

	/**
	 * Sets the type number of the current build step. You should use the
	 * action() method instead, unless you want to manually set this value
	 * yourself.
	 * 
	 * @param type
	 * @return this
	 */
	public EcYabotEncoder type(int type) {
		curStep.type = type;
		return this;
	}

	/**
	 * Sets the item number of the current build step. You should use the
	 * action() method instead, unless you want to manually set this value
	 * yourself.
	 * 
	 * @param item
	 * @return this
	 */
	public EcYabotEncoder item(int item) {
		curStep.item = item;
		return this;
	}

	/**
	 * Sets whether the current build step is a cancel operation or not.
	 * Defaults to false.
	 * 
	 * @param cancel
	 * @return this
	 */
	public EcYabotEncoder cancel(boolean cancel) {
		curStep.cancel = cancel;
		return this;
	}

	/**
	 * Sets the tag of the current build step. Defaults to a single space.
	 * 
	 * @param tag
	 * @return this
	 */
	public EcYabotEncoder tag(String tag) {
		curStep.tag = tag;
		return this;
	}

	/**
	 * Completes the current build step and advances to a new build step.
	 * 
	 * @return this
	 */
	public EcYabotEncoder next() {
		steps.add(curStep);
		curStep = new BuildStep();
		return this;
	}

	/**
	 * Generates the entire YABOT string. Also resets the encoder so that the
	 * same object can be used to generate a new YABOT string.
	 * 
	 * @return the YABOT string
	 */
	public String done() {
		String yabot = toString();

		//reset so a new YABOT string can be created with the same object
		curStep = new BuildStep();
		steps.clear();

		return yabot;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();

		sb.append("1 [i] " + name + " | 11 | " + author + " | " + description + " [/i]");
		if (steps.size() > 0) {
			sb.append(" [s] ");

			sb.append(steps.get(0).toString());
			for (int i = 1; i < steps.size(); i++) {
				BuildStep entry = steps.get(i);
				sb.append(" | " + entry.toString());
			}
			sb.append(" [/s]");
		}
		
		return sb.toString();
	}

	/**
	 * Represents a build order step in a YABOT string.
	 * 
	 * @author mike.angstadt
	 * 
	 */
	private static class BuildStep {
		public int supply = 0;
		public int minerals = 0;
		public int gas = 0;
		public String timestamp;
		public int type = 0;
		public int item = 0;
		public boolean cancel = false;
		public String tag;

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(supply);
			sb.append(" " + minerals);
			sb.append(" " + gas);

			sb.append(" ");
			if (timestamp == null) {
				sb.append("0:0");
			} else {
				sb.append(timestamp);
			}

			sb.append(" 1");
			sb.append(" " + type);
			sb.append(" " + item);
			sb.append(" " + (cancel ? "1" : "0"));

			sb.append(" ");
			if (tag == null) {
				sb.append(" ");
			} else {
				sb.append(tag);
			}
			return sb.toString();
		}
	}
}
