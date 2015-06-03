package patl4j.java.analyzer;

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

    public DataDependency(String classPath, String className, int totalLine) {
        analysis = new HashMap<String, ParaDependence>();
        lines = new Set[totalLine + 1];

        String oldPath = Scene.v().getSootClassPath();

        System.out.println("Class path: " + oldPath + classPath);
        Scene.v().setSootClassPath(oldPath + classPath);

        Options.v().set_keep_line_number(true);
   
        System.out.println(System.getProperty("user.dir"));
        
        SootClass s = Scene.v().loadClassAndSupport(className);
        Scene.v().loadNecessaryClasses();

        try {
            for (Iterator it = s.getMethods().iterator(); it.hasNext(); ) {
                SootMethod fun = (SootMethod) it.next();
                System.out.println(fun.getDeclaration());

                Body body = fun.retrieveActiveBody();

                // print the Jimple units
                PatchingChain<Unit> u = body.getUnits();
                for (Iterator uIt = u.iterator(); uIt.hasNext(); ) {
                    Unit unit = (Unit) uIt.next();
                    int lineNum = unit.getJavaSourceStartLineNumber();
                    System.out.println("@" + lineNum + "\t" + unit);
                    if (lineNum != -1) {
                    	if (lines[lineNum] == null) lines[lineNum] = new HashSet<Unit>();
                    	lines[lineNum].add(unit);
                    }
                }

                graph = new BriefUnitGraph(body);
                ParaDependence curAnalysis = new ParaDependence(graph);

                analysis.put(fun.getName(), curAnalysis);
            }
            
            try {
				PrintWriter writer = new PrintWriter("/Users/Vani/Patl4J/log", "UTF-8");
				// for test
	            for (Iterator it = s.getMethods().iterator(); it.hasNext();) {
	            	SootMethod fun = (SootMethod) it.next();
	            	writer.println("@fun: " + fun.getDeclaration());
	            	
	            	String methodName = fun.getName();
	            	Body body = fun.retrieveActiveBody();
	            	
	            	PatchingChain<Unit> u = body.getUnits();
	            	for (Iterator uIt = u.iterator(); uIt.hasNext(); ) {
	            		Unit unit = (Unit) uIt.next();
	            		for (Iterator oIt = u.iterator(); oIt.hasNext(); ) {
	            			Unit unit2 = (Unit) oIt.next();
	            			int line1 = unit.getJavaSourceStartLineNumber();
	            			int line2 = unit2.getJavaSourceStartLineNumber();
	            			if (line1 != -1 && line2 != -1) {
	            				boolean res = isDependent(methodName, line1, line2);
	            				writer.println(unit + " | " + unit2 + " -> " + res);
	            			}
	            		}
	            	}
	            }
	            writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

