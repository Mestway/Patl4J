/**
 * This is the main class used for the whole transformation process, 
 * containing the following steps:
 * 	1) Normalize: use the ProjectNormalizer
 * 	2) Read in Patl files: and store them in a list
 * 	... (TODO)
 */

package patl4j.tools;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.JavaModelException;

import patl4j.core.transformer.TransformationVisitor;
import patl4j.java.JavaFile;
import patl4j.java.JavaPackage;
import patl4j.java.JavaProject;
import patl4j.patl.ast.Rule;
import patl4j.patl.ast.parser.ParseException;
import patl4j.patl.ast.parser.PatlParser;
import patl4j.util.ErrorManager;

public class ProjectTransformer {
	
	JavaProject project;
	List<Rule> patlRules = new ArrayList<Rule>();
	
	public ProjectTransformer(JavaProject project) {
		this.project = project;
		this.collectPatlRules();
	}
	
	// Collect Patl rules for transformation purpose
	private void collectPatlRules() {
		for (JavaPackage p : project.getPackages()) {
			try {
				for (Object i : p.getIPackageFrag().getNonJavaResources()) {
					if (i instanceof IFile) {
						try {
							if (!((IFile)i).getName().endsWith(".patl")) {
								continue;
							}
							List<Rule> ruleList = new PatlParser(((IFile) i).getContents()).Pi();
				            for (Rule k : ruleList) {
				            	patlRules.add(k);
				            }
						} catch (CoreException | ParseException e) {
							ErrorManager.error("Patl parsing error");
							e.printStackTrace();
						}
					}
				}
			} catch (JavaModelException e) {
				ErrorManager.error("Cannot open package error");
				e.printStackTrace();
			}
		}
	}
	
	// This is the top level transformation call, which will then lead to *method* level transformation
	public void transform() {
		for (JavaPackage p : project.getPackages()) {
			for (JavaFile f : p.getFiles()) {
				TransformationVisitor tv = new TransformationVisitor(patlRules, f.getNormlizedAST());
				f.getNormlizedAST().accept(tv);
			}
		}
	}
	
	public void printRules() {
		for (Rule i : patlRules) {
			System.out.println(i.toString());
		}
	}
	
}
