package patl4j.common.java.analyzer;

import soot.Local;
import soot.PointsToAnalysis;
import soot.PointsToSet;
import soot.Scene;

public class AliasAnalysis {
	PointsToAnalysis analysis;
	
	public AliasAnalysis() {
		analysis = Scene.v().getPointsToAnalysis();
	}
	
	public boolean mayAlias(Local x, Local y) {
		if (x == null || y == null) return false;
		PointsToSet xset = analysis.reachingObjects(x);
		PointsToSet yset = analysis.reachingObjects(y);
		return xset.hasNonEmptyIntersection(yset);
	}
}
