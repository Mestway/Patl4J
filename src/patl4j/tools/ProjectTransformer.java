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
import org.eclipse.jdt.core.IPackageFragment;
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
	
	public ProjectTransformer(JavaProject project) {
		this.project = project;
		this.option = project.getOption();
		try {
			this.collectPatlRules();
		} catch (JavaModelException e) {
			ErrorManager.error("ProjectTransformer@42", "Rule collecting error");
			e.printStackTrace();
		}
	}
	
	// Collect Patl rules for transformation purpose
	private void collectPatlRules() throws JavaModelException {
		System.out.println("Projectname: " + project.getIJavaProject().getElementName());
		for (Object i : project.getIJavaProject().getNonJavaResources()) {
			System.out.println("LEIME: " + i.getClass());
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
		for (IPackageFragment p : project.getIJavaProject().getPackageFragments()) {
			try {
				for (Object i : p.getNonJavaResources()) {
					System.out.println("LEIME: " + i.getClass());
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
			
			for (JavaFile f : p.getFiles()) {

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
				
				//p.putTheOriginalASTBack(this.option);
				p.generatedTransformedFiles(f.getCU().getElementName(), f.getNormalizedAST().toString(), this.option);
			}
		}
	}
	
	public void printRules() {
		for (Rule i : patlRules) {
			System.out.println(i.toString());
		}
	}
	
}
