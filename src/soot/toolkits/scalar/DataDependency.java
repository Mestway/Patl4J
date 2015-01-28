package soot.toolkits.scalar;

import soot.Local;
import soot.Unit;
import soot.ValueBox;
import soot.toolkits.graph.UnitGraph;

import java.util.*;

/**
 * Created by Vani on 1/28/15.
 */
public class DataDependency {
    Map<Unit, List> hash;

    public DataDependency(UnitGraph g) {
        DataDependencyAnalysis analysis = new DataDependencyAnalysis(g);
        hash = new HashMap<Unit, List>();

        for (Iterator it = g.iterator(); it.hasNext();) {
            Unit u = (Unit) it.next();

            ArraySparseSet list = new ArraySparseSet();
            HashMap<Local, ArraySparseSet> state = (HashMap<Local, ArraySparseSet>) analysis.getFlowBefore(u);

            for (Iterator useIt = u.getUseBoxes().iterator(); useIt.hasNext();) {
                ValueBox box = (ValueBox) useIt.next();
                if (box.getValue() instanceof  Local) {
                    ArraySparseSet units = state.get(box.getValue());
                    if (units != null) {
                        list.union(units, list);
                    }
                }
            }

            hash.put(u, Collections.unmodifiableList(list.toList()));
        }
    }

    public List getDependencyOfUnit(Unit u) {return hash.get(u);}
}

class DataDependencyAnalysis extends ForwardFlowAnalysis {
    Map emptySet;

    DataDependencyAnalysis(UnitGraph g) {
        super(g);
        emptySet = new HashMap<Local, ArraySparseSet>();
        doAnalysis();
    }

    protected Object newInitialFlow()
    {
        return new HashMap<Local, ArraySparseSet>();
    }

    protected Object entryInitialFlow()
    {
        return new HashMap<Local, ArraySparseSet>();
    }

    protected void flowThrough(Object in, Object u, Object out) {
        copy(in, out);

        HashMap<Local, ArraySparseSet> src = (HashMap<Local, ArraySparseSet>) in;
        HashMap<Local, ArraySparseSet> dst = (HashMap<Local, ArraySparseSet>) out;
        Unit unit = (Unit) u;

        ArraySparseSet currentUnit = new ArraySparseSet();
        currentUnit.add(unit);

        for (Iterator defIt = unit.getDefBoxes().iterator(); defIt.hasNext();) {
            ValueBox box = (ValueBox) defIt.next();
            if (box.getValue() instanceof Local) {
                Local tmp = (Local) box.getValue().clone();
                dst.put(tmp, currentUnit.clone());
            }
        }
    }

    protected void copy(Object in, Object out) {
        HashMap<Local, ArraySparseSet> src = (HashMap<Local, ArraySparseSet>) in;
        HashMap<Local, ArraySparseSet> dst = (HashMap<Local, ArraySparseSet>) out;

        for (Iterator it = src.entrySet().iterator();it.hasNext(); ) {
            Map.Entry<Local, ArraySparseSet> tmp = (Map.Entry<Local, ArraySparseSet>) it.next();
            dst.put((Local) tmp.getKey().clone(), tmp.getValue().clone());
        }
    }

    protected void merge(Object in1, Object in2, Object out) {
        HashMap<Local, ArraySparseSet> src1 = (HashMap<Local, ArraySparseSet>) in1;
        HashMap<Local, ArraySparseSet> src2 = (HashMap<Local, ArraySparseSet>) in2;
        HashMap<Local, ArraySparseSet> dst = (HashMap<Local, ArraySparseSet>) out;

        for (Iterator it = src1.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Local, ArraySparseSet> tmp = (Map.Entry<Local, ArraySparseSet>) it.next();
            merge(dst, tmp.getKey(), tmp.getValue());
        }

        for (Iterator it = src2.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Local, ArraySparseSet> tmp = (Map.Entry<Local, ArraySparseSet>) it.next();
            merge(dst, tmp.getKey(), tmp.getValue());
        }
    }

    void merge(HashMap<Local, ArraySparseSet> out, Local key, ArraySparseSet value) {
        ArraySparseSet old = out.get(key);
        if (old == null) {
            out.put((Local) key.clone(), value.clone());
        } else {
            old.union(old, value);
            out.put((Local) key.clone(), value.clone());
        }
    }
}

