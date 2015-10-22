package patl4j.handlers;

import java.io.PrintStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import patl4j.java.JavaProject;
import patl4j.java.JavaWorkspace;
import patl4j.tools.Console;
import patl4j.tools.ProjectNormalizer;
import patl4j.tools.ProjectTransformer;

public class Patl4JTransformer extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		// Set output to log files
		MessageConsole console = Console.findConsole("Patl4J:console");
		MessageConsoleStream stream = console.newMessageStream();
		System.setErr(new PrintStream(stream));
		System.setOut(new PrintStream(stream));
        
		System.out.println("=============Excuting Patl4JTransformer===============");
		
		// Read in all projects with all packages and java files
		JavaWorkspace workspace = new JavaWorkspace(ResourcesPlugin.getWorkspace());
		
		// Loop over the projects
		for (JavaProject project : workspace.getProjects()) {
			//new ProjectPrinter().printJavaProjectInfo(project);
			
			new ProjectNormalizer().normalize(project);
			
			System.out.println("[Transformation Start] Project: " + project.getIJavaProject().getElementName()); 
			
			System.out.println("[[Collect Rules Begin]]");
			/* first step: collect transformation rules */
			ProjectTransformer projectTransformer = new ProjectTransformer(project);
			projectTransformer.printRules();
			System.out.println("[[Collect Rules End]]");
			
			/* third step: create matcher based on the rules */
			projectTransformer.transform();
			
			System.out.println("[Transformation Finish] Project: " + project.getIJavaProject().getElementName()); 
		}
		
		System.out.println("=============Finish Patl4JTransformer===============");
		
		return null;
	}
}