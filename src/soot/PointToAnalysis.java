package soot;

import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;
import soot.jimple.spark.SparkTransformer;
import soot.jimple.spark.pag.Node;
import soot.jimple.spark.sets.DoublePointsToSet;
import soot.jimple.spark.sets.EmptyPointsToSet;
import soot.jimple.spark.sets.HashPointsToSet;
import soot.jimple.spark.sets.P2SetVisitor;
import soot.util.Chain;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.*;
import java.util.List;

/**
 * Created by Vani on 1/30/15.
 */
public class PointToAnalysis {

    private static SootClass loadClass(String name, boolean main) {
        SootClass c = Scene.v().loadClassAndSupport(name);
        c.setApplicationClass();
        if(main) Scene.v().setMainClass(c);
        return c;
    }

    public static void main(String[] args) {
        List<String> sootArgs = new LinkedList(Arrays.asList(args));

        //enable whole program mode
        sootArgs.add("-W");
        sootArgs.add("-p");
        sootArgs.add("wjop");
        sootArgs.add("enabled:true");

        //enable points-to analysis
        sootArgs.add("-p");
        sootArgs.add("cg");
        sootArgs.add("enabled:true");

        //enable Spark
        sootArgs.add("-p");
        sootArgs.add("cg.spark");
        sootArgs.add("enabled:true");

        sootArgs.add("soot.test.BinarySearchTree");

        String[] argsArray = sootArgs.toArray(new String[0]);

        PackManager.v().getPack("wjop").add(new Transform("wjop.MyTransform",new MyTransform()));
        soot.Main.main(argsArray);
    }
}

class MyTransform extends SceneTransformer {

    protected void internalTransform(String phaseName, Map _options) {
        //set the PointsToAnalysis with phase options
        HashMap options = new HashMap();
        options.put("verbose", "true");
        options.put("propagator", "worklist");
        options.put("simple-edges-bidirectional", "false");
        options.put("on-fly-cg", "true");
        options.put("set-impl", "hash");
        options.put("double-set-old", "hybrid");
        options.put("double-set-new", "hybrid");
        options.put("dump-html", "true");
        SparkTransformer.v().transform("",options);

        PointsToAnalysis tmp = Scene.v().getPointsToAnalysis();

        Chain<SootClass> classes = Scene.v().getApplicationClasses();
        for (SootClass sootClass: classes) {
            for (SootMethod sootMethod: sootClass.getMethods()) {
                System.out.println(sootMethod);
                System.out.println(sootMethod.retrieveActiveBody());
                System.out.println();

                for (Local local: sootMethod.retrieveActiveBody().getLocals()) {
                    PointsToSet s = tmp.reachingObjects(local);
                    System.out.println(local);
                    if (!(s instanceof EmptyPointsToSet)) {
                        ((DoublePointsToSet) s).forall(new MyPointSetVisitor());
                    }
                }
                System.out.println();

                for (Local sootLocal1: sootMethod.retrieveActiveBody().getLocals()) {
                    PointsToSet s1 = tmp.reachingObjects(sootLocal1);
                    if (!(s1 instanceof EmptyPointsToSet)) {
                        for (Local sootLocal2 : sootMethod.retrieveActiveBody().getLocals()) {
                            PointsToSet s2 = tmp.reachingObjects(sootLocal2);
                            if (s1.hasNonEmptyIntersection(s2)) {
                                System.out.println(sootLocal1 + "  &&  " + sootLocal2);
                            }
                        }
                    }
                }
            }
        }
    }
}

class MyPointSetVisitor extends P2SetVisitor {
    public void visit(Node v) {
        System.out.println("    " + v);
    }
}
