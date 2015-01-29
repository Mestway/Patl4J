package soot.toolkits.scalar;

import soot.Unit;
import soot.toolkits.graph.MHGPostDominatorsFinder;
import soot.toolkits.graph.UnitGraph;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vani on 1/29/15.
 */
public class ControlDependency {
    Map<Unit, List> cDependences;

    public ControlDependency(UnitGraph graph) {
        ControlDependencyAnalysis analysis = new ControlDependencyAnalysis(graph);

        cDependences = new HashMap<Unit, List>();
        for (Unit unit: graph) {
            ArraySparseSet tmpSet = (ArraySparseSet) analysis.getFlowAfter(unit);
            tmpSet.remove(unit);
            cDependences.put(unit, Collections.unmodifiableList(tmpSet.toList()));
        }
    }

    public List getDependentUnits(Unit u) { return cDependences.get(u); }
}

class ControlDependencyAnalysis extends ForwardFlowAnalysis {
    Map<Unit, FlowSet> killSet;
    FlowSet emptySet;

    public ControlDependencyAnalysis(UnitGraph graph) {
        super(graph);

        killSet = new HashMap<Unit, FlowSet>();
        emptySet = new ArraySparseSet();

        MHGPostDominatorsFinder postDominatorsFinder = new MHGPostDominatorsFinder(graph);

        for (Unit unit: graph) {
            List<Unit> pDominators = postDominatorsFinder.getDominators(unit);
            for (Unit nextUnit: pDominators) {
                FlowSet nextUnitSet = killSet.get(nextUnit);
                if (killSet.containsKey(nextUnit)) {
                    FlowSet oldSet = killSet.get(nextUnit);
                    oldSet.add(unit);
                } else {
                    FlowSet newSet = new ArraySparseSet();
                    newSet.add(unit);
                    killSet.put(nextUnit, newSet);
                }
            }
        }
        doAnalysis();
    }

    protected Object newInitialFlow()
    {
        return emptySet.clone();
    }

    protected Object entryInitialFlow()
    {
        return emptySet.clone();
    }

    protected void flowThrough(Object inValue, Object unit, Object outValue) {
        FlowSet in = (FlowSet) inValue, out = (FlowSet) outValue;

        // Perform kill
        in.difference(killSet.get(unit), out);
        out.add(unit);
    }

    protected void merge(Object in1, Object in2, Object out) {
        FlowSet inSet1 = (FlowSet) in1,
                inSet2 = (FlowSet) in2;

        FlowSet outSet = (FlowSet) out;

        inSet1.union(inSet2, outSet);
    }

    protected void copy(Object source, Object dest) {
        FlowSet sourceSet = (FlowSet) source,
                destSet = (FlowSet) dest;

        sourceSet.copy(destSet);
    }
}