package soot.toolkits.scalar;

import soot.AbstractValueBox;
import soot.Local;
import soot.Unit;
import soot.ValueBox;
import soot.baf.SpecialInvokeInst;
import soot.jimple.SpecialInvokeExpr;
import soot.toolkits.graph.UnitGraph;
import sun.text.resources.CollationData_sk;

import java.util.*;

/**
 * Created by Vani on 1/28/15.
 */

public class DataDependency {
    Map<Unit, List> dependentUnits;
    DirectDataDependency analysis;

    Set<Unit> vis;

    public DataDependency(UnitGraph graph) {
        analysis = new DirectDataDependency(graph);
        dependentUnits = new HashMap<Unit, List>();
        vis = new HashSet<Unit>();
        for (Iterator it = graph.iterator(); it.hasNext();) {
            Unit unit = (Unit) it.next();
            dependentUnits.put(unit, dfs(unit));
        }
    }

    public List getDependentUnits(Unit u) { return dependentUnits.get(u); }

    List dfs(Unit unit) {
        Set result = doDfs(unit);
        vis.clear();
        return Collections.unmodifiableList(new ArrayList(result));
    }

    HashSet<Unit> doDfs(Unit unit) {
        List current = analysis.getDependentUnits(unit);
        HashSet<Unit> others = new HashSet<Unit>();
        for (Iterator it = current.iterator(); it.hasNext();) {
            Unit nextUnit = (Unit) it.next();
            if (!vis.contains(nextUnit)) {
                vis.add(nextUnit);
                others.addAll(doDfs(nextUnit));
            }
        }
        others.addAll(current);
        return others;
    }
}

class DirectDataDependency {
    Map<Unit, List> dependentUnits;

    public DirectDataDependency(UnitGraph graph) {
        DirectDataDependencyAnalysis analysis = new DirectDataDependencyAnalysis(graph);
        dependentUnits = new HashMap<Unit, List>();

        for (Iterator it = graph.iterator(); it.hasNext();) {
            Unit unit = (Unit) it.next();
            List<ValueBox> useBoxes = unit.getUseBoxes();

            FlowSet reachableDef = ((FlowSet) analysis.getFlowBefore(unit)).clone();
            FlowSet kills = new ArraySparseSet();

            for (Iterator dIt = reachableDef.iterator(); dIt.hasNext();) {
                Unit dUnit = (Unit) dIt.next();
                if (useBoxes.isEmpty() || !useBoxes.containsAll(dUnit.getDefBoxes())) {
                    kills.add(dUnit);
                }
            }
            reachableDef.difference(kills, reachableDef);

            dependentUnits.put(unit, Collections.unmodifiableList(reachableDef.toList()));
        }
    }

    public List getDependentUnits(Unit u) {return dependentUnits.get(u);}
}

class DirectDataDependencyAnalysis extends ForwardFlowAnalysis {
    FlowSet emptySet;

    Map<Unit, FlowSet> genSet;

    DirectDataDependencyAnalysis(UnitGraph g) {
        super(g);

        emptySet = new ArraySparseSet();
        genSet = new HashMap<Unit, FlowSet>();

        //generate genSet
        for (Iterator it = g.iterator(); it.hasNext();) {
            Unit u = (Unit) it.next();
            ArraySparseSet tmp = new ArraySparseSet();
            tmp.add(u);
            genSet.put(u, tmp);
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

    protected void flowThrough(Object inObject, Object u, Object outObject) {
        FlowSet in = (FlowSet) inObject, out = (FlowSet) outObject;
        Unit unit = (Unit) u;

        List<ValueBox> defBoxes = unit.getDefBoxes();
        for (Iterator it = in.iterator(); it.hasNext();) {
            Unit lastUnit = (Unit) it.next();
            if (!defBoxes.containsAll(lastUnit.getDefBoxes())) {
                out.add(lastUnit);
            }
        }

        if (!defBoxes.isEmpty()) out.union(genSet.get(unit), out);
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

