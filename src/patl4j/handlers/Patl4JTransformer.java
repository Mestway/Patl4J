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
		
		JavaWorkspace workspace = new JavaWorkspace(ResourcesPlugin.getWorkspace());
		
		// Loop over all projects
		System.out.println("Print eclipse project start ========================== ");
		
		for (JavaProject project : workspace.getProjects()) {
			System.out.println("This is the project name: " + project.getIJavaProject().getElementName()); 
			//new ProjectPrinter().printJavaProjectInfo(project);
			
			PatlOption option = new PatlOption(project);
			
			if (option.ignored == true)
				continue;

			/* second step: normalize the client program */
			new ProjectNormalizer().normalize(project, option);
			
			/* first step: collect transformation rules */
			ProjectTransformer projectTransformer = new ProjectTransformer(project);
			projectTransformer.printRules();
			
			/* thrid step: create matcher based on the rules */
			projectTransformer.transform();
		}
		
		System.out.println("This is the end ============================== ");
		
		return null;
	}
	
}