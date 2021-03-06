package patl4j.common.java.analyzer;

import soot.*;
import soot.baf.SpecialInvokeInst;
import soot.jimple.SpecialInvokeExpr;
import soot.options.Options;
import soot.tagkit.LineNumberTag;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.UnitGraph;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class DataDependency {
    Map analysis;
    BriefUnitGraph graph;

    Set[] lines;
    
    public DataDependency(SootClass s) {
        analysis = new HashMap<String, ParaDependence>();
        lines = new Set[3000 + 1];

        try {
            for (Iterator it = s.getMethods().iterator(); it.hasNext(); ) {
                SootMethod fun = (SootMethod) it.next();
                System.out.println(fun.getDeclaration());

                if (fun.getSource() == null) continue;
                System.out.println("[Fun Success] " + fun.getSignature());
                Body body = fun.retrieveActiveBody();
                
                // print the Jimple units
                PatchingChain<Unit> u = body.getUnits();
                for (Iterator uIt = u.iterator(); uIt.hasNext(); ) {
                    Unit unit = (Unit) uIt.next();
                    int lineNum = unit.getJavaSourceStartLineNumber();
                    // System.out.println("@" + lineNum + "\t" + unit);
                    if (lineNum != -1) {
                    	if (lines[lineNum] == null) lines[lineNum] = new HashSet<Unit>();
                    	lines[lineNum].add(unit);
                    }
                }

                VariableCluster cluster = new VariableCluster(body);
                graph = new BriefUnitGraph(body);
                ParaDependence curAnalysis = new ParaDependence(graph, cluster);

                analysis.put(fun.getName(), curAnalysis);
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
                if (curAnalysis.isDependent((Unit) u1, (Unit) u2)) return true;
            }
        }
        return false;
        
        
    }
}

