package patl4j.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;

import patl4j.java.JavaProject;
import patl4j.java.JavaWorkspace;
import patl4j.tools.ProjectTransformer;
import patl4j.tools.ProjectNormalizer;

public class Patl4JTransformer extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		// Read in all projects with all packages and java files
		JavaWorkspace workspace = new JavaWorkspace(ResourcesPlugin.getWorkspace());
		
		// Loop over the projects
		for (JavaProject project : workspace.getProjects()) {
			//new ProjectPrinter().printJavaProjectInfo(project);
			
			System.out.println("\n[[Transformation Start]] Project: " + project.getIJavaProject().getElementName()); 
			
			System.out.println("[[Normalization Begin]]");
			/* second step: normalize the client program */
			new ProjectNormalizer().normalize(project);
			System.out.println("[[Normlaization End]]");
			
			System.out.println("[[Collect Rules Begin]]");
			/* first step: collect transformation rules */
			ProjectTransformer projectTransformer = new ProjectTransformer(project);
			projectTransformer.printRules();
			System.out.println("[[Collect Rules End]]");
			
			/* third step: create matcher based on the rules */
			projectTransformer.transform();
		}
		
		System.out.println("[[Transformation End]]");
		
		return null;
	}
	
}