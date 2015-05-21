package patl4j.java.analyzer;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
import soot.grimp.internal.ExprBox;
import soot.jimple.internal.ImmediateBox;
import soot.jimple.internal.VariableBox;
import soot.toolkits.graph.UnitGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;

import java.util.*;

/**
 * Created by Vani on 4/16/15.
 */

public class ParaDependence {
    ParaDependenceAnalysis analysis;
    public ParaDependence(UnitGraph g) {
        analysis = new ParaDependenceAnalysis<Unit, VariableRecord>(g);
    }

    public boolean isDependence(Unit u1, Unit u2) {
        VariableRecord recordToU1 = (VariableRecord) analysis.getFlowBefore(u1);
        VariableRecord recordToU2 = (VariableRecord) analysis.getFlowBefore(u2);
        return isRW(u1, u2, recordToU1, recordToU2) || isWR(u1, u2, recordToU1, recordToU2) || isWW(u1, u2, recordToU1, recordToU2);
    }

    boolean isRW(Unit unit1, Unit unit2, VariableRecord U1, VariableRecord U2) {
        for (ValueBox defVar : unit1.getDefBoxes()) {
            if (U1.contains(defVar, unit2, 0)) return true;
        }
        for (ValueBox defVar : unit2.getDefBoxes()) {
            if (U2.contains(defVar, unit1, 0)) return true;
        }
        return false;
    }

    boolean isWR(Unit unit1, Unit unit2, VariableRecord U1, VariableRecord U2) {
        for (ValueBox useVar : unit1.getUseBoxes()) {
            if (U1.contains(useVar, unit2, 1)) return true;
        }
        for (ValueBox useVar : unit2.getUseBoxes()) {
            if (U2.contains(useVar, unit1, 1)) return true;
        }
        return false;
    }

    boolean isWW(Unit unit1, Unit unit2, VariableRecord U1, VariableRecord U2) {
        for (ValueBox defVar : unit1.getDefBoxes()) {
            if (U1.contains(defVar, unit2, 1)) return true;
        }
        for (ValueBox defVar : unit2.getDefBoxes()) {
            if (U2.contains(defVar, unit1, 1)) return true;
        }
        return false;
    }
}

class VariableRecord {
    // 0 for use & read
    // 1 for def & write
    Map<Object, Set> useRecord;
    Map<Object, Set> defRecord;

    public VariableRecord() {
        useRecord = new HashMap<Object, Set>();
        defRecord = new HashMap<Object, Set>();
    }

    public boolean contains(ValueBox var, Object u, int type) {
        Map<Object, Set> activeRecord;
        if (type == 0) activeRecord = useRecord; else activeRecord = defRecord;
        Set entry = (Set) activeRecord.get(var.getValue());
        if (entry != null) {
            return entry.contains(u);
        }
        return false;
    }

    public void update(Object var, Object u, int type) {
        Map activeRecord;
        if (type == 0) activeRecord = useRecord; else activeRecord = defRecord;
        Set entry = (Set) activeRecord.get(var);
        if (entry == null) {
            entry = new HashSet<Object>();
            entry.add(u);
            activeRecord.put(var, entry);
        } else {
            entry.add(u);
        }
    }

    public Map<Object, Set> getUseRecord() {return useRecord;}
    public Map<Object, Set> getDefRecord() {return defRecord;}

    public void merge(VariableRecord oth) {
        for (Map.Entry<Object, Set> e : oth.getUseRecord().entrySet()) {
            Object var = e.getKey();
            for (Object unit : e.getValue()) {
                update(var, unit, 0);
            }
        }
        for (Map.Entry<Object, Set> e : oth.getDefRecord().entrySet()) {
            Object var = e.getKey();
            for (Object unit : e.getValue()) {
                update(var, unit, 1);
            }
        }
    }

    public void clear() {
        defRecord.clear();
        useRecord.clear();
    }

    public VariableRecord clone(VariableRecord ret) {
        ret.clear();
        ret.merge(this);
        return ret;
    }

    public void print() {
        System.out.println("==============");
        System.out.println("-> def");
        for (Map.Entry entry : defRecord.entrySet()) {
            Value var = (Value)entry.getKey();
            System.out.println(var);
            for (Object u: (Set) entry.getValue()) {
                Unit unit = (Unit) u;
                System.out.println("    " + unit);
            }
        }
        System.out.println("-> use");
        for (Map.Entry entry : useRecord.entrySet()) {
            Value var = (Value)entry.getKey();
            System.out.println(var);
            for (Object u: (Set) entry.getValue()) {
                Unit unit = (Unit) u;
                System.out.println("    " + unit);
            }
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof VariableRecord)) {
            return false;
        }
        VariableRecord oth = (VariableRecord) o;
        return equalsMap(defRecord, oth.getDefRecord()) && equalsMap(useRecord, oth.getUseRecord());
    }

    boolean equalsMap(Map<Object, Set> a, Map<Object, Set> b) {
        if (a.size() != b.size()) return false;
        for (Map.Entry entry : a.entrySet()) {
            Object var = entry.getKey();
            Set othSet = b.get(var);
            if (!equalsSet((Set)entry.getValue(), othSet)) return false;
        }
        return true;
    }

    boolean equalsSet(Set a, Set b) {
        if (a == null) return b == null;
        if (b == null) return a == null;
        return a.equals(b);
    }
}

class ParaDependenceAnalysis<N, A> extends ForwardFlowAnalysis<N, A> {

    VariableRecord varRecord;

    ParaDependenceAnalysis(UnitGraph g) {
        super((soot.toolkits.graph.DirectedGraph<N>) g);
        varRecord = new VariableRecord();
        doAnalysis();
    }

    @Override
    protected void flowThrough(Object in, Object d, Object out) {
        Unit unit = (Unit) d;
        VariableRecord inRecord = (VariableRecord) in, outRecord = (VariableRecord) out;

        inRecord.clone(outRecord);

        List useBoxes = unit.getUseBoxes();
        for (Object v : useBoxes) {
            ValueBox v_ = (ValueBox) v;
            if (v_.getValue() instanceof Local)
                outRecord.update(v_.getValue(), unit, 0);
        }

        List defBoxes = unit.getDefBoxes();
        for (Object v : defBoxes) {
            ValueBox v_ = (ValueBox) v;
            if (v_.getValue() instanceof Local)
                outRecord.update(v_.getValue(), unit, 1);
        }
/*
        System.out.println("@" + unit);
        System.out.println("in");
        inRecord.print();
        System.out.println("out");
        outRecord.print();
        */
    }

    @Override
    protected A newInitialFlow() {
        return (A)new VariableRecord();
    }

    @Override
    protected A entryInitialFlow() {
        return (A)new VariableRecord();
    }

    @Override
    protected void merge(Object in1, Object in2, Object out) {
        VariableRecord inRecord1 = (VariableRecord) in1;
        VariableRecord inRecord2 = (VariableRecord) in2;
        VariableRecord outRecord = (VariableRecord) out;

        inRecord1.clone(outRecord);
        outRecord.merge(inRecord2);
    }

    @Override
    protected void copy(Object source, Object dest) {
        VariableRecord inRecord = (VariableRecord) source;
        VariableRecord outRecord = (VariableRecord) dest;
        inRecord.clone(outRecord);
    }
}
