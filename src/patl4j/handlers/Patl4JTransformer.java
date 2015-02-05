package patl4j.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;

import patl4j.java.JavaProject;
import patl4j.java.JavaWorkspace;
import patl4j.tools.ProjectNormalizer;

public class Patl4JTransformer extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		JavaWorkspace workspace = new JavaWorkspace(ResourcesPlugin.getWorkspace());
		
		// Loop over all projects
		System.out.println("Print eclipse project start ========================== ");
		for (JavaProject project : workspace.getProjects()) {
			//new ProjectPrinter().printJavaProjectInfo(project);
			new ProjectNormalizer().normalize(project);
		}
		System.out.println("This is the end ============================== ");
		
		return null;
	}
}