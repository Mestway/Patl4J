package patl4j.java.analyzer;

import soot.Local;
import soot.PointsToAnalysis;
import soot.PointsToSet;
import soot.Scene;

public class AliasAnalysis {
	PointsToAnalysis analysis;
	
	public AliasAnalysis() {
		analysis = Scene.v().getPointsToAnalysis();
	}
	
	boolean mayAlias(Local x, Local y) {
		PointsToSet xset = analysis.reachingObjects(x);
		PointsToSet yset = analysis.reachingObjects(y);
		return xset.hasNonEmptyIntersection(yset);
	}
}
