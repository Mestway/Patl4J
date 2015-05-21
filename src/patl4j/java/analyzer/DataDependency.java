package patl4j.java.analyzer;

import soot.*;
import soot.baf.SpecialInvokeInst;
import soot.jimple.SpecialInvokeExpr;
import soot.options.Options;
import soot.tagkit.LineNumberTag;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.UnitGraph;

import java.util.*;

public class DataDependency {
    Map analysis;
    BriefUnitGraph graph;

    Set[] lines;

    public DataDependency(String classPath, String className, int totalLine) {
        analysis = new HashMap<String, ParaDependence>();
        lines = new Set[totalLine + 1];

        String oldPath = Scene.v().getSootClassPath();

        Scene.v().setSootClassPath(oldPath + ":" + classPath);
        
        Options.v().set_keep_line_number(true);
   
        SootClass s = Scene.v().loadClassAndSupport(className);

        try {
            for (Iterator it = s.getMethods().iterator(); it.hasNext(); ) {
                SootMethod fun = (SootMethod) it.next();
                System.out.println(fun.getDeclaration());

                Body body = fun.retrieveActiveBody();

                // print the Jimple units
                PatchingChain<Unit> u = body.getUnits();
                for (Iterator uIt = u.iterator(); uIt.hasNext(); ) {
                    Unit unit = (Unit) uIt.next();
                    int lineNum = ((LineNumberTag) unit.getTag("LineNumberTag")).getLineNumber();
                    if (lines[lineNum] == null) lines[lineNum] = new HashSet<Unit>();
                    lines[lineNum].add(unit);
                    //System.out.println("    " + "@" + lineNum + "." + unit);
                }

                graph = new BriefUnitGraph(body);
                ParaDependence curAnalysis = new ParaDependence(graph);

                analysis.put(fun.getName(), curAnalysis);
/*
                for (Unit i1 : u) {
                    for (Unit i2 : u)
                        if (!i1.equals(i2)) {
                            System.out.println(i1 + "  |  " + i2 + " => " + curAnalysis.isDependence(i1, i2));
                        }
                }
                */
            }
        } catch (OutOfMemoryError e) {
            G.v().out.println("Soot has run out of the memory allocated to it by the Java VM.");
            G.v().out.println("To allocate more memory to Soot, use the -Xmx switch to Java.");
            G.v().out.println("For example (for 400MB): java -Xmx400m soot.Main ...");
            throw e;
        }
    }

    public boolean isDependent(String methodName, int line1, int line2) {
        if (lines[line1] == null || lines[line2] == null) return false;
        ParaDependence curAnalysis = (ParaDependence) analysis.get(methodName);
        for (Object u1: lines[line1]) {
            for (Object u2: lines[line2]) {
                if (curAnalysis.isDependence((Unit) u1, (Unit) u2)) return true;
            }
        }
        return false;
    }
}

