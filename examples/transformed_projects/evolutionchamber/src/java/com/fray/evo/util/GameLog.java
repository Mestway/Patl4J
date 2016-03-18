package com.fray.evo.util;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.PrintStream;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public final class GameLog {
	public enum FailReason {
		OutOfActions,
		OverDrone,
		Waypoint
	};

	public enum MessageType {
		Evolved,
		Mining,
		Scout,
		Obtained
	};

	private boolean		on;
	private PrintStream	log;

	public GameLog() {
		on = false;
		log = System.out;
	}

	public void setEnabled (boolean on) {
		this.on = on;
	}

	public void setPrintStream (PrintStream stream) {
		log = stream;
	}

	public boolean isEnabled() {
		return on;
	}

	public void printFailure(FailReason r, EcState des, EcState curr) {
		if (on)
		{
			String goal = messages.getString("Goal");
			
			log.print("-------"+goal+"-------");
			log.println(des.toUnitsOnlyString());
			switch( r ) {
				case OutOfActions:
					log.println(messages.getString("OutOfActions"));
					break;
				case OverDrone:
					log.println(messages.getString("FailedToHaveRequired")
							+ " " + curr.getOverDrones(curr)
							+ " " + messages.getString("waypoint.drones"));
					break;
				case Waypoint:
					log.println(messages.getString("FailedToMeetWaypoint"));
					break;
			}
			if (curr != null)
				log.println(curr.toCompleteString());
		}
	}

	public void printWaypoint(int waypointIndex, EcState waypoint) {
		if (on)
		{
			log.println("---"+messages.getString("Waypoint")+ " " + waypointIndex + "---");
			log.println(waypoint.toCompleteString());
			log.println("----------------");
		}
	}

	public void printSatisfied(int actions, EcState curr, EcState des) {
		if (on)
		{
			log.println(messages.getString("Satisfied"));
			log.println(messages.getString("NumberOfActions") + " " + (actions));
	
			log.print("-------"+messages.getString("Goal")+"-------");
			log.println(des.toUnitsOnlyString());
			log.println("---"+messages.getString("FinalOutput")+"---");
			log.println(curr.toCompleteString());
			log.println("------------------");
		}
	}
	
	public void printAction(EcBuildOrder bo, EcAction a) {
		if (on)
			log.println(bo.toShortString() + "\t" + a);
	}

	public void printMessage(EcBuildOrder s, MessageType message, String string) {
		if (on) {
			String messageString = null;

			switch( message ) {
				case Evolved:
					messageString = messages.getString("Evolved");
					break;
				case Mining:
					messageString = messages.getString("Mining");
					break;
				case Scout:
					messageString = messages.getString("Scout"); 
					break;
				case Obtained:
					messageString = messages.getString("Spawned");
					break;
			}

			log.println("@" + s.timestamp() + "\t"+ messageString +": \t" + string.trim());
		}
	}
}
