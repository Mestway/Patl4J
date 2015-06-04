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
import patl4j.handlers.PatlOption;
import patl4j.java.JavaFile;
import patl4j.java.JavaPackage;
import patl4j.java.JavaProject;
import patl4j.java.codeformatter.DeNormalizer;
import patl4j.patl.ast.Rule;
import patl4j.patl.ast.parser.ParseException;
import patl4j.patl.ast.parser.PatlParser;
import patl4j.util.ErrorManager;

public class ProjectTransformer {
	
	JavaProject project;
	List<Rule> patlRules = new ArrayList<Rule>();
	PatlOption option;
	
	public ProjectTransformer(JavaProject project, PatlOption option) {
		this.option = option;
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
							ErrorManager.error("ProjectTransfomer@line55", "Patl parsing error");
							e.printStackTrace();
						}
					}
				}
			} catch (JavaModelException e) {
				ErrorManager.error("ProjectTransfomer@line61", "Cannot open the package.");
				e.printStackTrace();
			}
		}
	}
	
	// This is the top level transformation call, which will then lead to *method* level transformation
	public void transform() {
		for (JavaPackage p : project.getPackages()) {
			// Check whether the package is ignored
			if (option.packageIgnored(p.getIPackageFrag().getElementName()))
				continue;
			
			for (JavaFile f : p.getFiles()) {
				// Check whether the file is ignored in the option file
				if (option.fileIgnored(f.getCU().getElementName()))
					continue;

				TransformationVisitor tv = new TransformationVisitor(patlRules, f.getNormalizedAST(), this.option);
				f.getNormalizedAST().accept(tv);
				
				// Perform 
				DeNormalizer deNomalizer = new DeNormalizer(f.getNormalizedAST());
				f.getNormalizedAST().accept(deNomalizer);
				deNomalizer.setSecondRound();
				f.getNormalizedAST().accept(deNomalizer);
				
				System.out.println(">>>>>>>");
				System.out.println(f.getNormalizedAST());
				// Generate the transformed body in the package
				p.generatedTransformedFiles(f.getCU().getElementName(), f.getNormalizedAST().toString());
			}
		}
	}
	
	public void printRules() {
		for (Rule i : patlRules) {
			System.out.println(i.toString());
		}
	}
	
}
